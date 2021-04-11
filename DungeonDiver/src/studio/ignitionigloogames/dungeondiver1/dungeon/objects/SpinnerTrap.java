package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.Preferences;
import studio.ignitionigloogames.dungeondiver1.dungeon.DungeonGUI;
import studio.ignitionigloogames.dungeondiver1.utilities.BufferedImageIcon;

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
    public BufferedImageIcon getGameAppearance() {
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