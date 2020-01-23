package net.worldwizard.dungeondiver.dungeon.objects;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.HoldingBag;
import net.worldwizard.dungeondiver.dungeon.DungeonGUI;

public class ZoomTube extends GenericRONSObject {
    // Serialization
    private static final long serialVersionUID = -329523532523463L;

    // Constructors
    public ZoomTube() {
        super(false, "ZoomTube");
    }

    @Override
    public void moveOntoHook() {
        final HoldingBag mm = DungeonDiver.getHoldingBag();
        final DungeonGUI gui = mm.getDungeonGUI();
        gui.newDungeon();
    }
}
