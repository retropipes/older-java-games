/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.editor.MazeEditor;
import com.puttysoftware.brainmaze.generic.GenericBlockTeleport;
import com.puttysoftware.brainmaze.generic.MazeObject;

public class BlockTeleport extends GenericBlockTeleport {
    // Constructors
    public BlockTeleport() {
        super(0, 0, 0, "teleport");
    }

    public BlockTeleport(final int destinationRow, final int destinationColumn,
            final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor, "teleport");
    }

    @Override
    public String getName() {
        return "Block Teleport";
    }

    @Override
    public String getPluralName() {
        return "Block Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = BrainMaze.getApplication().getEditor();
        return me.editTeleportDestination(MazeEditor.TELEPORT_TYPE_BLOCK);
    }

    @Override
    public String getDescription() {
        return "Block Teleports send blocks to a predetermined destination when blocks are moved into them.";
    }
}