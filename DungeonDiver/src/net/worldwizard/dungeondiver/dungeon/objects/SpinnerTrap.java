package net.worldwizard.dungeondiver.dungeon.objects;

import javax.swing.ImageIcon;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.Preferences;
import net.worldwizard.dungeondiver.dungeon.DungeonGUI;

public class SpinnerTrap extends GenericNSRSBObject {
    // Constants
    private static final long serialVersionUID = -235935209530923L;
    private static final int DIZZY_LENGTH = 3;

    // Constructors
    public SpinnerTrap() {
        super(false, "SpinnerTrap", 160);
    }

    @Override
    public void moveOntoHook() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(Preferences.SPINNER_TRAP_ENABLED);
        if (enabled) {
            final DungeonGUI gui = DungeonDiver.getHoldingBag().getDungeonGUI();
            gui.getBuffManager().setDizzy(SpinnerTrap.DIZZY_LENGTH);
        }
    }

    @Override
    public ImageIcon getGameAppearance() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(Preferences.SPINNER_TRAP_ENABLED);
        if (enabled) {
            return super.getGameAppearance();
        } else {
            DungeonDiver.getHoldingBag().getDungeonGUI().getObjectList();
            return DungeonObjectList.getSpecificObject("Tile")
                    .getGameAppearance();
        }
    }
}