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

public class Springboard extends StairsUp {
    // Fields
    private final InternalScript postMoveScript;

    // Constructors
    public Springboard() {
        super();
        this.setTemplateTransform(new TemplateTransform(1.0, 0.75, 0.5));
        // Create post-move script
        InternalScript scpt = new InternalScript();
        InternalScriptEntry entry1 = new InternalScriptEntry();
        entry1.setActionCode(InternalScriptActionCode.MOVE);
        entry1.addActionArg(new InternalScriptEntryArgument(false));
        entry1.addActionArg(new InternalScriptEntryArgument(false));
        entry1.addActionArg(new InternalScriptEntryArgument(0));
        entry1.addActionArg(new InternalScriptEntryArgument(0));
        entry1.addActionArg(new InternalScriptEntryArgument(1));
        entry1.finalizeActionArgs();
        scpt.addAction(entry1);
        InternalScriptEntry entry2 = new InternalScriptEntry();
        entry2.setActionCode(InternalScriptActionCode.SOUND);
        entry2.addActionArg(new InternalScriptEntryArgument(
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
        int ucl = map.getFloors() * 2;
        if (floor >= ucl) {
            throw new InfiniteRecursionException();
        }
        if (map.doesFloorExist(floor)) {
            MapObject obj = map.getCell(dirX, dirY, floor,
                    MapConstants.LAYER_OBJECT);
            if (obj.isConditionallySolid(map, floor)) {
                return false;
            } else {
                if (obj.getName().equals("Springboard")
                        || obj.getName().equals("Invisible Springboard")) {
                    return this.searchNestedSprings(dirX, dirY, floor + 1, map);
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
    public InternalScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ) {
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
    public String getDescription() {
        return "Springboards bounce anything that wanders into them to the floor above. If one of these is placed on the top-most floor, it is impassable.";
    }
}
