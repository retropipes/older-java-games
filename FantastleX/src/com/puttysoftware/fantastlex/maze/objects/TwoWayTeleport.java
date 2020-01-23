/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.editor.MazeEditorLogic;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.abc.AbstractTeleport;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class TwoWayTeleport extends AbstractTeleport {
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
            final int dirY, final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
        final MazeEditorLogic me = FantastleX.getApplication().getEditor();
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
