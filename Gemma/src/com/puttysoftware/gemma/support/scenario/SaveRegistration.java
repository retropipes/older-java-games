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
        ArrayList<String> registeredNames = SaveRegistration.readSaveRegistry();
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
                String name = registeredNames.get(x);
                saveList[x] = name;
            }
        }
        return saveList;
    }

    public static void autoregisterSave(String res) {
        // Load save list
        String[] saveList = SaveRegistration.getSaveList();
        if (res != null) {
            // Verify that save is not already registered
            boolean alreadyRegistered = false;
            if (saveList != null) {
                for (int x = 0; x < saveList.length; x++) {
                    if (saveList[x].equalsIgnoreCase(res)) {
                        alreadyRegistered = true;
                        break;
                    }
                }
            }
            if (!alreadyRegistered) {
                // Register it
                if (saveList != null) {
                    String[] newSaveList = new String[saveList.length + 1];
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
        String basePath = SaveRegistration.getSaveBasePath();
        // Load save registry file
        ArrayList<String> registeredNames = new ArrayList<>();
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
        } catch (IOException io) {
            // Abort
            return null;
        }
        return registeredNames;
    }

    private static void writeSaveRegistry(String[] newSaveList) {
        String basePath = SaveRegistration.getSaveBasePath();
        // Check if registry is writable
        File regFile = new File(basePath + File.separator + "SaveRegistry"
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
        } catch (IOException io) {
            io.printStackTrace();
            // Abort
        }
    }

    private static String getDirPrefix() {
        String osName = System.getProperty("os.name");
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
        String osName = System.getProperty("os.name");
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
        StringBuilder b = new StringBuilder();
        b.append(SaveRegistration.getDirPrefix());
        b.append(SaveRegistration.getSaveDirectory());
        return b.toString();
    }
}
