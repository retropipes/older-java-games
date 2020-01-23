/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.map.objects;

import com.puttysoftware.riskyrescue.RiskyRescue;
import com.puttysoftware.riskyrescue.assets.ObjectImage;
import com.puttysoftware.riskyrescue.assets.SoundConstants;
import com.puttysoftware.riskyrescue.creatures.party.PartyManager;
import com.puttysoftware.riskyrescue.map.Map;
import com.puttysoftware.riskyrescue.map.MapConstants;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScript;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptEntry;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptEntryArgument;

public class StairsUp extends MapObject {
    // Fields
    private final InternalScript postMoveScript;

    // Constructors
    public StairsUp() {
        super(ObjectImage.UP, false, false);
        // Create post-move script
        final InternalScript scpt = new InternalScript();
        final InternalScriptEntry entry1 = new InternalScriptEntry();
        entry1.setActionCode(InternalScriptActionCode.RELATIVE_LEVEL_CHANGE);
        entry1.addActionArg(new InternalScriptEntryArgument(-1));
        entry1.finalizeActionArgs();
        scpt.addAction(entry1);
        final InternalScriptEntry entry2 = new InternalScriptEntry();
        entry2.setActionCode(InternalScriptActionCode.SOUND);
        entry2.addActionArg(new InternalScriptEntryArgument(SoundConstants.UP));
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
    public InternalScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ) {
        RiskyRescue.getApplication().getGameManager().skipBattlesOnce();
        PartyManager.decreaseDungeonLevel();
        return this.postMoveScript;
    }

    @Override
    public int getLayer() {
        return MapConstants.LAYER_OBJECT;
    }

    @Override
    public String getDescription() {
        return "Stairs Up lead further away from the depths of the dungeon.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }

    @Override
    public boolean isRequired(final int level) {
        return true;
    }

    @Override
    public int getMinimumRequiredQuantity(final Map map, final int level) {
        return 1;
    }

    @Override
    public int getMaximumRequiredQuantity(final Map map, final int level) {
        return 1;
    }
}
