package com.puttysoftware.gemma.game;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.gemma.support.map.Map;
import com.puttysoftware.gemma.support.map.MapNote;

public class NoteManager {
    private NoteManager() {
        // Do nothing
    }

    public static void editNote() {
        Map m = Gemma.getApplication().getScenarioManager().getMap();
        int x = m.getPlayerLocationX();
        int y = m.getPlayerLocationY();
        int z = m.getPlayerLocationZ();
        String defaultText = "Empty Note";
        if (m.hasNote(x, y, z)) {
            defaultText = m.getNote(x, y, z).getContents();
        }
        String result = CommonDialogs.showTextInputDialogWithDefault(
                "Note Text:", "Edit Note", defaultText);
        if (result != null) {
            if (!m.hasNote(x, y, z)) {
                m.createNote(x, y, z);
            }
            MapNote mn = m.getNote(x, y, z);
            mn.setContents(result);
        }
    }
}
