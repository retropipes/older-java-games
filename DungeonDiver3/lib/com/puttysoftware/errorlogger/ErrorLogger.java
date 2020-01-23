package com.puttysoftware.errorlogger;

public final class ErrorLogger {
    // Fields
    private String name;

    // Constructor
    public ErrorLogger(String programName) {
        this.name = programName;
    }

    // Methods
    public final void logError(Throwable t) {
        LogWriter lw = new LogWriter(t, this.name);
        lw.writeErrorInfo();
        System.exit(1);
    }

    public final void logNonFatalError(Throwable t) {
        NonFatalLogger nfl = new NonFatalLogger(t, this.name);
        nfl.writeLogInfo();
    }
}
