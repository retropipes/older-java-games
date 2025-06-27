/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public abstract class AbstractTeleport extends AbstractMazeObject {
    // Fields
    private int destRow;
    private int destCol;
    private int destFloor;

    // Constructors
    protected AbstractTeleport(final boolean doesAcceptPushInto,
            final int attrName) {
        super(false, false, doesAcceptPushInto, false, false, false, false,
                true, false);
        this.setAttributeID(attrName);
        this.setTemplateColor(ColorConstants.COLOR_BLUE);
        this.setAttributeTemplateColor(ColorConstants.COLOR_YELLOW);
    }

    protected AbstractTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final boolean doesAcceptPushInto, final int attrName) {
        super(false, false, doesAcceptPushInto, false, false, false, false,
                true, false);
        this.destRow = destinationRow;
        this.destCol = destinationColumn;
        this.destFloor = destinationFloor;
        this.setAttributeID(attrName);
        this.setTemplateColor(ColorConstants.COLOR_BLUE);
        this.setAttributeTemplateColor(ColorConstants.COLOR_YELLOW);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TELEPORT_BASE;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final AbstractTeleport other = (AbstractTeleport) obj;
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
        return 67 * hash + this.destFloor;
    }

    @Override
    public AbstractTeleport clone() {
        final AbstractTeleport copy = (AbstractTeleport) super.clone();
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
        return MazeRunnerII.getApplication().getMazeManager().getMaze()
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
            final MazeObjectInventory inv) {
        final Application app = MazeRunnerII.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TELEPORT);
    }

    @Override
    public void editorProbeHook() {
        MazeRunnerII.getApplication()
                .showMessage(this.getName() + ": Destination ("
                        + (this.destCol + 1) + "," + (this.destRow + 1) + ","
                        + (this.destFloor + 1) + ")");
    }

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
                return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
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
