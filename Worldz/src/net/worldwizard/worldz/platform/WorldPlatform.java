package net.worldwizard.worldz.platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.UIManager;

import apple.dts.samplecode.osxadapter.OSXAdapter;

public class WorldPlatform {
    public void hookLAF() {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            // Mac OS X-specific stuff
            System.setProperty(
                    "com.apple.mrj.application.apple.menu.about.name", "Worldz");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            try {
                // Tell the UIManager to use the Quaqua look and feel
                UIManager
                        .setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
            } catch (final Exception e) {
                // Do nothing
            }
        } else {
            try {
                // Tell the UIManager to use the platform native look and feel
                UIManager.setLookAndFeel(UIManager
                        .getSystemLookAndFeelClassName());
                // Hint to the UI that the L&F is decorated
                JFrame.setDefaultLookAndFeelDecorated(true);
            } catch (final Exception e) {
                // Do nothing
            }
        }
    }

    public void hookFileOpen(final Object o, final Method m, final String s) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            OSXAdapter.setFileHandler(o, m);
        } else {
            try {
                m.invoke(o, s);
            } catch (final IllegalArgumentException e) {
                // Ignore
            } catch (final IllegalAccessException e) {
                // Ignore
            } catch (final InvocationTargetException e) {
                // Ignore
            }
        }
    }

    public void hookQuit(final Object o, final Method m) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            OSXAdapter.setQuitHandler(o, m);
        }
    }

    public void hookPreferences(final Object o, final Method m) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            OSXAdapter.setPreferencesHandler(o, m);
        }
    }

    public void hookAbout(final Object o, final Method m) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            OSXAdapter.setAboutHandler(o, m);
        }
    }
}
