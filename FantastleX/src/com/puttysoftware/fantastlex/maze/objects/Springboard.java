/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.game.InfiniteRecursionException;
import com.puttysoftware.fantastlex.maze.MazeConstants;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.abc.AbstractMovableObject;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class Springboard extends StairsUp {
    // Constructors
    public Springboard() {
        super(true);
        this.setTemplateColor(ColorConstants.COLOR_BRIDGE);
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
            final int dirY, final MazeObjectInventory inv) {
        return this.searchNestedSprings(dirX, dirY, FantastleX.getApplication()
                .getMazeManager().getMaze().getPlayerLocationZ() + 1, inv);
    }

    private boolean searchNestedSprings(final int dirX, final int dirY,
            final int floor, final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
        // Stop infinite recursion
        final int ucl = app.getMazeManager().getMaze().getFloors() * 2;
        if (floor >= ucl) {
            throw new InfiniteRecursionException();
        }
        if (app.getGameManager().doesFloorExist(floor)) {
            final AbstractMazeObject obj = app.getMazeManager().getMaze()
                    .getCell(dirX, dirY, floor, MazeConstants.LAYER_OBJECT);
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
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        SoundManager.playSound(SoundConstants.SOUND_SPRING);
    }

    @Override
    public void pushIntoAction(final MazeObjectInventory inv,
            final AbstractMazeObject pushed, final int x, final int y,
            final int z) {
        final Application app = FantastleX.getApplication();
        try {
            this.searchNestedSprings(x, y, z + 1, inv);
            if (pushed.isPushable()) {
                final AbstractMovableObject pushedInto = (AbstractMovableObject) pushed;
                app.getGameManager().updatePushedIntoPositionAbsolute(x, y,
                        z - 1, x, y, z, pushedInto, this);
                SoundManager.playSound(SoundConstants.SOUND_SPRING);
            }
        } catch (final InfiniteRecursionException ir) {
            SoundManager.playSound(SoundConstants.SOUND_SPRING);
            FantastleX.getApplication().getMazeManager().getMaze()
                    .setCell(new Empty(), x, y, z, MazeConstants.LAYER_OBJECT);
        }
    }

    @Override
    public boolean isConditionallySolid(final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
        if (!app.getGameManager().isFloorAbove()) {
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
        return "Springboards bounce anything that wanders into them to the floor above. If one of these is placed on the top-most floor, it is impassable.";
    }
}
