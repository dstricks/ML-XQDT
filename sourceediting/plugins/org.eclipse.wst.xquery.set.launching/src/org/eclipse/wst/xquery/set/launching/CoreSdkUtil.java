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
package org.eclipse.wst.xquery.set.launching;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.dltk.core.DLTKCore;
import org.eclipse.dltk.launching.IInterpreterInstall;
import org.eclipse.dltk.launching.ScriptRuntime;
import org.eclipse.dltk.launching.ScriptRuntime.DefaultInterpreterEntry;
import org.eclipse.wst.xquery.set.core.SETNature;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class CoreSdkUtil {

    public static String getCoreSdkApiVersion(IProject project) throws CoreException {
        IInterpreterInstall install = ScriptRuntime.getInterpreterInstall(DLTKCore.create(project));
        if (install == null) {
            throw new CoreException(new Status(IStatus.ERROR, SETLaunchingPlugin.PLUGIN_ID,
                    "Sausalito CoreSDK is not properly set up for project: " + project.getName()));
        }

        IPath sausalitoConfigTemplatePath = install.getInstallLocation().getPath().removeLastSegments(2)
                .append("templates").append("config").append("sausalito.xml");

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(sausalitoConfigTemplatePath.toOSString());

            Element rootElement = document.getDocumentElement();

            Node child = rootElement.getFirstChild();
            do {
                if (child.getNodeType() != Document.ELEMENT_NODE) {
                    continue;
                }
                if (child.getNodeName().equals("api_version")) {
                    return child.getTextContent();
                }

            } while ((child = child.getNextSibling()) != null);

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IPath getCoreSDKScriptPath(IProject project) throws CoreException {
        IInterpreterInstall install = ScriptRuntime.getInterpreterInstall(DLTKCore.create(project));
        if (install == null) {
            throw new CoreException(new Status(IStatus.ERROR, SETLaunchingPlugin.PLUGIN_ID,
                    "Sausalito CoreSDK is not properly set up for project: " + project.getName()));
        }
        return install.getInstallLocation().getPath();
    }

    public static IPath getProjectCoreSDKInstallationPath(IProject project) throws CoreException {
        return getCoreSDKScriptPath(project).removeLastSegments(2);
    }

    public static IPath getKillCommandPath(IProject project) throws CoreException {
        if (Platform.getOS().equals(Platform.OS_WIN32)) {
            return getProjectCoreSDKInstallationPath(project).append(
                    ISETLaunchingConstants.SAUSALITO_EXECUTABLE_DIRECTORY).append("term.exe");
        }
        return new Path("kill");
    }

    public static IPath getDefaultCoreSDKInstallationPath() throws CoreException {
        DefaultInterpreterEntry[] entries = ScriptRuntime.getDefaultInterpreterIDs();
        for (DefaultInterpreterEntry entry : entries) {
            if (entry.getNature().equals(SETNature.NATURE_ID)) {
                IInterpreterInstall install = ScriptRuntime.getDefaultInterpreterInstall(entry);
                if (install == null) {
                    return null;
                }
                return install.getInstallLocation().getPath().removeLastSegments(2);
            }
        }
        return null;
    }

}