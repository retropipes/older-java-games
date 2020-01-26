/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractAmulet;
import com.puttysoftware.dungeondiver4.dungeon.effects.DungeonEffectConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.game.GameLogicManager;

public class NormalAmulet extends AbstractAmulet {
    // Constructors
    public NormalAmulet() {
        super(ColorConstants.COLOR_MAGENTA);
    }

    @Override
    public String getName() {
        return "Normal Amulet";
    }

    @Override
    public String getPluralName() {
        return "Normal Amulets";
    }

    @Override
    public String getDescription() {
        return "Normal Amulets have no special effect. Note that you can only wear one amulet at once.";
    }

    @Override
    public void postMoveActionHook() {
        // Deactivate other amulet effects
        final GameLogicManager gm = DungeonDiver4.getApplication()
                .getGameManager();
        gm.deactivateEffect(DungeonEffectConstants.EFFECT_COUNTER_POISONED);
        gm.deactivateEffect(DungeonEffectConstants.EFFECT_FIERY);
        gm.deactivateEffect(DungeonEffectConstants.EFFECT_GHOSTLY);
        gm.deactivateEffect(DungeonEffectConstants.EFFECT_ICY);
        gm.deactivateEffect(DungeonEffectConstants.EFFECT_POISONOUS);
    }
}