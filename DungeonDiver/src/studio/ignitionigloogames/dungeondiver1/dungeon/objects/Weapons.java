package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;

public class Weapons extends GenericNSRSBObject {
    // Serialization
    private static final long serialVersionUID = -14959003064620L;

    // Constructors
    public Weapons() {
        super(false, "Weapons", 80);
    }

    @Override
    public void moveOntoHook() {
        DungeonDiver.getHoldingBag().getWeapons().showShop();
    }
}
