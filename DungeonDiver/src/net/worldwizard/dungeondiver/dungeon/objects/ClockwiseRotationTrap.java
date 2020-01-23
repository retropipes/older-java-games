package net.worldwizard.dungeondiver.dungeon.objects;

import javax.swing.ImageIcon;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.Preferences;
import net.worldwizard.dungeondiver.dungeon.DungeonGUI;
import net.worldwizard.dungeondiver.dungeon.buffs.Rotated;

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
        final boolean enabled = DungeonDiver
                .getHoldingBag()
                .getPrefs()
                .getPreferenceValue(Preferences.CLOCKWISE_ROTATION_TRAP_ENABLED);
        if (enabled) {
            final DungeonGUI gui = DungeonDiver.getHoldingBag().getDungeonGUI();
            gui.getBuffManager().setRotated(
                    ClockwiseRotationTrap.ROTATION_LENGTH,
                    Rotated.ROTATED_STATE_CLOCKWISE);
        }
    }

    @Override
    public ImageIcon getGameAppearance() {
        final boolean enabled = DungeonDiver
                .getHoldingBag()
                .getPrefs()
                .getPreferenceValue(Preferences.CLOCKWISE_ROTATION_TRAP_ENABLED);
        if (enabled) {
            return super.getGameAppearance();
        } else {
            DungeonDiver.getHoldingBag().getDungeonGUI().getObjectList();
            return DungeonObjectList.getSpecificObject("Tile")
                    .getGameAppearance();
        }
    }
}