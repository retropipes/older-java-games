/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericField;
import com.puttysoftware.loopchute.generic.TypeConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class Slime extends GenericField {
    // Constructors
    public Slime() {
        super(new BioHazardBoots(), ColorConstants.COLOR_GREEN);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_ON_SLIME);
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        LoopChute.getApplication().showMessage("You'll corrode");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_SLIME);
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
        return "Slime is too corrosive to walk on without Bio-Hazard Boots.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_GENERATION_ELIGIBLE);
    }
}