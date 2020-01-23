package net.worldwizard.support;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.ResourceStreamReader;

public class SystemRegistration {
    // Fields
    private static boolean ANY_FOUND = false;
    private static String systemObjectType;
    private static String systemObjectPluralType;

    // Methods
    public static String[] getRawSystemObjectList(final String type,
            final String plural) {
        // Configure type
        SystemRegistration.systemObjectType = type;
        SystemRegistration.systemObjectPluralType = plural;
        final ArrayList<String> registeredNames = SystemRegistration
                .readSystemObjectRegistry();
        SystemRegistration.ANY_FOUND = false;
        if (registeredNames != null) {
            if (registeredNames.size() > 0) {
                SystemRegistration.ANY_FOUND = true;
            }
        }
        // Load systemObject list
        String[] systemObjectList = null;
        if (SystemRegistration.ANY_FOUND && registeredNames != null) {
            registeredNames.trimToSize();
            systemObjectList = new String[registeredNames.size()];
            for (int x = 0; x < registeredNames.size(); x++) {
                final String name = registeredNames.get(x);
                systemObjectList[x] = name;
            }
        }
        return systemObjectList;
    }

    public static String[] getSystemObjectNameList(final String type,
            final String plural) {
        // Load systemObject list
        final String[] rawSystemObjectList = SystemRegistration
                .getRawSystemObjectList(type, plural);
        if (rawSystemObjectList != null) {
            final String[] systemObjectList = new String[rawSystemObjectList.length];
            for (int x = 0; x < rawSystemObjectList.length; x++) {
                systemObjectList[x] = rawSystemObjectList[x].split(",")[1]
                        .trim();
            }
            return systemObjectList;
        }
        return null;
    }

    public static String[] getSystemObjectIDList(final String type,
            final String plural) {
        // Load systemObject list
        final String[] rawSystemObjectList = SystemRegistration
                .getRawSystemObjectList(type, plural);
        if (rawSystemObjectList != null) {
            final String[] systemObjectList = new String[rawSystemObjectList.length];
            for (int x = 0; x < rawSystemObjectList.length; x++) {
                systemObjectList[x] = rawSystemObjectList[x].split(",")[0]
                        .trim();
            }
            return systemObjectList;
        }
        return null;
    }

    public static void autoregisterSystemObject(final String id,
            final String name, final String type, final String plural) {
        // Load systemObject list
        final String[] systemObjectIDList = SystemRegistration
                .getSystemObjectIDList(type, plural);
        final String[] systemObjectNameList = SystemRegistration
                .getSystemObjectNameList(type, plural);
        if (id != null && name != null) {
            // Verify that systemObject is not already registered
            boolean alreadyRegistered = false;
            if (systemObjectIDList != null) {
                for (final String element : systemObjectIDList) {
                    if (element.equalsIgnoreCase(id)) {
                        alreadyRegistered = true;
                        break;
                    }
                }
            }
            if (!alreadyRegistered) {
                // Register it
                if (systemObjectIDList != null && systemObjectNameList != null) {
                    final String[] newSystemObjectList = new String[systemObjectIDList.length + 1];
                    for (int x = 0; x < newSystemObjectList.length; x++) {
                        if (x < systemObjectIDList.length) {
                            newSystemObjectList[x] = systemObjectIDList[x]
                                    + " , " + systemObjectNameList[x];
                        } else {
                            newSystemObjectList[x] = id + " , " + name;
                        }
                    }
                    SystemRegistration
                            .writeSystemObjectRegistry(newSystemObjectList);
                } else {
                    SystemRegistration
                            .writeSystemObjectRegistry(new String[] { id
                                    + " , " + name });
                }
            }
        }
    }

    private static ArrayList<String> readSystemObjectRegistry() {
        final String basePath = SystemRegistration.getBasePath();
        // Load systemObject registry file
        final ArrayList<String> registeredNames = new ArrayList<>();
        ResourceStreamReader rsr = null;
        try (final FileInputStream fis = new FileInputStream(basePath
                + SystemRegistration.systemObjectType + "Registry"
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

    private static void writeSystemObjectRegistry(
            final String[] newSystemObjectList) {
        final String basePath = SystemRegistration.getBasePath();
        // Check if registry is writable
        final File regFile = new File(basePath
                + SystemRegistration.systemObjectType + "Registry"
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
        // Save systemObject registry file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(regFile))) {
            if (newSystemObjectList != null) {
                for (int x = 0; x < newSystemObjectList.length; x++) {
                    if (x != newSystemObjectList.length - 1) {
                        bw.write(newSystemObjectList[x] + "\n");
                    } else {
                        bw.write(newSystemObjectList[x]);
                    }
                }
            }
        } catch (final IOException io) {
            // Abort
        }
    }

    public static String getBasePath() {
        final StringBuilder b = new StringBuilder();
        b.append(Support.getSystemVariables().getBasePath());
        b.append(File.separator);
        b.append(SystemRegistration.systemObjectPluralType);
        b.append(File.separator);
        return b.toString();
    }
}
