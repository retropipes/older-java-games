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

public class Lava extends GenericField {
    // Constructors
    public Lava() {
        super(5);
        this.setTemplateTransform(new TemplateTransform(1.0, 0.5, 0.25));
    }

    @Override
    protected InternalScript playSoundHook() {
        InternalScript scpt = new InternalScript();
        InternalScriptEntry entry0 = new InternalScriptEntry();
        entry0.setActionCode(InternalScriptActionCode.SOUND);
        entry0.addActionArg(new InternalScriptEntryArgument(
                GameSoundConstants.SOUND_SHORT_OW));
        entry0.finalizeActionArgs();
        scpt.addAction(entry0);
        InternalScriptEntry entry1 = new InternalScriptEntry();
        entry1.setActionCode(InternalScriptActionCode.MESSAGE);
        entry1.addActionArg(
                new InternalScriptEntryArgument("Ow, the lava burned you!"));
        entry1.finalizeActionArgs();
        scpt.addAction(entry1);
        scpt.finalizeActions();
        return scpt;
    }

    @Override
    public String getName() {
        return "Lava";
    }

    @Override
    public String getPluralName() {
        return "Squares of Lava";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Lava will burn you if you walk on it.";
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
