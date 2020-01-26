/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import java.io.IOException;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;
import com.puttysoftware.weaselweb.maze.objects.Empty;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;
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
        super();
        this.savedObject = new Empty();
        this.setType(TypeConstants.TYPE_CHARACTER);
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
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        // Shot self
        WeaselWeb.getApplication().showMessage("Ouch, you shot yourself!");
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_PLAIN) {
            WeaselWeb.getApplication().getMazeManager().getMaze()
                    .doDamage(GenericCharacter.SHOT_SELF_NORMAL_DAMAGE);
        } else {
            WeaselWeb.getApplication().getMazeManager().getMaze()
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
    protected void writeMazeObjectHook(final XDataWriter writer)
            throws IOException {
        this.savedObject.writeMazeObject(writer);
    }

    @Override
    protected MazeObject readMazeObjectHook(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.savedObject = WeaselWeb.getApplication().getObjects()
                .readMazeObject(reader, formatVersion);
        return this;
    }
}
