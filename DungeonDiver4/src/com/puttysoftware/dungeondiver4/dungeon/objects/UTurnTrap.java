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

public class UTurnTrap extends AbstractTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public UTurnTrap() {
        super(ColorConstants.COLOR_LIGHT_MAGENTA,
                ObjectImageConstants.OBJECT_IMAGE_U_TURN,
                ColorConstants.COLOR_DARK_MAGENTA);
    }

    @Override
    public String getName() {
        return "U Turn Trap";
    }

    @Override
    public String getPluralName() {
        return "U Turn Traps";
    }

    @Override
    public void postMoveAction(boolean ie, int dirX, int dirY,
            DungeonObjectInventory inv) {
        DungeonDiver4.getApplication().showMessage(
                "Your controls are turned around!");
        DungeonDiver4
                .getApplication()
                .getGameManager()
                .activateEffect(DungeonEffectConstants.EFFECT_U_TURNED,
                        UTurnTrap.EFFECT_DURATION);
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
    }

    @Override
    public String getDescription() {
        return "U Turn Traps invert your controls for 10 steps when stepped on.";
    }
}