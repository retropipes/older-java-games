/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.editor.MazeEditor;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.generic.MazeObject;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

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
        final Application app = Main.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_FINISH);
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
        Main.getApplication().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public void editorProbeHook() {
        Main.getApplication().showMessage(
                this.getName() + " Level " + (this.getDestinationLevel() + 1));
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = Main.getApplication().getEditor();
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