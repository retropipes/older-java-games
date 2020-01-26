/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.editor.MazeEditor;
import com.puttysoftware.widgetwarren.generic.GenericInvisibleTeleport;
import com.puttysoftware.widgetwarren.generic.MazeObject;

public class InvisibleTeleport extends GenericInvisibleTeleport {
    // Constructors
    public InvisibleTeleport() {
        super(0, 0, 0);
    }

    public InvisibleTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor);
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
        final MazeEditor me = WidgetWarren.getApplication().getEditor();
        final MazeObject mo = me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_INVISIBLE_GENERIC);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Invisible Teleports behave like regular teleports, except for the fact that they can't be seen.";
    }
}