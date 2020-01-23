package net.worldwizard.dungeondiver.dungeon.objects;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.dungeon.DungeonGUI;

public class LightGem extends GenericNSRSBObject {
    // Serialization
    private static final long serialVersionUID = -32034603396305L; // Constants
    private static final int SHINING_LENGTH = 30;

    // Constructors
    public LightGem() {
        super(false, "LightGem", 80);
    }

    @Override
    public void moveOntoHook() {
        final DungeonGUI gui = DungeonDiver.getHoldingBag().getDungeonGUI();
        gui.decay();
        gui.getBuffManager().setShining(LightGem.SHINING_LENGTH);
    }
}
