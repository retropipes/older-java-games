/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;

public abstract class GenericMultipleLock extends GenericLock {
    // Fields
    private int keyCount;

    // Constructors
    protected GenericMultipleLock(final GenericMultipleKey mgk) {
        super(mgk);
        this.keyCount = 0;
    }

    // Methods
    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_MULTIPLE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
        return inv.getItemCount(this.getKey()) < this.keyCount;
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final ObjectInventory inv) {
        return inv.getItemCount(this.getKey()) < this.keyCount;
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
        Messager.showMessage("You need " + this.keyCount + " "
                + this.getKey().getName() + fill);
        // Play move failed sound, if it's enabled
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveFailedSound();
        }
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
    public WorldObject editorPropertiesHook() {
        try {
            this.keyCount = Integer.parseInt(Messager
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
