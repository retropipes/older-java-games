/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTrap;
import com.puttysoftware.dungeondiver4.dungeon.effects.DungeonEffectConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class CounterclockwiseRotationTrap extends AbstractTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public CounterclockwiseRotationTrap() {
        super(ColorConstants.COLOR_YELLOW,
                ObjectImageConstants.OBJECT_IMAGE_SMALL_ROTATION,
                ColorConstants.COLOR_DARK_YELLOW);
    }

    @Override
    public String getName() {
        return "Counterclockwise Rotation Trap";
    }

    @Override
    public String getPluralName() {
        return "Counterclockwise Rotation Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
        DungeonDiver4.getApplication()
                .showMessage("Your controls are rotated!");
        DungeonDiver4.getApplication().getGameManager().activateEffect(
                DungeonEffectConstants.EFFECT_ROTATED_COUNTERCLOCKWISE,
                CounterclockwiseRotationTrap.EFFECT_DURATION);
    }

    @Override
    public String getDescription() {
        return "Counterclockwise Rotation Traps rotate your controls counterclockwise for 10 steps when stepped on.";
    }
}