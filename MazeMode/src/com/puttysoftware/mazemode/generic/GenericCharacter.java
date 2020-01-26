/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.generic;

import java.io.IOException;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.maze.MazeConstants;
import com.puttysoftware.mazemode.objects.Empty;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class GenericCharacter extends MazeObject {
    // Fields
    private MazeObject savedObject;
    public static final int FULL_HEAL_PERCENTAGE = 100;
    private static final int SHOT_SELF_NORMAL_DAMAGE = 5;
    private static final int SHOT_SELF_SPECIAL_DAMAGE = 10;

    // Constructors
    protected GenericCharacter() {
        super(false);
        this.savedObject = new Empty();
    }

    // Methods
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        // Do nothing
    }

    public MazeObject getSavedObject() {
        return this.savedObject;
    }

    public void setSavedObject(final MazeObject obj) {
        this.savedObject = obj;
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_CHARACTER);
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        // Shot self
        MazeMode.getApplication().showMessage("Ouch, you shot yourself!");
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_PLAIN) {
            MazeMode.getApplication().getMazeManager().getMaze()
                    .doDamage(GenericCharacter.SHOT_SELF_NORMAL_DAMAGE);
        } else {
            MazeMode.getApplication().getMazeManager().getMaze()
                    .doDamage(GenericCharacter.SHOT_SELF_SPECIAL_DAMAGE);
        }
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_LAVA);
        return false;
    }

    @Override
    public int getCustomFormat() {
        return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    protected void writeMazeObjectHookX(final XDataWriter writer)
            throws IOException {
        this.savedObject.writeMazeObjectX(writer);
    }

    @Override
    protected MazeObject readMazeObjectHookX(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.savedObject = MazeMode.getApplication().getObjects()
                .readMazeObjectX(reader, formatVersion);
        return this;
    }
}
