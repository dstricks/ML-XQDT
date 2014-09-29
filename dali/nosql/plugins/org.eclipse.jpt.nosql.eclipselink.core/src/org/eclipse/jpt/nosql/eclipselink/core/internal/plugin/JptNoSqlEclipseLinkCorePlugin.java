/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.nosql.eclipselink.core.internal.plugin;

import org.eclipse.jpt.common.core.internal.utility.JptPlugin;

public class JptNoSqlEclipseLinkCorePlugin
	extends JptPlugin
{
	// ********** singleton **********

	private static volatile JptNoSqlEclipseLinkCorePlugin INSTANCE;

	/**
	 * Return the singleton Dali EclipseLink core plug-in.
	 */
	public static JptNoSqlEclipseLinkCorePlugin instance() {
		return INSTANCE;
	}	


	// ********** Dali plug-in **********

	public JptNoSqlEclipseLinkCorePlugin() {
		super();
	}

	@Override
	protected void setInstance(JptPlugin plugin) {
		INSTANCE = (JptNoSqlEclipseLinkCorePlugin) plugin;
	}
}