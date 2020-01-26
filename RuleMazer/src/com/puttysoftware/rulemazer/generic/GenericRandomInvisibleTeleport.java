/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.generic;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.editor.MazeEditor;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public abstract class GenericRandomInvisibleTeleport
        extends GenericRandomTeleport {
    // Constructors
    public GenericRandomInvisibleTeleport(final int newRandomRangeY,
            final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX);
    }

    // Scriptability
    @Override
    abstract public String getName();

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = Main.getApplication();
        int dr, dc;
        do {
            dr = this.getDestinationRow();
            dc = this.getDestinationColumn();
        } while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
        app.getGameManager().updatePositionRelative(dr, dc);
        Main.getApplication().showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = Main.getApplication().getEditor();
        final MazeObject mo = me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_RANDOM_INVISIBLE);
        return mo;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_RANDOM_INVISIBLE_TELEPORT);
        this.type.set(TypeConstants.TYPE_RANDOM_TELEPORT);
    }
}