package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;

public class Regenerator extends GenericNSRSBObject {
    // Serialization
    private static final long serialVersionUID = -3523023052075L;

    // Constructors
    public Regenerator() {
        super(false, "Regenerator", 20);
    }

    @Override
    public void moveOntoHook() {
        DungeonDiver.getHoldingBag().getRegenerator().showShop();
    }
}
