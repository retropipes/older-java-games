/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.generic;

import com.puttysoftware.loopchute.Application;
import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.editor.MazeEditor;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public abstract class GenericRandomInvisibleTeleport
        extends GenericRandomTeleport {
    // Constructors
    public GenericRandomInvisibleTeleport(final int newRandomRangeY,
            final int newRandomRangeX, final String attrName) {
        super(newRandomRangeY, newRandomRangeX, attrName);
        this.setTemplateColor(ColorConstants.COLOR_CYAN);
        this.setAttributeTemplateColor(
                ColorConstants.COLOR_INVISIBLE_TELEPORT_ATTRIBUTE);
    }

    // Scriptability
    @Override
    abstract public String getName();

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = LoopChute.getApplication();
        int dr, dc;
        do {
            dr = this.getDestinationRow();
            dc = this.getDestinationColumn();
        } while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
        app.getGameManager().updatePositionRelative(dr, dc);
        LoopChute.getApplication().showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = LoopChute.getApplication().getEditor();
        return me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_RANDOM_INVISIBLE);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_RANDOM_INVISIBLE_TELEPORT);
        this.type.set(TypeConstants.TYPE_RANDOM_TELEPORT);
    }
}