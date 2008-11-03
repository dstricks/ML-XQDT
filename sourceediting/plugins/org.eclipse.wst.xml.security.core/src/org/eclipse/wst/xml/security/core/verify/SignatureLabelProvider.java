/*******************************************************************************
 * Copyright (c) 2008 Dominik Schadow - http://www.xml-sicherheit.de
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dominik Schadow - initial API and implementation
 *******************************************************************************/
package org.eclipse.wst.xml.security.core.verify;

import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.wst.xml.security.core.utils.XmlSecurityImageRegistry;


/**
 * <p>The label provider of the XML Digital Signatures View.</p>
 *
 * @author Dominik Schadow
 * @version 0.5.0
 */
public class SignatureLabelProvider implements ITableLabelProvider {
    /**
     * Returns the text for the current column.
     *
     * @param element The object representing the entire row, or null indicating that no input
     *        object is set in the viewer
     * @param columnIndex The zero-based index of the column in which the label appears
     * @return The text of the column
     */
    public String getColumnText(final Object element, final int columnIndex) {
        VerificationResult result = (VerificationResult) element;
        switch (columnIndex) {
            case 0:
                return "";
            case 1:
                return result.getId();
            case 2:
                return result.getType();
            case 3:
                return result.getAlgorithm();
            default:
                return null;
        }
    }

    /**
     * Returns the label image for the given column of the given element.
     *
     * @param element The object representing the entire row, or null indicating that no input
     *        object is set in the viewer
     * @param columnIndex The zero-based index of the column in which the label appears
     * @return Image or null if there is no image for the given object at columnIndex
     */
    public Image getColumnImage(final Object element, final int columnIndex) {
        VerificationResult result = (VerificationResult) element;

        if (columnIndex != 0 || result == null || result.getStatus() == null) {
            return null;
        }

        if (Verification.VALID.equals(result.getStatus())) {
            return XmlSecurityImageRegistry.getImageRegistry().get("sig_valid_small");
        } else if (Verification.INVALID.equals(result.getStatus())) {
            return XmlSecurityImageRegistry.getImageRegistry().get("sig_invalid_small");
        } else {
            return XmlSecurityImageRegistry.getImageRegistry().get("sig_unknown_small");
        }
    }

    /**
     * Adds a listener to this label provider.
     *
     * @param listener A label provider listener
     */
    public void addListener(final ILabelProviderListener listener) {
    }

    /**
     * Disposes this label provider.
     */
    public void dispose() {
    }

    /**
     * Returns whether the label would be affected by a change to the given property of the given
     * element.
     *
     * @param element The element
     * @param property The property
     * @return True if the label would be affected, and false if it would be unaffected
     */
    public boolean isLabelProperty(final Object element, final String property) {
        return false;
    }

    /**
     * Removes a listener of this label provider.
     *
     * @param listener A label provider listener
     */
    public void removeListener(final ILabelProviderListener listener) {
    }
}
