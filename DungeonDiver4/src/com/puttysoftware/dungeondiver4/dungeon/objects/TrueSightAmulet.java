/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractAmulet;
import com.puttysoftware.dungeondiver4.dungeon.effects.DungeonEffectConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class TrueSightAmulet extends AbstractAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public TrueSightAmulet() {
        super(ColorConstants.COLOR_BLUE);
    }

    @Override
    public String getName() {
        return "True Sight Amulet";
    }

    @Override
    public String getPluralName() {
        return "True Sight Amulets";
    }

    @Override
    public String getDescription() {
        return "True Sight Amulets grant the power to see what things really are for 30 steps. Note that you can only wear one amulet at once.";
    }

    @Override
    public void postMoveActionHook() {
        DungeonDiver4.getApplication().getGameManager().activateEffect(
                DungeonEffectConstants.EFFECT_TRUE_SIGHT,
                TrueSightAmulet.EFFECT_DURATION);
    }
}