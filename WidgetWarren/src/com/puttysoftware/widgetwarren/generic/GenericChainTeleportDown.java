/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.generic;

import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.InfiniteRecursionException;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.maze.MazeConstants;
import com.puttysoftware.widgetwarren.objects.Empty;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public abstract class GenericChainTeleportDown extends GenericTeleportDown {
    // Constructors
    protected GenericChainTeleportDown() {
        super();
    }

    protected GenericChainTeleportDown(final boolean doesAcceptPushInto) {
        super(doesAcceptPushInto);
    }

    // Methods
    @Override
    public boolean preMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        return this
                .searchNestedPits(dirX, dirY,
                        WidgetWarren.getApplication().getGameManager()
                                .getPlayerManager().getPlayerLocationZ() - 1,
                        inv);
    }

    private boolean searchNestedPits(final int dirX, final int dirY,
            final int floor, final ObjectInventory inv) {
        final Application app = WidgetWarren.getApplication();
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
        final Application app = WidgetWarren.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_FALL_INTO_PIT);
    }

    @Override
    public void pushIntoAction(final ObjectInventory inv,
            final MazeObject pushed, final int x, final int y, final int z) {
        final Application app = WidgetWarren.getApplication();
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
            WidgetWarren.getApplication().getMazeManager().getMaze()
                    .setCell(new Empty(), x, y, z, MazeConstants.LAYER_OBJECT);
        }
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final ObjectInventory inv) {
        final Application app = WidgetWarren.getApplication();
        if (!app.getGameManager().isFloorBelow()) {
            if (ie) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
