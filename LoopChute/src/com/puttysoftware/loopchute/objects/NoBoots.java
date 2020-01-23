/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.LoopChute;
import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericInventoryModifier;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class NoBoots extends GenericInventoryModifier {
    // Constructors
    public NoBoots() {
        super();
        this.setAttributeName("no");
        this.setAttributeTemplateColor(ColorConstants.COLOR_RED);
    }

    @Override
    public String getName() {
        return "No Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of No Boots";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        LoopChute.getApplication().getGameManager().decay();
        inv.removeAllBoots();
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_GRAB);
    }

    @Override
    public String getDescription() {
        return "No Boots remove any boots worn when picked up.";
    }

    @Override
    public final String getBaseName() {
        return "boots";
    }
}
