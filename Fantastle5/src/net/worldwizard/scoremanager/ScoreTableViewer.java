/*  Fantastle: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Any questions should be directed to the author via email at: fantastle@worldwizard.net
 */
package net.worldwizard.scoremanager;

import net.worldwizard.fantastle5.Messager;

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
        String msg = "";
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
                    msg += table.getEntryName(x + y - 1) + " - "
                            + table.getEntryScore(x + y - 1) + unit + "\n";
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    // Do nothing
                }
            }
            // Strip final newline character
            msg = msg.substring(0, msg.length() - 1);
            Messager.showTitledDialog(msg, title);
        }
    }
}
