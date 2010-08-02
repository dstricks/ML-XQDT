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

/**
 * Global variable declaration
 * 
 * @author <a href="villard@us.ibm.com">Lionel Villard</a>
 */
public class ASTVarDecl extends ASTNode {

	// State

	/** Variable expression (if any) */
	private IASTNode expr;

	/** Variable raw name */
	private String name;

	// Constructors

	public ASTVarDecl() {
	}

	// Methods

	/**
	 * Return the variable initialization expression
	 * 
	 * @return
	 */
	public IASTNode getExpr() {
		return expr;
	}

	/**
	 * @param expr
	 */
	public void setExpr(IASTNode expr) {
		if (this.expr != null)
			this.expr.setASTParent(null);

		this.expr = expr;
		if (this.expr != null)
			this.expr.setASTParent(this);
	}

	/**
	 * @return
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	// Overrides

	@Override
	public int getType() {
		return VARDECL;
	}

}