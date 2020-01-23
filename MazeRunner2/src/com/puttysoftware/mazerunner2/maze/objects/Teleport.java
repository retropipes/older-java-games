/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.editor.MazeEditorLogic;
import com.puttysoftware.mazerunner2.maze.abc.AbstractTeleport;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class Teleport extends AbstractTeleport {
    // Constructors
    public Teleport() {
        super(0, 0, 0, true, ObjectImageConstants.OBJECT_IMAGE_TELEPORT);
    }

    public Teleport(final int destinationRow, final int destinationColumn,
            final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor, true,
                ObjectImageConstants.OBJECT_IMAGE_TELEPORT);
    }

    @Override
    public String getName() {
        return "Teleport";
    }

    @Override
    public String getPluralName() {
        return "Teleports";
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
        MazeEditorLogic me = MazeRunnerII.getApplication().getEditor();
        return me
                .editTeleportDestination(MazeEditorLogic.TELEPORT_TYPE_GENERIC);
    }

    @Override
    public String getDescription() {
        return "Teleports send you to a predetermined destination when stepped on.";
    }
}