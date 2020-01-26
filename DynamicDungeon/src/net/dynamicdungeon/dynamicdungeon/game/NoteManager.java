package net.dynamicdungeon.dynamicdungeon.game;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.Dungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.DungeonNote;

public class NoteManager {
    private NoteManager() {
        // Do nothing
    }

    public static void editNote() {
        final Dungeon m = DynamicDungeon.getApplication().getDungeonManager()
                .getDungeon();
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
            final DungeonNote mn = m.getNote(x, y, z);
            mn.setContents(result);
        }
    }
}
