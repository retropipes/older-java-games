/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractAmulet;
import com.puttysoftware.dungeondiver4.dungeon.effects.DungeonEffectConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class FireAmulet extends AbstractAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public FireAmulet() {
        super(ColorConstants.COLOR_RED);
    }

    @Override
    public String getName() {
        return "Fire Amulet";
    }

    @Override
    public String getPluralName() {
        return "Fire Amulets";
    }

    @Override
    public String getDescription() {
        return "Fire Amulets grant the power to transform ground into Hot Rock for 30 steps. Note that you can only wear one amulet at once.";
    }

    @Override
    public void stepAction() {
        final int x = DungeonDiver4.getApplication().getDungeonManager()
                .getDungeon().getPlayerLocationX();
        final int y = DungeonDiver4.getApplication().getDungeonManager()
                .getDungeon().getPlayerLocationY();
        final int z = DungeonDiver4.getApplication().getDungeonManager()
                .getDungeon().getPlayerLocationZ();
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .hotGround(x, y, z);
    }

    @Override
    public void postMoveActionHook() {
        DungeonDiver4.getApplication().getGameManager().activateEffect(
                DungeonEffectConstants.EFFECT_FIERY,
                FireAmulet.EFFECT_DURATION);
    }
}