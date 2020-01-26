package net.worldwizard.dungeondiver.dungeon.objects;

import javax.swing.ImageIcon;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.Preferences;
import net.worldwizard.dungeondiver.dungeon.DungeonGUI;
import net.worldwizard.map.NDimensionalMap;
import net.worldwizard.randomnumbers.RandomRange;

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
            int x = (int) row.generate();
            int y = (int) column.generate();
            while (!gui.tryWarp(x, y)) {
                x = (int) row.generate();
                y = (int) column.generate();
            }
            gui.warp(x, y);
        }
    }

    @Override
    public ImageIcon getGameAppearance() {
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