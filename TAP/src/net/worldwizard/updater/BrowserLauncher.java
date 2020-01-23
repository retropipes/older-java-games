/*  TAP: A Text Adventure Parser
Copyright (C) 2010 Eric Ahnell

Any questions should be directed to the author via email at: tap@worldwizard.net
 */
package net.worldwizard.updater;

import java.lang.reflect.Method;

import net.worldwizard.tap.Messager;

class BrowserLauncher {
    private static final String errMsg = "Error attempting to launch web browser";

    protected static void openURL(final String url) {
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
                        "epiphany", "mozilla", "netscape" };
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
            Messager.showErrorDialog(
                    BrowserLauncher.errMsg + ":\n" + e.getLocalizedMessage(),
                    "Error");
        }
    }
}
