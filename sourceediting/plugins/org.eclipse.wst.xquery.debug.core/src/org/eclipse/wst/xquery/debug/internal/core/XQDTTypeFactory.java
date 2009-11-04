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
package org.eclipse.wst.xquery.debug.internal.core;

import org.eclipse.dltk.debug.core.model.ArrayScriptType;
import org.eclipse.dltk.debug.core.model.AtomicScriptType;
import org.eclipse.dltk.debug.core.model.ComplexScriptType;
import org.eclipse.dltk.debug.core.model.HashScriptType;
import org.eclipse.dltk.debug.core.model.IScriptType;
import org.eclipse.dltk.debug.core.model.IScriptTypeFactory;
import org.eclipse.dltk.debug.core.model.StringScriptType;

public class XQDTTypeFactory implements IScriptTypeFactory {

    private static final String[] simpleTypes = { "string", "boolean", "decimal", "float", "double", "duration",
            "dateTime", "time", "date", "gYearMonth", "gYear", "gMonthDay", "gDay", "gMonth", "hexBinary",
            "base64Binary", "anyURI", "QName", "NOTATION" };

    public IScriptType buildType(String type) {
        if (STRING.equalsIgnoreCase(type)) {
            return new StringScriptType(type);
        }
        if (type.endsWith("*") || type.endsWith("+")) {
            return new ArrayScriptType();
        }
        if (HASH.equals(type)) {
            return new HashScriptType();
        }
        for (int i = 0; i < simpleTypes.length; ++i) {
            if (simpleTypes[i].equals(type)) {
                return new AtomicScriptType(type);
            }
        }
        return new ComplexScriptType(type);
    }

}
