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

import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocument;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;
import org.eclipse.wst.validation.internal.provisional.core.IValidator;
import org.eclipse.wst.xquery.sse.core.internal.model.ast.ASTVisitor;
import org.eclipse.wst.xquery.sse.core.internal.model.ast.IASTExprSingleClause;
import org.eclipse.wst.xquery.sse.core.internal.model.ast.IASTNode;

/**
 * Clause of the form <tt>'clauseName' ExprSingle</tt>
 * 
 * @author <a href="villard@us.ibm.com">Lionel Villard</a>
 */
public class ASTExprSingleClause extends ASTClause implements IASTExprSingleClause {

    // State

    /** Expression */
    protected IASTNode expr;

    // Methods

    /**
     * Set clause expression
     */
    public void setExpr(IASTNode expr) {
        if (expr != null) {
            expr.setASTParent(this);
        }

        this.expr = expr;
    }

    /**
     * Get clause expression
     */
    public IASTNode getExpr() {
        return this.expr;
    }

    // Overrides

    @Override
    protected void accept0(ASTVisitor visitor) {
        boolean children = visitor.visit(this);
        if (children) {
            acceptChildren(visitor);
        }
        visitor.endVisit(this);
    }

    @Override
    public void staticCheck(IStructuredDocument document, IValidator validator, IReporter reporter) {
        if (expr != null) {
            expr.staticCheck(document, validator, reporter);
        }

        super.staticCheck(document, validator, reporter);
    }

}
