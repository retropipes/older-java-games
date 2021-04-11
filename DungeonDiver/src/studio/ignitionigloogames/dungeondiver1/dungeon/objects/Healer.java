package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;

public class Healer extends GenericNSRSBObject {
    // Serialization
    private static final long serialVersionUID = -5939638046443L;

    // Constructors
    public Healer() {
        super(false, "Healer", 20);
    }

    @Override
    public void moveOntoHook() {
        DungeonDiver.getHoldingBag().getHealer().showShop();
    }
}
