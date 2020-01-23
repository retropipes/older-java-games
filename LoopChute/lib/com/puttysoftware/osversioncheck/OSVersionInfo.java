package com.puttysoftware.osversioncheck;

public class OSVersionInfo implements Comparable<OSVersionInfo> {
    // Constants
    public static final String MAC_OS_X = "Mac OS X";
    public static final String WINDOWS = "Windows";
    public static final String LINUX = "Linux";
    public static final int OS_VERSION_ANY = Integer.MAX_VALUE;
    // Common Operating Systems
    public static final OSVersionInfo TIGER = new OSVersionInfo(
            OSVersionInfo.MAC_OS_X, 10, 4, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    public static final OSVersionInfo LEOPARD = new OSVersionInfo(
            OSVersionInfo.MAC_OS_X, 10, 5, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    public static final OSVersionInfo SNOW_LEOPARD = new OSVersionInfo(
            OSVersionInfo.MAC_OS_X, 10, 6, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    public static final OSVersionInfo LION = new OSVersionInfo(
            OSVersionInfo.MAC_OS_X, 10, 7, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    public static final OSVersionInfo WIN2K = new OSVersionInfo(
            OSVersionInfo.WINDOWS, 5, 0, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    public static final OSVersionInfo XP = new OSVersionInfo(
            OSVersionInfo.WINDOWS, 5, 1, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    public static final OSVersionInfo VISTA = new OSVersionInfo(
            OSVersionInfo.WINDOWS, 6, 0, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    public static final OSVersionInfo SEVEN = new OSVersionInfo(
            OSVersionInfo.WINDOWS, 6, 1, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    public static final OSVersionInfo LINUX22 = new OSVersionInfo(
            OSVersionInfo.LINUX, 2, 2, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    public static final OSVersionInfo LINUX24 = new OSVersionInfo(
            OSVersionInfo.LINUX, 2, 4, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    public static final OSVersionInfo LINUX26 = new OSVersionInfo(
            OSVersionInfo.LINUX, 2, 6, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    public static final OSVersionInfo LINUX30 = new OSVersionInfo(
            OSVersionInfo.LINUX, 3, 0, OSVersionInfo.OS_VERSION_ANY,
            OSVersionInfo.OS_VERSION_ANY);
    // Fields
    private final String subName;
    private final int versionMajor;
    private final int versionMinor;
    private final int versionBugfix;
    private final int versionBuild;

    // Constructor
    public OSVersionInfo(final String name, final int major, final int minor,
            final int bugfix, final int build) {
        this.subName = name;
        this.versionMajor = major;
        this.versionMinor = minor;
        this.versionBugfix = bugfix;
        this.versionBuild = build;
    }

    // Methods
    /**
     * @return the subName
     */
    public String getSubName() {
        return this.subName;
    }

    /**
     * @return the versionMajor
     */
    public int getVersionMajor() {
        return this.versionMajor;
    }

    /**
     * @return the versionMinor
     */
    public int getVersionMinor() {
        return this.versionMinor;
    }

    /**
     * @return the versionBugfix
     */
    public int getVersionBugfix() {
        return this.versionBugfix;
    }

    /**
     * @return the versionBuild
     */
    public int getVersionBuild() {
        return this.versionBuild;
    }

    public boolean doNamesMatch(final OSVersionInfo other) {
        if (this.subName.contains(OSVersionInfo.WINDOWS)
                || other.subName.contains(OSVersionInfo.WINDOWS)) {
            try {
                final String thisSub = this.subName.substring(0,
                        OSVersionInfo.WINDOWS.length());
                final String otherSub = other.subName.substring(0,
                        OSVersionInfo.WINDOWS.length());
                if (!thisSub.equals(otherSub)) {
                    return false;
                }
            } catch (final StringIndexOutOfBoundsException sioob) {
                return false;
            }
        } else if (!this.subName.equals(other.subName)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(final OSVersionInfo other) {
        if (this.versionMajor != other.versionMajor) {
            return this.versionMajor - other.versionMajor;
        } else {
            if (this.versionMinor != other.versionMinor) {
                return this.versionMinor - other.versionMinor;
            } else {
                if (this.versionBugfix != other.versionBugfix) {
                    return this.versionBugfix - other.versionBugfix;
                } else {
                    if (this.versionBuild != other.versionBuild) {
                        return this.versionBuild - other.versionBuild;
                    } else {
                        return 0;
                    }
                }
            }
        }
    }

    public static OSVersionInfo getCurrentOSVersionInfo() {
        final String nameOS = "os.name";
        final String versionOS = "os.version";
        final String OSname = System.getProperty(nameOS);
        final String OSversion = System.getProperty(versionOS);
        final String[] versionSplit = OSversion.split("\\.");
        final int[] version = new int[4];
        for (int x = 0; x < 4; x++) {
            if (x < versionSplit.length) {
                try {
                    version[x] = Integer.parseInt(versionSplit[x]);
                } catch (final NumberFormatException nfe) {
                    // Is this Linux?
                    if (nameOS.equals(OSVersionInfo.LINUX)) {
                        // Yes, so attempt to parse further
                        if (versionSplit[x].contains("-")) {
                            // Probably of the form X.Y.Z-nameW
                            final String[] doubleSplit = versionSplit[x]
                                    .split("-");
                            try {
                                version[x] = Integer
                                        .parseInt(doubleSplit[x - 2]);
                            } catch (final NumberFormatException nfe2) {
                                // Not in that format, so ignore it
                                version[x] = OSVersionInfo.OS_VERSION_ANY;
                            } catch (final ArrayIndexOutOfBoundsException aioobe) {
                                // Something went wrong, ignore it
                                version[x] = OSVersionInfo.OS_VERSION_ANY;
                            }
                        } else {
                            // Not a recognized version format, ignore it
                            version[x] = OSVersionInfo.OS_VERSION_ANY;
                        }
                    } else {
                        // No, so ignore it
                        version[x] = OSVersionInfo.OS_VERSION_ANY;
                    }
                }
            } else {
                version[x] = OSVersionInfo.OS_VERSION_ANY;
            }
        }
        return new OSVersionInfo(OSname, version[0], version[1], version[2],
                version[3]);
    }
}
