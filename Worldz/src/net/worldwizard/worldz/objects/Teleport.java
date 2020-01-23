/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.editor.WorldEditor;
import net.worldwizard.worldz.generic.GenericTeleport;
import net.worldwizard.worldz.generic.WorldObject;

public class Teleport extends GenericTeleport {
    // Constructors
    public Teleport() {
        super(0, 0, 0, 0);
    }

    public Teleport(final int destinationRow, final int destinationColumn,
            final int destinationFloor, final int destinationLevel) {
        super(destinationRow, destinationColumn, destinationFloor,
                destinationLevel);
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
    public WorldObject editorPropertiesHook() {
        final WorldEditor me = Worldz.getApplication().getEditor();
        final WorldObject mo = me
                .editTeleportDestination(WorldEditor.TELEPORT_TYPE_GENERIC);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Teleports send you to a predetermined destination when stepped on.";
    }
}