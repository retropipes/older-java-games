/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.objects.GhostAmulet;
import com.puttysoftware.mazerunner2.maze.objects.PasswallBoots;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public abstract class AbstractMultipleLock extends AbstractLock {
    // Fields
    private int keyCount;

    // Constructors
    protected AbstractMultipleLock(final AbstractMultipleKey mgk,
            final int attrColor) {
        super(mgk);
        this.keyCount = 0;
        this.setTemplateColor(ColorConstants.COLOR_BROWN);
        this.setAttributeTemplateColor(attrColor);
    }

    // Methods
    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_WALL_ON;
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_SQUARE;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_MULTIPLE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    public boolean isConditionallySolid(final MazeObjectInventory inv) {
        return inv.getItemCount(this.getKey()) < this.keyCount;
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final MazeObjectInventory inv) {
        // Handle passwall boots and ghost amulet
        if (inv.isItemThere(new PasswallBoots())
                || inv.isItemThere(new GhostAmulet())) {
            return false;
        } else {
            return inv.getItemCount(this.getKey()) < this.keyCount;
        }
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        String fill = "";
        if (this.keyCount > 1) {
            fill = "s";
        } else {
            fill = "";
        }
        MazeRunnerII.getApplication().showMessage("You need " + this.keyCount
                + " " + this.getKey().getName() + fill);
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return this.keyCount;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        this.keyCount = value;
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
        try {
            this.keyCount = Integer
                    .parseInt(CommonDialogs.showTextInputDialogWithDefault(
                            "Set Key Count for " + this.getName(), "Editor",
                            Integer.toString(this.keyCount)));
        } catch (final NumberFormatException nf) {
            // Ignore
        }
        return this;
    }

    @Override
    public int getCustomFormat() {
        return 1;
    }
}
