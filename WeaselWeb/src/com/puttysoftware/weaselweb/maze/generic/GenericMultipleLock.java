/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.objects.GhostAmulet;
import com.puttysoftware.weaselweb.maze.objects.PasswallBoots;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public abstract class GenericMultipleLock extends GenericLock {
    // Fields
    private int keyCount;

    // Constructors
    protected GenericMultipleLock(final GenericMultipleKey mgk) {
        super(mgk);
        this.keyCount = 0;
        this.setType(TypeConstants.TYPE_MULTIPLE_LOCK);
    }

    // Methods
    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
        return inv.getItemCount(this.getKey()) < this.keyCount;
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final ObjectInventory inv) {
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
            final int dirY, final ObjectInventory inv) {
        String fill = "";
        if (this.keyCount > 1) {
            fill = "s";
        } else {
            fill = "";
        }
        WeaselWeb.getApplication().showMessage(
                "You need " + this.keyCount + " " + this.getKey().getName()
                        + fill);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_FAILED);
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
    public MazeObject editorPropertiesHook() {
        try {
            this.keyCount = Integer.parseInt(CommonDialogs
                    .showTextInputDialogWithDefault(
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
