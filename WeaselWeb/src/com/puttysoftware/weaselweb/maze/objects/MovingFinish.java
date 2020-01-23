/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.editor.MazeEditor;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;
import com.puttysoftware.weaselweb.maze.generic.MazeObject;
import com.puttysoftware.weaselweb.resourcemanagers.ImageManager;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class MovingFinish extends Finish {
    // Fields
    private boolean active;

    // Constructors
    public MovingFinish() {
        super();
        this.active = false;
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Finish");
    }

    public MovingFinish(final int destinationRow, final int destinationColumn,
            final int destinationFloor) {
        super();
        this.active = false;
        this.setDestinationRow(destinationRow);
        this.setDestinationColumn(destinationColumn);
        this.setDestinationFloor(destinationFloor);
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Finish");
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.active) {
            final Application app = WeaselWeb.getApplication();
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
        this.activateTimer(WeaselWeb.getApplication().getMazeManager()
                .getMaze().getFinishMoveSpeed());
    }

    public void deactivate() {
        this.active = false;
        this.deactivateTimer();
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        this.active = false;
        final MazeObject obj = WeaselWeb
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
            final Application app = WeaselWeb.getApplication();
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
        WeaselWeb.getApplication().showMessage(
                this.getName() + ": Next Moving Finish ("
                        + (this.getDestinationColumn() + 1) + ","
                        + (this.getDestinationRow() + 1) + ","
                        + (this.getDestinationFloor() + 1) + ")");
    }

    @Override
    public BufferedImageIcon gameRenderHook(final int x, final int y,
            final int z) {
        if (this.active) {
            return ImageManager.getImage("Finish");
        } else {
            return ImageManager.getImage("Sealed Finish");
        }
    }

    @Override
    public String getName() {
        return "Moving Finish";
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
        final MazeEditor me = WeaselWeb.getApplication().getEditor();
        final MazeObject mo = me
                .editTeleportDestination(MazeEditor.TELEPORT_TYPE_MOVING_FINISH);
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