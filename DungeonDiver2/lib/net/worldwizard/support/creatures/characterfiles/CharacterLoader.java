package net.worldwizard.support.creatures.characterfiles;

import java.io.File;
import java.io.IOException;

import net.worldwizard.support.creatures.PartyMember;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class CharacterLoader {
    private static PartyMember loadCharacter(final String name) {
        final String basePath = CharacterRegistration.getBasePath();
        XDataReader loader = null;
        try {
            final String loadPath = basePath + File.separator + name
                    + Extension.getCharacterExtensionWithPeriod();
            loader = new XDataReader(loadPath, "character");
            return PartyMember.read(loader);
        } catch (final Exception e) {
            return null;
        } finally {
            if (loader != null) {
                try {
                    loader.close();
                } catch (final IOException io) {
                    // Ignore
                }
            }
        }
    }

    public static PartyMember[] loadAllRegisteredCharacters() {
        final String[] registeredNames = CharacterRegistration
                .getCharacterList();
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
        final File characterFile = new File(basePath + File.separator + name
                + Extension.getCharacterExtensionWithPeriod());
        XDataWriter saver = null;
        try {
            final String savePath = characterFile.getAbsolutePath();
            saver = new XDataWriter(savePath, "character");
            character.write(saver);
        } catch (final Exception e) {
            // Ignore
        } finally {
            if (saver != null) {
                try {
                    saver.close();
                } catch (final IOException io) {
                    // Ignore
                }
            }
        }
    }
}
