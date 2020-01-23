package net.worldwizard.worldz.creatures;

import java.io.File;
import java.io.IOException;

import net.worldwizard.io.DataConstants;
import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.world.Extension;

public class CharacterLoader {
    private static PartyMember loadCharacter(final String name) {
        final String basePath = CharacterRegistration.getBasePath();
        DataReader loader = null;
        try {
            final String loadPath = basePath + "/Characters/" + name
                    + Extension.getCharacterExtensionWithPeriod();
            loader = new DataReader(loadPath, DataConstants.DATA_MODE_BINARY);
            return PartyMember.readCharacter(loader);
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
        final File characterFile = new File(basePath + "/Characters/" + name
                + Extension.getCharacterExtensionWithPeriod());
        DataWriter saver = null;
        try {
            final String savePath = characterFile.getAbsolutePath();
            saver = new DataWriter(savePath, DataConstants.DATA_MODE_BINARY);
            character.writeCharacter(saver);
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
