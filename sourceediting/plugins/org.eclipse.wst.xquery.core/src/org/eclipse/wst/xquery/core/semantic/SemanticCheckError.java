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
package org.eclipse.wst.xquery.core.semantic;

import org.eclipse.dltk.compiler.problem.CategorizedProblem;
import org.eclipse.dltk.compiler.problem.DefaultProblem;
import org.eclipse.dltk.compiler.problem.IProblemIdentifier;

public class SemanticCheckError extends CategorizedProblem {

    private int fLineNumber;
    private int fSourceStart;
    private int fSourceEnd;

    private String fErrorCode;
    private String fDescription;
    private String fFileName;

    public SemanticCheckError(String fileName, String errorCode, String description, int line) {
        this(fileName, errorCode, description, line, -1, -1);
    }

    public SemanticCheckError(String fileName, String errorCode, String description, int line, int start) {
        this(fileName, errorCode, description, line, start, start);
    }

    public SemanticCheckError(String fileName, String errorCode, String description, int line, int start, int end) {
        fErrorCode = errorCode;
        fDescription = description;
        fFileName = fileName;
        fLineNumber = line;
        fSourceStart = start;
        fSourceEnd = end;
    }

    public String[] getArguments() {
        return new String[0];
    }

    public IProblemIdentifier getID() {
        return null;
    }

    public String getErrorCode() {
        return fErrorCode;
    }

    public String getMessage() {
        return "[" + fErrorCode + "]: " + fDescription;
    }

    public String getOriginatingFileName() {
        return fFileName;
    }

    public int getSourceEnd() {
        return fSourceEnd;
    }

    public int getSourceLineNumber() {
        return fLineNumber;
    }

    public int getSourceStart() {
        return fSourceStart;
    }

    public boolean isError() {
        return true;
    }

    public boolean isWarning() {
        return false;
    }

    public boolean isTask() {
        return false;
    }

    public void setSourceEnd(int sourceEnd) {
        fSourceEnd = sourceEnd;
    }

    public void setSourceLineNumber(int lineNumber) {
        fLineNumber = lineNumber;
    }

    public void setSourceStart(int sourceStart) {
        fSourceStart = sourceStart;
    }

    public int getCategoryID() {
        if ("err:XPST0003".equals(fErrorCode)) {
            return CAT_SYNTAX;
        } else if ("err:XQST0059".equals(fErrorCode)) {
            return CAT_IMPORT;
        }

        return CAT_UNSPECIFIED;
    }

    public String getMarkerType() {
        return DefaultProblem.MARKER_TYPE_PROBLEM;
    }

}
