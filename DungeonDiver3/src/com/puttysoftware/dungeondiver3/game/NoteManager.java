package com.puttysoftware.dungeondiver3.game;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver3.DungeonDiver3;
import com.puttysoftware.dungeondiver3.support.map.Map;
import com.puttysoftware.dungeondiver3.support.map.MapNote;

public class NoteManager {
    private NoteManager() {
        // Do nothing
    }

    public static void editNote() {
        final Map m = DungeonDiver3.getApplication().getScenarioManager()
                .getMap();
        final int x = m.getPlayerLocationX();
        final int y = m.getPlayerLocationY();
        final int z = m.getPlayerLocationZ();
        String defaultText = "Empty Note";
        if (m.hasNote(x, y, z)) {
            defaultText = m.getNote(x, y, z).getContents();
        }
        final String result = CommonDialogs.showTextInputDialogWithDefault(
                "Note Text:", "Edit Note", defaultText);
        if (result != null) {
            if (!m.hasNote(x, y, z)) {
                m.createNote(x, y, z);
            }
            final MapNote mn = m.getNote(x, y, z);
            mn.setContents(result);
        }
    }
}
