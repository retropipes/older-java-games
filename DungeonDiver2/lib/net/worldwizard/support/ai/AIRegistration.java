package net.worldwizard.support.ai;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.worldwizard.commondialogs.CommonDialogs;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.DirectoryUtilities;
import net.worldwizard.xio.ResourceStreamReader;

public class AIRegistration {
    // Fields
    private static boolean ANY_FOUND = false;
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Putty Software/DungeonDiverII/AI";
    private static final String WIN_DIR = "\\Putty Software\\DungeonDiverII\\AI";
    private static final String UNIX_DIR = "/.DungeonDiverII/ai";

    // Methods
    public static void activateRegisteredAIs() {
        final AIRoutine[] routines = AILoader.loadAllRegisteredAIs();
        if (routines != null) {
            for (final AIRoutine routine : routines) {
                RandomAIRoutinePicker.addRoutine(routine);
            }
        }
    }

    public static String[] getAIList() {
        final ArrayList<String> registeredNames = AIRegistration
                .readAIRegistry();
        AIRegistration.ANY_FOUND = false;
        if (registeredNames != null) {
            if (registeredNames.size() > 0) {
                AIRegistration.ANY_FOUND = true;
            }
        }
        // Load AI list
        String[] aiList = null;
        if (AIRegistration.ANY_FOUND && registeredNames != null) {
            registeredNames.trimToSize();
            aiList = new String[registeredNames.size()];
            for (int x = 0; x < registeredNames.size(); x++) {
                final String name = registeredNames.get(x);
                aiList[x] = name;
            }
        }
        return aiList;
    }

    public static void autoregisterAI(final String res) {
        // Load AI list
        final String[] aiList = AIRegistration.getAIList();
        if (res != null) {
            // Verify that AI is not already registered
            boolean alreadyRegistered = false;
            if (aiList != null) {
                for (final String element : aiList) {
                    if (element.equalsIgnoreCase(res)) {
                        alreadyRegistered = true;
                        break;
                    }
                }
            }
            if (!alreadyRegistered) {
                // Verify that AI file exists
                if (new File(AIRegistration.getBasePath() + File.separator + res
                        + Extension.getAIScriptExtensionWithPeriod())
                                .exists()) {
                    // Register it
                    if (AIRegistration.ANY_FOUND && aiList != null) {
                        final String[] newAIList = new String[aiList.length
                                + 1];
                        for (int x = 0; x < newAIList.length; x++) {
                            if (x < aiList.length) {
                                newAIList[x] = aiList[x];
                            } else {
                                newAIList[x] = res;
                            }
                        }
                        AIRegistration.writeAIRegistry(newAIList);
                    } else {
                        AIRegistration.writeAIRegistry(new String[] { res });
                    }
                    // Activate it
                    final AIRoutine routine = AILoader.loadAI(res);
                    RandomAIRoutinePicker.addRoutine(routine);
                }
            }
        }
    }

    public static void registerAI() {
        // Load AI list
        final String[] aiList = AIRegistration.getAIList();
        final String[] aiNames = new File(AIRegistration.getBasePath())
                .list(new AIRegistrationFilter());
        if (aiNames != null && aiNames.length > 0) {
            // Strip extension from list entries
            for (int z = 0; z < aiNames.length; z++) {
                aiNames[z] = aiNames[z].substring(0, aiNames[z].length() - 4);
            }
            // Pick AI to register
            final String res = CommonDialogs.showInputDialog(
                    "Register Which AI?", "Register AI", aiNames, aiNames[0]);
            if (res != null) {
                // Verify that AI is not already registered
                boolean alreadyRegistered = false;
                if (aiList != null) {
                    for (final String element : aiList) {
                        if (element.equalsIgnoreCase(res)) {
                            alreadyRegistered = true;
                            break;
                        }
                    }
                }
                if (!alreadyRegistered) {
                    // Verify that AI file exists
                    if (new File(AIRegistration.getBasePath() + File.separator
                            + res + Extension.getAIScriptExtensionWithPeriod())
                                    .exists()) {
                        // Register it
                        if (AIRegistration.ANY_FOUND && aiList != null) {
                            final String[] newAIList = new String[aiList.length
                                    + 1];
                            for (int x = 0; x < newAIList.length; x++) {
                                if (x < aiList.length) {
                                    newAIList[x] = aiList[x];
                                } else {
                                    newAIList[x] = res;
                                }
                            }
                            AIRegistration.writeAIRegistry(newAIList);
                        } else {
                            AIRegistration
                                    .writeAIRegistry(new String[] { res });
                        }
                        // Activate it
                        final AIRoutine routine = AILoader.loadAI(res);
                        RandomAIRoutinePicker.addRoutine(routine);
                    } else {
                        CommonDialogs.showDialog(
                                "The AI to register is not a valid AI.");
                    }
                } else {
                    CommonDialogs.showDialog(
                            "The AI to register has been registered already.");
                }
            }
        } else {
            CommonDialogs.showDialog("No AIs found to register!");
        }
    }

    public static void importAI() {
        final FileDialog fc = new FileDialog((Frame) null, "Load AI Script",
                FileDialog.LOAD);
        fc.setVisible(true);
        if (fc.getFile() != null && fc.getDirectory() != null) {
            final File source = new File(fc.getDirectory() + fc.getFile());
            final String basePath = AIRegistration.getBasePath();
            final File dest = new File(basePath + File.separator
                    + AIRegistration.getFileNameOnly(source));
            try {
                DirectoryUtilities.copyFile(source, dest);
                AIRegistration
                        .autoregisterAI(AIRegistration.getNameWithoutExtension(
                                AIRegistration.getFileNameOnly(dest)));
                CommonDialogs.showTitledDialog("AI Imported Successfully.",
                        "Import AI");
            } catch (final IOException io) {
                CommonDialogs.showErrorDialog("AI Import Failed!", "Import AI");
            }
        }
    }

    private static String getFileNameOnly(final File f) {
        String fno = null;
        final String s = f.getAbsolutePath();
        final int i = s.lastIndexOf(File.separatorChar);
        if (i > 0 && i < s.length() - 1) {
            fno = s.substring(i + 1);
        } else {
            fno = s;
        }
        return fno;
    }

    private static String getNameWithoutExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }

    public static void unregisterAI() {
        // Load AI list
        final String[] aiList = AIRegistration.getAIList();
        // Check for null list
        if (aiList == null) {
            CommonDialogs.showTitledDialog("No AIs Registered!",
                    "Unregister AI");
            return;
        }
        // Pick AI to unregister
        final String res = CommonDialogs.showInputDialog("Unregister Which AI?",
                "Unregister AI", aiList, aiList[0]);
        if (res != null) {
            // Find AI index
            int index = -1;
            for (int x = 0; x < aiList.length; x++) {
                if (aiList[x].equals(res)) {
                    index = x;
                    break;
                }
            }
            if (index != -1) {
                // Unregister it
                if (aiList.length > 1) {
                    aiList[index] = null;
                    final String[] newAIList = new String[aiList.length - 1];
                    int offset = 0;
                    for (int x = 0; x < aiList.length; x++) {
                        if (aiList[x] != null) {
                            newAIList[x - offset] = aiList[x];
                        } else {
                            offset++;
                        }
                    }
                    AIRegistration.writeAIRegistry(newAIList);
                } else {
                    AIRegistration.writeAIRegistry(null);
                }
                // Deactivate it
                final AIRoutine routine = AILoader.loadAI(res);
                RandomAIRoutinePicker.removeRoutine(routine);
            }
        }
    }

    private static ArrayList<String> readAIRegistry() {
        final String basePath = AIRegistration.getBasePath();
        // Load AI registry file
        final ArrayList<String> registeredNames = new ArrayList<>();
        ResourceStreamReader rsr = null;
        try (final FileInputStream fis = new FileInputStream(
                basePath + File.separator + "AIRegistry"
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

    private static void writeAIRegistry(final String[] newAIList) {
        final String basePath = AIRegistration.getBasePath();
        // Check if registry is writable
        final File regFile = new File(basePath + File.separator + "AIRegistry"
                + Extension.getRegistryExtensionWithPeriod());
        if (!regFile.exists()) {
            // Not writable, probably because needed folders don't exist
            final File regParent = regFile.getParentFile();
            final boolean res = regParent.mkdirs();
            if (!res) {
                // Creating the needed folders failed, so abort
                return;
            }
        }
        // Save AI registry file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(regFile))) {
            if (newAIList != null) {
                for (int x = 0; x < newAIList.length; x++) {
                    if (x != newAIList.length - 1) {
                        bw.write(newAIList[x] + "\n");
                    } else {
                        bw.write(newAIList[x]);
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
            return System.getenv(AIRegistration.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(AIRegistration.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(AIRegistration.UNIX_PREFIX);
        }
    }

    private static String getDirectory() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return AIRegistration.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return AIRegistration.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return AIRegistration.UNIX_DIR;
        }
    }

    static String getBasePath() {
        final StringBuilder b = new StringBuilder();
        b.append(AIRegistration.getDirPrefix());
        b.append(AIRegistration.getDirectory());
        return b.toString();
    }
}
