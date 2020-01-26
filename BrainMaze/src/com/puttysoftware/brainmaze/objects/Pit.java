/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.Application;
import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.game.InfiniteRecursionException;
import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.generic.GenericMovableObject;
import com.puttysoftware.brainmaze.generic.MazeObject;
import com.puttysoftware.brainmaze.maze.MazeConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundManager;

public class Pit extends StairsDown {
    // Constructors
    public Pit() {
        super(true);
    }

    @Override
    public String getName() {
        return "Pit";
    }

    @Override
    public String getPluralName() {
        return "Pits";
    }

    @Override
    public boolean preMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        return this.searchNestedPits(dirX, dirY, BrainMaze.getApplication()
                .getMazeManager().getMaze().getPlayerLocationZ() - 1, inv);
    }

    private boolean searchNestedPits(final int dirX, final int dirY,
            final int floor, final ObjectInventory inv) {
        final Application app = BrainMaze.getApplication();
        // Stop infinite recursion
        final int lcl = -app.getMazeManager().getMaze().getFloors();
        if (floor <= lcl) {
            throw new InfiniteRecursionException();
        }
        if (app.getGameManager().doesFloorExist(floor)) {
            final MazeObject obj = app.getMazeManager().getMaze().getCell(dirX,
                    dirY, floor, MazeConstants.LAYER_OBJECT);
            if (obj.isConditionallySolid(inv)) {
                return false;
            } else {
                if (obj.getName().equals("Pit")
                        || obj.getName().equals("Invisible Pit")) {
                    return this.searchNestedPits(dirX, dirY, floor - 1, inv);
                } else if (obj.getName().equals("Springboard")
                        || obj.getName().equals("Invisible Springboard")) {
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            return false;
        }
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = BrainMaze.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_FALL_INTO_PIT);
    }

    @Override
    public void pushIntoAction(final ObjectInventory inv,
            final MazeObject pushed, final int x, final int y, final int z) {
        final Application app = BrainMaze.getApplication();
        try {
            this.searchNestedPits(x, y, z - 1, inv);
            if (pushed.isPushable()) {
                final GenericMovableObject pushedInto = (GenericMovableObject) pushed;
                app.getGameManager().updatePushedIntoPositionAbsolute(x, y,
                        z - 1, x, y, z, pushedInto, this);
                SoundManager.playSound(
                        SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                        SoundConstants.SOUND_FALL_INTO_PIT);
            }
        } catch (final InfiniteRecursionException ir) {
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_FALL_INTO_PIT);
            BrainMaze.getApplication().getMazeManager().getMaze()
                    .setCell(new Empty(), x, y, z, MazeConstants.LAYER_OBJECT);
        }
    }

    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
        final Application app = BrainMaze.getApplication();
        if (!app.getGameManager().isFloorBelow()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void editorPlaceHook() {
        // Do nothing
    }

    @Override
    public String getDescription() {
        return "Pits dump anything that wanders in to the floor below. If one of these is placed on the bottom-most floor, it is impassable.";
    }
}
