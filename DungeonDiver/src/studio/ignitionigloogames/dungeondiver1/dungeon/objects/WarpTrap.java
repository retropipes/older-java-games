package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.Preferences;
import studio.ignitionigloogames.dungeondiver1.dungeon.DungeonGUI;
import studio.ignitionigloogames.dungeondiver1.utilities.BufferedImageIcon;
import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalMap;
import studio.ignitionigloogames.dungeondiver1.utilities.RandomRange;

public class WarpTrap extends GenericNSRSBObject {
    // Constants
    private static final long serialVersionUID = -352372525502L;

    // Constructors
    public WarpTrap() {
        super(false, "WarpTrap", 80);
    }

    @Override
    public void moveOntoHook() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(Preferences.WARP_TRAP_ENABLED);
        if (enabled) {
            final DungeonGUI gui = DungeonDiver.getHoldingBag().getDungeonGUI();
            final RandomRange row = new RandomRange(0,
                    gui.getDungeon().getDimensions()
                            .getLocation(NDimensionalMap.ROW_DIMENSION) - 1);
            final RandomRange column = new RandomRange(0,
                    gui.getDungeon().getDimensions()
                            .getLocation(NDimensionalMap.COLUMN_DIMENSION) - 1);
            int x = row.generate();
            int y = column.generate();
            while (!gui.tryWarp(x, y)) {
                x = row.generate();
                y = column.generate();
            }
            gui.warp(x, y);
        }
    }

    @Override
    public BufferedImageIcon getGameAppearance() {
        final boolean enabled = DungeonDiver.getHoldingBag().getPrefs()
                .getPreferenceValue(Preferences.WARP_TRAP_ENABLED);
        if (enabled) {
            return super.getGameAppearance();
        } else {
            DungeonDiver.getHoldingBag().getDungeonGUI().getObjectList();
            return DungeonObjectList.getSpecificObject("Tile")
                    .getGameAppearance();
        }
    }
}