package de.hftstuttgart.projectindoorweb.inputHandler.internal;

import java.io.File;

public abstract class Parser {

    protected File sourceFile;
    protected boolean evaluationFile;

    public Parser(boolean evaluationFile, File sourceFile) {
        this.evaluationFile = evaluationFile;
        this.sourceFile = sourceFile;
    }

    protected abstract boolean isDataValid(String source);

    public abstract boolean isParsingFinished();

    public File getSourceFile() {
        return sourceFile;
    }
}
