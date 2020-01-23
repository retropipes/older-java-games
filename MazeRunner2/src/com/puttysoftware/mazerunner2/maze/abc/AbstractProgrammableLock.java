/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.abc;

import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.effects.MazeEffectConstants;
import com.puttysoftware.mazerunner2.maze.objects.GhostAmulet;
import com.puttysoftware.mazerunner2.maze.objects.PasswallBoots;
import com.puttysoftware.mazerunner2.maze.objects.SignalCrystal;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectList;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;
import com.puttysoftware.xio.legacy.XLegacyDataReader;

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
            final int dirY, final MazeObjectInventory inv) {
        Application app = MazeRunnerII.getApplication();
        if (!app.getGameManager().isEffectActive(
                MazeEffectConstants.EFFECT_GHOSTLY)
                && !inv.isItemThere(new PasswallBoots())) {
            if (this.getKey() != SIGNAL) {
                if (!this.getKey().isInfinite()) {
                    inv.removeItem(this.getKey());
                }
            }
            app.getGameManager().decay();
            // Play unlock sound, if it's enabled
            SoundManager.playSound(SoundConstants.SOUND_WALK);
            MazeRunnerII.getApplication().getGameManager()
                    .addToScore(AbstractLock.SCORE_UNLOCK);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_WALK);
        }
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            if (this.getKey() == SIGNAL) {
                MazeRunnerII.getApplication().showMessage("You need a Crystal");
            } else {
                MazeRunnerII.getApplication().showMessage(
                        "You need a " + this.getKey().getName());
            }
        }
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public boolean isConditionallySolid(final MazeObjectInventory inv) {
        if (this.getKey() != SIGNAL) {
            return !(inv.isItemThere(this.getKey()));
        } else {
            return !(inv
                    .isItemCategoryThere(TypeConstants.TYPE_PROGRAMMABLE_KEY));
        }
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final MazeObjectInventory inv) {
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
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
        MazeObjectList objects = MazeRunnerII.getApplication().getObjects();
        String[] tempKeyNames = objects.getAllProgrammableKeyNames();
        AbstractMazeObject[] tempKeys = objects.getAllProgrammableKeys();
        String[] keyNames = new String[tempKeyNames.length + 1];
        AbstractMazeObject[] keys = new AbstractMazeObject[tempKeys.length + 1];
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
    protected AbstractMazeObject readLegacyMazeObjectHook(
            XLegacyDataReader reader, int formatVersion) throws IOException {
        AbstractMazeObject o = MazeRunnerII.getApplication().getObjects()
                .readLegacyMazeObject(reader, formatVersion);
        if (o == null) {
            this.setKey(SIGNAL);
        } else {
            this.setKey((AbstractKey) o);
        }
        return this;
    }

    @Override
    protected AbstractMazeObject readMazeObjectHook(XDataReader reader,
            int formatVersion) throws IOException {
        AbstractMazeObject o = MazeRunnerII.getApplication().getObjects()
                .readMazeObject(reader, formatVersion);
        if (o == null) {
            this.setKey(SIGNAL);
        } else {
            this.setKey((AbstractKey) o);
        }
        return this;
    }

    @Override
    protected void writeMazeObjectHook(XDataWriter writer) throws IOException {
        this.getKey().writeMazeObject(writer);
    }

    @Override
    public int getCustomFormat() {
        return AbstractMazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}
