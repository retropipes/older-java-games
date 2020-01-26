/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.Application;
import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.editor.MazeEditor;
import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.generic.GenericTeleport;
import com.puttysoftware.brainmaze.generic.MazeObject;
import com.puttysoftware.brainmaze.resourcemanagers.SoundConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundManager;

public class TwoWayTeleport extends GenericTeleport {
    public TwoWayTeleport() {
        super(0, 0, 0, true, "two_way");
    }

    public TwoWayTeleport(final int destRow, final int destCol,
            final int destFloor) {
        super(destRow, destCol, destFloor, true, "two_way");
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = BrainMaze.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = BrainMaze.getApplication().getEditor();
        return me.editTeleportDestination(MazeEditor.TELEPORT_TYPE_TWOWAY);
    }

    @Override
    public String getName() {
        return "Two-Way Teleport";
    }

    @Override
    public String getPluralName() {
        return "Two-Way Teleports";
    }

    @Override
    public String getDescription() {
        return "Two-Way Teleports send you to their companion at their destination, and are linked such that stepping on the companion sends you back to the original.";
    }
}
