/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.editor.MazeEditorLogic;
import com.puttysoftware.mastermaze.maze.generic.GenericInvisibleTeleport;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class InvisibleTeleport extends GenericInvisibleTeleport {
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
    public MazeObject editorPropertiesHook() {
        final MazeEditorLogic me = MasterMaze.getApplication().getEditor();
        return me
                .editTeleportDestination(MazeEditorLogic.TELEPORT_TYPE_INVISIBLE_GENERIC);
    }

    @Override
    public String getDescription() {
        return "Invisible Teleports behave like regular teleports, except for the fact that they can't be seen.";
    }
}