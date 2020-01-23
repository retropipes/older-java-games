/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.editor.MazeEditorLogic;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public abstract class AbstractRandomInvisibleTeleport extends
        AbstractRandomTeleport {
    // Constructors
    public AbstractRandomInvisibleTeleport(final int newRandomRangeY,
            final int newRandomRangeX, final int attrName) {
        super(newRandomRangeY, newRandomRangeX, attrName);
        this.setTemplateColor(ColorConstants.COLOR_CYAN);
        this.setAttributeTemplateColor(ColorConstants.COLOR_INVISIBLE_TELEPORT_ATTRIBUTE);
    }

    // Scriptability
    @Override
    abstract public String getName();

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        Application app = MazeRunnerII.getApplication();
        int dr, dc;
        do {
            dr = this.getDestinationRow();
            dc = this.getDestinationColumn();
        } while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
        app.getGameManager().updatePositionRelative(dr, dc, 0);
        MazeRunnerII.getApplication().showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
        MazeEditorLogic me = MazeRunnerII.getApplication().getEditor();
        return me
                .editTeleportDestination(MazeEditorLogic.TELEPORT_TYPE_RANDOM_INVISIBLE);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_RANDOM_INVISIBLE_TELEPORT);
        this.type.set(TypeConstants.TYPE_RANDOM_TELEPORT);
    }
}