/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures.characterfiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.fileutils.ResourceStreamReader;
import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.scenario.Extension;

public class CharacterRegistration {
    // Fields
    private static boolean ANY_FOUND = false;
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Putty Software/Gemma/Characters";
    private static final String WIN_DIR = "\\Putty Software\\Gemma\\Characters";
    private static final String UNIX_DIR = "/.puttysoftware/gemma/characters";

    // Methods
    public static void registerCharacter() {
        String suffix;
        if (Support.inDebugMode()) {
            suffix = " (DEBUG)";
        } else {
            suffix = "";
        }
        // Load character list
        String[] characterNameList = CharacterRegistration
                .getCharacterNameList();
        String[] characterNames = new File(CharacterRegistration.getBasePath())
                .list(new CharacterFilter());
        if (characterNames != null && characterNames.length > 0) {
            // Strip extension
            int stripCount = Extension.getCharacterExtensionWithPeriod()
                    .length();
            for (int x = 0; x < characterNames.length; x++) {
                String temp = characterNames[x];
                characterNames[x] = temp.substring(0,
                        temp.length() - stripCount);
            }
            // Pick character to register
            String res = CommonDialogs.showInputDialog(
                    "Register Which Character?", "Register Character" + suffix,
                    characterNames, characterNames[0]);
            if (res != null) {
                // Verify that character is not already registered
                boolean alreadyRegistered = false;
                if (characterNameList != null) {
                    for (int x = 0; x < characterNameList.length; x++) {
                        if (characterNameList[x].equalsIgnoreCase(res)) {
                            alreadyRegistered = true;
                            break;
                        }
                    }
                }
                if (!alreadyRegistered) {
                    // Verify that character file exists
                    if (new File(CharacterRegistration.getBasePath()
                            + File.separator + res
                            + Extension.getCharacterExtensionWithPeriod())
                                    .exists()) {
                        // Register it
                        if (CharacterRegistration.ANY_FOUND
                                && characterNameList != null) {
                            String[] newCharacterList = new String[characterNameList.length
                                    + 1];
                            for (int x = 0; x < newCharacterList.length; x++) {
                                if (x < characterNameList.length) {
                                    newCharacterList[x] = characterNameList[x];
                                } else {
                                    newCharacterList[x] = res;
                                }
                            }
                            CharacterRegistration
                                    .writeCharacterRegistry(newCharacterList);
                        } else {
                            CharacterRegistration.writeCharacterRegistry(
                                    new String[] { res });
                        }
                    } else {
                        CommonDialogs.showDialog(
                                "The character to register is not a valid character.");
                    }
                } else {
                    CommonDialogs.showDialog(
                            "The character to register has been registered already.");
                }
            }
        } else {
            CommonDialogs.showDialog("No characters found to register!");
        }
    }

    public static void unregisterCharacter() {
        String suffix;
        if (Support.inDebugMode()) {
            suffix = " (DEBUG)";
        } else {
            suffix = "";
        }
        // Load character list
        String[] characterNameList = CharacterRegistration
                .getCharacterNameList();
        // Check for null list
        if (characterNameList == null) {
            CommonDialogs.showTitledDialog("No Characters Registered!",
                    "Unregister Character" + suffix);
            return;
        }
        // Pick character to unregister
        String res = CommonDialogs.showInputDialog(
                "Unregister Which Character?", "Unregister Character" + suffix,
                characterNameList, characterNameList[0]);
        if (res != null) {
            // Find character index
            int index = -1;
            for (int x = 0; x < characterNameList.length; x++) {
                if (characterNameList[x].equals(res)) {
                    index = x;
                    break;
                }
            }
            if (index != -1) {
                // Unregister it
                if (characterNameList.length > 1) {
                    characterNameList[index] = null;
                    String[] newCharacterList = new String[characterNameList.length
                            - 1];
                    int offset = 0;
                    for (int x = 0; x < characterNameList.length; x++) {
                        if (characterNameList[x] != null) {
                            newCharacterList[x - offset] = characterNameList[x];
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

    public static void removeCharacter() {
        String suffix;
        if (Support.inDebugMode()) {
            suffix = " (DEBUG)";
        } else {
            suffix = "";
        }
        // Load character list
        String[] characterNameList = CharacterRegistration
                .getCharacterNameList();
        // Check for null list
        if (characterNameList == null) {
            CommonDialogs.showTitledDialog("No Characters Registered!",
                    "Remove Character" + suffix);
            return;
        }
        // Pick character to unregister
        String res = CommonDialogs.showInputDialog("Remove Which Character?",
                "Remove Character" + suffix, characterNameList,
                characterNameList[0]);
        if (res != null) {
            // Find character index
            int index = -1;
            for (int x = 0; x < characterNameList.length; x++) {
                if (characterNameList[x].equals(res)) {
                    index = x;
                    break;
                }
            }
            if (index != -1) {
                // Unregister it
                if (characterNameList.length > 1) {
                    characterNameList[index] = null;
                    String[] newCharacterList = new String[characterNameList.length
                            - 1];
                    int offset = 0;
                    for (int x = 0; x < characterNameList.length; x++) {
                        if (characterNameList[x] != null) {
                            newCharacterList[x - offset] = characterNameList[x];
                        } else {
                            offset++;
                        }
                    }
                    CharacterRegistration
                            .writeCharacterRegistry(newCharacterList);
                } else {
                    CharacterRegistration.writeCharacterRegistry(null);
                }
                CharacterLoader.deleteCharacter(res);
            }
        }
    }

    private static String getDirPrefix() {
        String osName = System.getProperty("os.name");
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
        String osName = System.getProperty("os.name");
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
        StringBuilder b = new StringBuilder();
        b.append(CharacterRegistration.getDirPrefix());
        b.append(CharacterRegistration.getDirectory());
        return b.toString();
    }

    static String[] getCharacterNameList() {
        ArrayList<String> registeredNames = CharacterRegistration
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
                String name = registeredNames.get(x);
                characterList[x] = name;
            }
        }
        return characterList;
    }

    public static void autoregisterCharacter(String name) {
        // Load character list
        String[] characterNameList = CharacterRegistration
                .getCharacterNameList();
        if (name != null) {
            // Verify that character is not already registered
            boolean alreadyRegistered = false;
            if (characterNameList != null) {
                for (int x = 0; x < characterNameList.length; x++) {
                    if (characterNameList[x].equalsIgnoreCase(name)) {
                        alreadyRegistered = true;
                        break;
                    }
                }
            }
            if (!alreadyRegistered) {
                // Register it
                if (characterNameList != null) {
                    String[] newCharacterList = new String[characterNameList.length
                            + 1];
                    for (int x = 0; x < newCharacterList.length; x++) {
                        if (x < characterNameList.length) {
                            newCharacterList[x] = characterNameList[x];
                        } else {
                            newCharacterList[x] = name;
                        }
                    }
                    CharacterRegistration
                            .writeCharacterRegistry(newCharacterList);
                } else {
                    CharacterRegistration
                            .writeCharacterRegistry(new String[] { name });
                }
            }
        }
    }

    private static ArrayList<String> readCharacterRegistry() {
        String basePath = CharacterRegistration.getBasePath();
        // Load character registry file
        ArrayList<String> registeredNames = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(
                basePath + File.separator + "CharacterRegistry"
                        + Extension.getRegistryExtensionWithPeriod());
                ResourceStreamReader rsr = new ResourceStreamReader(fis)) {
            String input = "";
            while (input != null) {
                input = rsr.readString();
                if (input != null) {
                    registeredNames.add(input);
                }
            }
        } catch (IOException io) {
            // Abort
            return new ArrayList<>();
        }
        return registeredNames;
    }

    private static void writeCharacterRegistry(String[] newCharacterList) {
        String basePath = CharacterRegistration.getBasePath();
        // Check if registry is writable
        File regFile = new File(basePath + File.separator + "CharacterRegistry"
                + Extension.getRegistryExtensionWithPeriod());
        if (!regFile.exists()) {
            // Not writable, probably because needed folders don't exist
            File regParent = regFile.getParentFile();
            if (!regParent.exists()) {
                boolean res = regParent.mkdirs();
                if (!res) {
                    // Creating the needed folders failed, so abort
                    return;
                }
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
        } catch (IOException io) {
            io.printStackTrace();
            // Abort
        }
    }
}
