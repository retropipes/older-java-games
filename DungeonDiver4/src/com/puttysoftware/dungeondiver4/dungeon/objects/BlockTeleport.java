/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBlockTeleport;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.editor.DungeonEditorLogic;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

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
    public AbstractDungeonObject editorPropertiesHook() {
        final DungeonEditorLogic me = DungeonDiver4.getApplication()
                .getEditor();
        return me.editTeleportDestination(
                DungeonEditorLogic.TELEPORT_TYPE_BLOCK);
    }

    @Override
    public String getDescription() {
        return "Block Teleports send blocks to a predetermined destination when blocks are moved into them.";
    }
}