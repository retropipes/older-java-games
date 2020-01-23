/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.editor.MazeEditorLogic;
import com.puttysoftware.mastermaze.maze.generic.GenericTeleport;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class TwoWayTeleport extends GenericTeleport {
    public TwoWayTeleport() {
        super(0, 0, 0, true, ObjectImageConstants.OBJECT_IMAGE_TWO_WAY);
    }

    public TwoWayTeleport(final int destRow, final int destCol,
            final int destFloor) {
        super(destRow, destCol, destFloor, true,
                ObjectImageConstants.OBJECT_IMAGE_TWO_WAY);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = MasterMaze.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditorLogic me = MasterMaze.getApplication().getEditor();
        return me.editTeleportDestination(MazeEditorLogic.TELEPORT_TYPE_TWOWAY);
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
