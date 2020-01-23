/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures.characterfiles;

import java.io.File;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.support.creatures.PartyMember;
import com.puttysoftware.gemma.support.scenario.Extension;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class CharacterLoader {
    private static PartyMember loadCharacter(String name) {
        String basePath = CharacterRegistration.getBasePath();
        String loadPath = basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod();
        try (XDataReader loader = new XDataReader(loadPath, "character")) {
            return PartyMember.read(loader);
        } catch (Exception e) {
            return null;
        }
    }

    public static PartyMember[] loadAllRegisteredCharacters() {
        String[] registeredNames = CharacterRegistration.getCharacterNameList();
        if (registeredNames != null) {
            PartyMember[] res = new PartyMember[registeredNames.length];
            // Load characters
            for (int x = 0; x < registeredNames.length; x++) {
                String name = registeredNames[x];
                PartyMember characterWithName = CharacterLoader
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

    public static void saveCharacter(PartyMember character) {
        String basePath = CharacterRegistration.getBasePath();
        String name = character.getName();
        String characterFile = basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod();
        try (XDataWriter saver = new XDataWriter(characterFile, "character")) {
            character.write(saver);
        } catch (Exception e) {
            // Ignore
        }
    }

    static void deleteCharacter(String name) {
        String basePath = CharacterRegistration.getBasePath();
        String characterFile = basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod();
        File toDelete = new File(characterFile);
        if (toDelete.exists()) {
            boolean success = toDelete.delete();
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
