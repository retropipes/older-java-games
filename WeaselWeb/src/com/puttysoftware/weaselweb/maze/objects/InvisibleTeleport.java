/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.editor.MazeEditor;
import com.puttysoftware.weaselweb.maze.generic.GenericInvisibleTeleport;
import com.puttysoftware.weaselweb.maze.generic.MazeObject;

public class InvisibleTeleport extends GenericInvisibleTeleport {
    // Constructors
    public InvisibleTeleport() {
        super(0, 0, 0);
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Empty");
    }

    public InvisibleTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor);
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Empty");
    }

    // Scriptability
    @Override
    public String getName() {
        return "Invisible Teleport";
    }

    @Override
    public String getPluralName() {
        return "Invisible Teleports";
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = WeaselWeb.getApplication().getEditor();
        final MazeObject mo = me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_INVISIBLE_GENERIC);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Invisible Teleports behave like regular teleports, except for the fact that they can't be seen.";
    }
}