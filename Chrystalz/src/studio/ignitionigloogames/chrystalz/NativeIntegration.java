/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.awt.desktop.AboutHandler;
import java.awt.desktop.PreferencesHandler;
import java.awt.desktop.QuitHandler;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class NativeIntegration {
    // Constructor
    private NativeIntegration() {
        // Do nothing
    }

    // Methods
    public static void hookLAF(final String programName) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            // Mac OS X-specific stuff
            System.setProperty(
                    "studio.apple.mrj.application.apple.menu.about.name",
                    programName);
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        } else if (System.getProperty("os.name").startsWith("Windows")) {
            // Windows-specific stuff
            try {
                // Tell the UIManager to use the platform native look and
                // feel
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
                // Hint to the UI that the L&F is decorated
                JFrame.setDefaultLookAndFeelDecorated(true);
            } catch (final Exception e) {
                // Do nothing
            }
        } else {
            // All other platforms
            try {
                // Tell the UIManager to use the Nimbus look and feel
                UIManager.setLookAndFeel(
                        "studio.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                // Hint to the UI that the L&F is decorated
                JFrame.setDefaultLookAndFeelDecorated(true);
            } catch (final Exception e) {
                // Do nothing
            }
        }
    }

    public static void hookQuit(final QuitHandler qh) {
        if (Desktop.isDesktopSupported()) {
            Desktop d = Desktop.getDesktop();
            if (d.isSupported(Action.APP_QUIT_HANDLER)) {
                d.setQuitHandler(qh);
            }
        }
    }

    public static void hookPreferences(final PreferencesHandler ph) {
        if (Desktop.isDesktopSupported()) {
            Desktop d = Desktop.getDesktop();
            if (d.isSupported(Action.APP_PREFERENCES)) {
                d.setPreferencesHandler(ph);
            }
        }
    }

    public static void hookAbout(final AboutHandler ah) {
        if (Desktop.isDesktopSupported()) {
            Desktop d = Desktop.getDesktop();
            if (d.isSupported(Action.APP_ABOUT)) {
                d.setAboutHandler(ah);
            }
        }
    }
}
