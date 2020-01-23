package net.worldwizard.dungeondiver.dungeon.buffs;

import net.worldwizard.dungeondiver.DungeonDiver;

public class Shining extends DungeonBuff {
    // Constructor
    public Shining(final int newRounds) {
        super("Shining", 0, DungeonBuff.WHAT_VISION, newRounds);
    }

    @Override
    public int customUseLogic(final int arg) {
        if (!this.isActive()) {
            DungeonDiver.getHoldingBag().getDungeonGUI().decreaseVisibility();
        }
        return arg;
    }
}