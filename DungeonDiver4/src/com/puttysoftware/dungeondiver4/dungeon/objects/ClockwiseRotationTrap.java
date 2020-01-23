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

public class ClockwiseRotationTrap extends AbstractTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public ClockwiseRotationTrap() {
        super(ColorConstants.COLOR_CYAN,
                ObjectImageConstants.OBJECT_IMAGE_SMALL_ROTATION,
                ColorConstants.COLOR_DARK_CYAN);
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
    public void postMoveAction(boolean ie, int dirX, int dirY,
            DungeonObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
        DungeonDiver4.getApplication()
                .showMessage("Your controls are rotated!");
        DungeonDiver4
                .getApplication()
                .getGameManager()
                .activateEffect(
                        DungeonEffectConstants.EFFECT_ROTATED_CLOCKWISE,
                        ClockwiseRotationTrap.EFFECT_DURATION);
    }

    @Override
    public String getDescription() {
        return "Clockwise Rotation Traps rotate your controls clockwise for 10 steps when stepped on.";
    }
}