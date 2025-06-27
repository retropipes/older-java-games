/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericTeleport extends WorldObject {
    // Fields
    private int destRow;
    private int destCol;
    private int destFloor;
    private int destLevel;

    // Constructors
    protected GenericTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor,
            final int destinationLevel) {
        super(false);
        this.destRow = destinationRow;
        this.destCol = destinationColumn;
        this.destFloor = destinationFloor;
        this.destLevel = destinationLevel;
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
        if (this.destLevel != other.destLevel) {
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
        hash = 67 * hash + this.destLevel;
        return hash;
    }

    @Override
    public GenericTeleport clone() {
        final GenericTeleport copy = (GenericTeleport) super.clone();
        copy.destCol = this.destCol;
        copy.destFloor = this.destFloor;
        copy.destLevel = this.destLevel;
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
        return this.destLevel;
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

    public void setDestinationLevel(final int destinationLevel) {
        this.destLevel = destinationLevel;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = Worldz.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        if (app.getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return WorldConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TELEPORT);
    }

    @Override
    public void editorProbeHook() {
        Messager.showMessage(this.getName() + ": Destination ("
                + (this.destCol + 1) + "," + (this.destRow + 1) + ","
                + (this.destFloor + 1) + "," + (this.destLevel + 1) + ")");
    }

    @Override
    public abstract WorldObject editorPropertiesHook();

    @Override
    public String getMoveSuccessSoundName() {
        return "teleport";
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
            case 4:
                return this.destLevel;
            default:
                return WorldObject.DEFAULT_CUSTOM_VALUE;
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
            case 4:
                this.destLevel = value;
                break;
            default:
                break;
        }
    }

    @Override
    public int getCustomFormat() {
        return 4;
    }
}
