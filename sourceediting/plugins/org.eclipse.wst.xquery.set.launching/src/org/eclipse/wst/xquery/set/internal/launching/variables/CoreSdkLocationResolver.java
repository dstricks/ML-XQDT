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
package org.eclipse.wst.xquery.set.internal.launching.variables;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.variables.IDynamicVariable;
import org.eclipse.core.variables.IDynamicVariableResolver;
import org.eclipse.wst.xquery.launching.XQDTLaunchingPlugin;
import org.eclipse.wst.xquery.set.launching.SETLaunchingPlugin;
import org.osgi.framework.Bundle;

public class CoreSdkLocationResolver implements IDynamicVariableResolver {

    public static final String VARIABLE = "sdk_location";
    private static final String WIN_DIR_NAME_PREFIX = "Sausalito-CoreSDK";

    public String resolveValue(IDynamicVariable variable, String argument) throws CoreException {
        String result = findStrategies();

        return result;
    }

    private String findStrategies() {
        String result = null;

        // I. first search for the shipped Sausalito CoreSDK
        // this case happens in the 28msec distribution of the plugins
        result = findShippedCoreSDK();
        if (result != null) {
            return result;
        }
        if (XQDTLaunchingPlugin.DEBUG_AUTOMATIC_PROCESSOR_DETECTION) {
            log(IStatus.INFO, "No shipped Sausalito CoreSDK was found.", null);
        }

        // II. if no CoreSDK is shipped (for some platforms)
        // go and search in some predefined install locations
        result = findInstalledCoreSDK();
        if (result != null) {
            return result;
        }
        if (XQDTLaunchingPlugin.DEBUG_AUTOMATIC_PROCESSOR_DETECTION) {
            log(IStatus.INFO, "No installed Sausalito CoreSDK was found.", null);
        }

        return result;
    }

    private String findShippedCoreSDK() {
        String os = Platform.getOS();

        String osPart = "." + os;
        String archPart = "";

        // in case of non-Windows platforms we make have more versions of the CoreSDK
        if (!os.equals(Platform.OS_WIN32)) {
            archPart = "." + Platform.getOSArch();
        }

        String pluginID = "com.28msec.sausalito";
        String fragment = pluginID + osPart + archPart;

        Bundle[] bundles = Platform.getBundles(fragment, null);
        if (bundles == null || bundles.length == 0) {
            if (XQDTLaunchingPlugin.DEBUG_AUTOMATIC_PROCESSOR_DETECTION) {
                log(IStatus.INFO, "Could not find plug-in fragment: " + fragment
                        + ". No default Sausalito CoreSDK will be configured.", null);
            }
            return null;
        }
        if (XQDTLaunchingPlugin.DEBUG_AUTOMATIC_PROCESSOR_DETECTION) {
            log(IStatus.INFO, "Found Sausalito CoreSDK plug-in fragment: " + fragment, null);
        }

        Bundle bundle = bundles[0];
        URL coreSdkUrl = FileLocator.find(bundle, new Path("coresdk"), null);
        if (coreSdkUrl == null) {
            if (XQDTLaunchingPlugin.DEBUG_AUTOMATIC_PROCESSOR_DETECTION) {
                log(IStatus.INFO, "Could not find the \"coresdk\" directory in plug-in fragment: " + fragment
                        + ". No default Sausalito CoreSDK will be configured.", null);
            }
            return null;
        }
        try {
            coreSdkUrl = FileLocator.toFileURL(coreSdkUrl);
        } catch (IOException ioe) {
            log(IStatus.ERROR, "Cound not retrieve the Eclipse bundle location: " + fragment, ioe);
            return null;
        }

        IPath coreSdkPath = new Path(coreSdkUrl.getPath());

        if (isScriptIn(coreSdkPath)) {
            return coreSdkPath.toOSString();
        }

        return null;
    }

    private String findInstalledCoreSDK() {
        String os = Platform.getOS();
        IPath possiblePath = null;
        if (os.equals(Platform.OS_WIN32)) {
            String programFiles = System.getenv("ProgramFiles");
            if (programFiles == null) {
                return null;
            }
            possiblePath = getLatestFromProgramFiles(new File(programFiles));
        } else {
            possiblePath = new Path("/opt/sausalito");
        }

        if (isScriptIn(possiblePath)) {
            return possiblePath.toOSString();
        }

        return null;
    }

    private IPath getLatestFromProgramFiles(File programFiles) {
        IPath result = null;

        try {
            String[] programs = programFiles.list();

            List<String> candidates = new ArrayList<String>();
            for (String program : programs) {
                if (program.startsWith(WIN_DIR_NAME_PREFIX)) {
                    candidates.add(program);
                }
            }
            if (candidates.size() == 0) {
                result = null;
            } else if (candidates.size() == 1) {
                result = new Path(programFiles.getPath()).append(candidates.get(0));
            } else {
                String candidate = candidates.get(0);
                for (String newCandidate : candidates) {
                    if (candidate.compareTo(newCandidate) < 0) {
                        candidate = newCandidate;
                    }
                }
                result = new Path(programFiles.getAbsolutePath()).append(candidate);
            }

        } catch (Exception e) {
        }

        return result;
    }

    private boolean isScriptIn(IPath coreSdkPath) {
        if (coreSdkPath == null) {
            NullPointerException npe = new NullPointerException();
            log(IStatus.ERROR, "Could not locate the Sausalito script.", npe);
            return false;
        }

        IPath scriptPath = coreSdkPath.append("bin").append("sausalito");
        if (Platform.getOS().equals(Platform.OS_WIN32)) {
            scriptPath = scriptPath.addFileExtension("bat");
        }

        File scrptFile = scriptPath.toFile();
        if (!scrptFile.exists()) {
            log(IStatus.ERROR, "Could not find the Sausalito script at location: " + coreSdkPath.toOSString(), null);
            return false;
        }

        return true;
    }

    public static IStatus log(int severity, String message, Throwable t) {
        Status status = new Status(severity, SETLaunchingPlugin.PLUGIN_ID, message, t);
        SETLaunchingPlugin.log(status);
        return status;
    }

}
