/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@puttysoftware.com
 */
package com.puttysoftware.errorlogger;

import com.puttysoftware.mazemode.CommonDialogs;

public final class ErrorLogger {
    // Fields
    private final String name;
    private final String msg;
    private final String title;

    // Constructor
    public ErrorLogger(final String programName, final String errorMessage,
            final String errorTitle) {
        this.name = programName;
        this.msg = errorMessage;
        this.title = errorTitle;
    }

    // Methods
    public final void logError(final Throwable t) {
        final LogWriter lw = new LogWriter(t, this.name);
        CommonDialogs.showErrorDialog(this.msg
                + "\nThe error log is located at:\n" + lw.getFullErrorPath(),
                this.title);
        lw.writeErrorInfo();
        System.exit(1);
    }
}
