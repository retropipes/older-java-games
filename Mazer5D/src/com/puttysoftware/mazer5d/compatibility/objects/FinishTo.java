/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.editor.MazeEditor;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class FinishTo extends Finish {
    // Fields
    private int destinationLevel;

    // Constructors
    public FinishTo() {
        super();
        this.destinationLevel = 0;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        SoundPlayer.playSound(SoundIndex.FINISH, SoundGroup.GAME);
        app.getGameManager().solvedLevelWarp(this.getDestinationLevel());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + this.destinationLevel;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof FinishTo)) {
            return false;
        }
        final FinishTo other = (FinishTo) obj;
        if (this.destinationLevel != other.destinationLevel) {
            return false;
        }
        return true;
    }

    @Override
    public int getDestinationLevel() {
        return this.destinationLevel;
    }

    public void setDestinationLevel(final int level) {
        this.destinationLevel = level;
    }

    @Override
    public String getName() {
        return "Finish To";
    }

    @Override
    public String getPluralName() {
        return "Finishes To";
    }

    @Override
    public void gameProbeHook() {
        Mazer5D.getBagOStuff().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public void editorProbeHook() {
        Mazer5D.getBagOStuff().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public MazeObjectModel editorPropertiesHook() {
        final MazeEditor me = Mazer5D.getBagOStuff().getEditor();
        me.editFinishToDestination(this);
        return this;
    }

    @Override
    public String getDescription() {
        return "Finishes To behave like regular Finishes, except that the level they send you to might not be the next one.";
    }

    @Override
    public int getCustomFormat() {
        return 1;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return this.destinationLevel;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        this.destinationLevel = value;
    }
}