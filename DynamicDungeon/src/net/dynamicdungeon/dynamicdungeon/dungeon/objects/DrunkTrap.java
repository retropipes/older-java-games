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

public class DrunkTrap extends AbstractTrap {
    // Constructors
    public DrunkTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_DRUNK_TRAP);
    }

    @Override
    public String getName() {
        return "Drunk Trap";
    }

    @Override
    public String getPluralName() {
        return "Drunk Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DynamicDungeon.getApplication()
                .showMessage("You stumble around drunkenly!");
        DynamicDungeon.getApplication().getGameManager()
                .activateEffect(DungeonEffectConstants.EFFECT_DRUNK);
        SoundManager.playSound(SoundConstants.SOUND_DRUNK);
    }

    @Override
    public String getDescription() {
        return "Drunk Traps alter your movement in a way that resembles being intoxicated for 9 steps when stepped on.";
    }
}