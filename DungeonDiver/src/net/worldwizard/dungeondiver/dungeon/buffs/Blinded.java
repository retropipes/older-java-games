package net.worldwizard.dungeondiver.dungeon.buffs;

import net.worldwizard.dungeondiver.DungeonDiver;

public class Blinded extends DungeonBuff {
    // Constructor
    public Blinded(final int newRounds) {
        super("Blinded", 0, DungeonBuff.WHAT_VISION, newRounds);
    }

    @Override
    public int customUseLogic(final int arg) {
        if (!this.isActive()) {
            DungeonDiver.getHoldingBag().getDungeonGUI().increaseVisibility();
            DungeonDiver.getHoldingBag().getDungeonGUI().increaseVisibility();
            DungeonDiver.getHoldingBag().getDungeonGUI().getBuffManager()
                    .setPartlyBlindedOnExpiry();
        }
        return arg;
    }
}