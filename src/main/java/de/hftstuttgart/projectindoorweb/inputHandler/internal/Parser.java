package de.hftstuttgart.projectindoorweb.inputHandler.internal;

import java.io.File;

public abstract class Parser implements Runnable {

    protected File sourceFile;
    protected boolean fileForRadiomap;

    public Parser(boolean fileForRadiomap, File sourceFile) {
        this.fileForRadiomap = fileForRadiomap;
        this.sourceFile = sourceFile;
    }

    protected abstract boolean isDataValid(String source);

    public abstract boolean isParsingFinished();
}
