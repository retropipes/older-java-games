/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.creatures.characterfiles;

import java.io.File;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.creatures.party.PartyMember;
import com.puttysoftware.dungeondiver4.dungeon.Extension;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class CharacterLoader {
    private static PartyMember loadCharacter(final String name) {
        final String basePath = CharacterRegistration.getBasePath();
        final String loadPath = basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod();
        try (XDataReader loader = new XDataReader(loadPath, "character")) {
            return PartyMember.read(loader);
        } catch (final Exception e) {
            return null;
        }
    }

    public static PartyMember[] loadAllRegisteredCharacters() {
        final String[] registeredNames = CharacterRegistration
                .getCharacterNameList();
        if (registeredNames != null) {
            final PartyMember[] res = new PartyMember[registeredNames.length];
            // Load characters
            for (int x = 0; x < registeredNames.length; x++) {
                final String name = registeredNames[x];
                final PartyMember characterWithName = CharacterLoader
                        .loadCharacter(name);
                if (characterWithName != null) {
                    res[x] = characterWithName;
                } else {
                    // Bad data
                    return null;
                }
            }
            return res;
        }
        return null;
    }

    public static void saveCharacter(final PartyMember character) {
        final String basePath = CharacterRegistration.getBasePath();
        final String name = character.getName();
        final String characterFile = basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod();
        try (XDataWriter saver = new XDataWriter(characterFile, "character")) {
            character.write(saver);
        } catch (final Exception e) {
            // Ignore
        }
    }

    static void deleteCharacter(final String name) {
        final String basePath = CharacterRegistration.getBasePath();
        final String characterFile = basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod();
        final File toDelete = new File(characterFile);
        if (toDelete.exists()) {
            final boolean success = toDelete.delete();
            if (success) {
                CommonDialogs.showDialog("Character removed.");
            } else {
                CommonDialogs.showDialog("Character removal failed!");
            }
        } else {
            CommonDialogs.showDialog(
                    "The character to be removed does not have a corresponding file.");
        }
    }
}
