/*******************************************************************************
\ * Copyright (c) 2008 xored software, Inc.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     xored software, Inc. - initial API and Implementation (Alex Panchenko)
 *     Gabriel Petrovay (28msec) - Adjusted to avoid cache retrieval for full builds
 *******************************************************************************/
package org.eclipse.wst.xquery.internal.core.builder;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.dltk.ast.parser.IModuleDeclaration;
import org.eclipse.dltk.ast.parser.ISourceParser;
import org.eclipse.dltk.compiler.env.ModuleSource;
import org.eclipse.dltk.compiler.problem.ProblemCollector;
import org.eclipse.dltk.core.DLTKLanguageManager;
import org.eclipse.dltk.core.IScriptProject;
import org.eclipse.dltk.core.ISourceModuleInfoCache.ISourceModuleInfo;
import org.eclipse.dltk.core.SourceParserUtil;
import org.eclipse.dltk.core.builder.AbstractBuildParticipantType;
import org.eclipse.dltk.core.builder.IBuildContext;
import org.eclipse.dltk.core.builder.IBuildParticipant;
import org.eclipse.dltk.internal.core.ModelManager;

@SuppressWarnings("restriction")
public class XQDTParserBuildParticipantFactory extends AbstractBuildParticipantType implements IExecutableExtension {

    private static class XQDTParserBuildParticipant implements IBuildParticipant {

        private final ISourceParser parser;

        public XQDTParserBuildParticipant(ISourceParser parser) {
            this.parser = parser;
        }

        public void build(IBuildContext context) throws CoreException {
            IModuleDeclaration moduleDeclaration = (IModuleDeclaration)context
                    .get(IBuildContext.ATTR_MODULE_DECLARATION);
            if (moduleDeclaration != null) {
                // do nothing if already have AST - optimization for reconcile
                return;
            }
            // get cache entry
            final ISourceModuleInfo cacheEntry = ModelManager.getModelManager().getSourceModuleInfoCache()
                    .get(context.getSourceModule());

            if (context.getBuildType() == IBuildContext.RECONCILE_BUILD) {
                // check if there is cached AST
                moduleDeclaration = SourceParserUtil.getModuleFromCache(cacheEntry, context.getProblemReporter());
                if (moduleDeclaration != null) {
                    // use AST from cache
                    context.set(IBuildContext.ATTR_MODULE_DECLARATION, moduleDeclaration);
                    return;
                }
            }

            // create problem collector
            final ProblemCollector problemCollector = new ProblemCollector();
            // parse
            moduleDeclaration = parser.parse(new ModuleSource(context.getFile().getFullPath().toPortableString(),
                    context.getSourceContents()), context.getProblemReporter());
            // put result to the cache
            SourceParserUtil.putModuleToCache(cacheEntry, moduleDeclaration, problemCollector);
            // report errors to the build context
            problemCollector.copyTo(context.getProblemReporter());
        }
    }

    private String natureId = null;

    public IBuildParticipant createBuildParticipant(IScriptProject project) throws CoreException {
        if (natureId != null) {
            final ISourceParser parser = DLTKLanguageManager.getSourceParser(natureId);
            if (parser != null) {

                return new XQDTParserBuildParticipant(parser);
            }
        }
        return null;
    }

    public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
            throws CoreException {
        natureId = config.getAttribute("nature"); //$NON-NLS-1$
    }

}
