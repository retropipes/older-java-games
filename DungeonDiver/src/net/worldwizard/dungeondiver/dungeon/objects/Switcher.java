package net.worldwizard.dungeondiver.dungeon.objects;

import net.worldwizard.dungeondiver.DungeonDiver;

public class Switcher extends GenericRONSObject {
    // Serialization
    private static final long serialVersionUID = -329523532523463L;

    // Constructors
    public Switcher() {
        super(false, "Switcher");
    }

    @Override
    public void moveOntoHook() {
        DungeonDiver.getHoldingBag().getDungeonGUI().newScheme();
    }
}
