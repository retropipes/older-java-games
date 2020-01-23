package net.worldwizard.dungeondiver.dungeon.objects;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.dungeon.DungeonGUI;

public class DarkGem extends GenericNSRSBObject {
    // Constants
    private static final long serialVersionUID = 7350460823056L;
    private static final int PARTLY_BLINDED_LENGTH = 30;

    // Constructors
    public DarkGem() {
        super(false, "DarkGem", 80);
    }

    @Override
    public void moveOntoHook() {
        final DungeonGUI gui = DungeonDiver.getHoldingBag().getDungeonGUI();
        gui.decay();
        gui.getBuffManager().setPartlyBlinded(DarkGem.PARTLY_BLINDED_LENGTH);
    }
}
