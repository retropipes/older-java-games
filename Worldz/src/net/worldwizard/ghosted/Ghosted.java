/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.ghosted;

import net.worldwizard.worldz.Messager;

public final class Ghosted {
    // Fields
    private final String name;
    private final String msg;
    private final String nonFatalMsg;
    private final String title;

    // Constructor
    public Ghosted(final String programName, final String errorMessage,
            final String errorTitle, final String nonFatalErrorMessage) {
        this.name = programName;
        this.msg = errorMessage;
        this.nonFatalMsg = nonFatalErrorMessage;
        this.title = errorTitle;
    }

    // Methods
    public void debug(final Throwable t) {
        final DebugInfoWriter diw = new DebugInfoWriter(t, this.name);
        Messager.showErrorDialog(this.msg + "\nThe debug log is located at:\n"
                + diw.getFullDebugPath(), this.title);
        diw.writeDebugInfo();
        System.exit(1);
    }

    public void logError(final Throwable t) {
        final NonFatalLogger nfl = new NonFatalLogger(t, this.name);
        Messager.showErrorDialog(this.nonFatalMsg
                + "\nThe debug log is located at:\n" + nfl.getFullLogPath(),
                this.title);
        nfl.writeLogInfo();
    }
}
