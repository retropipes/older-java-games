/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.objects;

import com.puttysoftware.dungeondiver3.support.map.generic.GenericField;
import com.puttysoftware.dungeondiver3.support.map.generic.TemplateTransform;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScript;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntryArgument;

public class Water extends GenericField {
    // Constructors
    public Water() {
        super(5);
        this.setTemplateTransform(new TemplateTransform(0.0, 0.0, 1.0));
    }

    // Scriptability
    @Override
    protected InternalScript playSoundHook() {
        final InternalScript scpt = new InternalScript();
        final InternalScriptEntry entry0 = new InternalScriptEntry();
        entry0.setActionCode(InternalScriptActionCode.SOUND);
        entry0.addActionArg(new InternalScriptEntryArgument(
                GameSoundConstants.SOUND_WATER));
        entry0.finalizeActionArgs();
        scpt.addAction(entry0);
        final InternalScriptEntry entry1 = new InternalScriptEntry();
        entry1.setActionCode(InternalScriptActionCode.MESSAGE);
        entry1.addActionArg(
                new InternalScriptEntryArgument("Brrr, that water's COLD!"));
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

    @Override
    public String getEditorImageNameHook() {
        return "textured";
    }
}
