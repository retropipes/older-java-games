/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.abc;

import java.io.IOException;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.objects.Empty;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.Layers;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public abstract class GenericCharacter extends MazeObjectModel {
    // Fields
    private MazeObjectModel savedObject;
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

    public MazeObjectModel getSavedObject() {
        return this.savedObject;
    }

    public void setSavedObject(final MazeObjectModel obj) {
        this.savedObject = obj;
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return Layers.OBJECT;
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
        Mazer5D.getBagOStuff().showMessage("Ouch, you shot yourself!");
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_PLAIN) {
            Mazer5D.getBagOStuff().getMazeManager().getMaze()
                    .doDamage(GenericCharacter.SHOT_SELF_NORMAL_DAMAGE);
        } else {
            Mazer5D.getBagOStuff().getMazeManager().getMaze()
                    .doDamage(GenericCharacter.SHOT_SELF_SPECIAL_DAMAGE);
        }
        SoundPlayer.playSound(SoundIndex.LAVA, SoundGroup.GAME);
        return false;
    }

    @Override
    public int getCustomFormat() {
        if (Mazer5D.getBagOStuff().getMazeManager().isMazeXML2Compatible()) {
            return 0;
        } else {
            return MazeObjectModel.CUSTOM_FORMAT_MANUAL_OVERRIDE;
        }
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObjectModel.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    protected void writeMazeObjectHookXML(final XDataWriter writer)
            throws IOException {
        this.savedObject.writeMazeObjectXML(writer);
    }

    @Override
    protected MazeObjectModel readMazeObjectHookXML(final XDataReader reader,
            final int formatVersion) throws IOException {
        
        this.savedObject = GameObjects
                .readObject(reader, formatVersion);
        return this;
    }
}
