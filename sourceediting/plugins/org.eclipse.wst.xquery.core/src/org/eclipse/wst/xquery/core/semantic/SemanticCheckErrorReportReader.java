/*******************************************************************************
 * Copyright (c) 2008 28msec Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Gabriel Petrovay (28msec) - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xquery.core.semantic;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.core.IBuffer;
import org.eclipse.dltk.core.IModelElement;
import org.eclipse.dltk.core.ISourceModule;
import org.eclipse.dltk.core.ModelException;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.wst.xquery.core.XQDTCorePlugin;
import org.eclipse.wst.xquery.core.utils.PathUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SemanticCheckErrorReportReader {

    protected String fData;
    protected ISourceModule fModule;

    public SemanticCheckErrorReportReader(ISourceModule module, String data) {
        fData = data;
        fModule = module;
    }

    public List<SemanticCheckError> getErrors() {
        if (fData == null || fData.length() == 0) {
            return null;
        }

        List<SemanticCheckError> list = new ArrayList<SemanticCheckError>();

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(fData.getBytes()));

            Element rootElement = document.getDocumentElement();

            NodeList errors = rootElement.getElementsByTagName("error");
            for (int i = 0; i < errors.getLength(); i++) {
                Element error = (Element)errors.item(i);
                SemanticCheckError semErr = readError(error);
                if (semErr != null) {
                    list.add(semErr);
                }
            }

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            Status status = new Status(IStatus.ERROR, XQDTCorePlugin.PLUGIN_ID,
                    "The interpreter returned an invalid XML error report during a semantic check validation.", se);
            XQDTCorePlugin.getDefault().getLog().log(status);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    private SemanticCheckError readError(Element error) {

        String errorCode = "", description = "";
        IResource moduleFile = null;
        int lineStart = -1, lineEnd = -1, columnStart = -1, columnEnd = -1;
        IDocument document = null;

        errorCode = error.getAttribute("code");

        Node child = error.getFirstChild();

        do {
            if (child.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            if (child.getNodeName().equals("location")) {
                Element loc = (Element)child;
                String module = loc.getAttribute("module");
                if (module.startsWith("file://")) {
                    try {
                        URL url = new URL(module);
                        module = URLDecoder.decode(url.getFile(), "UTF-8");
                    } catch (UnsupportedEncodingException uee) {
                        uee.printStackTrace();
                    } catch (MalformedURLException mue) {
                        mue.printStackTrace();
                    }
                }
                IPath fullPath = new Path(module);
                IPath path = PathUtil.makePathRelativeTo(fullPath, fModule.getScriptProject().getResource()
                        .getLocation());
                path = fModule.getScriptProject().getProject().getFullPath().append(path);
                moduleFile = ResourcesPlugin.getWorkspace().getRoot().findMember(path);
                IModelElement element = DLTKCore.create(moduleFile);
                if (element instanceof ISourceModule) {
                    ISourceModule targetModule = (ISourceModule)element;
                    try {
                        IBuffer buffer = targetModule.getBuffer();
                        document = new org.eclipse.jface.text.Document(buffer.getContents());
                    } catch (ModelException e) {
                    }
                }

                if (document == null) {
                    return null;
                }

                try {
                    lineStart = Integer.parseInt(loc.getAttribute("lineStart"));
                } catch (NumberFormatException nf) {
                }
                try {
                    lineEnd = Integer.parseInt(loc.getAttribute("lineEnd"));
                } catch (NumberFormatException nf) {
                }
                try {
                    columnStart = Integer.parseInt(loc.getAttribute("columnStart"));
                } catch (NumberFormatException nf) {
                }
                try {
                    columnEnd = Integer.parseInt(loc.getAttribute("columnEnd"));
                } catch (NumberFormatException nf) {
                }

            } else if (child.getNodeName().equals("description")) {
                description = child.getTextContent();
            }

        } while ((child = child.getNextSibling()) != null);

        try {
            int sourceStart = document.getLineOffset(lineStart - 1) + columnStart - 1;
            int sourceEnd = document.getLineOffset(lineEnd - 1) + columnEnd - 1;
            if (sourceEnd == sourceStart) {
                sourceEnd++;
            }
            return new SemanticCheckError(moduleFile, errorCode, description, lineStart - 1, sourceStart, sourceEnd);
        } catch (BadLocationException ble) {
            return null;
        }
    }

}