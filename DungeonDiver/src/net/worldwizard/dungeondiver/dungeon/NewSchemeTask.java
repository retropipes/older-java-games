package net.worldwizard.dungeondiver.dungeon;

import javax.swing.JFrame;

import net.worldwizard.dungeondiver.DungeonDiver;
import net.worldwizard.dungeondiver.dungeon.objects.DungeonObjectList;

public class NewSchemeTask implements Runnable {
    @Override
    public void run() {
        try {
            final DungeonGUI gui = DungeonDiver.getHoldingBag().getDungeonGUI();
            final JFrame generatorFrame = gui.getGenerator();
            gui.hideDungeon();
            generatorFrame.setVisible(true);
            SchemeList.setActiveScheme();
            gui.getDungeon().updateAllAppearances();
            gui.getObjectList();
            DungeonObjectList.updateAllAppearances();
            gui.getSavedMapObject().updateAppearance();
            generatorFrame.setVisible(false);
            gui.showDungeonImmediately();
            gui.redrawDungeonBypassHook();
        } catch (final Throwable t) {
            DungeonDiver.getDebug().debug(t);
        }
    }
}
