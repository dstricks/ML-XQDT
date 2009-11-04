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
package org.eclipse.wst.xquery.debug.debugger.zorba;

import org.eclipse.dltk.compiler.util.Util;
import org.eclipse.jface.preference.IPreferenceStore;

public final class ZorbaDebuggerConstants {

    public static final String ENGINE_ID = "org.eclipse.wst.xquery.debug.engines.zorba";

    public static final String LOG_ENABLE_KEY = "debugging_engine_log_enable";
    public static final String LOG_FILE_NAME = "log_file_name";

    public static final String DEBUGGING_ENGINE_NEEDS_DBGP_TRANSLATOR = "debugging_engine_needs_dbgp_translator";
    public static final String DEBUGGING_ENGINE_SERVER_PORTS = "debugging_engine_server_ports";

    public static final int SERVER_COMMAND_PORT = 28028;
    public static final int SERVER_EVENT_PORT = 28029;

    public static void initalizeDefaults(IPreferenceStore store) {
        store.setDefault(LOG_ENABLE_KEY, Util.EMPTY_STRING);
        store.setDefault(LOG_FILE_NAME, Util.EMPTY_STRING);
        store.setDefault(DEBUGGING_ENGINE_NEEDS_DBGP_TRANSLATOR, true);
        store.setDefault(DEBUGGING_ENGINE_SERVER_PORTS, SERVER_COMMAND_PORT + ":" + SERVER_EVENT_PORT);
    }

    private ZorbaDebuggerConstants() {
        // private constructor
    }
}
