package net.worldwizard.dungeondiver.dungeon.buffs;

import net.worldwizard.dungeondiver.DungeonDiver;

public class ShiningBrightly extends DungeonBuff {
    // Constructor
    public ShiningBrightly(final int newRounds) {
        super("Shining Brightly", 0, DungeonBuff.WHAT_VISION, newRounds);
    }

    @Override
    public int customUseLogic(final int arg) {
        if (!this.isActive()) {
            DungeonDiver.getHoldingBag().getDungeonGUI().decreaseVisibility();
            DungeonDiver.getHoldingBag().getDungeonGUI().decreaseVisibility();
            DungeonDiver.getHoldingBag().getDungeonGUI().getBuffManager()
                    .setShiningOnExpiry();
        }
        return arg;
    }
}