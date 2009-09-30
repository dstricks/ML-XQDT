/*******************************************************************************
 * Copyright (c) 2009 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.ws.jaxws.dom.runtime.validation.webparam;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.validation.model.IModelConstraint;

/**
 * Constraint provider for validation of webPram. Collects all 
 * constraints for WebParam annotation and returns them as collection. 
 * 
 * @author Georgi Vachkov
 */
public class WpConstraintProvider 
{
	private Set<IModelConstraint> constraints;
	
	public Collection<IModelConstraint> getConstraints() 
	{
		if (constraints == null) 
		{
			constraints = new HashSet<IModelConstraint>();
			constraints.add(new NameIsNCNameConstraint());
			constraints.add(new NameIsUniqueConstraint());
			constraints.add(new NameIsRedundantConstraint());
			constraints.add(new NameIsRequiredConstraint());
			constraints.add(new PartNameIsNCNameConstraint());			
			constraints.add(new TargetNsValidUriConstraint());
		}
		
		return constraints;
	}
}