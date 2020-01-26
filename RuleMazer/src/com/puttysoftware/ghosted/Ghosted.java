/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package com.puttysoftware.ghosted;

import com.puttysoftware.rulemazer.CommonDialogs;

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
        CommonDialogs.showErrorDialog(this.msg
                + "\nThe debug log is located at:\n" + diw.getFullDebugPath(),
                this.title);
        diw.writeDebugInfo();
        System.exit(1);
    }
}
