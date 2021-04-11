package studio.ignitionigloogames.dungeondiver1.dungeon.buffs;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;

public class PartlyBlinded extends DungeonBuff {
    // Constructor
    public PartlyBlinded(final int newRounds) {
        super("Partly Blinded", 0, DungeonBuff.WHAT_VISION, newRounds);
    }

    @Override
    public int customUseLogic(final int arg) {
        if (!this.isActive()) {
            DungeonDiver.getHoldingBag().getDungeonGUI().increaseVisibility();
        }
        return arg;
    }
}