package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.HoldingBag;
import studio.ignitionigloogames.dungeondiver1.dungeon.DungeonGUI;

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
