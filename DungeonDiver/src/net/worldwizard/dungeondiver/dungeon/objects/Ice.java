package net.worldwizard.dungeondiver.dungeon.objects;

import javax.swing.ImageIcon;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.Preferences;
import net.worldwizard.map.NDimensionalMap;

public class Ice extends GenericNotSurroundedObject {
    // Serialization
    private static final long serialVersionUID = -84993523502L;

    // Constructors
    public Ice() {
        super(false, "Ice");
    }

    @Override
    public int getMaximumRequiredQuantity(final NDimensionalMap map) {
        return 0;
    }

    @Override
    public int getMinimumRequiredQuantity(final NDimensionalMap map) {
        return 0;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public void moveOntoHook() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(Preferences.ICE_ENABLED);
        if (enabled) {
            DungeonDiver.getHoldingBag().getDungeonGUI().updatePositionAgain();
        }
    }

    @Override
    public ImageIcon getGameAppearance() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(Preferences.ICE_ENABLED);
        if (enabled) {
            return super.getGameAppearance();
        } else {
            DungeonDiver.getHoldingBag().getDungeonGUI().getObjectList();
            return DungeonObjectList.getSpecificObject("Tile")
                    .getGameAppearance();
        }
    }
}
