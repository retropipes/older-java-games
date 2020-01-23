/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.editor.WorldEditor;
import net.worldwizard.worldz.generic.GenericInvisibleTeleport;
import net.worldwizard.worldz.generic.WorldObject;

public class InvisibleTeleport extends GenericInvisibleTeleport {
    // Constructors
    public InvisibleTeleport() {
        super(0, 0, 0, 0);
    }

    public InvisibleTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int destinationLevel) {
        super(destinationRow, destinationColumn, destinationFloor,
                destinationLevel);
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
    public WorldObject editorPropertiesHook() {
        final WorldEditor me = Worldz.getApplication().getEditor();
        final WorldObject mo = me
                .editTeleportDestination(WorldEditor.TELEPORT_TYPE_INVISIBLE_GENERIC);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Invisible Teleports behave like regular teleports, except for the fact that they can't be seen.";
    }
}