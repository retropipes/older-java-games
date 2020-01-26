/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.objects.MasterTrappedWall;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public abstract class GenericWallTrap extends MazeObject {
    // Fields
    private int number;
    private GenericTrappedWall trigger;
    private final GenericTrappedWall masterTrigger = new MasterTrappedWall();
    protected static final int NUMBER_MASTER = -1;

    // Constructors
    protected GenericWallTrap(final int newNumber,
            final GenericTrappedWall newTrigger) {
        super(false, false);
        this.number = newNumber;
        this.trigger = newTrigger;
    }

    @Override
    public GenericWallTrap clone() {
        final GenericWallTrap copy = (GenericWallTrap) super.clone();
        copy.number = this.number;
        copy.trigger = this.trigger.clone();
        return copy;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        MasterMaze.getApplication().getGameManager().decay();
        MasterMaze.getApplication().getMazeManager().getMaze()
                .findAllMatchingObjectsAndDecay(this.masterTrigger);
        if (this.number == GenericWallTrap.NUMBER_MASTER) {
            MasterMaze.getApplication().getMazeManager().getMaze()
                    .masterTrapTrigger();
        } else {
            MasterMaze.getApplication().getMazeManager().getMaze()
                    .findAllMatchingObjectsAndDecay(this);
            MasterMaze.getApplication().getMazeManager().getMaze()
                    .findAllMatchingObjectsAndDecay(this.trigger);
        }
        MasterMaze.getApplication().getGameManager().redrawMaze();
        SoundManager.playSound(SoundConstants.SOUND_WALL_TRAP);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TRAP_BASE;
    }

    @Override
    public int getGameBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_GENERIC_WALL_TRAP;
    }

    @Override
    public abstract int getAttributeID();

    @Override
    public int getGameAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_NONE;
    }

    @Override
    public int getTemplateColor() {
        return ColorConstants.COLOR_LIGHT_YELLOW;
    }

    @Override
    public int getAttributeTemplateColor() {
        return ColorConstants.COLOR_DARK_BLUE;
    }

    @Override
    public int getGameAttributeTemplateColor() {
        return ColorConstants.COLOR_NONE;
    }

    @Override
    public String getName() {
        if (this.number != GenericWallTrap.NUMBER_MASTER) {
            return "Wall Trap " + this.number;
        } else {
            return "Master Wall Trap";
        }
    }

    @Override
    public String getGameName() {
        return "Wall Trap";
    }

    @Override
    public String getPluralName() {
        if (this.number != GenericWallTrap.NUMBER_MASTER) {
            return "Wall Traps " + this.number;
        } else {
            return "Master Wall Traps";
        }
    }

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL_TRAP);
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}