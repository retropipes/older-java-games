package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.Preferences;
import studio.ignitionigloogames.dungeondiver1.dungeon.DungeonGUI;
import studio.ignitionigloogames.dungeondiver1.dungeon.buffs.Rotated;
import studio.ignitionigloogames.dungeondiver1.utilities.BufferedImageIcon;

public class ClockwiseRotationTrap extends GenericNSRSBObject {
    // Fields
    private static final long serialVersionUID = -38406034300080L;
    private static final int ROTATION_LENGTH = 10;

    // Constructors
    public ClockwiseRotationTrap() {
        super(false, "ClockwiseRotationTrap", 80);
    }

    @Override
    public void moveOntoHook() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(
                        Preferences.CLOCKWISE_ROTATION_TRAP_ENABLED);
        if (enabled) {
            final DungeonGUI gui = DungeonDiver.getHoldingBag().getDungeonGUI();
            gui.getBuffManager().setRotated(
                    ClockwiseRotationTrap.ROTATION_LENGTH,
                    Rotated.ROTATED_STATE_CLOCKWISE);
        }
    }

    @Override
    public BufferedImageIcon getGameAppearance() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(
                        Preferences.CLOCKWISE_ROTATION_TRAP_ENABLED);
        if (enabled) {
            return super.getGameAppearance();
        } else {
            DungeonDiver.getHoldingBag().getDungeonGUI().getObjectList();
            return DungeonObjectList.getSpecificObject("Tile")
                    .getGameAppearance();
        }
    }
}