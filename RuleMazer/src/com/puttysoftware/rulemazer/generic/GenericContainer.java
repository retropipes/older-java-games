/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.generic;

import java.io.IOException;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.maze.effects.MazeEffectConstants;
import com.puttysoftware.rulemazer.objects.Empty;
import com.puttysoftware.rulemazer.objects.PasswallBoots;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;
import com.puttysoftware.xmlio.XMLDataReader;
import com.puttysoftware.xmlio.XMLDataWriter;

public abstract class GenericContainer extends GenericLock {
    // Fields
    private MazeObject inside;

    // Constructors
    protected GenericContainer(final GenericSingleKey mgk) {
        super(mgk);
        this.inside = new Empty();
    }

    protected GenericContainer(final GenericSingleKey mgk,
            final MazeObject insideObject) {
        super(mgk);
        this.inside = insideObject;
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
        final Application app = Main.getApplication();
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
            Main.getApplication().getGameManager()
                    .addToScore(GenericLock.SCORE_UNLOCK);
        } else {
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_WALK);
        }
    }

    @Override
    public void editorProbeHook() {
        if (!this.inside.getName().equals("Empty")) {
            Main.getApplication().showMessage(
                    this.getName() + ": Contains " + this.inside.getName());
        } else {
            Main.getApplication()
                    .showMessage(this.getName() + ": Contains Nothing");
        }
    }

    @Override
    public abstract MazeObject editorPropertiesHook();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CONTAINER);
        this.type.set(TypeConstants.TYPE_SINGLE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    protected MazeObject readMazeObjectHookXML(final XMLDataReader reader,
            final int formatVersion) throws IOException {
        final MazeObjectList objectList = Main.getApplication().getObjects();
        this.inside = objectList.readMazeObjectXML(reader, formatVersion);
        return this;
    }

    @Override
    protected void writeMazeObjectHookXML(final XMLDataWriter writer)
            throws IOException {
        if (this.inside == null) {
            new Empty().writeMazeObjectXML(writer);
        } else {
            this.inside.writeMazeObjectXML(writer);
        }
    }

    @Override
    public int getCustomFormat() {
        return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}