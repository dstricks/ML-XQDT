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
import org.eclipse.wst.xquery.sse.core.internal.model.ast.IASTOperator;

/**
 * Operator expression
 * 
 * @author <a href="villard@us.ibm.com">Lionel Villard</a>
 */
public class ASTOperator extends ASTParentNode implements IASTOperator {

    // Constants

    /** Operator Type */
    protected int operatorType;

    // Constructors

    public ASTOperator(int operatorType) {
        this.operatorType = operatorType;
    }

    public ASTOperator() {
    }

    // Methods

    public int getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(int type) {
        this.operatorType = type;

    }

    // Override

    @Override
    public int getType() {
        return OPERATOR;
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
