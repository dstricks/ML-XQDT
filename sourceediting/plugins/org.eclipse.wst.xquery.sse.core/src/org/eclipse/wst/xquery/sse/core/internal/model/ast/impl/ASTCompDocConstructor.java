/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xquery.sse.core.internal.model.ast.impl;

import org.eclipse.wst.xquery.sse.core.internal.model.ast.ASTVisitor;
import org.eclipse.wst.xquery.sse.core.internal.model.ast.IASTCompDocConstructor;

/**
 * XQuery 1.0 computed document constructor
 * 
 * @author <a href="villard@us.ibm.com">Lionel Villard</a>
 */
public class ASTCompDocConstructor extends ASTParentNode implements IASTCompDocConstructor {

    // Overrides

    @Override
    public int getType() {
        return COMPDOCCONSTRUCTOR;
    }

    @Override
    protected void accept0(ASTVisitor visitor) {
        boolean children = visitor.visit(this);
        if (children) {
            acceptChildren(visitor);

        }
        visitor.endVisit(this);
    }
}
