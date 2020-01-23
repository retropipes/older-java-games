package net.worldwizard.dungeondiver.dungeon;

import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.dungeon.objects.Darkness;
import net.worldwizard.dungeondiver.dungeon.objects.DungeonObjectList;
import net.worldwizard.dungeondiver.dungeon.objects.Player;
import net.worldwizard.dungeondiver.dungeon.objects.Tile;
import net.worldwizard.map.NDimensionalLocation;
import net.worldwizard.map.NDimensionalMap;

public class NewDungeonAndSchemeTask implements Runnable {
    @Override
    public void run() {
        try {
            final DungeonGUI gui = DungeonDiver.getHoldingBag().getDungeonGUI();
            int visionState;
            try {
                visionState = gui.getDungeon().getDrawDistance();
            } catch (final NullPointerException np) {
                visionState = gui.getDefaultDrawDistance();
            }
            final DungeonObjectList objectList = gui.getObjectList();
            final Dimension viewingWindow = gui.getViewingWindow();
            final Darkness dark = (Darkness) DungeonObjectList
                    .getSpecificObject("Darkness");
            final int size = gui.computeDungeonSize();
            final DungeonMap dungeon = new DungeonMap(size, size,
                    viewingWindow, dark, dark);
            final JFrame generatorFrame = gui.getGenerator();
            gui.hideDungeon();
            final Tile tile = (Tile) DungeonObjectList
                    .getSpecificObject("Tile");
            final Player player = (Player) DungeonObjectList
                    .getSpecificObject("Player");
            generatorFrame.setVisible(true);
            dungeon.fillMapRandomly(objectList, tile);
            NDimensionalLocation playerLocation;
            boolean found = dungeon.findObject(player);
            if (found) {
                gui.setPlayerLocation(dungeon.getFindResult());
                playerLocation = gui.getPlayerLocation();
                final Point newViewingWindow = new Point(
                        playerLocation.getLocation(NDimensionalMap.ROW_DIMENSION)
                                - DungeonGUI.VIEWING_WINDOW_SIZE / 2,
                        playerLocation
                                .getLocation(NDimensionalMap.COLUMN_DIMENSION)
                                - DungeonGUI.VIEWING_WINDOW_SIZE / 2);
                dungeon.alterViewingWindow(newViewingWindow);
            } else {
                while (!found) {
                    dungeon.fillMapRandomly(objectList, tile);
                    found = dungeon.findObject(player);
                    if (found) {
                        gui.setPlayerLocation(dungeon.getFindResult());
                        playerLocation = gui.getPlayerLocation();
                        final Point newViewingWindow = new Point(
                                playerLocation.getLocation(NDimensionalMap.ROW_DIMENSION)
                                        - DungeonGUI.VIEWING_WINDOW_SIZE / 2,
                                playerLocation
                                        .getLocation(NDimensionalMap.COLUMN_DIMENSION)
                                        - DungeonGUI.VIEWING_WINDOW_SIZE / 2);
                        dungeon.alterViewingWindow(newViewingWindow);
                    }
                }
            }
            gui.setSavedMapObject(tile);
            SchemeList.setActiveScheme();
            dungeon.updateAllAppearances();
            gui.getObjectList();
            DungeonObjectList.updateAllAppearances();
            gui.getSavedMapObject().updateAppearance();
            dungeon.setDrawDistance(visionState);
            gui.setDungeon(dungeon);
            generatorFrame.setVisible(false);
            gui.showDungeonImmediately();
            gui.redrawDungeonBypassHook();
        } catch (final Throwable t) {
            DungeonDiver.getDebug().debug(t);
        }
    }
}
