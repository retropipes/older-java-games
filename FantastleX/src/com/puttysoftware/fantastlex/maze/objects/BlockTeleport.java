/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.editor.MazeEditorLogic;
import com.puttysoftware.fantastlex.maze.abc.AbstractBlockTeleport;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class BlockTeleport extends AbstractBlockTeleport {
    // Constructors
    public BlockTeleport() {
        super(0, 0, 0, ObjectImageConstants.OBJECT_IMAGE_TELEPORT);
    }

    public BlockTeleport(final int destinationRow, final int destinationColumn,
            final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor,
                ObjectImageConstants.OBJECT_IMAGE_TELEPORT);
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
    public AbstractMazeObject editorPropertiesHook() {
        final MazeEditorLogic me = FantastleX.getApplication().getEditor();
        return me.editTeleportDestination(MazeEditorLogic.TELEPORT_TYPE_BLOCK);
    }

    @Override
    public String getDescription() {
        return "Block Teleports send blocks to a predetermined destination when blocks are moved into them.";
    }
}