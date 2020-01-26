package com.puttysoftware.scoremanager;

import com.puttysoftware.commondialogs.CommonDialogs;

public final class ScoreTableViewer {
    // Private constants
    private static final int ENTRIES_PER_PAGE = 10;
    private static final String VIEWER_STRING = "Score Table Viewer";

    // Private constructor
    private ScoreTableViewer() {
        // Do nothing
    }

    // Methods
    public static void view(final ScoreTable table, final String customTitle,
            final String unit) {
        final StringBuilder msgBuilder = new StringBuilder();
        String msg = null;
        String title = null;
        if (customTitle == null || customTitle.equals("")) {
            title = ScoreTableViewer.VIEWER_STRING;
        } else {
            title = customTitle;
        }
        int x;
        int y;
        for (x = 0; x < table
                .getLength(); x += ScoreTableViewer.ENTRIES_PER_PAGE) {
            msg = "";
            for (y = 1; y <= ScoreTableViewer.ENTRIES_PER_PAGE; y++) {
                try {
                    msgBuilder.append(table.getEntryName(x + y - 1));
                    msgBuilder.append(" - ");
                    msgBuilder.append(table.getEntryScore(x + y - 1));
                    msgBuilder.append(unit);
                    msgBuilder.append("\n");
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    // Do nothing
                }
            }
            msg = msgBuilder.toString();
            // Strip final newline character
            msg = msg.substring(0, msg.length() - 1);
            CommonDialogs.showTitledDialog(msg, title);
        }
    }
}
