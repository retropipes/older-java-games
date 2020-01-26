/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.scenario;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.fileutils.ResourceStreamReader;

public class SaveRegistration {
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_SAVE_DIR = "/Library/Application Support/Putty Software/Gemma/Saves";
    private static final String WIN_SAVE_DIR = "\\Putty Software\\Gemma\\Saves";
    private static final String UNIX_SAVE_DIR = "/.puttysoftware/gemma/saves";
    private static boolean ANY_SAVES_FOUND = false;

    public static String[] getSaveList() {
        final ArrayList<String> registeredNames = SaveRegistration
                .readSaveRegistry();
        SaveRegistration.ANY_SAVES_FOUND = false;
        if (registeredNames != null) {
            if (registeredNames.size() > 0) {
                SaveRegistration.ANY_SAVES_FOUND = true;
            }
        }
        // Load save list
        String[] saveList = null;
        if (SaveRegistration.ANY_SAVES_FOUND && registeredNames != null) {
            registeredNames.trimToSize();
            saveList = new String[registeredNames.size()];
            for (int x = 0; x < registeredNames.size(); x++) {
                final String name = registeredNames.get(x);
                saveList[x] = name;
            }
        }
        return saveList;
    }

    public static void autoregisterSave(final String res) {
        // Load save list
        final String[] saveList = SaveRegistration.getSaveList();
        if (res != null) {
            // Verify that save is not already registered
            boolean alreadyRegistered = false;
            if (saveList != null) {
                for (final String element : saveList) {
                    if (element.equalsIgnoreCase(res)) {
                        alreadyRegistered = true;
                        break;
                    }
                }
            }
            if (!alreadyRegistered) {
                // Register it
                if (saveList != null) {
                    final String[] newSaveList = new String[saveList.length
                            + 1];
                    for (int x = 0; x < newSaveList.length; x++) {
                        if (x < saveList.length) {
                            newSaveList[x] = saveList[x];
                        } else {
                            newSaveList[x] = res;
                        }
                    }
                    SaveRegistration.writeSaveRegistry(newSaveList);
                } else {
                    SaveRegistration.writeSaveRegistry(new String[] { res });
                }
            }
        }
    }

    private static ArrayList<String> readSaveRegistry() {
        final String basePath = SaveRegistration.getSaveBasePath();
        // Load save registry file
        final ArrayList<String> registeredNames = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(basePath + File.separator
                + "SaveRegistry" + Extension.getRegistryExtensionWithPeriod());
                ResourceStreamReader rsr = new ResourceStreamReader(fis)) {
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
        }
        return registeredNames;
    }

    private static void writeSaveRegistry(final String[] newSaveList) {
        final String basePath = SaveRegistration.getSaveBasePath();
        // Check if registry is writable
        final File regFile = new File(basePath + File.separator + "SaveRegistry"
                + Extension.getRegistryExtensionWithPeriod());
        if (!regFile.exists()) {
            // Not writable, probably because needed folders don't exist
            final File regParent = regFile.getParentFile();
            if (!regParent.exists()) {
                final boolean res = regParent.mkdirs();
                if (!res) {
                    // Creating the needed folders failed, so abort
                    return;
                }
            }
        }
        // Save save registry file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(regFile))) {
            if (newSaveList != null) {
                for (int x = 0; x < newSaveList.length; x++) {
                    if (x != newSaveList.length - 1) {
                        bw.write(newSaveList[x] + "\n");
                    } else {
                        bw.write(newSaveList[x]);
                    }
                }
            }
        } catch (final IOException io) {
            io.printStackTrace();
            // Abort
        }
    }

    private static String getDirPrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(SaveRegistration.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(SaveRegistration.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(SaveRegistration.UNIX_PREFIX);
        }
    }

    private static String getSaveDirectory() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return SaveRegistration.MAC_SAVE_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return SaveRegistration.WIN_SAVE_DIR;
        } else {
            // Other - assume UNIX-like
            return SaveRegistration.UNIX_SAVE_DIR;
        }
    }

    public static String getSaveBasePath() {
        final StringBuilder b = new StringBuilder();
        b.append(SaveRegistration.getDirPrefix());
        b.append(SaveRegistration.getSaveDirectory());
        return b.toString();
    }
}
