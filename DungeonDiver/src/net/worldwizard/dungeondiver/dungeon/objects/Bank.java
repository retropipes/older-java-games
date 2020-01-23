package net.worldwizard.dungeondiver.dungeon.objects;

import net.worldwizard.dungeondiver.DungeonDiver;

public class Bank extends GenericNSRSBObject {
    // Serialization
    private static final long serialVersionUID = -23523898352360L;

    // Constructors
    public Bank() {
        super(false, "Bank", 20);
    }

    @Override
    public void moveOntoHook() {
        DungeonDiver.getHoldingBag().getBank().showShop();
    }
}
