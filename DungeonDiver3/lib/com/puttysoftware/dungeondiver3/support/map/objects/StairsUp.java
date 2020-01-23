/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.Map;
import com.puttysoftware.dungeondiver3.support.map.generic.GenericTeleport;
import com.puttysoftware.dungeondiver3.support.map.generic.MapObject;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScript;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntryArgument;

public class StairsUp extends GenericTeleport {
    // Fields
    private final InternalScript postMoveScript;

    // Constructors
    public StairsUp() {
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
        entry1.addActionArg(new InternalScriptEntryArgument(1));
        entry1.finalizeActionArgs();
        scpt.addAction(entry1);
        InternalScriptEntry entry2 = new InternalScriptEntry();
        entry2.setActionCode(InternalScriptActionCode.SOUND);
        entry2.addActionArg(new InternalScriptEntryArgument(
                GameSoundConstants.SOUND_STAIRS));
        entry2.finalizeActionArgs();
        scpt.addAction(entry2);
        scpt.finalizeActions();
        this.postMoveScript = scpt;
    }

    @Override
    public String getName() {
        return "Stairs Up";
    }

    @Override
    public String getPluralName() {
        return "Sets of Stairs Up";
    }

    @Override
    public boolean preMoveCheck(final boolean ie, final int dirX,
            final int dirY, final int dirZ, final Map map) {
        return map.isFloorAbove(dirZ) && map.isMoveOK(dirX, dirY, dirZ);
    }

    @Override
    public InternalScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ) {
        return this.postMoveScript;
    }

    @Override
    public String getDescription() {
        return "Stairs Up lead to the floor above.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }

    @Override
    public int getCustomProperty(int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }
}
