/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.generic.GenericField;
import com.puttysoftware.gemma.support.map.generic.TemplateTransform;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.gemma.support.scripts.internal.InternalScript;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntryArgument;

public class Slime extends GenericField {
    // Constructors
    public Slime() {
        super(5);
        this.setTemplateTransform(new TemplateTransform(0.25, 1.0, 0.25));
    }

    @Override
    protected InternalScript playSoundHook() {
        InternalScript scpt = new InternalScript();
        InternalScriptEntry entry0 = new InternalScriptEntry();
        entry0.setActionCode(InternalScriptActionCode.SOUND);
        entry0.addActionArg(new InternalScriptEntryArgument(
                GameSoundConstants.SOUND_SLIME_HIT));
        entry0.finalizeActionArgs();
        scpt.addAction(entry0);
        InternalScriptEntry entry1 = new InternalScriptEntry();
        entry1.setActionCode(InternalScriptActionCode.MESSAGE);
        entry1.addActionArg(new InternalScriptEntryArgument(
                "Ow, the slime ate into your flesh!"));
        entry1.finalizeActionArgs();
        scpt.addAction(entry1);
        scpt.finalizeActions();
        return scpt;
    }

    @Override
    public String getName() {
        return "Slime";
    }

    @Override
    public String getPluralName() {
        return "Squares of Slime";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Slime is corrosive to walk on.";
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
