package studio.ignitionigloogames.dungeondiver1.dungeon;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.dungeon.objects.DungeonObjectList;

public class NewSchemeTask implements Runnable {
    @Override
    public void run() {
        try {
            final DungeonGUI gui = DungeonDiver.getHoldingBag().getDungeonGUI();
            gui.generateModeOn();
            SchemeList.setActiveScheme();
            gui.getDungeon().updateAllAppearances();
            gui.getObjectList();
            DungeonObjectList.updateAllAppearances();
            gui.getSavedMapObject().updateAppearance();
            gui.generateModeOff();
            gui.redrawDungeonBypassHook();
        } catch (final Throwable t) {
            DungeonDiver.debug(t);
        }
    }
}
