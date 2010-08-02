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
package org.eclipse.wst.xquery.sse.core.internal.model.ast;

import java.util.List;

import org.eclipse.wst.sse.core.internal.provisional.text.IStructuredDocumentRegion;

/**
 * Quantified expression
 * 
 * @author <a href="villard@us.ibm.com">Lionel Villard</a>
 */
@SuppressWarnings("restriction")
public class ASTQuantified extends ASTParentNode {

	// Methods

	/**
	 * Set binding clause
	 * 
	 * @param index
	 * @param region
	 */
	public void setBindingClause(ASTBindingClause clause) {
		setChildASTNodeAt(0, clause);
	}

	/**
	 * Get binding clause
	 * 
	 * @param index
	 */
	public ASTBindingClause getBindingClause() {
		return (ASTBindingClause) getChildASTNodeAt(0);
	}

	/**
	 * @param expr
	 */
	public void setSatisfiesExpr(IASTNode expr) {
		setChildASTNodeAt(1, expr);
	}

	/**
	 * @return satisfies expression
	 */
	public IASTNode getSatisfiesExpr() {
		return getChildASTNodeAt(1);
	}

	// Overrides

	@Override
	public int getType() {
		return QUANTIFIED;
	}

	@Override
	protected void getInScopeVariables(List<String> vars, IASTNode child) {
		if (child == getSatisfiesExpr()) {

			ASTBindingClause bindings = getBindingClause();
			if (bindings != null)
				for (int i = bindings.getBindingExprCount() - 1; i >= 0; i--) {
					
					IStructuredDocumentRegion var = bindings
							.getBindingVariable(i);
					if (var != null)
						vars.add(var.getFullText().trim());
				}
		}

		super.getInScopeVariables(vars, child);
	}

}