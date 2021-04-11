package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.Preferences;
import studio.ignitionigloogames.dungeondiver1.dungeon.DungeonGUI;
import studio.ignitionigloogames.dungeondiver1.dungeon.buffs.Rotated;
import studio.ignitionigloogames.dungeondiver1.utilities.BufferedImageIcon;

public class CounterclockwiseRotationTrap extends GenericNSRSBObject {
    // Serialization
    private static final long serialVersionUID = -38406034300080L; // Private
                                                                   // Constants
    private static final int ROTATION_LENGTH = 10;

    // Constructors
    public CounterclockwiseRotationTrap() {
        super(false, "CounterclockwiseRotationTrap", 80);
    }

    @Override
    public void moveOntoHook() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(
                        Preferences.COUNTERCLOCKWISE_ROTATION_TRAP_ENABLED);
        if (enabled) {
            final DungeonGUI gui = DungeonDiver.getHoldingBag().getDungeonGUI();
            gui.getBuffManager().setRotated(
                    CounterclockwiseRotationTrap.ROTATION_LENGTH,
                    Rotated.ROTATED_STATE_COUNTERCLOCKWISE);
        }
    }

    @Override
    public BufferedImageIcon getGameAppearance() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(
                        Preferences.COUNTERCLOCKWISE_ROTATION_TRAP_ENABLED);
        if (enabled) {
            return super.getGameAppearance();
        } else {
            DungeonDiver.getHoldingBag().getDungeonGUI().getObjectList();
            return DungeonObjectList.getSpecificObject("Tile")
                    .getGameAppearance();
        }
    }
}