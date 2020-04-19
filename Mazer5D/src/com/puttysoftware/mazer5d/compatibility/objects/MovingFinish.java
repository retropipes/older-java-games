/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.editor.MazeEditor;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.Layers;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

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
            final BagOStuff app = Mazer5D.getBagOStuff();
            SoundPlayer.playSound(SoundIndex.FINISH, SoundGroup.GAME);
            app.getGameManager().solvedLevel();
        } else {
            SoundPlayer.playSound(SoundIndex.WALK, SoundGroup.GAME);
        }
    }

    @Override
    public void activate() {
        this.active = true;
        this.activateTimer(Mazer5D.getBagOStuff().getMazeManager().getMaze()
                .getFinishMoveSpeed());
    }

    @Override
    public void deactivate() {
        this.active = false;
        this.deactivateTimer();
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        this.active = false;
        final MazeObjectModel obj = Mazer5D.getBagOStuff().getMazeManager()
                .getMazeObject(this.getDestinationRow(), this
                        .getDestinationColumn(), this.getDestinationFloor(),
                        Layers.OBJECT);
        if (obj instanceof MovingFinish) {
            final MovingFinish mf = (MovingFinish) obj;
            SoundPlayer.playSound(SoundIndex.CHANGE, SoundGroup.GAME);
            mf.activate();
        } else {
            final BagOStuff app = Mazer5D.getBagOStuff();
            final MazeObjectModel saved = app.getGameManager()
                    .getSavedMazeObject();
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
                SoundPlayer.playSound(SoundIndex.FINISH, SoundGroup.GAME);
                CommonDialogs.showDialog("A finish opens under your feet!");
                app.getGameManager().solvedLevel();
            }
        }
    }

    @Override
    public void editorProbeHook() {
        Mazer5D.getBagOStuff().showMessage(this.getName()
                + ": Next Moving Finish (" + (this.getDestinationColumn() + 1)
                + "," + (this.getDestinationRow() + 1) + "," + (this
                        .getDestinationFloor() + 1) + ")");
    }

    @Override
    public MazeObjectModel gameRenderHook() {
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
    public boolean defersSetProperties() {
        return true;
    }

    @Override
    public MazeObjectModel editorPropertiesHook() {
        final MazeEditor me = Mazer5D.getBagOStuff().getEditor();
        final MazeObjectModel mo = me.editTeleportDestination(
                MazeEditor.TELEPORT_TYPE_MOVING_FINISH);
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
            return MazeObjectModel.DEFAULT_CUSTOM_VALUE;
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

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.MOVING_FINISH;
    }
}