/*  DynamicDungeon: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DynamicDungeon@worldwizard.net
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.effects.DungeonEffectConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public class DizzinessTrap extends AbstractTrap {
    // Constructors
    public DizzinessTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_DIZZINESS_TRAP);
    }

    @Override
    public String getName() {
        return "Dizziness Trap";
    }

    @Override
    public String getPluralName() {
        return "Dizziness Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DynamicDungeon.getApplication().showMessage("You feel dizzy!");
        DynamicDungeon.getApplication().getGameManager()
                .activateEffect(DungeonEffectConstants.EFFECT_DIZZY);
        SoundManager.playSound(SoundConstants.SOUND_DIZZY);
    }

    @Override
    public String getDescription() {
        return "Dizziness Traps randomly alter your controls each step for 3 steps when stepped on.";
    }
}