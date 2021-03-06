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
 * XQuery Update 1.0 rename expression
 * 
 * @author <a href="villard@us.ibm.com">Lionel Villard</a>
 */
public interface IASTRename extends IASTNode {

    /**
     * @return target expression
     */
    public IASTNode getTargetExpr();

    /**
     * Set target expression
     */
    public void setTargetExpr(IASTNode node);

    /**
     * @return new name expression
     */
    public IASTNode getNewNameExpr();

    /**
     * Set new name expression
     */
    public void setNewNameExpr(IASTNode node);

}