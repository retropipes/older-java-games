/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.InfiniteRecursionException;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.generic.GenericMovableObject;
import com.puttysoftware.rulemazer.generic.MazeObject;
import com.puttysoftware.rulemazer.maze.MazeConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public class Springboard extends StairsUp {
    // Constructors
    public Springboard() {
        super(true);
    }

    @Override
    public String getName() {
        return "Springboard";
    }

    @Override
    public String getPluralName() {
        return "Springboards";
    }

    @Override
    public boolean preMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        return this
                .searchNestedSprings(dirX, dirY,
                        Main.getApplication().getGameManager()
                                .getPlayerManager().getPlayerLocationZ() + 1,
                        inv);
    }

    private boolean searchNestedSprings(final int dirX, final int dirY,
            final int floor, final ObjectInventory inv) {
        final Application app = Main.getApplication();
        // Stop infinite recursion
        final int ucl = app.getMazeManager().getMaze().getFloors() * 2;
        if (floor >= ucl) {
            throw new InfiniteRecursionException();
        }
        if (app.getGameManager().doesFloorExist(floor)) {
            final MazeObject obj = app.getMazeManager().getMaze().getCell(dirX,
                    dirY, floor, MazeConstants.LAYER_OBJECT);
            if (obj.isConditionallySolid(inv)) {
                return false;
            } else {
                if (obj.getName().equals("Springboard")
                        || obj.getName().equals("Invisible Springboard")) {
                    return this.searchNestedSprings(dirX, dirY, floor + 1, inv);
                } else if (obj.getName().equals("Pit")
                        || obj.getName().equals("Invisible Pit")) {
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
        final Application app = Main.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_SPRINGBOARD);
    }

    @Override
    public void pushIntoAction(final ObjectInventory inv,
            final MazeObject pushed, final int x, final int y, final int z) {
        final Application app = Main.getApplication();
        try {
            this.searchNestedSprings(x, y, z + 1, inv);
            if (pushed.isPushable()) {
                final GenericMovableObject pushedInto = (GenericMovableObject) pushed;
                app.getGameManager().updatePushedIntoPositionAbsolute(x, y,
                        z - 1, x, y, z, pushedInto, this);
                SoundManager.playSound(
                        SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                        SoundConstants.SOUND_SPRINGBOARD);
            }
        } catch (final InfiniteRecursionException ir) {
            SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                    SoundConstants.SOUND_SPRINGBOARD);
            Main.getApplication().getMazeManager().getMaze()
                    .setCell(new Empty(), x, y, z, MazeConstants.LAYER_OBJECT);
        }
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final ObjectInventory inv) {
        final Application app = Main.getApplication();
        if (!app.getGameManager().isFloorAbove()) {
            if (ie) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void editorPlaceHook() {
        // Do nothing
    }

    @Override
    public MazeObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Springboards bounce anything that wanders into them to the floor above. If one of these is placed on the top-most floor, it is impassable.";
    }
}
