/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.effects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.objects.IceAmulet;

public class Icy extends DungeonEffect {
    // Constructor
    public Icy(final int newRounds) {
        super("Icy", newRounds);
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        DungeonDiver4.getApplication().getGameManager().getObjectInventory()
                .removeItem(new IceAmulet());
    }
}