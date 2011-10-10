/*******************************************************************************
 * Copyright (c) 2008, 2009 28msec Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Gabriel Petrovay (28msec) - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xquery.set.core.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.xquery.set.core.ISETCoreConstants;
import org.eclipse.wst.xquery.set.core.SETCorePlugin;
import org.eclipse.wst.xquery.set.core.SETProjectConfig;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class SETProjectConfigUtil {

    public static SETProjectConfig readProjectConfig(IProject project) {
        if (project == null || !project.exists() || !project.isOpen()) {
            return null;
        }
        try {
            IPath configPath = new Path(ISETCoreConstants.PROJECT_DIRECTORY_CONFIG)
                    .append(ISETCoreConstants.PROJECT_FILE_CONFIG);
            IFile f = project.getFile(configPath);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(f.getLocationURI().toString());

            Element rootElement = document.getDocumentElement();

            String uriStr = null;
            String version = null;
            String apiVersion = null;
            String startPage = null;

            Node child = rootElement.getFirstChild();
            do {
                if (child.getNodeType() != Document.ELEMENT_NODE) {
                    continue;
                }

                if (child.getNodeName().equals("project_uri")) {
                    uriStr = child.getTextContent();
                } else if (child.getNodeName().equals("start_page")) {
                    startPage = child.getTextContent();
                } else if (child.getNodeName().equals("version")) {
                    version = child.getTextContent();
                } else if (child.getNodeName().equals("api_version")) {
                    apiVersion = child.getTextContent();
                }

            } while ((child = child.getNextSibling()) != null);

            if (startPage == null || startPage.trim().length() == 0) {
                startPage = "";
            }
            if (version == null || version.trim().length() == 0) {
                version = "1.0";
            }
            if (apiVersion == null || apiVersion.trim().length() == 0) {
                apiVersion = "";
            }

            return new SETProjectConfig(new URI(uriStr), startPage, version, apiVersion);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            IStatus status = new Status(IStatus.ERROR, SETCorePlugin.PLUGIN_ID,
                    "An error ocured while trying to read the project sausalito.xml configuration.", se);
            SETCorePlugin.getDefault().getLog().log(status);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeProjectConfig(IProject project, SETProjectConfig config) {
        DocumentBuilder builder;

        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return;
        }

        Document document = builder.newDocument();

        Element root = document.createElement("sausalito");

        Element uri = document.createElement("project_uri");
        uri.setTextContent(config.getLogicalUri().toString());
        Element version = document.createElement("version");
        version.setTextContent(config.getVersion());
        String apiVersionStr = config.getApiVersion();
        if (apiVersionStr != null && apiVersionStr.equals("")) {
            Element apiVersion = document.createElement("api_version");
            apiVersion.setTextContent(apiVersionStr);
        }

        root.appendChild(uri);
        root.appendChild(version);

        if (config.getStartPage() != null) {
            Element page = document.createElement("start_page");
            page.setTextContent(config.getStartPage());
            root.appendChild(page);

        }

        document.appendChild(root);

        try {
            IFile f = project.getFile(".config/sausalito.xml");

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(document);
            FileOutputStream fos = new FileOutputStream(new Path(f.getLocationURI().getPath()).toOSString());
            StreamResult result = new StreamResult(fos);
            transformer.transform(source, result);
            fos.close();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
