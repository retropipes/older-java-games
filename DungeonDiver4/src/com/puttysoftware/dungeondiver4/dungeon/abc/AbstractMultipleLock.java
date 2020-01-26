/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.objects.GhostAmulet;
import com.puttysoftware.dungeondiver4.dungeon.objects.PasswallBoots;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

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
    public boolean isConditionallySolid(final DungeonObjectInventory inv) {
        return inv.getItemCount(this.getKey()) < this.keyCount;
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final DungeonObjectInventory inv) {
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
            final int dirY, final DungeonObjectInventory inv) {
        String fill = "";
        if (this.keyCount > 1) {
            fill = "s";
        } else {
            fill = "";
        }
        DungeonDiver4.getApplication().showMessage("You need " + this.keyCount
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
    public AbstractDungeonObject editorPropertiesHook() {
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
