/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractBoots;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;

public class HotBoots extends AbstractBoots {
    // Constructors
    public HotBoots() {
        super(ColorConstants.COLOR_RED);
    }

    @Override
    public String getName() {
        return "Hot Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Hot Boots";
    }

    @Override
    public String getDescription() {
        return "Hot Boots transform any ground into Hot Rock as you walk. Note that you can only wear one pair of boots at once.";
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
}
