/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.editor.MazeEditorLogic;
import com.puttysoftware.mazerunner2.maze.abc.AbstractInvisibleTeleport;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;

public class InvisibleTeleport extends AbstractInvisibleTeleport {
    // Constructors
    public InvisibleTeleport() {
        super(0, 0, 0, ObjectImageConstants.OBJECT_IMAGE_TELEPORT);
    }

    public InvisibleTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor,
                ObjectImageConstants.OBJECT_IMAGE_TELEPORT);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Invisible Teleport";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Invisible Teleports";
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
        final MazeEditorLogic me = MazeRunnerII.getApplication().getEditor();
        return me.editTeleportDestination(
                MazeEditorLogic.TELEPORT_TYPE_INVISIBLE_GENERIC);
    }

    @Override
    public String getDescription() {
        return "Invisible Teleports behave like regular teleports, except for the fact that they can't be seen.";
    }
}