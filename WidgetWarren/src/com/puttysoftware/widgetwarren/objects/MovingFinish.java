/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.editor.MazeEditor;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericTeleport;
import com.puttysoftware.widgetwarren.generic.MazeObject;
import com.puttysoftware.widgetwarren.maze.MazeConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class MovingFinish extends GenericTeleport {
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
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.active) {
            final Application app = WidgetWarren.getApplication();
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_FINISH);
            app.getGameManager().solvedLevel();
        } else {
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_WALK);
        }
    }

    public void activate() {
        this.active = true;
        this.activateTimer(WidgetWarren.getApplication().getMazeManager()
                .getMaze().getFinishMoveSpeed());
    }

    public void deactivate() {
        this.active = false;
        this.deactivateTimer();
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        this.active = false;
        final MazeObject obj = WidgetWarren
                .getApplication()
                .getMazeManager()
                .getMazeObject(this.getDestinationRow(),
                        this.getDestinationColumn(),
                        this.getDestinationFloor(), MazeConstants.LAYER_OBJECT);
        if (obj instanceof MovingFinish) {
            final MovingFinish mf = (MovingFinish) obj;
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_CHANGE);
            mf.activate();
        } else {
            final Application app = WidgetWarren.getApplication();
            final MazeObject saved = app.getGameManager().getSavedMazeObject();
            final int px = app.getGameManager().getPlayerManager()
                    .getPlayerLocationX();
            final int py = app.getGameManager().getPlayerManager()
                    .getPlayerLocationY();
            final int pz = app.getGameManager().getPlayerManager()
                    .getPlayerLocationZ();
            final int ax = this.getDestinationRow();
            final int ay = this.getDestinationColumn();
            final int az = this.getDestinationFloor();
            if (saved instanceof MovingFinish && px == ax && py == ay
                    && pz == az) {
                SoundManager.playSound(
                        SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                        SoundConstants.SOUND_FINISH);
                CommonDialogs.showDialog("A finish opens under your feet!");
                app.getGameManager().solvedLevel();
            }
        }
    }

    @Override
    public void editorProbeHook() {
        WidgetWarren.getApplication().showMessage(
                this.getName() + ": Next Moving Finish ("
                        + (this.getDestinationColumn() + 1) + ","
                        + (this.getDestinationRow() + 1) + ","
                        + (this.getDestinationFloor() + 1) + ")");
    }

    @Override
    public MazeObject gameRenderHook() {
        if (this.active) {
            return this;
        } else {
            return new SealedFinish();
        }
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
    public String getPluralName() {
        return "Moving Finishes";
    }

    @Override
    public MazeObject editorPropertiesHook() {
        final MazeEditor me = WidgetWarren.getApplication().getEditor();
        final MazeObject mo = me
                .editTeleportDestination(MazeEditor.TELEPORT_TYPE_MOVING_FINISH);
        return mo;
    }

    @Override
    public String getDescription() {
        return "Moving Finishes lead to the next level, if one exists. Otherwise, entering one solves the maze.";
    }
}