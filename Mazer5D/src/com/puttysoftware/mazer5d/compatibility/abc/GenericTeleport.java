/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.abc;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.Layers;

public abstract class GenericTeleport extends MazeObjectModel {
    // Fields
    private int destRow;
    private int destCol;
    private int destFloor;

    // Constructors
    protected GenericTeleport() {
        super(false);
        this.destRow = 0;
        this.destCol = 0;
        this.destFloor = 0;
    }

    protected GenericTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor) {
        super(false);
        this.destRow = destinationRow;
        this.destCol = destinationColumn;
        this.destFloor = destinationFloor;
    }

    protected GenericTeleport(final boolean doesAcceptPushInto) {
        super(false, false, doesAcceptPushInto, false, false, false, false,
                true, false, 0);
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
        return Mazer5D.getBagOStuff().getGameManager().getPlayerManager()
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
    public void activate() {
        // Do nothing
    }

    public void deactivate() {
        // Do nothing
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        SoundPlayer.playSound(SoundIndex.TELEPORT, SoundGroup.GAME);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return Layers.OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TELEPORT);
    }

    @Override
    public void editorProbeHook() {
        Mazer5D.getBagOStuff().showMessage(this.getName() + ": Destination ("
                + (this.destCol + 1) + "," + (this.destRow + 1) + ","
                + (this.destFloor + 1) + ")");
    }

    @Override
    public abstract MazeObjectModel editorPropertiesHook();

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
            return MazeObjectModel.DEFAULT_CUSTOM_VALUE;
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

    public void setDestinationLevel(final int inDestW) {
        // Do nothing
    }
}
