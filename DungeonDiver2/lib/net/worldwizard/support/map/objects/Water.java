/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.objects;

import net.worldwizard.support.map.generic.GameSoundConstants;
import net.worldwizard.support.map.generic.GenericField;
import net.worldwizard.support.map.generic.TemplateTransform;
import net.worldwizard.support.scripts.game.GameActionCode;
import net.worldwizard.support.scripts.game.GameScript;
import net.worldwizard.support.scripts.game.GameScriptEntry;
import net.worldwizard.support.scripts.game.GameScriptEntryArgument;

public class Water extends GenericField {
    // Constructors
    public Water() {
        super(5);
        this.setTemplateTransform(new TemplateTransform(0.0, 0.0, 1.0, ""));
    }

    // Scriptability
    @Override
    protected GameScript playSoundHook() {
        final GameScript scpt = new GameScript();
        final GameScriptEntry entry0 = new GameScriptEntry();
        entry0.setActionCode(GameActionCode.SOUND);
        entry0.addActionArg(new GameScriptEntryArgument(
                GameSoundConstants.SOUND_SPLASHING));
        entry0.finalizeActionArgs();
        scpt.addAction(entry0);
        final GameScriptEntry entry1 = new GameScriptEntry();
        entry1.setActionCode(GameActionCode.MESSAGE);
        entry1.addActionArg(
                new GameScriptEntryArgument("Brrr, that water's COLD!"));
        entry1.finalizeActionArgs();
        scpt.addAction(entry1);
        scpt.finalizeActions();
        return scpt;
    }

    @Override
    public String getName() {
        return "Water";
    }

    @Override
    public String getPluralName() {
        return "Squares of Water";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Water is cold enough to chill you to the bone.";
    }

    @Override
    public String getGameImageNameHook() {
        return "textured";
    }
}
