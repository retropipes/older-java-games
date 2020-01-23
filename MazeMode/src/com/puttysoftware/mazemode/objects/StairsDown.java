/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode.objects;

import com.puttysoftware.mazemode.Application;
import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.editor.MazeEditor;
import com.puttysoftware.mazemode.game.ObjectInventory;
import com.puttysoftware.mazemode.generic.GenericTeleport;
import com.puttysoftware.mazemode.generic.MazeObject;
import com.puttysoftware.mazemode.resourcemanagers.SoundConstants;
import com.puttysoftware.mazemode.resourcemanagers.SoundManager;

public class StairsDown extends GenericTeleport {
    // Constructors
    public StairsDown() {
        super(0, 0, 0);
    }

    // For derived classes only
    protected StairsDown(final boolean doesAcceptPushInto) {
        super(doesAcceptPushInto);
    }

    @Override
    public String getName() {
        return "Stairs Down";
    }

    @Override
    public String getPluralName() {
        return "Sets of Stairs Down";
    }

    @Override
    public int getDestinationRow() {
        final Application app = MazeMode.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationX();
    }

    @Override
    public int getDestinationColumn() {
        final Application app = MazeMode.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationY();
    }

    @Override
    public int getDestinationFloor() {
        final Application app = MazeMode.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationZ() - 1;
    }

    @Override
    public int getDestinationLevel() {
        final Application app = MazeMode.getApplication();
        return app.getGameManager().getPlayerManager().getPlayerLocationW();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = MazeMode.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_DOWN);
    }

    @Override
    public void editorPlaceHook() {
        final MazeEditor me = MazeMode.getApplication().getEditor();
        me.pairStairs(MazeEditor.STAIRS_DOWN);
    }

    @Override
    public MazeObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Stairs Down lead to the floor below.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
