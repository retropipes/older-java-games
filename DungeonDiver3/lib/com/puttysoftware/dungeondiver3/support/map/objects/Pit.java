/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.Map;
import com.puttysoftware.dungeondiver3.support.map.MapConstants;
import com.puttysoftware.dungeondiver3.support.map.generic.InfiniteRecursionException;
import com.puttysoftware.dungeondiver3.support.map.generic.MapObject;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScript;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntryArgument;

public class Pit extends StairsDown {
    // Fields
    private final InternalScript postMoveScript;

    // Constructors
    public Pit() {
        super();
        this.setTemplateTransform(new TemplateTransform(1.0, 1.0, 1.0));
        // Create post-move script
        InternalScript scpt = new InternalScript();
        InternalScriptEntry entry1 = new InternalScriptEntry();
        entry1.setActionCode(InternalScriptActionCode.MOVE);
        entry1.addActionArg(new InternalScriptEntryArgument(false));
        entry1.addActionArg(new InternalScriptEntryArgument(false));
        entry1.addActionArg(new InternalScriptEntryArgument(0));
        entry1.addActionArg(new InternalScriptEntryArgument(0));
        entry1.addActionArg(new InternalScriptEntryArgument(-1));
        entry1.finalizeActionArgs();
        scpt.addAction(entry1);
        InternalScriptEntry entry2 = new InternalScriptEntry();
        entry2.setActionCode(InternalScriptActionCode.SOUND);
        entry2.addActionArg(new InternalScriptEntryArgument(
                GameSoundConstants.SOUND_FALLING));
        entry2.finalizeActionArgs();
        scpt.addAction(entry2);
        scpt.finalizeActions();
        this.postMoveScript = scpt;
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
    public boolean preMoveCheck(final boolean ie, final int dirX,
            final int dirY, final int dirZ, final Map map) {
        return this.searchNestedPits(dirX, dirY, dirZ - 1, map);
    }

    private boolean searchNestedPits(final int dirX, final int dirY,
            final int floor, final Map map) {
        // Stop infinite recursion
        int lcl = -map.getFloors();
        if (floor <= lcl) {
            throw new InfiniteRecursionException();
        }
        if (map.doesFloorExist(floor)) {
            MapObject obj = map.getCell(dirX, dirY, floor,
                    MapConstants.LAYER_OBJECT);
            if (obj.isConditionallySolid(map, floor)) {
                return false;
            } else {
                if (obj.getName().equals("Pit")
                        || obj.getName().equals("Invisible Pit")) {
                    return this.searchNestedPits(dirX, dirY, floor - 1, map);
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
    public InternalScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ) {
        return this.postMoveScript;
    }

    @Override
    public boolean isConditionallySolid(Map map, final int z) {
        if (!Map.isFloorBelow(z)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getDescription() {
        return "Pits dump anything that wanders in to the floor below. If one of these is placed on the bottom-most floor, it is impassable.";
    }
}
