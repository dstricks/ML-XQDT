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

import org.eclipse.dltk.ui.PreferenceConstants;
import org.eclipse.dltk.ui.text.completion.ScriptCompletionProposal;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.xquery.ui.XQDTUIPlugin;

public class XQDTCompletionProposal extends ScriptCompletionProposal {

    public XQDTCompletionProposal(String replacementString, int replacementOffset, int replacementLength, Image image,
            String displayString, int relevance) {
        super(replacementString, replacementOffset, replacementLength, image, displayString, relevance);
    }

    public XQDTCompletionProposal(String replacementString, int replacementOffset, int replacementLength, Image image,
            String displayString, int relevance, boolean isInDoc) {
        super(replacementString, replacementOffset, replacementLength, image, displayString, relevance, isInDoc);
    }

    protected boolean isSmartTrigger(char trigger) {
        if (trigger == ':') {
            return true;
        }
        return false;
    }

    protected boolean insertCompletion() {
        IPreferenceStore preference = XQDTUIPlugin.getDefault().getPreferenceStore();
        return preference.getBoolean(PreferenceConstants.CODEASSIST_INSERT_COMPLETION);
    }

}
