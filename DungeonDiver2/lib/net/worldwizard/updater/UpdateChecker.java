/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.updater;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JOptionPane;

import net.worldwizard.commondialogs.CommonDialogs;

public class UpdateChecker {
    /**
     * @author wrldwzrd89
     * @version 3.20
     */
    // Fields and Constants
    private final int syntaxVersion;
    private URL updateURL;
    private URL blurbURL;
    private URL newVersionURL;
    private final String rDNSCompanyName;
    private final String companyName;
    private final String productName;
    private final int majorVersion;
    private final int minorVersion;
    private final int bugfixVersion;
    private final int betaVersion;
    private boolean cachedDB;
    private final String cacheFile;
    private final String cacheTTLFile;
    private final long cacheTTL;
    private final boolean betaMode;
    private static final String MAC_CACHE_PREFIX = "HOME";
    private static final String WIN_CACHE_PREFIX = "APPDATA";
    private static final String UNIX_CACHE_PREFIX = "HOME";
    private static final String MAC_CACHE_DIR = "/Library/Caches/";
    private static final String WIN_CACHE_DIR = "\\Caches\\";
    private static final String UNIX_CACHE_DIR = "/caches/";
    public static final long CACHE_TTL_1_DAY = 86400000L;

    // Constructors
    public UpdateChecker(final String updateSite, final String blurbSite,
            final String newVersionSite, final String companyMac,
            final String company, final String product, final int major,
            final int minor, final int bugfix) {
        try {
            this.updateURL = new URL(updateSite);
            this.newVersionURL = new URL(newVersionSite);
            this.blurbURL = new URL(blurbSite);
        } catch (final MalformedURLException mu) {
            // Ignore exception
        }
        this.majorVersion = major;
        this.minorVersion = minor;
        this.bugfixVersion = bugfix;
        this.betaVersion = 0;
        this.rDNSCompanyName = companyMac;
        this.companyName = company;
        this.productName = product;
        this.syntaxVersion = 2;
        this.cachedDB = false;
        this.cacheFile = this.productName + "_updatecache";
        this.cacheTTLFile = this.productName + "_updatettl";
        this.cacheTTL = UpdateChecker.CACHE_TTL_1_DAY;
        this.betaMode = false;
    }

    public UpdateChecker(final String updateSite, final String blurbSite,
            final String newVersionSite, final String companyMac,
            final String company, final String product, final int major,
            final int minor, final int bugfix, final int betabuild) {
        try {
            this.updateURL = new URL(updateSite);
            this.newVersionURL = new URL(newVersionSite);
            this.blurbURL = new URL(blurbSite);
        } catch (final MalformedURLException mu) {
            // Ignore exception
        }
        this.majorVersion = major;
        this.minorVersion = minor;
        this.bugfixVersion = bugfix;
        this.betaVersion = betabuild;
        this.rDNSCompanyName = companyMac;
        this.companyName = company;
        this.productName = product;
        this.syntaxVersion = 3;
        this.cachedDB = false;
        this.betaMode = true;
        this.cacheFile = this.productName + "_betaupdatecache";
        this.cacheTTLFile = this.productName + "_betaupdatettl";
        this.cacheTTL = UpdateChecker.CACHE_TTL_1_DAY;
    }

    // Methods
    public void checkForUpdates() {
        if (this.syntaxVersion == 2) {
            this.checkForUpdatesInternalV2(false);
        } else if (this.syntaxVersion == 3) {
            this.checkForUpdatesInternalV3(false);
        }
    }

    public void checkForUpdatesAtStartup() {
        if (this.syntaxVersion == 2) {
            this.checkForUpdatesInternalV2(true);
        } else if (this.syntaxVersion == 3) {
            this.checkForUpdatesInternalV3(true);
        }
    }

    private void checkForUpdatesInternalV2(final boolean startup) {
        if (this.updateURL == null || this.newVersionURL == null) {
            CommonDialogs.showErrorDialog(
                    "An internal error occurred while checking for updates.",
                    "Update Error");
        } else {
            int newVersionMajor = this.majorVersion;
            int newVersionMinor = this.minorVersion;
            int newVersionBugfix = this.bugfixVersion;
            try {
                // Check the cache for validity
                this.isCacheGood();
                // If the update DB isn't cached...
                if (!this.cachedDB) {
                    // Generate the cache
                    this.generateCache();
                }
                // Read entries from the cached update DB
                try (final BufferedReader in = new BufferedReader(
                        new FileReader(this.getCacheFile()))) {
                    String inputLine;
                    try {
                        inputLine = in.readLine();
                        newVersionMajor = Integer.parseInt(inputLine);
                        inputLine = in.readLine();
                        newVersionMinor = Integer.parseInt(inputLine);
                        inputLine = in.readLine();
                        newVersionBugfix = Integer.parseInt(inputLine);
                    } catch (final NumberFormatException nf) {
                        CommonDialogs
                                .showErrorDialog(
                                        "An internal error occurred while checking for updates.",
                                        "Update Error");
                    }
                }
                String blurb = "";
                // Compare current version to most recent one
                if (newVersionMajor > this.majorVersion) {
                    // Major update available
                    blurb = this.readBlurb();
                    this.showUpdatesAvailableMessage(newVersionMajor,
                            newVersionMinor, newVersionBugfix, blurb);
                } else if (newVersionMajor == this.majorVersion
                        && newVersionMinor > this.minorVersion) {
                    // Minor update available
                    blurb = this.readBlurb();
                    this.showUpdatesAvailableMessage(newVersionMajor,
                            newVersionMinor, newVersionBugfix, blurb);
                } else if (newVersionMajor == this.majorVersion
                        && newVersionMinor == this.minorVersion
                        && newVersionBugfix > this.bugfixVersion) {
                    // Bug fix update available
                    blurb = this.readBlurb();
                    this.showUpdatesAvailableMessage(newVersionMajor,
                            newVersionMinor, newVersionBugfix, blurb);
                } else if (newVersionMajor < this.majorVersion) {
                    // Major version newer
                    if (!startup) {
                        this.showNewerVersionMessage(newVersionMajor,
                                newVersionMinor, newVersionBugfix);
                    }
                } else if (newVersionMajor == this.majorVersion
                        && newVersionMinor < this.minorVersion) {
                    // Minor version newer
                    if (!startup) {
                        this.showNewerVersionMessage(newVersionMajor,
                                newVersionMinor, newVersionBugfix);
                    }
                } else if (newVersionMajor == this.majorVersion
                        && newVersionMinor == this.minorVersion
                        && newVersionBugfix < this.bugfixVersion) {
                    // Bug fix version newer
                    if (!startup) {
                        this.showNewerVersionMessage(newVersionMajor,
                                newVersionMinor, newVersionBugfix);
                    }
                } else {
                    // No update
                    if (!startup) {
                        this.showNoUpdatesAvailableMessage();
                    }
                }
            } catch (final IOException ie) {
                CommonDialogs
                        .showErrorDialog(
                                "Unable to contact the update site.\n"
                                        + "Make sure you are connected to the Internet,\n"
                                        + "then try again.", "Update Error");
            }
        }
    }

    private void checkForUpdatesInternalV3(final boolean startup) {
        if (this.updateURL == null || this.newVersionURL == null) {
            CommonDialogs.showErrorDialog(
                    "An internal error occurred while checking for updates.",
                    "Update Error");
        } else {
            int newVersionMajor = this.majorVersion;
            int newVersionMinor = this.minorVersion;
            int newVersionBugfix = this.bugfixVersion;
            int newVersionBeta = this.betaVersion;
            try {
                // Check the cache for validity
                this.isCacheGood();
                // If the update DB isn't cached...
                if (!this.cachedDB) {
                    // Generate the cache
                    this.generateCache();
                }
                // Read entries from the cached update DB
                try (final BufferedReader in = new BufferedReader(
                        new FileReader(this.getCacheFile()))) {
                    String inputLine;
                    try {
                        inputLine = in.readLine();
                        newVersionMajor = Integer.parseInt(inputLine);
                        inputLine = in.readLine();
                        newVersionMinor = Integer.parseInt(inputLine);
                        inputLine = in.readLine();
                        newVersionBugfix = Integer.parseInt(inputLine);
                        inputLine = in.readLine();
                        newVersionBeta = Integer.parseInt(inputLine);
                    } catch (final NumberFormatException nf) {
                        CommonDialogs
                                .showErrorDialog(
                                        "An internal error occurred while checking for updates.",
                                        "Update Error");
                    }
                }
                String blurb = "";
                // Compare current version to most recent one
                if (newVersionMajor > this.majorVersion) {
                    // Major update available
                    blurb = this.readBlurb();
                    this.showUpdatesAvailableMessage(newVersionMajor,
                            newVersionMinor, newVersionBugfix, blurb);
                } else if (newVersionMajor == this.majorVersion
                        && newVersionMinor > this.minorVersion) {
                    // Minor update available
                    blurb = this.readBlurb();
                    this.showUpdatesAvailableMessage(newVersionMajor,
                            newVersionMinor, newVersionBugfix, blurb);
                } else if (newVersionMajor == this.majorVersion
                        && newVersionMinor == this.minorVersion
                        && newVersionBugfix > this.bugfixVersion) {
                    // Bug fix update available
                    blurb = this.readBlurb();
                    this.showUpdatesAvailableMessage(newVersionMajor,
                            newVersionMinor, newVersionBugfix, blurb);
                } else if (newVersionMajor == this.majorVersion
                        && newVersionMinor == this.minorVersion
                        && newVersionBugfix == this.bugfixVersion
                        && newVersionBeta > this.betaVersion) {
                    // Beta update available
                    blurb = this.readBlurb();
                    this.showUpdatesAvailableMessage(newVersionMajor,
                            newVersionMinor, newVersionBugfix, newVersionBeta,
                            blurb);
                } else if (newVersionMajor < this.majorVersion) {
                    // Major version newer
                    if (!startup) {
                        this.showNewerVersionMessage(newVersionMajor,
                                newVersionMinor, newVersionBugfix);
                    }
                } else if (newVersionMajor == this.majorVersion
                        && newVersionMinor < this.minorVersion) {
                    // Minor version newer
                    if (!startup) {
                        this.showNewerVersionMessage(newVersionMajor,
                                newVersionMinor, newVersionBugfix);
                    }
                } else if (newVersionMajor == this.majorVersion
                        && newVersionMinor == this.minorVersion
                        && newVersionBugfix < this.bugfixVersion) {
                    // Bug fix version newer
                    if (!startup) {
                        this.showNewerVersionMessage(newVersionMajor,
                                newVersionMinor, newVersionBugfix);
                    }
                } else if (newVersionMajor == this.majorVersion
                        && newVersionMinor == this.minorVersion
                        && newVersionBugfix == this.bugfixVersion
                        && newVersionBeta < this.betaVersion) {
                    // Beta version newer
                    if (!startup) {
                        this.showNewerVersionMessage(newVersionMajor,
                                newVersionMinor, newVersionBugfix,
                                newVersionBeta);
                    }
                } else {
                    // No update
                    if (!startup) {
                        this.showNoUpdatesAvailableMessage();
                    }
                }
            } catch (final IOException ie) {
                CommonDialogs
                        .showErrorDialog(
                                "Unable to contact the update site.\n"
                                        + "Make sure you are connected to the Internet,\n"
                                        + "then try again.", "Update Error");
            }
        }
    }

    private void showNewerVersionMessage(final int major, final int minor,
            final int bugfix) {
        final String oldVersionString = Integer.toString(this.majorVersion)
                + "." + Integer.toString(this.minorVersion) + "."
                + Integer.toString(this.bugfixVersion);
        final String newVersionString = Integer.toString(major) + "."
                + Integer.toString(minor) + "." + Integer.toString(bugfix);
        CommonDialogs
                .showTitledDialog(
                        "Version "
                                + newVersionString
                                + " is the latest, according to the update site, but you have version "
                                + oldVersionString + ", which is newer.",
                        "Newer Version");
    }

    private void showUpdatesAvailableMessage(final int major, final int minor,
            final int bugfix, final String blurb) {
        final String oldVersionString = Integer.toString(this.majorVersion)
                + "." + Integer.toString(this.minorVersion) + "."
                + Integer.toString(this.bugfixVersion);
        final String newVersionString = Integer.toString(major) + "."
                + Integer.toString(minor) + "." + Integer.toString(bugfix);
        final String newVersionLink = this.newVersionURL.toExternalForm();
        final int result = CommonDialogs.showConfirmDialog("Version "
                + newVersionString + " is available.\nYou have version "
                + oldVersionString + ".\n" + blurb
                + "Do you want to go to the program web site now?",
                "Update Available");
        if (result == JOptionPane.YES_OPTION) {
            // Launch Browser
            BrowserLauncher.openURL(newVersionLink);
        }
    }

    private void showNewerVersionMessage(final int major, final int minor,
            final int bugfix, final int beta) {
        final String oldVersionString = Integer.toString(this.majorVersion)
                + "." + Integer.toString(this.minorVersion) + "."
                + Integer.toString(this.bugfixVersion) + "-dev"
                + Integer.toString(this.betaVersion);
        final String newVersionString = Integer.toString(major) + "."
                + Integer.toString(minor) + "." + Integer.toString(bugfix)
                + "-dev" + Integer.toString(beta);
        CommonDialogs
                .showTitledDialog(
                        "Version "
                                + newVersionString
                                + " is the latest, according to the update site, but you have version "
                                + oldVersionString + ", which is newer.",
                        "Newer Beta Version");
    }

    private void showUpdatesAvailableMessage(final int major, final int minor,
            final int bugfix, final int beta, final String blurb) {
        final String oldVersionString = Integer.toString(this.majorVersion)
                + "." + Integer.toString(this.minorVersion) + "."
                + Integer.toString(this.bugfixVersion) + "-dev"
                + Integer.toString(this.betaVersion);
        final String newVersionString = Integer.toString(major) + "."
                + Integer.toString(minor) + "." + Integer.toString(bugfix)
                + "-dev" + Integer.toString(beta);
        final String newVersionLink = this.newVersionURL.toExternalForm();
        final int result = CommonDialogs.showConfirmDialog("Version "
                + newVersionString + " is available.\nYou have version "
                + oldVersionString + ".\n" + blurb
                + "Do you want to go to the program web site now?",
                "Beta Update Available");
        if (result == JOptionPane.YES_OPTION) {
            // Launch Browser
            BrowserLauncher.openURL(newVersionLink);
        }
    }

    private void showNoUpdatesAvailableMessage() {
        if (this.betaMode) {
            CommonDialogs.showTitledDialog("You have the latest beta version.",
                    "No Beta Update Available");
        } else {
            CommonDialogs.showTitledDialog("You have the latest version.",
                    "No Update Available");
        }
    }

    private String readBlurb() {
        // Attempt to read blurb
        final StringBuilder blurbBuilder = new StringBuilder();
        String blurb = null;
        String line = "";
        try (final BufferedReader blurbReader = new BufferedReader(
                new InputStreamReader(this.blurbURL.openStream()))) {
            line = blurbReader.readLine();
            while (line != null) {
                blurbBuilder.append(line);
                blurbBuilder.append("\n");
                line = blurbReader.readLine();
            }
            blurb = blurbBuilder.toString();
            blurbReader.close();
        } catch (final IOException ie) {
            blurb = "";
        }
        return blurb;
    }

    private static String getCachePrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(UpdateChecker.MAC_CACHE_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(UpdateChecker.WIN_CACHE_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(UpdateChecker.UNIX_CACHE_PREFIX);
        }
    }

    private String getCacheDirectory() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return UpdateChecker.MAC_CACHE_DIR + "/" + this.rDNSCompanyName
                    + "/";
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return "\\" + this.companyName + "\\" + this.productName
                    + UpdateChecker.WIN_CACHE_DIR;
        } else {
            // Other - assume UNIX-like
            return "/." + this.productName.toLowerCase()
                    + UpdateChecker.UNIX_CACHE_DIR;
        }
    }

    private File getCacheFile() {
        final StringBuilder b = new StringBuilder();
        b.append(UpdateChecker.getCachePrefix());
        b.append(this.getCacheDirectory());
        b.append(this.cacheFile);
        return new File(b.toString());
    }

    private File getCacheTTLFile() {
        final StringBuilder b = new StringBuilder();
        b.append(UpdateChecker.getCachePrefix());
        b.append(this.getCacheDirectory());
        b.append(this.cacheTTLFile);
        return new File(b.toString());
    }

    private void generateCache() throws IOException {
        // Cache the update DB to a file
        final File cache = this.getCacheFile();
        final String ttl = Long.toString(System.currentTimeMillis()
                + this.cacheTTL);
        final File ttlFile = this.getCacheTTLFile();
        try (final BufferedReader in = new BufferedReader(
                new InputStreamReader(this.updateURL.openStream()));
                final BufferedWriter out = new BufferedWriter(new FileWriter(
                        cache));
                final BufferedWriter outTTL = new BufferedWriter(
                        new FileWriter(ttlFile))) {
            // Create the needed subdirectories, if they don't already exist
            final File cacheParent = new File(this.getCacheFile().getParent());
            if (!cacheParent.exists()) {
                final boolean success = cacheParent.mkdirs();
                if (!success) {
                    throw new IOException("Cache creation failed!");
                }
            }
            String inputLine = "";
            while (inputLine != null) {
                inputLine = in.readLine();
                if (inputLine != null) {
                    out.write(inputLine + "\n", 0, inputLine.length() + 1);
                }
            }
            outTTL.write(ttl, 0, ttl.length());
            this.cachedDB = true;
        }
    }

    private void isCacheGood() {
        final long curr = System.currentTimeMillis();
        final File ttlFile = this.getCacheTTLFile();
        try (final BufferedReader inTTL = new BufferedReader(new FileReader(
                ttlFile))) {
            final String test = inTTL.readLine();
            final long stored = Long.parseLong(test);
            this.cachedDB = curr <= stored;
        } catch (final IOException io) {
            this.cachedDB = false;
        } catch (final NumberFormatException nfe) {
            this.cachedDB = false;
        }
    }
}
