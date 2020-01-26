package net.worldwizard.support.spells;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.worldwizard.support.Support;
import net.worldwizard.support.SystemRegistration;
import net.worldwizard.support.variables.Extension;
import net.worldwizard.xio.ResourceStreamReader;

public class SpellBookRegistration {
    // Fields
    private static boolean ANY_FOUND = false;

    // Methods
    private static String[] getRawSpellBookList() {
        final ArrayList<String> registeredNames = SpellBookRegistration
                .readSpellBookRegistry();
        SpellBookRegistration.ANY_FOUND = false;
        if (registeredNames != null) {
            if (registeredNames.size() > 0) {
                SpellBookRegistration.ANY_FOUND = true;
            }
        }
        // Load spell book list
        String[] spellBookList = null;
        if (SpellBookRegistration.ANY_FOUND && registeredNames != null) {
            registeredNames.trimToSize();
            spellBookList = new String[registeredNames.size()];
            for (int x = 0; x < registeredNames.size(); x++) {
                final String name = registeredNames.get(x);
                spellBookList[x] = name;
            }
        }
        return spellBookList;
    }

    public static String[] getSpellBookNameList() {
        // Load spellBook list
        final String[] rawSpellBookList = SpellBookRegistration
                .getRawSpellBookList();
        final String[] systemSpellBookList = SystemRegistration
                .getRawSystemObjectList("SpellBook", "spellBooks");
        if (rawSpellBookList != null || systemSpellBookList != null) {
            int len;
            if (systemSpellBookList == null && rawSpellBookList != null) {
                len = rawSpellBookList.length;
            } else if (systemSpellBookList != null
                    && rawSpellBookList == null) {
                len = systemSpellBookList.length;
            } else if (systemSpellBookList != null
                    && rawSpellBookList != null) {
                len = systemSpellBookList.length + rawSpellBookList.length;
            } else {
                // Shouldn't ever get here
                return null;
            }
            final String[] mergedRawList = new String[len];
            for (int x = 0; x < len; x++) {
                if (systemSpellBookList != null) {
                    if (x < systemSpellBookList.length) {
                        mergedRawList[x] = systemSpellBookList[x];
                    } else {
                        if (rawSpellBookList != null) {
                            mergedRawList[x] = rawSpellBookList[x
                                    - systemSpellBookList.length];
                        } else {
                            // Shouldn't ever get here
                            return null;
                        }
                    }
                } else {
                    if (rawSpellBookList != null) {
                        mergedRawList[x] = rawSpellBookList[x];
                    } else {
                        // Shouldn't ever get here
                        return null;
                    }
                }
            }
            final String[] spellBookList = new String[len];
            for (int x = 0; x < mergedRawList.length; x++) {
                spellBookList[x] = mergedRawList[x].split(",")[1].trim();
            }
            return spellBookList;
        } else {
            return null;
        }
    }

    public static String[] getSpellBookIDList() {
        // Load spellBook list
        final String[] rawSpellBookList = SpellBookRegistration
                .getRawSpellBookList();
        final String[] systemSpellBookList = SystemRegistration
                .getRawSystemObjectList("SpellBook", "spellBooks");
        if (rawSpellBookList != null || systemSpellBookList != null) {
            int len;
            if (systemSpellBookList == null && rawSpellBookList != null) {
                len = rawSpellBookList.length;
            } else if (systemSpellBookList != null
                    && rawSpellBookList == null) {
                len = systemSpellBookList.length;
            } else if (systemSpellBookList != null
                    && rawSpellBookList != null) {
                len = systemSpellBookList.length + rawSpellBookList.length;
            } else {
                // Shouldn't ever get here
                return null;
            }
            final String[] mergedRawList = new String[len];
            for (int x = 0; x < len; x++) {
                if (systemSpellBookList != null) {
                    if (x < systemSpellBookList.length) {
                        mergedRawList[x] = systemSpellBookList[x];
                    } else {
                        if (rawSpellBookList != null) {
                            mergedRawList[x] = rawSpellBookList[x
                                    - systemSpellBookList.length];
                        } else {
                            // Shouldn't ever get here
                            return null;
                        }
                    }
                } else {
                    if (rawSpellBookList != null) {
                        mergedRawList[x] = rawSpellBookList[x];
                    } else {
                        // Shouldn't ever get here
                        return null;
                    }
                }
            }
            final String[] spellBookList = new String[len];
            for (int x = 0; x < mergedRawList.length; x++) {
                spellBookList[x] = mergedRawList[x].split(",")[0].trim();
            }
            return spellBookList;
        } else {
            return null;
        }
    }

    public static String getIDForName(final String name) {
        // Load spell book list
        final String[] spellBookIDList = SpellBookRegistration
                .getSpellBookIDList();
        final String[] spellBookNameList = SpellBookRegistration
                .getSpellBookNameList();
        if (spellBookIDList != null && spellBookNameList != null) {
            for (int x = 0; x < spellBookNameList.length; x++) {
                if (spellBookNameList[x].equals(name)) {
                    return spellBookIDList[x];
                }
            }
        }
        return null;
    }

    private static ArrayList<String> readSpellBookRegistry() {
        // Load spellBook registry file
        final ArrayList<String> registeredNames = new ArrayList<>();
        ResourceStreamReader rsr = null;
        final String basePath = SpellBookRegistration.getBasePath();
        try (final FileInputStream fis = new FileInputStream(
                basePath + File.separator + "SpellBookRegistry"
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
        } catch (final NullPointerException npe) {
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

    public static String getBasePath() {
        final StringBuilder b = new StringBuilder();
        b.append(Support.getVariables().getBasePath());
        b.append(File.separator);
        b.append("spellbooks");
        b.append(File.separator);
        return b.toString();
    }
}
