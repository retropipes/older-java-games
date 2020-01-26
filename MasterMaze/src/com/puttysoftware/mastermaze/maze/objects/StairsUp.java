/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.editor.MazeEditorLogic;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericTeleport;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class StairsUp extends GenericTeleport {
    // Constructors
    public StairsUp() {
        super(0, 0, 0, false, ObjectImageConstants.OBJECT_IMAGE_NONE);
        this.setTemplateColor(ColorConstants.COLOR_NONE);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    // For derived classes only
    protected StairsUp(final boolean doesAcceptPushInto) {
        super(doesAcceptPushInto, ObjectImageConstants.OBJECT_IMAGE_NONE);
        this.setTemplateColor(ColorConstants.COLOR_NONE);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_STAIRS_UP;
    }

    @Override
    public String getName() {
        return "Stairs Up";
    }

    @Override
    public String getPluralName() {
        return "Sets of Stairs Up";
    }

    @Override
    public int getDestinationRow() {
        final Application app = MasterMaze.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationX();
    }

    @Override
    public int getDestinationColumn() {
        final Application app = MasterMaze.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationY();
    }

    @Override
    public int getDestinationFloor() {
        final Application app = MasterMaze.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationZ() + 1;
    }

    @Override
    public int getDestinationLevel() {
        final Application app = MasterMaze.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationW();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = MasterMaze.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_UP);
    }

    @Override
    public void editorPlaceHook() {
        final MazeEditorLogic me = MasterMaze.getApplication().getEditor();
        me.pairStairs(MazeEditorLogic.STAIRS_UP);
    }

    @Override
    public MazeObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Stairs Up lead to the floor above.";
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
