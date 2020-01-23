package net.worldwizard.worldz.creatures;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import net.worldwizard.io.ResourceStreamReader;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.world.Extension;

public class CharacterRegistration {
    // Fields
    private static boolean ANY_FOUND = false;
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Worldz";
    private static final String WIN_DIR = "\\Worldz";
    private static final String UNIX_DIR = "/.worldz";

    // Methods
    public static String[] getCharacterList() {
        final Vector<String> registeredNames = CharacterRegistration
                .readCharacterRegistry();
        CharacterRegistration.ANY_FOUND = false;
        if (registeredNames != null) {
            if (registeredNames.size() > 0) {
                CharacterRegistration.ANY_FOUND = true;
            }
        }
        // Load character list
        String[] characterList = null;
        if (CharacterRegistration.ANY_FOUND && registeredNames != null) {
            registeredNames.trimToSize();
            characterList = new String[registeredNames.size()];
            for (int x = 0; x < registeredNames.size(); x++) {
                final String name = registeredNames.get(x);
                characterList[x] = name;
            }
        }
        return characterList;
    }

    public static void registerCharacter() {
        // Load character list
        final String[] characterList = CharacterRegistration.getCharacterList();
        // Pick character to register
        final String res = Messager.showTextInputDialog(
                "Register Which Character?", "Register Character");
        if (res != null) {
            // Verify that character is not already registered
            boolean alreadyRegistered = false;
            if (characterList != null) {
                for (final String element : characterList) {
                    if (element.equalsIgnoreCase(res)) {
                        alreadyRegistered = true;
                        break;
                    }
                }
            }
            if (!alreadyRegistered) {
                // Verify that character file exists
                if (new File(CharacterRegistration.getBasePath()
                        + File.separator + "Characters" + File.separator + res
                        + Extension.getCharacterExtensionWithPeriod()).exists()) {
                    // Register it
                    if (CharacterRegistration.ANY_FOUND
                            && characterList != null) {
                        final String[] newCharacterList = new String[characterList.length + 1];
                        for (int x = 0; x < newCharacterList.length; x++) {
                            if (x < characterList.length) {
                                newCharacterList[x] = characterList[x];
                            } else {
                                newCharacterList[x] = res;
                            }
                        }
                        CharacterRegistration
                                .writeCharacterRegistry(newCharacterList);
                    } else {
                        CharacterRegistration
                                .writeCharacterRegistry(new String[] { res });
                    }
                } else {
                    Messager.showDialog("The character to register is not a valid character.");
                }
            } else {
                Messager.showDialog("The character to register has been registered already.");
            }
        }
    }

    public static void autoregisterCharacter(final String res) {
        // Load character list
        final String[] characterList = CharacterRegistration.getCharacterList();
        if (res != null) {
            // Verify that character is not already registered
            boolean alreadyRegistered = false;
            if (characterList != null) {
                for (final String element : characterList) {
                    if (element.equalsIgnoreCase(res)) {
                        alreadyRegistered = true;
                        break;
                    }
                }
            }
            if (!alreadyRegistered) {
                // Register it
                if (CharacterRegistration.ANY_FOUND && characterList != null) {
                    final String[] newCharacterList = new String[characterList.length + 1];
                    for (int x = 0; x < newCharacterList.length; x++) {
                        if (x < characterList.length) {
                            newCharacterList[x] = characterList[x];
                        } else {
                            newCharacterList[x] = res;
                        }
                    }
                    CharacterRegistration
                            .writeCharacterRegistry(newCharacterList);
                } else {
                    CharacterRegistration
                            .writeCharacterRegistry(new String[] { res });
                }
            }
        }
    }

    public static void unregisterCharacter() {
        // Load character list
        String[] characterList = CharacterRegistration.getCharacterList();
        // Check for null list
        if (characterList == null) {
            characterList = new String[1];
            characterList[0] = "No Characters Registered";
        }
        // Pick character to unregister
        final String res = Messager.showInputDialog(
                "Unregister Which Character?", "Unregister Character",
                characterList, characterList[0]);
        if (res != null) {
            // Find character index
            int index = -1;
            for (int x = 0; x < characterList.length; x++) {
                if (characterList[x].equals(res)) {
                    index = x;
                    break;
                }
            }
            if (index != -1) {
                // Unregister it
                if (characterList.length > 1) {
                    characterList[index] = null;
                    final String[] newCharacterList = new String[characterList.length - 1];
                    int offset = 0;
                    for (int x = 0; x < newCharacterList.length; x++) {
                        if (characterList[x + offset] != null) {
                            newCharacterList[x] = characterList[x + offset];
                        } else {
                            offset++;
                        }
                    }
                    CharacterRegistration
                            .writeCharacterRegistry(newCharacterList);
                } else {
                    CharacterRegistration.writeCharacterRegistry(null);
                }
            }
        }
    }

    private static Vector<String> readCharacterRegistry() {
        final String basePath = CharacterRegistration.getBasePath();
        // Load character registry file
        final Vector<String> registeredNames = new Vector<>();
        ResourceStreamReader rsr = null;
        try (final FileInputStream fis = new FileInputStream(basePath
                + File.separator + "Characters" + File.separator
                + "CharacterRegistry"
                + Extension.getRegistryExtensionWithPeriod())) {
            rsr = new ResourceStreamReader(fis);
            String input = "";
            while (input != null) {
                input = rsr.readString();
                if (input != null) {
                    registeredNames.add(input);
                }
            }
        } catch (final IOException io) {
            // Abort
            return null;
        } finally {
            if (rsr != null) {
                try {
                    rsr.close();
                } catch (final IOException io2) {
                    // Ignore
                }
            }
        }
        return registeredNames;
    }

    private static void writeCharacterRegistry(final String[] newCharacterList) {
        final String basePath = CharacterRegistration.getBasePath();
        // Check if registry is writable
        final File regFile = new File(basePath + File.separator + "Characters"
                + File.separator + "CharacterRegistry"
                + Extension.getRegistryExtensionWithPeriod());
        if (!regFile.canWrite()) {
            // Not writable, probably because needed folders don't exist
            final File regParent = regFile.getParentFile();
            final boolean res = regParent.mkdirs();
            if (!res) {
                // Creating the needed folders failed, so abort
                return;
            }
        }
        // Save character registry file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(regFile))) {
            if (newCharacterList != null) {
                for (int x = 0; x < newCharacterList.length; x++) {
                    if (x != newCharacterList.length - 1) {
                        bw.write(newCharacterList[x] + "\n");
                    } else {
                        bw.write(newCharacterList[x]);
                    }
                }
            }
        } catch (final IOException io) {
            // Abort
        }
    }

    private static String getDirPrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(CharacterRegistration.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(CharacterRegistration.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(CharacterRegistration.UNIX_PREFIX);
        }
    }

    private static String getDirectory() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return CharacterRegistration.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return CharacterRegistration.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return CharacterRegistration.UNIX_DIR;
        }
    }

    static String getBasePath() {
        final StringBuilder b = new StringBuilder();
        b.append(CharacterRegistration.getDirPrefix());
        b.append(CharacterRegistration.getDirectory());
        return b.toString();
    }
}
