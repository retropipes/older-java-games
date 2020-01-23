/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.gemma.support.map.Map;
import com.puttysoftware.gemma.support.map.generic.GenericTeleport;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.gemma.support.scripts.internal.InternalScript;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntryArgument;

public class StairsInto extends GenericTeleport {
    // Fields
    private final InternalScript postMoveScript;

    // Constructors
    public StairsInto() {
        super();
        this.setTemplateTransform(new TemplateTransform(1.0, 1.0, 1.0));
        // Create post-move script
        InternalScript scpt = new InternalScript();
        InternalScriptEntry entry1 = new InternalScriptEntry();
        entry1.setActionCode(InternalScriptActionCode.RELATIVE_LEVEL_CHANGE);
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
        return "Stairs Into";
    }

    @Override
    public String getPluralName() {
        return "Sets of Stairs Into";
    }

    @Override
    public InternalScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ) {
        PartyManager.getParty().increaseDungeonLevel();
        return this.postMoveScript;
    }

    @Override
    public String getDescription() {
        return "Stairs Into lead deeper into the depths of the dungeon.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    @Override
    public int getMinimumRequiredQuantity(Map map) {
        return map.getRows() * 2;
    }

    @Override
    public int getMaximumRequiredQuantity(Map map) {
        return map.getRows() * 4;
    }
}
