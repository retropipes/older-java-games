/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.editor.MazeEditorLogic;
import com.puttysoftware.mazerunner2.maze.abc.AbstractTeleport;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public class StairsUp extends AbstractTeleport {
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
        Application app = MazeRunnerII.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationX();
    }

    @Override
    public int getDestinationColumn() {
        Application app = MazeRunnerII.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationY();
    }

    @Override
    public int getDestinationFloor() {
        Application app = MazeRunnerII.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationZ() + 1;
    }

    @Override
    public int getDestinationLevel() {
        Application app = MazeRunnerII.getApplication();
        return app.getMazeManager().getMaze().getPlayerLocationW();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        Application app = MazeRunnerII.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_UP);
    }

    @Override
    public void editorPlaceHook() {
        MazeEditorLogic me = MazeRunnerII.getApplication().getEditor();
        me.pairStairs(MazeEditorLogic.STAIRS_UP);
    }

    @Override
    public AbstractMazeObject editorPropertiesHook() {
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
    public int getCustomProperty(int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }
}
