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
package org.eclipse.wst.xquery.set.launching.deploy;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.dltk.core.IScriptProject;

public class DeployManager {

    private static DeployManager instance;

    private Map<IScriptProject, DeployInfo> fProjectDeploys = new HashMap<IScriptProject, DeployInfo>();

    private DeployManager() {
    }

    public static DeployManager getInstance() {
        if (instance == null) {
            instance = new DeployManager();
        }
        return instance;
    }

//    public DeployInfo getDeployer(DeployInfo info, boolean useCache) {
//        DeployInfo info = fProjectDeploys.get(info.getProject());
//
//        if (deployer != null) {
//            if (useCache) {
//                deployer.setDeployInfo(info);
//            } else {
//                fProjectDeployers.remove(info.getProject());
//            }
//            deployer.initJobs();
//        } else {
//            deployer = new DeployerJob(info);
//            if (useCache) {
//                fProjectDeployers.put(info.getProject(), deployer);
//            }
//        }
//
//        return deployer;
//    }

    public void cacheDeployInfo(DeployInfo info) {
        fProjectDeploys.put(info.getProject(), info);
    }

    public DeployInfo getCachedDeployInfo(IScriptProject project) {
        return fProjectDeploys.get(project);
    }
}
