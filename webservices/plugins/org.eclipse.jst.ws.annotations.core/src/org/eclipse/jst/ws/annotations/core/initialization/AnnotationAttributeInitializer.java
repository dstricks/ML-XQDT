/*******************************************************************************
 * Copyright (c) 2009 Shane Clarke.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Shane Clarke - initial API and implementation
 *******************************************************************************/
package org.eclipse.jst.ws.annotations.core.initialization;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jface.text.contentassist.CompletionProposal;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;

/**
 * Base class for initializers contributed to the
 * <code>org.eclipse.jst.ws.annotations.core.annotationInitializer</code> extension point.
 * <p>
 * Provides default implementations for all methods.
 * </p>
 * <p>
 * <strong>Provisional API:</strong> This class/interface is part of an interim API that is still under development and 
 * expected to change significantly before reaching stability. It is being made available at this early stage 
 * to solicit feedback from pioneering adopters on the understanding that any code that uses this API will 
 * almost certainly be broken (repeatedly) as the API evolves.
 * </p>
 * 
*/
public class AnnotationAttributeInitializer implements IAnnotationAttributeInitializer {

    protected static final String MISSING_IDENTIFER = "$missing$";
	 
    public List<MemberValuePair> getMemberValuePairs(IJavaElement javaElement, AST ast,
            Class<? extends Annotation> annotationClass) {
        return Collections.emptyList();
    }

    public List<MemberValuePair> getMemberValuePairs(ASTNode astNode, AST ast,
            Class<? extends Annotation> annotationClass) {
        return Collections.emptyList();
    }

    public List<ICompletionProposal> getCompletionProposalsForMemberValuePair(IJavaElement javaElement,
            MemberValuePair memberValuePair) {
        return Collections.emptyList();
    }

    public List<ICompletionProposal> getCompletionProposalsForMemberValuePair(ASTNode astNode,
            MemberValuePair memberValuePair) {
        return Collections.emptyList();
    }

	protected CompletionProposal createCompletionProposal(String proposal, Expression value) {
		int replacementOffset = value.getStartPosition();
		int replacementLength = 0;
		if (value.toString().equals(MISSING_IDENTIFER)) {
			if (proposal.charAt(0) != '\"') {
				proposal = "\"" + proposal;
			}
			if (proposal.charAt(proposal.length() - 1) != '\"') {
				proposal = proposal + "\"";
			}
		} else {
			replacementOffset += 1;
			replacementLength = value.getLength() - 2;
		}

		Image image = PlatformUI.getWorkbench().getSharedImages().getImage(org.eclipse.ui.ISharedImages.IMG_OBJ_FILE);
		return new CompletionProposal(proposal, replacementOffset, replacementLength, proposal.length(),
				image, proposal, null, null);
	}

	protected CompletionProposal createCompletionProposal(String proposal, Expression value, Image image, 
			String displayString) {
		int replacementOffset = value.getStartPosition() + 1;
		int replacementLength = value.getLength() - 2;

		return new CompletionProposal(proposal, replacementOffset, replacementLength, proposal.length(),
				image, displayString, null, null);
	}

}
