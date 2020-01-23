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

public class ScoreManager {
    // Fields and Constants
    private static final String DID_NOT_MAKE_LIST = "You did not make the score list.";
    private static final String NAME_PROMPT = "Enter a name for the score list:";
    private static final String DIALOG_TITLE = "Score Manager";
    public static final boolean SORT_ORDER_ASCENDING = true;
    public static final boolean SORT_ORDER_DESCENDING = false;
    protected SortedScoreTable table;
    private String name;
    private final boolean displayFailMsg;
    private String title;
    private final String viewerTitle;

    // Constructors
    public ScoreManager() {
        this.table = new SortedScoreTable();
        this.name = "";
        this.displayFailMsg = true;
        this.title = ScoreManager.DIALOG_TITLE;
        this.viewerTitle = ScoreManager.DIALOG_TITLE;
    }

    public ScoreManager(final int length, final boolean sortOrder,
            final long startingScore, final boolean showFailedMessage,
            final String customTitle, final String customUnit) {
        this.table = new SortedScoreTable(length, sortOrder, startingScore,
                customUnit);
        this.name = "";
        this.displayFailMsg = showFailedMessage;
        if (customTitle == null || customTitle.equals("")) {
            this.title = ScoreManager.DIALOG_TITLE;
        } else {
            this.title = customTitle;
        }
        this.viewerTitle = customTitle;
    }

    // Methods
    public boolean addScore(final long newScore) {
        boolean success = this.table.checkScore(newScore);
        if (!success) {
            if (this.displayFailMsg) {
                Messager.showTitledDialog(ScoreManager.DID_NOT_MAKE_LIST,
                        this.title);
            }
        } else {
            this.name = null;
            this.name = Messager.showTextInputDialog(ScoreManager.NAME_PROMPT,
                    this.title);
            if (this.name != null) {
                this.table.addScore(newScore, this.name);
            } else {
                success = false;
            }
        }
        return success;
    }

    public boolean addScore(final long newScore, final String newName) {
        final boolean success = this.table.checkScore(newScore);
        if (!success) {
            if (this.displayFailMsg) {
                Messager.showTitledDialog(ScoreManager.DID_NOT_MAKE_LIST,
                        this.title);
            }
        } else {
            this.table.addScore(newScore, newName);
        }
        return success;
    }

    public boolean checkScore(final long newScore) {
        return this.table.checkScore(newScore);
    }

    public void viewTable() {
        ScoreTableViewer.view(this.table, this.viewerTitle,
                this.table.getUnit());
    }
}
