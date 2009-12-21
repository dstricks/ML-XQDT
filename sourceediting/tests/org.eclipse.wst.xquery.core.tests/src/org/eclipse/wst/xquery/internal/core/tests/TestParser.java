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
package org.eclipse.wst.xquery.internal.core.tests;

import junit.framework.TestCase;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.RecognitionException;
import org.eclipse.wst.xquery.internal.core.parser.antlr.NewLazyTokenStream;
import org.eclipse.wst.xquery.internal.core.parser.antlr.XQDTCommonTreeAdaptor;
import org.eclipse.wst.xquery.internal.core.parser.antlr.XQueryLexer;
import org.eclipse.wst.xquery.internal.core.parser.antlr.XQueryParser;

@SuppressWarnings("restriction")
public class TestParser extends TestCase {

    // *****************************************************
    // ****************** TESTS ****************************
    // *****************************************************

    // https://bugs.eclipse.org/bugs/show_bug.cgi?id=296671 
    public void testBug296671() {
        testQuery("\"<\"");
    }

    // https://bugs.eclipse.org/bugs/show_bug.cgi?id=295437
    public void testBug295437() {
        testQuery("<e>{.}--</e>");
    }

    // *****************************************************
    // *****************************************************

    private void testQuery(String query) {
        XQueryParser parser = prepareParser("parser_unit_tests.xq", query.toCharArray());
        try {
            parser.p_Module();
            // test for 0 syntax errors 
            assertEquals(0, parser.getNumberOfSyntaxErrors());
        } catch (RecognitionException e) {
            // test for RecognitionException 
            assertFalse(e.getMessage(), true);
        }
    }

    private XQueryParser prepareParser(String fileName, char[] source) {
        ANTLRStringStream inputStream = new ANTLRStringStream(source, source.length);
        XQueryLexer lexer = new XQueryLexer(inputStream);
        NewLazyTokenStream tokenStream = new NewLazyTokenStream(lexer);
        tokenStream.jumpToFirstValidToken();
        XQueryParser parser = new XQueryParser(tokenStream);
        parser.setCharSource(inputStream);
        parser.setTreeAdaptor(new XQDTCommonTreeAdaptor(null));
        parser.setFileName(fileName);
        return parser;
    }

}