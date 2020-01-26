/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.editor.MazeEditor;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.generic.GenericTeleport;
import com.puttysoftware.rulemazer.generic.MazeObject;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public class TwoWayTeleport extends GenericTeleport {
    public TwoWayTeleport() {
        super(0, 0, 0);
    }

    public TwoWayTeleport(final int destRow, final int destCol,
            final int destFloor) {
        super(destRow, destCol, destFloor);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = Main.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = Main.getApplication().getEditor();
        final MazeObject mo = me
                .editTeleportDestination(MazeEditor.TELEPORT_TYPE_TWOWAY);
        return mo;
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
