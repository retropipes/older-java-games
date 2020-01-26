/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMovableObject;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.game.InfiniteRecursionException;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

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
            final int dirY, final DungeonObjectInventory inv) {
        return this.searchNestedSprings(dirX, dirY,
                DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                        .getPlayerLocationZ() + 1,
                inv);
    }

    private boolean searchNestedSprings(final int dirX, final int dirY,
            final int floor, final DungeonObjectInventory inv) {
        final Application app = DungeonDiver4.getApplication();
        // Stop infinite recursion
        final int ucl = app.getDungeonManager().getDungeon().getFloors() * 2;
        if (floor >= ucl) {
            throw new InfiniteRecursionException();
        }
        if (app.getGameManager().doesFloorExist(floor)) {
            final AbstractDungeonObject obj = app.getDungeonManager()
                    .getDungeon()
                    .getCell(dirX, dirY, floor, DungeonConstants.LAYER_OBJECT);
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
            final DungeonObjectInventory inv) {
        final Application app = DungeonDiver4.getApplication();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        SoundManager.playSound(SoundConstants.SOUND_SPRING);
    }

    @Override
    public void pushIntoAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject pushed, final int x, final int y,
            final int z) {
        final Application app = DungeonDiver4.getApplication();
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
            DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                    .setCell(new Empty(), x, y, z,
                            DungeonConstants.LAYER_OBJECT);
        }
    }

    @Override
    public boolean isConditionallySolid(final DungeonObjectInventory inv) {
        final Application app = DungeonDiver4.getApplication();
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
