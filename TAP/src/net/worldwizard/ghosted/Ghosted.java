/*  TAP: A Text Adventure Parser
Copyright (C) 2010 Eric Ahnell

Any questions should be directed to the author via email at: tap@worldwizard.net
 */
package net.worldwizard.ghosted;

import net.worldwizard.tap.Messager;

public final class Ghosted {
    // Fields
    private final String name;
    private final String msg;
    private final String title;

    // Constructor
    public Ghosted(final String programName, final String errorMessage,
            final String errorTitle) {
        this.name = programName;
        this.msg = errorMessage;
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
}
