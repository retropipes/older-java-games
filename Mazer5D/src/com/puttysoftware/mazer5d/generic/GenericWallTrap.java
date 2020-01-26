/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.generic;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.maze.MazeConstants;
import com.puttysoftware.mazer5d.objects.MasterTrappedWall;
import com.puttysoftware.mazer5d.resourcemanagers.SoundConstants;
import com.puttysoftware.mazer5d.resourcemanagers.SoundManager;

public abstract class GenericWallTrap extends MazeObject {
    // Fields
    private int number;
    private GenericTrappedWall trigger;
    private final GenericTrappedWall masterTrigger = new MasterTrappedWall();
    protected static final int NUMBER_MASTER = -1;

    // Constructors
    protected GenericWallTrap(final int newNumber,
            final GenericTrappedWall newTrigger) {
        super(false);
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
        Mazer5D.getApplication().getGameManager().decay();
        Mazer5D.getApplication().getMazeManager().getMaze()
                .findAllMatchingObjectsAndDecay(this.masterTrigger);
        if (this.number == GenericWallTrap.NUMBER_MASTER) {
            Mazer5D.getApplication().getMazeManager().getMaze()
                    .masterTrapTrigger();
        } else {
            Mazer5D.getApplication().getMazeManager().getMaze()
                    .findAllMatchingObjectsAndDecay(this);
            Mazer5D.getApplication().getMazeManager().getMaze()
                    .findAllMatchingObjectsAndDecay(this.trigger);
        }
        Mazer5D.getApplication().getGameManager().redrawMaze();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALL_TRAP);
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