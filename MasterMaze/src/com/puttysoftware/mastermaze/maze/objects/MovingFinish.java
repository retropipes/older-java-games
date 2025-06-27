/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.editor.MazeEditorLogic;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class MovingFinish extends Finish {
    // Fields
    private boolean active;

    // Constructors
    public MovingFinish() {
        super();
        this.active = false;
    }

    public MovingFinish(final int destinationRow, final int destinationColumn,
            final int destinationFloor) {
        super();
        this.active = false;
        this.setDestinationRow(destinationRow);
        this.setDestinationColumn(destinationColumn);
        this.setDestinationFloor(destinationFloor);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        if (this.active) {
            final Application app = MasterMaze.getApplication();
            SoundManager.playSound(SoundConstants.SOUND_FINISH);
            app.getGameManager().solvedLevel();
        } else {
            SoundManager.playSound(SoundConstants.SOUND_WALK);
        }
    }

    public void activate() {
        this.active = true;
        this.activateTimer(MasterMaze.getApplication().getMazeManager()
                .getMaze().getFinishMoveSpeed());
    }

    public void deactivate() {
        this.active = false;
        this.deactivateTimer();
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        this.active = false;
        final MazeObject obj = MasterMaze.getApplication().getMazeManager()
                .getMazeObject(this.getDestinationRow(),
                        this.getDestinationColumn(), this.getDestinationFloor(),
                        MazeConstants.LAYER_OBJECT);
        if (obj instanceof MovingFinish) {
            final MovingFinish mf = (MovingFinish) obj;
            SoundManager.playSound(SoundConstants.SOUND_CHANGE);
            mf.activate();
        } else {
            final Application app = MasterMaze.getApplication();
            final MazeObject saved = app.getGameManager().getSavedMazeObject();
            final int px = app.getMazeManager().getMaze().getPlayerLocationX();
            final int py = app.getMazeManager().getMaze().getPlayerLocationY();
            final int pz = app.getMazeManager().getMaze().getPlayerLocationZ();
            final int ax = this.getDestinationRow();
            final int ay = this.getDestinationColumn();
            final int az = this.getDestinationFloor();
            if (saved instanceof MovingFinish && px == ax && py == ay
                    && pz == az) {
                SoundManager.playSound(SoundConstants.SOUND_FINISH);
                CommonDialogs.showDialog("A finish opens under your feet!");
                app.getGameManager().solvedLevel();
            }
        }
    }

    @Override
    public void editorProbeHook() {
        MasterMaze.getApplication()
                .showMessage(this.getName() + ": Next Moving Finish ("
                        + (this.getDestinationColumn() + 1) + ","
                        + (this.getDestinationRow() + 1) + ","
                        + (this.getDestinationFloor() + 1) + ")");
    }

    @Override
    public MazeObject gameRenderHook(final int x, final int y, final int z) {
        if (this.active) {
            return this;
        } else {
            return new SealedFinish();
        }
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_LARGE_MOVE;
    }

    @Override
    public int getGameAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_NONE;
    }

    @Override
    public int getAttributeTemplateColor() {
        return ColorConstants.COLOR_WHITE;
    }

    @Override
    public int getGameAttributeTemplateColor() {
        return ColorConstants.COLOR_NONE;
    }

    @Override
    public String getName() {
        return "Moving Finish";
    }

    @Override
    public String getGameName() {
        return "Finish";
    }

    @Override
    public int getGameBaseID() {
        if (this.active) {
            return ObjectImageConstants.OBJECT_IMAGE_FINISH;
        } else {
            return ObjectImageConstants.OBJECT_IMAGE_SEALED_FINISH;
        }
    }

    @Override
    public String getPluralName() {
        return "Moving Finishes";
    }

    @Override
    public boolean defersSetProperties() {
        return true;
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditorLogic me = MasterMaze.getApplication().getEditor();
        final MazeObject mo = me.editTeleportDestination(
                MazeEditorLogic.TELEPORT_TYPE_MOVING_FINISH);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Moving Finishes lead to the next level, if one exists. Otherwise, entering one solves the maze.";
    }

    @Override
    public int getCustomFormat() {
        return 3;
    }

    @Override
    public int getCustomProperty(final int propID) {
        switch (propID) {
            case 1:
                return this.getDestinationRow();
            case 2:
                return this.getDestinationColumn();
            case 3:
                return this.getDestinationFloor();
            default:
                return MazeObject.DEFAULT_CUSTOM_VALUE;
        }
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        switch (propID) {
            case 1:
                this.setDestinationRow(value);
                break;
            case 2:
                this.setDestinationColumn(value);
                break;
            case 3:
                this.setDestinationFloor(value);
                break;
            default:
                break;
        }
    }
}