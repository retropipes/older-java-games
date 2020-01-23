package com.puttysoftware.osversioncheck;

public class OSVersionCheck {
    // Private Constructor
    private OSVersionCheck() {
        // Do nothing
    }

    // Methods
    public static boolean checkOSVersionMin(final OSVersionInfo... baseOSes) {
        final OSVersionInfo current = OSVersionInfo.getCurrentOSVersionInfo();
        for (final OSVersionInfo os : baseOSes) {
            if (current.doNamesMatch(os) && current.compareTo(os) >= 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkOSVersionMax(final OSVersionInfo... baseOSes) {
        final OSVersionInfo current = OSVersionInfo.getCurrentOSVersionInfo();
        for (final OSVersionInfo os : baseOSes) {
            if (current.doNamesMatch(os) && current.compareTo(os) <= 0) {
                return true;
            }
        }
        return false;
    }
}
