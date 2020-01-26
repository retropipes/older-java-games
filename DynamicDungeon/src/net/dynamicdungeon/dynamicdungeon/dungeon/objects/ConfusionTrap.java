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

public class ConfusionTrap extends AbstractTrap {
    // Constructors
    public ConfusionTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_CONFUSION_TRAP);
    }

    @Override
    public String getName() {
        return "Confusion Trap";
    }

    @Override
    public String getPluralName() {
        return "Confusion Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DynamicDungeon.getApplication().showMessage("You are confused!");
        DynamicDungeon.getApplication().getGameManager()
                .activateEffect(DungeonEffectConstants.EFFECT_CONFUSED);
        SoundManager.playSound(SoundConstants.SOUND_CONFUSED);
    }

    @Override
    public String getDescription() {
        return "Confusion Traps randomly alter your controls for 6 steps when stepped on.";
    }
}