/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;
import com.puttysoftware.weaselweb.maze.objects.MasterTrappedWall;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public abstract class GenericWallTrap extends MazeObject {
    // Fields
    private int number;
    private GenericTrappedWall trigger;
    private final GenericTrappedWall masterTrigger = new MasterTrappedWall();
    protected static final int NUMBER_MASTER = -1;

    // Constructors
    protected GenericWallTrap(final int newNumber,
            final GenericTrappedWall newTrigger) {
        super();
        this.number = newNumber;
        this.trigger = newTrigger;
        this.setType(TypeConstants.TYPE_WALL_TRAP);
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Wall Trap");
    }

    @Override
    public GenericWallTrap clone() {
        final GenericWallTrap copy = (GenericWallTrap) super.clone();
        copy.number = this.number;
        copy.trigger = this.trigger.clone();
        return copy;
    }

    public int getNumber() {
        return this.number;
    }

    public GenericTrappedWall getTrigger() {
        return this.trigger;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        WeaselWeb.getApplication().getGameManager().decay();
        WeaselWeb.getApplication().getMazeManager().getMaze()
                .findAllMatchingObjectsAndDecay(this.masterTrigger);
        if (this.number == GenericWallTrap.NUMBER_MASTER) {
            WeaselWeb.getApplication().getMazeManager().getMaze()
                    .masterTrapTrigger();
        } else {
            WeaselWeb.getApplication().getMazeManager().getMaze()
                    .findAllMatchingObjectsAndDecay(this);
            WeaselWeb.getApplication().getMazeManager().getMaze()
                    .findAllMatchingObjectsAndDecay(this.trigger);
        }
        WeaselWeb.getApplication().getGameManager().redrawMaze();
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
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}