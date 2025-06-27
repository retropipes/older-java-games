package com.puttysoftware.mazemode.maze;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.puttysoftware.fileutils.FileUtilities;
import com.puttysoftware.fileutils.ResourceStreamReader;

public class MazeRegistration {
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Putty Software/MazeMode/Mazes/";
    private static final String WIN_DIR = "\\Putty Software\\MazeMode\\Mazes\\";
    private static final String UNIX_DIR = "/.mazemode/mazes/";

    public static void autoRegisterAllMazes() {
        MazeRegistration.autoRegisterAllGlobalMazes();
        MazeRegistration.autoRegisterAllUserMazes();
    }

    public static String[] getAllRegisteredMazes() {
        final ArrayList<String> global = MazeRegistration
                .readGlobalMazeRegistry();
        final ArrayList<String> user = MazeRegistration.readUserMazeRegistry();
        final ArrayList<String> combined = new ArrayList<>();
        if (global != null) {
            combined.addAll(global);
        }
        if (user != null) {
            combined.addAll(user);
        }
        return combined.toArray(new String[combined.size()]);
    }

    private static void autoRegisterAllGlobalMazes() {
        final ArrayList<String> registeredNames = MazeRegistration
                .readGlobalMazeRegistry();
        boolean anyFound = false;
        if (registeredNames != null) {
            if (registeredNames.size() > 0) {
                anyFound = true;
            }
        }
        // Load maze list
        String[] mazeList = null;
        if (registeredNames != null && anyFound) {
            registeredNames.trimToSize();
            mazeList = new String[registeredNames.size()];
            for (int x = 0; x < registeredNames.size(); x++) {
                final String name = registeredNames.get(x);
                mazeList[x] = name;
            }
        }
        // Get list of files in Mazes folder
        final String[] mazeNames = new File(
                MazeRegistration.getBasePath() + File.separator + "Mazes")
                .list(new MazeFilter());
        if (mazeNames != null && mazeNames.length > 0) {
            // Strip extension from list entries
            for (int z = 0; z < mazeNames.length; z++) {
                mazeNames[z] = mazeNames[z].substring(0,
                        mazeNames[z].length() - 5);
            }
            // Register them all
            for (final String res : mazeNames) {
                if (res != null) {
                    // Verify that maze is not already registered
                    boolean alreadyRegistered = false;
                    if (mazeList != null) {
                        for (final String element : mazeList) {
                            if (element.equalsIgnoreCase(res)) {
                                alreadyRegistered = true;
                                break;
                            }
                        }
                    }
                    if (!alreadyRegistered) {
                        // Verify that maze file exists
                        if (new File(MazeRegistration.getBasePath()
                                + File.separator + "Mazes" + File.separator
                                + res + Extension.getMazeExtensionWithPeriod())
                                .exists()) {
                            // Register it
                            if (mazeList != null && anyFound) {
                                final String[] newMazeList = new String[mazeList.length
                                        + 1];
                                for (int x = 0; x < newMazeList.length; x++) {
                                    if (x < mazeList.length) {
                                        newMazeList[x] = mazeList[x];
                                    } else {
                                        newMazeList[x] = res;
                                    }
                                }
                                MazeRegistration
                                        .writeGlobalMazeRegistry(newMazeList);
                            } else {
                                MazeRegistration.writeGlobalMazeRegistry(
                                        new String[] { res });
                            }
                        }
                    }
                }
            }
        }
    }

    private static void autoRegisterAllUserMazes() {
        final ArrayList<String> registeredNames = MazeRegistration
                .readUserMazeRegistry();
        boolean anyFound = false;
        if (registeredNames != null) {
            if (registeredNames.size() > 0) {
                anyFound = true;
            }
        }
        // Load maze list
        String[] mazeList = null;
        if (registeredNames != null && anyFound) {
            registeredNames.trimToSize();
            mazeList = new String[registeredNames.size()];
            for (int x = 0; x < registeredNames.size(); x++) {
                final String name = registeredNames.get(x);
                mazeList[x] = name;
            }
        }
        // Get list of files in Mazes folder
        final String[] mazeNames = new File(
                MazeRegistration.getPerUserBasePath()).list(new MazeFilter());
        if (mazeNames != null && mazeNames.length > 0) {
            // Strip extension from list entries
            for (int z = 0; z < mazeNames.length; z++) {
                mazeNames[z] = mazeNames[z].substring(0,
                        mazeNames[z].length() - 5);
            }
            // Register them all
            for (final String res : mazeNames) {
                if (res != null) {
                    // Verify that maze is not already registered
                    boolean alreadyRegistered = false;
                    if (mazeList != null) {
                        for (final String element : mazeList) {
                            if (element.equalsIgnoreCase(res)) {
                                alreadyRegistered = true;
                                break;
                            }
                        }
                    }
                    if (!alreadyRegistered) {
                        // Verify that maze file exists
                        if (new File(MazeRegistration.getPerUserBasePath()
                                + File.separator + res
                                + Extension.getMazeExtensionWithPeriod())
                                .exists()) {
                            // Register it
                            if (mazeList != null && anyFound) {
                                final String[] newMazeList = new String[mazeList.length
                                        + 1];
                                for (int x = 0; x < newMazeList.length; x++) {
                                    if (x < mazeList.length) {
                                        newMazeList[x] = mazeList[x];
                                    } else {
                                        newMazeList[x] = res;
                                    }
                                }
                                MazeRegistration
                                        .writeUserMazeRegistry(newMazeList);
                            } else {
                                MazeRegistration.writeUserMazeRegistry(
                                        new String[] { res });
                            }
                        }
                    }
                }
            }
        }
    }

    public static void autoRegisterUserMaze(final File f) {
        // Test to see if registration folder exists
        final File parent = new File(MazeRegistration.getPerUserBasePath());
        if (!parent.exists()) {
            final boolean val = parent.mkdirs();
            if (!val) {
                return;
            }
        }
        final String res = f.getName().substring(0, f.getName().length() - 5);
        // Copy file to user mazes folder
        try {
            FileUtilities.copyFile(f,
                    new File(MazeRegistration.getPerUserBasePath()
                            + File.separator + f.getName()));
        } catch (final IOException e) {
            return;
        }
        final ArrayList<String> registeredNames = MazeRegistration
                .readUserMazeRegistry();
        boolean anyFound = false;
        if (registeredNames != null) {
            if (registeredNames.size() > 0) {
                anyFound = true;
            }
        }
        // Load maze list
        String[] mazeList = null;
        if (registeredNames != null && anyFound) {
            registeredNames.trimToSize();
            mazeList = new String[registeredNames.size()];
            for (int x = 0; x < registeredNames.size(); x++) {
                final String name = registeredNames.get(x);
                mazeList[x] = name;
            }
        }
        // Get list of files in Mazes folder
        final String[] mazeNames = new File(
                MazeRegistration.getPerUserBasePath()).list(new MazeFilter());
        if (mazeNames != null && mazeNames.length > 0) {
            // Strip extension from list entries
            for (int z = 0; z < mazeNames.length; z++) {
                mazeNames[z] = mazeNames[z].substring(0,
                        mazeNames[z].length() - 5);
            }
            if (res != null) {
                // Verify that maze is not already registered
                boolean alreadyRegistered = false;
                if (mazeList != null) {
                    for (final String element : mazeList) {
                        if (element.equalsIgnoreCase(res)) {
                            alreadyRegistered = true;
                            break;
                        }
                    }
                }
                if (!alreadyRegistered) {
                    // Verify that maze file exists
                    if (new File(MazeRegistration.getPerUserBasePath()
                            + File.separator + res
                            + Extension.getMazeExtensionWithPeriod())
                            .exists()) {
                        // Register it
                        if (mazeList != null && anyFound) {
                            final String[] newMazeList = new String[mazeList.length
                                    + 1];
                            for (int x = 0; x < newMazeList.length; x++) {
                                if (x < mazeList.length) {
                                    newMazeList[x] = mazeList[x];
                                } else {
                                    newMazeList[x] = res;
                                }
                            }
                            MazeRegistration.writeUserMazeRegistry(newMazeList);
                        } else {
                            MazeRegistration.writeUserMazeRegistry(
                                    new String[] { res });
                        }
                    }
                }
            }
        }
    }

    static ArrayList<String> readGlobalMazeRegistry() {
        final String basePath = MazeRegistration.getPerUserBasePath();
        // Load maze registry file
        final ArrayList<String> registeredNames = new ArrayList<>();
        try (final FileInputStream fis = new FileInputStream(
                basePath + File.separator + "MazeRegistry"
                        + Extension.getRegistryExtensionWithPeriod())) {
            final ResourceStreamReader rsr = new ResourceStreamReader(fis);
            String input = "";
            while (input != null) {
                input = rsr.readString();
                if (input != null) {
                    registeredNames.add(input);
                }
            }
            rsr.close();
        } catch (final IOException io) {
            // Abort
            return null;
        }
        return registeredNames;
    }

    static ArrayList<String> readUserMazeRegistry() {
        final String basePath = MazeRegistration.getPerUserBasePath();
        // Load maze registry file
        final ArrayList<String> registeredNames = new ArrayList<>();
        try (final FileInputStream fis = new FileInputStream(
                basePath + File.separator + "UserMazeRegistry"
                        + Extension.getRegistryExtensionWithPeriod())) {
            final ResourceStreamReader rsr = new ResourceStreamReader(fis);
            String input = "";
            while (input != null) {
                input = rsr.readString();
                if (input != null) {
                    registeredNames.add(input);
                }
            }
            rsr.close();
        } catch (final IOException io) {
            // Abort
            return null;
        }
        return registeredNames;
    }

    private static void writeGlobalMazeRegistry(final String[] newMazeList) {
        final String basePath = MazeRegistration.getPerUserBasePath();
        // Make folders, if needed
        final File mazePerUserFolder = new File(basePath);
        if (!mazePerUserFolder.exists()) {
            final boolean res = mazePerUserFolder.mkdirs();
            if (!res) {
                return;
            }
        }
        // Save maze registry file
        try (final BufferedWriter bw = new BufferedWriter(
                new FileWriter(basePath + File.separator + "MazeRegistry"
                        + Extension.getRegistryExtensionWithPeriod()))) {
            if (newMazeList != null) {
                for (int x = 0; x < newMazeList.length; x++) {
                    if (newMazeList[x] != null) {
                        if (x != newMazeList.length - 1) {
                            bw.write(newMazeList[x] + "\n");
                        } else {
                            bw.write(newMazeList[x]);
                        }
                    }
                }
            }
        } catch (final IOException io) {
            // Abort
        }
    }

    private static void writeUserMazeRegistry(final String[] newMazeList) {
        final String basePath = MazeRegistration.getPerUserBasePath();
        // Make folders, if needed
        final File mazePerUserFolder = new File(basePath);
        if (!mazePerUserFolder.exists()) {
            final boolean res = mazePerUserFolder.mkdirs();
            if (!res) {
                return;
            }
        }
        // Save maze registry file
        try (final BufferedWriter bw = new BufferedWriter(
                new FileWriter(basePath + File.separator + "UserMazeRegistry"
                        + Extension.getRegistryExtensionWithPeriod()))) {
            if (newMazeList != null) {
                for (int x = 0; x < newMazeList.length; x++) {
                    if (newMazeList[x] != null) {
                        if (x != newMazeList.length - 1) {
                            bw.write(newMazeList[x] + "\n");
                        } else {
                            bw.write(newMazeList[x]);
                        }
                    }
                }
            }
        } catch (final IOException io) {
            // Abort
        }
    }

    public static String getBasePath() {
        // Detect if we're in a Mac OS X-style application package
        String basePath;
        if (new File("../../MacOS/JavaApplicationStub").canExecute()) {
            basePath = "../../../..";
        } else {
            basePath = ".";
        }
        return basePath;
    }

    static String getPerUserBasePath() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(MazeRegistration.MAC_PREFIX)
                    + MazeRegistration.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(MazeRegistration.WIN_PREFIX)
                    + MazeRegistration.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return System.getenv(MazeRegistration.UNIX_PREFIX)
                    + MazeRegistration.UNIX_DIR;
        }
    }
}
