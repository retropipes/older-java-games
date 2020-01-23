/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.generic.GenericField;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public class ForceField extends GenericField {
    // Constructors
    public ForceField() {
        super(new EnergySphere());
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        Main.getApplication().showMessage("You'll get zapped");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_FORCE_FIELD);
    }

    @Override
    public String getName() {
        return "Force Field";
    }

    @Override
    public String getPluralName() {
        return "Force Fields";
    }

    @Override
    public String getDescription() {
        return "Force Fields block movement without an Energy Sphere.";
    }
}
