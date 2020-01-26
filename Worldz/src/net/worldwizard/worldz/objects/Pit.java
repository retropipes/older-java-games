/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.InfiniteRecursionException;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericMovableObject;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.world.WorldConstants;

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
        return this
                .searchNestedPits(dirX, dirY,
                        Worldz.getApplication().getGameManager()
                                .getPlayerManager().getPlayerLocationZ() - 1,
                        inv);
    }

    private boolean searchNestedPits(final int dirX, final int dirY,
            final int floor, final ObjectInventory inv) {
        final Application app = Worldz.getApplication();
        // Stop infinite recursion
        final int lcl = -app.getWorldManager().getWorld().getFloors();
        if (floor <= lcl) {
            throw new InfiniteRecursionException();
        }
        if (app.getGameManager().doesFloorExist(floor)) {
            final WorldObject obj = app.getWorldManager().getWorld()
                    .getCell(dirX, dirY, floor, WorldConstants.LAYER_OBJECT);
            if (obj.isConditionallySolid(inv)) {
                return false;
            } else {
                if (obj.getName().equals("Pit")
                        || obj.getName().equals("Invisible Pit")) {
                    return this.searchNestedPits(dirX, dirY, floor - 1, inv);
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
        final Application app = Worldz.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        if (app.getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            WorldObject.playFallSound();
        }
    }

    @Override
    public void pushIntoAction(final ObjectInventory inv,
            final WorldObject pushed, final int x, final int y, final int z) {
        final Application app = Worldz.getApplication();
        try {
            this.searchNestedPits(x, y, z - 1, inv);
            if (pushed.isPushable()) {
                final GenericMovableObject pushedInto = (GenericMovableObject) pushed;
                app.getGameManager().updatePushedIntoPositionAbsolute(x, y,
                        z - 1, x, y, z, pushedInto, this);
                if (app.getPrefsManager()
                        .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
                    WorldObject.playFallSound();
                }
            }
        } catch (final InfiniteRecursionException ir) {
            if (app.getPrefsManager()
                    .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
                WorldObject.playFallSound();
            }
            Worldz.getApplication().getWorldManager().getWorld()
                    .setCell(new Empty(), x, y, z, WorldConstants.LAYER_OBJECT);
        }
    }

    @Override
    public boolean isConditionallyDirectionallySolid(final boolean ie,
            final int dirX, final int dirY, final ObjectInventory inv) {
        final Application app = Worldz.getApplication();
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

    @Override
    public void editorPlaceHook() {
        // Do nothing
    }

    @Override
    public WorldObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Pits dump anything that wanders in to the floor below. If one of these is placed on the bottom-most floor, it is impassable.";
    }
}
