/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2020 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.errorlogger.ErrorLogger;

final class GameErrorHandler implements Thread.UncaughtExceptionHandler {
    private static final String LOG_NAME = "Mazer5D";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: Mazer5D Bug Report";
    private static final String ERROR_TITLE = "Mazer5D Error";
    private final ErrorLogger logger;

    GameErrorHandler() {
        this.logger = new ErrorLogger(GameErrorHandler.LOG_NAME);
    }

    @Override
    public void uncaughtException(final Thread t, final Throwable e) {
        CommonDialogs.showErrorDialog(GameErrorHandler.ERROR_MESSAGE,
                GameErrorHandler.ERROR_TITLE);
        this.logger.logError(e);
    }
}
