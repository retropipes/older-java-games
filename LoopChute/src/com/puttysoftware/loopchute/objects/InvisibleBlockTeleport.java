/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.editor.MazeEditor;
import com.puttysoftware.loopchute.generic.GenericInvisibleBlockTeleport;
import com.puttysoftware.loopchute.generic.MazeObject;

public class InvisibleBlockTeleport extends GenericInvisibleBlockTeleport {
    // Constructors
    public InvisibleBlockTeleport() {
        super(0, 0, 0, "teleport");
    }

    public InvisibleBlockTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor, "teleport");
    }

    @Override
    public String getName() {
        return "Invisible Block Teleport";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Invisible Block Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = LoopChute.getApplication().getEditor();
        return me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_INVISIBLE_BLOCK);
    }

    @Override
    public String getDescription() {
        return "Invisible Block Teleports send blocks to a predetermined destination when blocks are moved into them.";
    }
}