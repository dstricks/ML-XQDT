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
package org.eclipse.wst.xquery.set.internal.launching.jobs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.wst.xquery.set.core.ISETCoreConstants;
import org.eclipse.wst.xquery.set.launching.SETLaunchingPlugin;

public class SETImportDataJob extends AbstractSETCoreSDKCommandJob {

    public static final String MESSAGE_NOTHING_TO_DO = "The bulkloader had nothing to do";
    private static final String MESSAGE_NOTHING_TO_DO_FORMAT = MESSAGE_NOTHING_TO_DO + " for project \"%1$s\". %2$s";
    private static String MESSAGE_NO_XQ_FILES = "No *.xq query files were found in the \""
            + ISETCoreConstants.PROJECT_DIRECTORY_BULKLOAD + "\" directory.";
    private static String MESSAGE_NO_BULKLOAD_DIRECTORY = "No \"" + ISETCoreConstants.PROJECT_DIRECTORY_BULKLOAD
            + "\" directory was found in the project root.";

    private String fProblemMessage = "";

    public SETImportDataJob(IProject project) {
        super("Bulkloading project " + "\"" + project.getName() + "\"...", project);
    }

    protected List<String> getCommandParameters() {
        List<String> params = new ArrayList<String>();
        params.add("import");
        params.add("data");
        params.add("-d");
        params.add(fProject.getLocation().toOSString());

        return params;
    }

    protected String getCommandConsleLabel() {
        return "Bulkload data";
    }

    @Override
    protected int getJobTaskSize() {
        final IFolder folder = fProject.getFolder(ISETCoreConstants.PROJECT_DIRECTORY_BULKLOAD);
        if (!folder.exists()) {
            fProblemMessage = MESSAGE_NO_BULKLOAD_DIRECTORY;
            return 0;
        }

        final Set<String> names = new HashSet<String>();

        try {
            folder.accept(new IResourceVisitor() {
                public boolean visit(IResource resource) throws CoreException {
                    if (resource.getType() == IResource.FILE) {
                        IPath path = resource.getFullPath();
                        String ext = path.getFileExtension();
                        if (ext.equals("xq")) {
                            names.add(path.removeFileExtension().removeFirstSegments(4).toPortableString());
                        }
                        return false;
                    }
                    return true;
                }
            }, IResource.DEPTH_INFINITE, IResource.FILE);
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        int count = names.size();
        if (count == 0) {
            fProblemMessage = MESSAGE_NO_XQ_FILES;
        }

        return count;
    }

    @Override
    protected String getJobTaskName() {
        return "Importing data for project: " + fProject.getName();
    }

    @Override
    protected void readCommandOutput(InputStream inputStream) throws IOException {
        OutputStream output = getOutputStream();

        BufferedReader ir = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = ir.readLine()) != null) {
            if (line.startsWith("Importing file ")) {
                updateMonitorTaskName(line);
            } else if (line.startsWith("  done") || line.startsWith("  failed")) {
                updateMonitorWork(1);
            }
            if (output != null) {
                output.write((line + "\n").getBytes());
            }
        }

    }

    @Override
    protected IStatus handleNoTicks() {
        final String message = String.format(MESSAGE_NOTHING_TO_DO_FORMAT, fProject.getName(), fProblemMessage);
        return new Status(IStatus.WARNING, SETLaunchingPlugin.PLUGIN_ID, message);
    }

    @Override
    protected boolean needsResourceRefresh() {
        return true;
    }

    @Override
    protected void refresh(IProgressMonitor monitor) {
        IPath path = new org.eclipse.core.runtime.Path(ISETCoreConstants.PROJECT_DIRECTORY_TEST);
        try {
            fProject.getFolder(path).refreshLocal(IResource.DEPTH_INFINITE, monitor);
        } catch (CoreException e) {
            // nothing to do
        }
    }

}
