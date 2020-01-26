package com.puttysoftware.dungeondiver4.game;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonNote;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class NoteManager {
    private NoteManager() {
        // Do nothing
    }

    public static void editNote() {
        final Dungeon m = DungeonDiver4.getApplication().getDungeonManager()
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
            SoundManager.playSound(SoundConstants.SOUND_SCRIBBLE);
        }
    }
}
