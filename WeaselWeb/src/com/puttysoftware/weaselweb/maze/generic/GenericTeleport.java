/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public abstract class GenericTeleport extends MazeObject {
    // Fields
    private int destRow;
    private int destCol;
    private int destFloor;

    // Constructors
    protected GenericTeleport() {
        super();
        this.destRow = 0;
        this.destCol = 0;
        this.destFloor = 0;
        this.setType(TypeConstants.TYPE_TELEPORT);
    }

    protected GenericTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor) {
        super();
        this.destRow = destinationRow;
        this.destCol = destinationColumn;
        this.destFloor = destinationFloor;
        this.setType(TypeConstants.TYPE_TELEPORT);
    }

    protected GenericTeleport(final boolean doesAcceptPushInto) {
        super();
        this.setPushableInto(doesAcceptPushInto);
        this.setType(TypeConstants.TYPE_TELEPORT);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final GenericTeleport other = (GenericTeleport) obj;
        if (this.destRow != other.destRow) {
            return false;
        }
        if (this.destCol != other.destCol) {
            return false;
        }
        if (this.destFloor != other.destFloor) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.destRow;
        hash = 67 * hash + this.destCol;
        hash = 67 * hash + this.destFloor;
        return hash;
    }

    @Override
    public GenericTeleport clone() {
        final GenericTeleport copy = (GenericTeleport) super.clone();
        copy.destCol = this.destCol;
        copy.destFloor = this.destFloor;
        copy.destRow = this.destRow;
        return copy;
    }

    // Accessor methods
    public int getDestinationRow() {
        return this.destRow;
    }

    public int getDestinationColumn() {
        return this.destCol;
    }

    public int getDestinationFloor() {
        return this.destFloor;
    }

    public int getDestinationLevel() {
        return WeaselWeb.getApplication().getGameManager().getPlayerManager()
                .getPlayerLocationW();
    }

    // Transformer methods
    public void setDestinationRow(final int destinationRow) {
        this.destRow = destinationRow;
    }

    public void setDestinationColumn(final int destinationColumn) {
        this.destCol = destinationColumn;
    }

    public void setDestinationFloor(final int destinationFloor) {
        this.destFloor = destinationFloor;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = WeaselWeb.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public void editorProbeHook() {
        WeaselWeb.getApplication()
                .showMessage(this.getName() + ": Destination ("
                        + (this.destCol + 1) + "," + (this.destRow + 1) + ","
                        + (this.destFloor + 1) + ")");
    }

    @Override
    public abstract MazeObject editorPropertiesHook();

    @Override
    public boolean defersSetProperties() {
        return true;
    }

    @Override
    public int getCustomProperty(final int propID) {
        switch (propID) {
            case 1:
                return this.destRow;
            case 2:
                return this.destCol;
            case 3:
                return this.destFloor;
            default:
                return MazeObject.DEFAULT_CUSTOM_VALUE;
        }
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        switch (propID) {
            case 1:
                this.destRow = value;
                break;
            case 2:
                this.destCol = value;
                break;
            case 3:
                this.destFloor = value;
                break;
            default:
                break;
        }
    }

    @Override
    public int getCustomFormat() {
        return 3;
    }
}
