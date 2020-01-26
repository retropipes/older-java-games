/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.generic;

import com.puttysoftware.brainmaze.Application;
import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.editor.MazeEditor;
import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.resourcemanagers.SoundConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundManager;

public abstract class GenericRandomInvisibleTeleport
        extends GenericRandomTeleport {
    // Constructors
    public GenericRandomInvisibleTeleport(final int newRandomRangeY,
            final int newRandomRangeX, final String attrName) {
        super(newRandomRangeY, newRandomRangeX, attrName);
        this.setTemplateColor(ColorConstants.COLOR_CYAN);
        this.setAttributeTemplateColor(
                ColorConstants.COLOR_INVISIBLE_TELEPORT_ATTRIBUTE);
    }

    // Scriptability
    @Override
    abstract public String getName();

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = BrainMaze.getApplication();
        int dr, dc;
        do {
            dr = this.getDestinationRow();
            dc = this.getDestinationColumn();
        } while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
        app.getGameManager().updatePositionRelative(dr, dc);
        BrainMaze.getApplication().showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = BrainMaze.getApplication().getEditor();
        return me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_RANDOM_INVISIBLE);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_RANDOM_INVISIBLE_TELEPORT);
        this.type.set(TypeConstants.TYPE_RANDOM_TELEPORT);
    }
}