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
package org.eclipse.wst.xquery.core.model.ast;

import org.eclipse.dltk.ast.declarations.Argument;
import org.eclipse.dltk.ast.references.SimpleReference;

public class XQueryParam extends Argument {

    private String fType;

    public XQueryParam(SimpleReference name, int start, String type) {
        super(name, start, null, 0);
        fType = type;
    }

    @Override
    public String toString() {
        String s = super.getName();
        return s;
    }

    public String getType() {
        return fType;
    }
}
