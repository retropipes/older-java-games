/*  DynamicDungeon: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DynamicDungeon@worldwizard.net
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractMPModifier;
import net.dynamicdungeon.dynamicdungeon.dungeon.effects.DungeonEffectConstants;
import net.dynamicdungeon.dynamicdungeon.game.GameLogicManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;
import net.dynamicdungeon.randomrange.RandomRange;

public class LightGem extends AbstractMPModifier {
    // Constructors
    public LightGem() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_LIGHT_GEM;
    }

    @Override
    public String getName() {
        return "Light Gem";
    }

    @Override
    public String getPluralName() {
        return "Light Gems";
    }

    @Override
    public String getDescription() {
        return "Light Gems regenerate MP.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DynamicDungeon.getApplication().showMessage("Your power gathers!");
        DynamicDungeon.getApplication().getGameManager()
                .activateEffect(DungeonEffectConstants.EFFECT_POWER_GATHER);
        SoundManager.playSound(SoundConstants.SOUND_FOCUS);
        GameLogicManager.decay();
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon maze, final int row,
            final int col, final int floor, final int level, final int layer) {
        // Generate Light Gems at 30% rate
        final RandomRange reject = new RandomRange(1, 100);
        return reject.generate() < 30;
    }
}
