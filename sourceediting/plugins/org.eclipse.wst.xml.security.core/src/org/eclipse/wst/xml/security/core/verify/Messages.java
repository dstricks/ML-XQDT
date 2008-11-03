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

import org.eclipse.osgi.util.NLS;

/**
 * <p>Externalized strings for the org.eclipse.wst.xml.security.core.verify package.</p>
 *
 * @author Dominik Schadow
 * @version 0.5.0
 */
public final class Messages extends NLS {
    /** The bundle name. */
    private static final String BUNDLE_NAME = "org.eclipse.wst.xml.security.core.verify.messages";

    /**
     * Private Constructor to avoid instantiation.
     */
    private Messages() {
    }

    static {
        // initialize resource bundle
        NLS.initializeMessages(BUNDLE_NAME, Messages.class);
    }

    /** Signature View externalized strings. */
    public static String signatureId, signatureStatus, signatureType, signatureAlgorithm, validSignature,
            invalidSignature, unknownSignature, properties, propertiesInfo, refresh,
            refreshInfo, verificationImpossible, verificationImpossibleInfo, invalidXml, invalidCertificate,
            generalError, noDocument, title, impossibleToGetKeyInformation, verificationResult;

    /** Signature Content Provider externalized strings. */
    public static String signatureInfo;
}
