/*  RolePlayer: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.Map;
import net.worldwizard.support.map.MapConstants;
import net.worldwizard.support.map.generic.GameSoundConstants;
import net.worldwizard.support.map.generic.GenericTeleport;
import net.worldwizard.support.map.generic.InfiniteRecursionException;
import net.worldwizard.support.map.generic.MapObject;
import net.worldwizard.support.map.generic.TemplateTransform;
import net.worldwizard.support.scripts.game.GameActionCode;
import net.worldwizard.support.scripts.game.GameScript;
import net.worldwizard.support.scripts.game.GameScriptEntry;
import net.worldwizard.support.scripts.game.GameScriptEntryArgument;

public class Springboard extends GenericTeleport {
    // Fields
    private final GameScript postMoveScript;

    // Constructors
    public Springboard() {
        super();
        this.setTemplateTransform(new TemplateTransform(1.0, 0.75, 0.5, ""));
        // Create post-move script
        final GameScript scpt = new GameScript();
        final GameScriptEntry entry1 = new GameScriptEntry();
        entry1.setActionCode(GameActionCode.MOVE);
        entry1.addActionArg(new GameScriptEntryArgument(false));
        entry1.addActionArg(new GameScriptEntryArgument(false));
        entry1.addActionArg(new GameScriptEntryArgument(0));
        entry1.addActionArg(new GameScriptEntryArgument(0));
        entry1.addActionArg(new GameScriptEntryArgument(1));
        entry1.finalizeActionArgs();
        scpt.addAction(entry1);
        final GameScriptEntry entry2 = new GameScriptEntry();
        entry2.setActionCode(GameActionCode.SOUND);
        entry2.addActionArg(new GameScriptEntryArgument(
                GameSoundConstants.SOUND_JUMPING));
        entry2.finalizeActionArgs();
        scpt.addAction(entry2);
        scpt.finalizeActions();
        this.postMoveScript = scpt;
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
    public boolean preMoveCheck(final boolean ie, final int dirX,
            final int dirY, final int dirZ, final Map map) {
        return this.searchNestedSprings(dirX, dirY, dirZ + 1, map);
    }

    private boolean searchNestedSprings(final int dirX, final int dirY,
            final int floor, final Map map) {
        // Stop infinite recursion
        final int ucl = map.getFloors() * 2;
        if (floor >= ucl) {
            throw new InfiniteRecursionException();
        }
        if (map.doesFloorExist(floor)) {
            final MapObject obj = map.getCell(dirX, dirY, floor,
                    MapConstants.LAYER_OBJECT);
            if (obj.isConditionallySolid(map, floor)) {
                return false;
            } else {
                if (obj.getName().equals("Springboard")) {
                    return this.searchNestedSprings(dirX, dirY, floor + 1, map);
                } else if (obj.getName().equals("Pit")) {
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
    public GameScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ, final Map map) {
        return this.postMoveScript;
    }

    @Override
    public boolean isConditionallySolid(final Map map, final int z) {
        if (!map.isFloorAbove(z)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldGenerateObject(final Map map, final int row,
            final int col, final int floor, final int level, final int layer) {
        if (!map.doesFloorExist(floor + 1)) {
            return false;
        } else {
            return super.shouldGenerateObject(map, row, col, floor, level,
                    layer);
        }
    }

    @Override
    public String getDescription() {
        return "Springboards bounce anything that wanders into them to the floor above.";
    }
}
