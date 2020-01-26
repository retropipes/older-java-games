/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.abc;

import java.io.IOException;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.effects.MazeEffectConstants;
import com.puttysoftware.mazerunner2.maze.objects.Empty;
import com.puttysoftware.mazerunner2.maze.objects.PasswallBoots;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectList;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;
import com.puttysoftware.xio.legacy.XLegacyDataReader;

public abstract class AbstractContainer extends AbstractLock {
    // Fields
    private AbstractMazeObject inside;

    // Constructors
    protected AbstractContainer(final AbstractSingleKey mgk) {
        super(mgk);
        this.inside = new Empty();
    }

    protected AbstractContainer(final AbstractSingleKey mgk,
            final AbstractMazeObject insideObject) {
        super(mgk);
        this.inside = insideObject;
    }

    public AbstractMazeObject getInsideObject() {
        return this.inside;
    }

    @Override
    public boolean defersSetProperties() {
        return true;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final AbstractContainer other = (AbstractContainer) obj;
        if (this.inside != other.inside
                && (this.inside == null || !this.inside.equals(other.inside))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + (this.inside != null ? this.inside.hashCode() : 0);
        return hash;
    }

    @Override
    public AbstractContainer clone() {
        final AbstractContainer copy = (AbstractContainer) super.clone();
        copy.inside = this.inside.clone();
        return copy;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        final Application app = MazeRunnerII.getApplication();
        if (!app.getGameManager()
                .isEffectActive(MazeEffectConstants.EFFECT_GHOSTLY)
                && !inv.isItemThere(new PasswallBoots())) {
            if (!this.getKey().isInfinite()) {
                inv.removeItem(this.getKey());
            }
            final int pz = app.getMazeManager().getMaze().getPlayerLocationZ();
            if (this.inside != null) {
                app.getGameManager().morph(this.inside, dirX, dirY, pz);
            } else {
                app.getGameManager().decay();
            }
            SoundManager.playSound(SoundConstants.SOUND_UNLOCK);
            app.getGameManager().backUpPlayer(this);
            MazeRunnerII.getApplication().getGameManager()
                    .addToScore(AbstractLock.SCORE_UNLOCK);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_WALK);
        }
    }

    @Override
    public void editorProbeHook() {
        if (!this.inside.getName().equals("Empty")) {
            MazeRunnerII.getApplication().showMessage(
                    this.getName() + ": Contains " + this.inside.getName());
        } else {
            MazeRunnerII.getApplication()
                    .showMessage(this.getName() + ": Contains Nothing");
        }
    }

    @Override
    public abstract AbstractMazeObject editorPropertiesHook();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CONTAINER);
        this.type.set(TypeConstants.TYPE_SINGLE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    protected AbstractMazeObject readLegacyMazeObjectHook(
            final XLegacyDataReader reader, final int formatVersion)
            throws IOException {
        final MazeObjectList objectList = MazeRunnerII.getApplication()
                .getObjects();
        this.inside = objectList.readLegacyMazeObject(reader, formatVersion);
        return this;
    }

    @Override
    protected AbstractMazeObject readMazeObjectHook(final XDataReader reader,
            final int formatVersion) throws IOException {
        final MazeObjectList objectList = MazeRunnerII.getApplication()
                .getObjects();
        this.inside = objectList.readMazeObject(reader, formatVersion);
        return this;
    }

    @Override
    protected void writeMazeObjectHook(final XDataWriter writer)
            throws IOException {
        if (this.inside == null) {
            new Empty().writeMazeObject(writer);
        } else {
            this.inside.writeMazeObject(writer);
        }
    }

    @Override
    public int getCustomFormat() {
        return AbstractMazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}