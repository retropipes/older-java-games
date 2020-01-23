/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.updater;

import java.awt.Desktop;
import java.lang.reflect.Method;
import java.net.URL;

import net.worldwizard.commondialogs.CommonDialogs;

public class BrowserLauncher {
    private static final String errMsg = "Error attempting to launch web browser";

    public static void openURL(final String url) {
        // Try it the Java 6 way first
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URL(url).toURI());
            } catch (final Exception e) {
                BrowserLauncher.openURLLegacy(url);
            }
        } else {
            BrowserLauncher.openURLLegacy(url);
        }
    }

    private static void openURLLegacy(final String url) {
        // If that failed, use the old method
        final String osName = System.getProperty("os.name");
        try {
            if (osName.startsWith("Mac OS")) {
                final Class<?> fileMgr = Class
                        .forName("com.apple.eio.FileManager");
                final Method openURL = fileMgr.getDeclaredMethod("openURL",
                        new Class<?>[] { String.class });
                openURL.invoke(null, new Object[] { url });
            } else if (osName.startsWith("Windows")) {
                Runtime.getRuntime().exec(
                        "rundll32 url.dll,FileProtocolHandler " + url);
            } else { // assume Unix or Linux
                final String[] browsers = { "firefox", "opera", "konqueror",
                        "epiphany", "mozilla", "netscape", "chromium" };
                String browser = null;
                for (int count = 0; count < browsers.length && browser == null; count++) {
                    if (Runtime.getRuntime()
                            .exec(new String[] { "which", browsers[count] })
                            .waitFor() == 0) {
                        browser = browsers[count];
                    }
                }
                if (browser == null) {
                    throw new Exception("Could not find web browser");
                } else {
                    Runtime.getRuntime().exec(new String[] { browser, url });
                }
            }
        } catch (final Exception e) {
            CommonDialogs.showDialog(BrowserLauncher.errMsg + ":\n"
                    + e.getLocalizedMessage());
        }
    }
}
