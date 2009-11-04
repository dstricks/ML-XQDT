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
package org.eclipse.wst.xquery.internal.launching.zorba;

import java.util.Map;

import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.dltk.launching.IInterpreterInstallType;
import org.eclipse.dltk.launching.IInterpreterRunner;
import org.eclipse.wst.xquery.core.semantic.ISemanticValidator;
import org.eclipse.wst.xquery.internal.launching.XQDTInterpreterInstall;
import org.eclipse.wst.xquery.launching.ISemanticValidatingInterpreterInstall;

public class ZorbaInstall extends XQDTInterpreterInstall implements ISemanticValidatingInterpreterInstall {

    private ZorbaBuiltinsHelper helper = new ZorbaBuiltinsHelper();

    public ZorbaInstall(IInterpreterInstallType type, String id) {
        super(type, id);
    }

    @Override
    public String getBuiltinModuleContent(String name) {
        final Map<String, String> sources = helper.getSources();
        return sources.get(name);
    }

    @Override
    public String[] getBuiltinModules() {
        final Map<String, String> sources = helper.getSources();
        return sources.keySet().toArray(new String[sources.size()]);
    }

    @Override
    public IInterpreterRunner getInterpreterRunner(String mode) {
        IInterpreterRunner runner = super.getInterpreterRunner(mode);

        if (runner != null) {
            return runner;
        }

        if (mode.equals(ILaunchManager.RUN_MODE)) {
            return new ZorbaRunner(this);
        }

        return null;
    }

    public ISemanticValidator getSemanticValidator() {
        return new ZorbaSemanticValidator(this);
    }
}