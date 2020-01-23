/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.scoremanager;

import com.puttysoftware.mazemode.CommonDialogs;

public class ScoreManager {
    // Fields and Constants
    private static final String DID_NOT_MAKE_LIST = "You did not make the score list.";
    private static final String NAME_PROMPT = "Enter a name for the score list:";
    private static final String DIALOG_TITLE = "Score Manager";
    public static final boolean SORT_ORDER_ASCENDING = true;
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
                CommonDialogs.showTitledDialog(ScoreManager.DID_NOT_MAKE_LIST,
                        this.title);
            }
        } else {
            this.name = null;
            this.name = CommonDialogs.showTextInputDialog(
                    ScoreManager.NAME_PROMPT, this.title);
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
                CommonDialogs.showTitledDialog(ScoreManager.DID_NOT_MAKE_LIST,
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
