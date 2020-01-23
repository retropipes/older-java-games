/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.effects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.objects.CounterpoisonAmulet;

public class CounterPoisoned extends DungeonEffect {
    // Constructor
    public CounterPoisoned(final int newRounds) {
        super("Counter-Poisoned", newRounds);
    }

    @Override
    public void customExtendLogic() {
        // Apply the effect
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .doCounterpoisonAmulet();
    }

    @Override
    public void customTerminateLogic() {
        // Remove item that granted effect from inventory
        DungeonDiver4.getApplication().getGameManager().getObjectInventory()
                .removeItem(new CounterpoisonAmulet());
        // Undo the effect
        DungeonDiver4.getApplication().getDungeonManager().getDungeon()
                .undoPoisonAmulets();
    }
}