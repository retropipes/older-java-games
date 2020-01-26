/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.creatures.characterfiles;

import java.io.File;
import java.io.IOException;

import net.dynamicdungeon.commondialogs.CommonDialogs;
import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.VersionException;
import net.dynamicdungeon.dynamicdungeon.creatures.party.PartyMember;
import net.dynamicdungeon.dynamicdungeon.dungeon.Extension;

public class CharacterLoader {
    private static PartyMember loadCharacter(final String name) {
        final String basePath = CharacterRegistration.getBasePath();
        final String loadPath = basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod();
        try (DatabaseReader loader = new DatabaseReader(loadPath)) {
            return PartyMember.read(loader);
        } catch (final VersionException e) {
            CharacterRegistration.autoremoveCharacter(name);
            return null;
        } catch (final IOException e) {
            DynamicDungeon.getErrorLogger().logError(e);
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
                    // Auto-removed character
                    return CharacterLoader.loadAllRegisteredCharacters();
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
        try (DatabaseWriter saver = new DatabaseWriter(characterFile)) {
            character.write(saver);
        } catch (final IOException e) {
            DynamicDungeon.getErrorLogger().logError(e);
        }
    }

    static void deleteCharacter(final String name, final boolean showResults) {
        final String basePath = CharacterRegistration.getBasePath();
        final String characterFile = basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod();
        final File toDelete = new File(characterFile);
        if (toDelete.exists()) {
            final boolean success = toDelete.delete();
            if (success) {
                if (showResults) {
                    CommonDialogs.showDialog("Character removed.");
                } else {
                    CommonDialogs.showDialog("Character " + name
                            + " autoremoved due to version change.");
                }
            } else {
                if (showResults) {
                    CommonDialogs.showDialog("Character removal failed!");
                } else {
                    CommonDialogs.showDialog(
                            "Character " + name + " failed to autoremove!");
                }
            }
        } else {
            if (showResults) {
                CommonDialogs.showDialog(
                        "The character to be removed does not have a corresponding file.");
            } else {
                CommonDialogs.showDialog(
                        "The character to be autoremoved does not have a corresponding file.");
            }
        }
    }
}
