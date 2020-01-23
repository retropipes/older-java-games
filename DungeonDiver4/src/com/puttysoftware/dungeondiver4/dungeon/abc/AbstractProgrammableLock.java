/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.effects.DungeonEffectConstants;
import com.puttysoftware.dungeondiver4.dungeon.objects.GhostAmulet;
import com.puttysoftware.dungeondiver4.dungeon.objects.PasswallBoots;
import com.puttysoftware.dungeondiver4.dungeon.objects.SignalCrystal;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectList;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class AbstractProgrammableLock extends AbstractSingleLock {
    private static final SignalCrystal SIGNAL = new SignalCrystal();

    protected AbstractProgrammableLock(int tc) {
        super(SIGNAL, tc);
    }

    // Scriptability
    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PROGRAMMABLE_LOCK);
        this.type.set(TypeConstants.TYPE_SINGLE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        Application app = DungeonDiver4.getApplication();
        if (!app.getGameManager().isEffectActive(
                DungeonEffectConstants.EFFECT_GHOSTLY)
                && !inv.isItemThere(new PasswallBoots())) {
            if (this.getKey() != SIGNAL) {
                if (!this.getKey().isInfinite()) {
                    inv.removeItem(this.getKey());
                }
            }
            app.getGameManager().decay();
            // Play unlock sound, if it's enabled
            SoundManager.playSound(SoundConstants.SOUND_WALK);
            DungeonDiver4.getApplication().getGameManager()
                    .addToScore(AbstractLock.SCORE_UNLOCK);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_WALK);
        }
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            if (this.getKey() == SIGNAL) {
                DungeonDiver4.getApplication()
                        .showMessage("You need a Crystal");
            } else {
                DungeonDiver4.getApplication().showMessage(
                        "You need a " + this.getKey().getName());
            }
        }
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public boolean isConditionallySolid(final DungeonObjectInventory inv) {
        if (this.getKey() != SIGNAL) {
            return !(inv.isItemThere(this.getKey()));
        } else {
            return !(inv
                    .isItemCategoryThere(TypeConstants.TYPE_PROGRAMMABLE_KEY));
        }
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final DungeonObjectInventory inv) {
        // Handle passwall boots and ghost amulet
        if (inv.isItemThere(new PasswallBoots())
                || inv.isItemThere(new GhostAmulet())) {
            return false;
        } else {
            if (this.getKey() != SIGNAL) {
                return !(inv.isItemThere(this.getKey()));
            } else {
                return !(inv
                        .isItemCategoryThere(TypeConstants.TYPE_PROGRAMMABLE_KEY));
            }
        }
    }

    @Override
    public int getCustomProperty(int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }

    @Override
    public AbstractDungeonObject editorPropertiesHook() {
        DungeonObjectList objects = DungeonDiver4.getApplication().getObjects();
        String[] tempKeyNames = objects.getAllProgrammableKeyNames();
        AbstractDungeonObject[] tempKeys = objects.getAllProgrammableKeys();
        String[] keyNames = new String[tempKeyNames.length + 1];
        AbstractDungeonObject[] keys = new AbstractDungeonObject[tempKeys.length + 1];
        System.arraycopy(tempKeyNames, 0, keyNames, 0, tempKeyNames.length);
        System.arraycopy(tempKeys, 0, keys, 0, tempKeys.length);
        keyNames[tempKeyNames.length] = "Any Crystal";
        keys[tempKeys.length] = SIGNAL;
        int oldIndex = -1;
        for (oldIndex = 0; oldIndex < keyNames.length; oldIndex++) {
            if (this.getKey().getName().equals(keyNames[oldIndex])) {
                break;
            }
        }
        oldIndex--;
        if (oldIndex == -1) {
            oldIndex = 0;
        }
        String res = CommonDialogs.showInputDialog(
                "Set Key for " + this.getName(), "Editor", keyNames,
                keyNames[oldIndex]);
        if (res != null) {
            int index = -1;
            for (index = 0; index < keyNames.length; index++) {
                if (res.equals(keyNames[index])) {
                    break;
                }
            }
            if (index != -1) {
                this.setKey((AbstractKey) keys[index]);
            }
        }
        return this;
    }

    @Override
    protected AbstractDungeonObject readDungeonObjectHook(XDataReader reader,
            int formatVersion) throws IOException {
        AbstractDungeonObject o = DungeonDiver4.getApplication().getObjects()
                .readDungeonObject(reader, formatVersion);
        if (o == null) {
            this.setKey(SIGNAL);
        } else {
            this.setKey((AbstractKey) o);
        }
        return this;
    }

    @Override
    protected void writeDungeonObjectHook(XDataWriter writer)
            throws IOException {
        this.getKey().writeDungeonObject(writer);
    }

    @Override
    public int getCustomFormat() {
        return AbstractDungeonObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}
