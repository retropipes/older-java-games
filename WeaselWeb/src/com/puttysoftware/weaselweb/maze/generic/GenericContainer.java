/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import java.io.IOException;

import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.effects.MazeEffectConstants;
import com.puttysoftware.weaselweb.maze.objects.Empty;
import com.puttysoftware.weaselweb.maze.objects.PasswallBoots;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class GenericContainer extends GenericSingleLock {
    // Fields
    private MazeObject inside;

    // Constructors
    protected GenericContainer(final GenericSingleKey mgk) {
        super(mgk);
        this.inside = new Empty();
        this.setType(TypeConstants.TYPE_CONTAINER);
    }

    protected GenericContainer(final GenericSingleKey mgk,
            final MazeObject insideObject) {
        super(mgk);
        this.inside = insideObject;
        this.setType(TypeConstants.TYPE_CONTAINER);
    }

    public MazeObject getInsideObject() {
        return this.inside;
    }

    public void setInsideObject(final MazeObject in) {
        this.inside = in;
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
        final GenericContainer other = (GenericContainer) obj;
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
    public GenericContainer clone() {
        final GenericContainer copy = (GenericContainer) super.clone();
        copy.inside = this.inside.clone();
        return copy;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = WeaselWeb.getApplication();
        if (!app.getGameManager()
                .isEffectActive(MazeEffectConstants.EFFECT_GHOSTLY)
                && !inv.isItemThere(new PasswallBoots())) {
            if (!this.getKey().isInfinite()) {
                inv.removeItem(this.getKey());
            }
            final int pz = app.getGameManager().getPlayerManager()
                    .getPlayerLocationZ();
            if (this.inside != null) {
                app.getGameManager().morph(this.inside, dirX, dirY, pz);
            } else {
                app.getGameManager().decay();
            }
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_UNLOCK);
            app.getGameManager().backUpPlayer();
            WeaselWeb.getApplication().getGameManager()
                    .addToScore(GenericLock.SCORE_UNLOCK);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_WALK);
        }
    }

    @Override
    public void editorProbeHook() {
        if (!this.inside.getName().equals("Empty")) {
            WeaselWeb.getApplication().showMessage(
                    this.getName() + ": Contains " + this.inside.getName());
        } else {
            WeaselWeb.getApplication()
                    .showMessage(this.getName() + ": Contains Nothing");
        }
    }

    @Override
    public abstract MazeObject editorPropertiesHook();

    @Override
    protected MazeObject readMazeObjectHook(final XDataReader reader,
            final int formatVersion) throws IOException {
        final MazeObjectList objectList = WeaselWeb.getApplication()
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
        return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}