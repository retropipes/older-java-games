/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.MazeConstants;
import com.puttysoftware.mazerunner2.maze.objects.MasterTrappedWall;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public abstract class AbstractWallTrap extends AbstractMazeObject {
    // Fields
    private int number;
    private AbstractTrappedWall trigger;
    private final AbstractTrappedWall masterTrigger = new MasterTrappedWall();
    protected static final int NUMBER_MASTER = -1;

    // Constructors
    protected AbstractWallTrap(final int newNumber,
            final AbstractTrappedWall newTrigger) {
        super(false, false);
        this.number = newNumber;
        this.trigger = newTrigger;
    }

    @Override
    public AbstractWallTrap clone() {
        final AbstractWallTrap copy = (AbstractWallTrap) super.clone();
        copy.number = this.number;
        copy.trigger = this.trigger.clone();
        return copy;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final MazeObjectInventory inv) {
        MazeRunnerII.getApplication().getGameManager().decay();
        MazeRunnerII.getApplication().getMazeManager().getMaze()
                .findAllMatchingObjectsAndDecay(this.masterTrigger);
        if (this.number == AbstractWallTrap.NUMBER_MASTER) {
            MazeRunnerII.getApplication().getMazeManager().getMaze()
                    .masterTrapTrigger();
        } else {
            MazeRunnerII.getApplication().getMazeManager().getMaze()
                    .findAllMatchingObjectsAndDecay(this);
            MazeRunnerII.getApplication().getMazeManager().getMaze()
                    .findAllMatchingObjectsAndDecay(this.trigger);
        }
        MazeRunnerII.getApplication().getGameManager().redrawMaze();
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
        if (this.number != AbstractWallTrap.NUMBER_MASTER) {
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
        if (this.number != AbstractWallTrap.NUMBER_MASTER) {
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
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}