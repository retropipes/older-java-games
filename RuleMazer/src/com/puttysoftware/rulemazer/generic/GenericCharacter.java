/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.generic;

import java.io.IOException;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.maze.MazeConstants;
import com.puttysoftware.rulemazer.objects.Empty;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;
import com.puttysoftware.xmlio.XMLDataReader;
import com.puttysoftware.xmlio.XMLDataWriter;

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
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
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
            final int locZ, final int dirX, final int dirY,
            final int arrowType, final ObjectInventory inv) {
        // Shot self
        Main.getApplication().showMessage("Ouch, you shot yourself!");
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_PLAIN) {
            Main.getApplication().getMazeManager().getMaze()
                    .doDamage(GenericCharacter.SHOT_SELF_NORMAL_DAMAGE);
        } else {
            Main.getApplication().getMazeManager().getMaze()
                    .doDamage(GenericCharacter.SHOT_SELF_SPECIAL_DAMAGE);
        }
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_LAVA);
        return false;
    }

    @Override
    public int getCustomFormat() {
        if (Main.getApplication().getMazeManager().isMazeXML2Compatible()) {
            return 0;
        } else {
            return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
        }
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
    protected void writeMazeObjectHookXML(final XMLDataWriter writer)
            throws IOException {
        this.savedObject.writeMazeObjectXML(writer);
    }

    @Override
    protected MazeObject readMazeObjectHookXML(final XMLDataReader reader,
            final int formatVersion) throws IOException {
        this.savedObject = Main.getApplication().getObjects()
                .readMazeObjectXML(reader, formatVersion);
        return this;
    }
}
