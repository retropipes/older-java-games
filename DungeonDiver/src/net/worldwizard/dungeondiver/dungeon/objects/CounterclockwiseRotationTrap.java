package net.worldwizard.dungeondiver.dungeon.objects;

import javax.swing.ImageIcon;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.Preferences;
import net.worldwizard.dungeondiver.dungeon.DungeonGUI;
import net.worldwizard.dungeondiver.dungeon.buffs.Rotated;

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
    public ImageIcon getGameAppearance() {
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