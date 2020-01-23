package net.worldwizard.dungeondiver.dungeon.objects;

import javax.swing.ImageIcon;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.Preferences;
import net.worldwizard.dungeondiver.dungeon.DungeonGUI;

public class ConfusionTrap extends GenericNSRSBObject {
    // Constants
    private static final long serialVersionUID = -235935209530923L;
    private static final int CONFUSION_LENGTH = 10;

    // Constructors
    public ConfusionTrap() {
        super(false, "ConfusionTrap", 80);
    }

    @Override
    public void moveOntoHook() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(Preferences.CONFUSION_TRAP_ENABLED);
        if (enabled) {
            final DungeonGUI gui = DungeonDiver.getHoldingBag().getDungeonGUI();
            gui.getBuffManager().setConfused(ConfusionTrap.CONFUSION_LENGTH);
        }
    }

    @Override
    public ImageIcon getGameAppearance() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(Preferences.CONFUSION_TRAP_ENABLED);
        if (enabled) {
            return super.getGameAppearance();
        } else {
            DungeonDiver.getHoldingBag().getDungeonGUI().getObjectList();
            return DungeonObjectList.getSpecificObject("Tile")
                    .getGameAppearance();
        }
    }
}