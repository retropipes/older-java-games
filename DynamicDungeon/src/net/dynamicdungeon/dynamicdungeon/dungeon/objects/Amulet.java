/*  DynamicDungeon: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DynamicDungeon@worldwizard.net
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractTrap;
import net.dynamicdungeon.dynamicdungeon.dungeon.effects.DungeonEffectConstants;
import net.dynamicdungeon.dynamicdungeon.game.GameLogicManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;

public class Amulet extends AbstractTrap {
    // Constructors
    public Amulet() {
        super(ObjectImageConstants.OBJECT_IMAGE_AMULET);
    }

    @Override
    public String getName() {
        return "Amulet";
    }

    @Override
    public String getPluralName() {
        return "Amulets";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DynamicDungeon.getApplication()
                .showMessage("You no longer slide on ice!");
        final GameLogicManager glm = DynamicDungeon.getApplication()
                .getGameManager();
        glm.activateEffect(DungeonEffectConstants.EFFECT_STICKY);
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        GameLogicManager.decay();
    }

    @Override
    public String getDescription() {
        return "Amulets make you not slide on ice for 15 steps when stepped on.";
    }
}