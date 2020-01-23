/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractNWayTeleport;
import com.puttysoftware.dungeondiver4.editor.DungeonEditorLogic;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class NWayTeleport extends AbstractNWayTeleport {
    // Constructors
    public NWayTeleport() {
        super(1, ObjectImageConstants.OBJECT_IMAGE_N_WAY);
    }

    @Override
    public String getName() {
        return "N-Way Teleport";
    }

    @Override
    public String getPluralName() {
        return "N-Way Teleports";
    }

    @Override
    public String getDescription() {
        return "N-Way Teleports send you to one of N predetermined destinations when stepped on, depending on which destination is selected.";
    }

    @Override
    public final AbstractDungeonObject editorPropertiesHook() {
        DungeonEditorLogic me = DungeonDiver4.getApplication().getEditor();
        me.setNWayDestCount(this.getDestinationCount());
        me.setNWayEdited(this);
        me.editTeleportDestination(DungeonEditorLogic.TELEPORT_TYPE_N_WAY);
        return null;
    }
}