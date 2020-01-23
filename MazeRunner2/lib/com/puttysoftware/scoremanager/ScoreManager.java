/*  LaserTank: An Arena-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: lasertank@worldwizard.net
 */
package com.puttysoftware.scoremanager;

import com.puttysoftware.commondialogs.CommonDialogs;

public class ScoreManager {
    // Fields and Constants
    private static final String NAME_PROMPT = "Enter a name for the score list:";
    private static final String DIALOG_TITLE = "Score Manager";
    public static final boolean SORT_ORDER_ASCENDING = true;
    public static final boolean SORT_ORDER_DESCENDING = false;
    protected SortedScoreTable table;
    private String name;
    private String title;
    private String viewerTitle;

    // Constructors
    public ScoreManager() {
        this.table = new SortedScoreTable();
        this.name = "";
        this.title = ScoreManager.DIALOG_TITLE;
        this.viewerTitle = ScoreManager.DIALOG_TITLE;
    }

    public ScoreManager(int mv, int length, boolean sortOrder,
            long startingScore, String customTitle, String[] customUnit) {
        this.table = new SortedScoreTable(mv, length, sortOrder, startingScore,
                customUnit);
        this.name = "";
        if (customTitle == null || customTitle.equals("")) {
            this.title = ScoreManager.DIALOG_TITLE;
        } else {
            this.title = customTitle;
        }
        this.viewerTitle = customTitle;
    }

    // Methods
    public boolean addScore(long newScore) {
        boolean success = true;
        this.name = null;
        this.name = CommonDialogs.showTextInputDialog(ScoreManager.NAME_PROMPT,
                this.title);
        if (this.name != null) {
            this.table.addScore(newScore, this.name);
        } else {
            success = false;
        }
        return success;
    }

    public boolean addScore(long[] newScore) {
        boolean success = true;
        this.name = null;
        this.name = CommonDialogs.showTextInputDialog(ScoreManager.NAME_PROMPT,
                this.title);
        if (this.name != null) {
            this.table.addScore(newScore, this.name);
        } else {
            success = false;
        }
        return success;
    }

    public boolean checkScore(long[] newScore) {
        return this.table.checkScore(newScore);
    }

    public boolean addScore(long newScore, String newName) {
        boolean success = true;
        this.table.addScore(newScore, newName);
        return success;
    }

    public void viewTable() {
        ScoreTableViewer.view(this.table, this.viewerTitle,
                this.table.getUnits());
    }
}
