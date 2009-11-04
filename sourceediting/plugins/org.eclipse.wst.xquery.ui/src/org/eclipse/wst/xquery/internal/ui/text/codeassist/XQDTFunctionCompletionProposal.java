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
package org.eclipse.wst.xquery.internal.ui.text.codeassist;

import org.eclipse.dltk.core.CompletionProposal;
import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.text.completion.ProposalInfo;
import org.eclipse.dltk.ui.text.completion.ScriptContentAssistInvocationContext;
import org.eclipse.dltk.ui.text.completion.ScriptMethodCompletionProposal;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.wst.xquery.ui.XQDTUIPlugin;

public class XQDTFunctionCompletionProposal extends ScriptMethodCompletionProposal {

    public static char[] FUNCTION_TRIGGERS_NO_PARAMS = { '(', ' ' };
    public static char[] FUNCTION_TRIGGERS_PARAMS = { '(', ' ', '$' };

    public XQDTFunctionCompletionProposal(final CompletionProposal proposal,
            ScriptContentAssistInvocationContext context) {
        super(proposal, context);

        // TODO: a hack like in ScriptCompletionProposal(); without this the proposal is not
        // displayed in the automatically triggered proposals
        setContextInformation(new ContextInformation(getDisplayString(), getDisplayString()));

        setProposalInfo(new ProposalInfo() {
            // TODO: review this
//            @Override
//            public String getInfo(IProgressMonitor monitor) {
//                return MarkLogicBuiltinsDoc.getDocs().get(new String(proposal.getName()));
//            }
        });
    }

    @Override
    protected boolean isValidPrefix(String prefix) {
        if (super.isValidPrefix(prefix)) {
            return true;
        }

        String word = getDisplayString();
        if (isPrefix(prefix, word)) {
            return true;
        }

        int colonIndex = word.indexOf(':');
        if (colonIndex != -1) {
            word = word.substring(colonIndex + 1);
            return isPrefix(prefix, word);
        }

        return false;
    }

    @Override
    protected boolean insertCompletion() {
        IPreferenceStore preference = XQDTUIPlugin.getDefault().getPreferenceStore();
        return preference.getBoolean(PreferenceConstants.CODEASSIST_INSERT_COMPLETION);
    }

    @Override
    protected char[] computeTriggerCharacters() {
        if (hasParameters()) {
            return FUNCTION_TRIGGERS_PARAMS;
        }
        return FUNCTION_TRIGGERS_NO_PARAMS;
    }

}