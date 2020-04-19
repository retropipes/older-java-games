/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.abc;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.editor.MazeEditor;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.Layers;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public abstract class GenericConditionalTeleport extends GenericTeleport {
    // Fields
    private int destRow2;
    private int destCol2;
    private int destFloor2;
    private int triggerVal;
    private int sunMoon;
    public static final int TRIGGER_SUN = 1;
    public static final int TRIGGER_MOON = 2;

    // Constructors
    protected GenericConditionalTeleport() {
        super();
        this.sunMoon = GenericConditionalTeleport.TRIGGER_SUN;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        final GenericConditionalTeleport other = (GenericConditionalTeleport) obj;
        if (this.destRow2 != other.destRow2) {
            return false;
        }
        if (this.destCol2 != other.destCol2) {
            return false;
        }
        if (this.destFloor2 != other.destFloor2) {
            return false;
        }
        if (this.triggerVal != other.triggerVal) {
            return false;
        }
        if (this.sunMoon != other.sunMoon) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.destRow2;
        hash = 67 * hash + this.destCol2;
        hash = 67 * hash + this.destFloor2;
        hash = 67 * hash + this.triggerVal;
        hash = 67 * hash + this.sunMoon;
        return hash;
    }

    @Override
    public GenericConditionalTeleport clone() {
        final GenericConditionalTeleport copy = (GenericConditionalTeleport) super.clone();
        copy.destCol2 = this.destCol2;
        copy.destFloor2 = this.destFloor2;
        copy.destRow2 = this.destRow2;
        copy.triggerVal = this.triggerVal;
        copy.sunMoon = this.sunMoon;
        return copy;
    }

    // Accessor methods
    public int getDestinationRow2() {
        return this.destRow2;
    }

    public int getDestinationColumn2() {
        return this.destCol2;
    }

    public int getDestinationFloor2() {
        return this.destFloor2;
    }

    public int getTriggerValue() {
        return this.triggerVal;
    }

    public int getSunMoon() {
        return this.sunMoon;
    }

    // Transformer methods
    public void setDestinationRow2(final int destinationRow) {
        this.destRow2 = destinationRow;
    }

    public void setDestinationColumn2(final int destinationColumn) {
        this.destCol2 = destinationColumn;
    }

    public void setDestinationFloor2(final int destinationFloor) {
        this.destFloor2 = destinationFloor;
    }

    public void setTriggerValue(final int t) {
        this.triggerVal = t;
    }

    public void setSunMoon(final int sm) {
        this.sunMoon = sm;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        int testVal;
        if (this.sunMoon == GenericConditionalTeleport.TRIGGER_SUN) {
            testVal = inv.getItemCount(MazeObjects.SUN_STONE);
        } else if (this.sunMoon == GenericConditionalTeleport.TRIGGER_MOON) {
            testVal = inv.getItemCount(MazeObjects.MOON_STONE);
        } else {
            testVal = 0;
        }
        if (testVal >= this.triggerVal) {
            app.getGameManager().updatePositionAbsolute(this
                    .getDestinationRow2(), this.getDestinationColumn2(), this
                            .getDestinationFloor2());
        } else {
            app.getGameManager().updatePositionAbsolute(this
                    .getDestinationRow(), this.getDestinationColumn(), this
                            .getDestinationFloor());
        }
        SoundPlayer.playSound(SoundIndex.TELEPORT, SoundGroup.GAME);
        this.postMoveActionHook();
    }

    public void postMoveActionHook() {
        // Do nothing
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
        Mazer5D.getBagOStuff().showMessage(this.getName() + ": Trigger Value "
                + this.triggerVal);
    }

    @Override
    public final MazeObjectModel editorPropertiesHook() {
        final MazeEditor me = Mazer5D.getBagOStuff().getEditor();
        me.editConditionalTeleportDestination(this);
        return this;
    }

    @Override
    public boolean defersSetProperties() {
        return true;
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
        case 4:
            return this.destRow2;
        case 5:
            return this.destCol2;
        case 6:
            return this.destFloor2;
        case 7:
            return this.triggerVal;
        case 8:
            return this.sunMoon;
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
        case 4:
            this.destRow2 = value;
            break;
        case 5:
            this.destCol2 = value;
            break;
        case 6:
            this.destFloor2 = value;
            break;
        case 7:
            this.triggerVal = value;
            break;
        case 8:
            this.sunMoon = value;
            break;
        default:
            break;
        }
    }

    @Override
    public int getCustomFormat() {
        if (Mazer5D.getBagOStuff().getMazeManager().isMazeXML4Compatible()) {
            return 7;
        } else {
            return 8;
        }
    }
}
