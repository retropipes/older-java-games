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

public class ClockwiseRotationTrap extends AbstractTrap {
    // Constructors
    public ClockwiseRotationTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_CW_ROTATION_TRAP);
    }

    @Override
    public String getName() {
        return "Clockwise Rotation Trap";
    }

    @Override
    public String getPluralName() {
        return "Clockwise Rotation Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
        DynamicDungeon.getApplication()
                .showMessage("Your controls are rotated!");
        DynamicDungeon.getApplication().getGameManager().activateEffect(
                DungeonEffectConstants.EFFECT_ROTATED_CLOCKWISE);
    }

    @Override
    public String getDescription() {
        return "Clockwise Rotation Traps rotate your controls clockwise for 6 steps when stepped on.";
    }
}