/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.editor.MazeEditorLogic;
import com.puttysoftware.mazerunner2.maze.objects.MoonStone;
import com.puttysoftware.mazerunner2.maze.objects.SunStone;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public abstract class AbstractConditionalTeleport extends AbstractTeleport {
    // Fields
    private int destRow2;
    private int destCol2;
    private int destFloor2;
    private int triggerVal;
    private int sunMoon;
    public static final int TRIGGER_SUN = 1;
    public static final int TRIGGER_MOON = 2;

    // Constructors
    protected AbstractConditionalTeleport(final int attrName) {
        super(true, attrName);
        this.sunMoon = AbstractConditionalTeleport.TRIGGER_SUN;
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
        final AbstractConditionalTeleport other = (AbstractConditionalTeleport) obj;
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
        return 67 * hash + this.sunMoon;
    }

    @Override
    public AbstractConditionalTeleport clone() {
        final AbstractConditionalTeleport copy = (AbstractConditionalTeleport) super.clone();
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
            final MazeObjectInventory inv) {
        final Application app = MazeRunnerII.getApplication();
        int testVal;
        if (this.sunMoon == AbstractConditionalTeleport.TRIGGER_SUN) {
            testVal = inv.getItemCount(new SunStone());
        } else if (this.sunMoon == AbstractConditionalTeleport.TRIGGER_MOON) {
            testVal = inv.getItemCount(new MoonStone());
        } else {
            testVal = 0;
        }
        if (testVal >= this.triggerVal) {
            app.getGameManager().updatePositionAbsolute(
                    this.getDestinationRow2(), this.getDestinationColumn2(),
                    this.getDestinationFloor2());
        } else {
            app.getGameManager().updatePositionAbsolute(
                    this.getDestinationRow(), this.getDestinationColumn(),
                    this.getDestinationFloor());
        }
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
        this.postMoveActionHook();
    }

    public void postMoveActionHook() {
        // Do nothing
    }

    @Override
    public abstract String getName();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TELEPORT);
    }

    @Override
    public void editorProbeHook() {
        MazeRunnerII.getApplication().showMessage(
                this.getName() + ": Trigger Value " + this.triggerVal);
    }

    @Override
    public final AbstractMazeObject editorPropertiesHook() {
        final MazeEditorLogic me = MazeRunnerII.getApplication().getEditor();
        me.editConditionalTeleportDestination(this);
        return this;
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
                return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
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
        return 8;
    }
}
