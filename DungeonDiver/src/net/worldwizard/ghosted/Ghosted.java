package net.worldwizard.ghosted;

import javax.swing.JOptionPane;

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
        JOptionPane.showMessageDialog(null,
                this.msg + "\nThe debug log is located at:\n"
                        + diw.getFullDebugPath(),
                this.title, JOptionPane.ERROR_MESSAGE);
        diw.writeDebugInfo();
        System.exit(1);
    }
}
