/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.abc;

import java.io.IOException;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.maze.effects.MazeEffectConstants;
import com.puttysoftware.mazer5d.compatibility.objects.GameObjects;
import com.puttysoftware.mazer5d.files.io.XDataReader;
import com.puttysoftware.mazer5d.files.io.XDataWriter;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public abstract class GenericContainer extends GenericLock {
    // Fields
    private MazeObjectModel inside;

    // Constructors
    protected GenericContainer(final GenericSingleKey mgk) {
        super(mgk);
        this.inside = GameObjects.getEmptySpace();
    }

    protected GenericContainer(final GenericSingleKey mgk,
            final MazeObjectModel insideObject) {
        super(mgk);
        this.inside = insideObject;
    }

    public MazeObjectModel getInsideObject() {
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
        final GenericContainer other = (GenericContainer) obj;
        if (this.inside != other.inside && (this.inside == null || !this.inside
                .equals(other.inside))) {
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
        final BagOStuff app = Mazer5D.getBagOStuff();
        if (!app.getGameManager().isEffectActive(
                MazeEffectConstants.EFFECT_GHOSTLY) && !inv.isItemThere(
                        MazeObjects.PASSWALL_BOOTS)) {
            if (!this.getKey().isInfinite()) {
                inv.removeItem(this.getKey().getUniqueID());
            }
            final int pz = app.getGameManager().getPlayerManager()
                    .getPlayerLocationZ();
            if (this.inside != null) {
                app.getGameManager().morph(this.inside, dirX, dirY, pz);
            } else {
                app.getGameManager().decay();
            }
            SoundPlayer.playSound(SoundIndex.UNLOCK, SoundGroup.GAME);
            app.getGameManager().backUpPlayer();
            Mazer5D.getBagOStuff().getGameManager().addToScore(
                    GenericLock.SCORE_UNLOCK);
        } else {
            SoundPlayer.playSound(SoundIndex.WALK, SoundGroup.GAME);
        }
    }

    @Override
    public void editorProbeHook() {
        if (!this.inside.getName().equals("Empty")) {
            Mazer5D.getBagOStuff().showMessage(this.getName() + ": Contains "
                    + this.inside.getName());
        } else {
            Mazer5D.getBagOStuff().showMessage(this.getName()
                    + ": Contains Nothing");
        }
    }

    @Override
    public abstract MazeObjectModel editorPropertiesHook();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CONTAINER);
        this.type.set(TypeConstants.TYPE_SINGLE_LOCK);
        this.type.set(TypeConstants.TYPE_LOCK);
    }

    @Override
    protected MazeObjectModel readMazeObjectHookXML(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.inside = GameObjects.readObject(reader, formatVersion);
        return this;
    }

    @Override
    protected void writeMazeObjectHookXML(final XDataWriter writer)
            throws IOException {
        if (this.inside == null) {
            GameObjects.getEmptySpace().writeMazeObjectXML(writer);
        } else {
            this.inside.writeMazeObjectXML(writer);
        }
    }

    @Override
    public int getCustomFormat() {
        return MazeObjectModel.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }
}