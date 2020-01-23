/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.editor.MazeEditor;
import com.puttysoftware.mazemode.generic.GenericTeleport;
import com.puttysoftware.mazemode.generic.MazeObject;

public class Teleport extends GenericTeleport {
    // Constructors
    public Teleport() {
        super(0, 0, 0);
    }

    public Teleport(final int destinationRow, final int destinationColumn,
            final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor);
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
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = MazeMode.getApplication().getEditor();
        final MazeObject mo = me
                .editTeleportDestination(MazeEditor.TELEPORT_TYPE_GENERIC);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Teleports send you to a predetermined destination when stepped on.";
    }
}