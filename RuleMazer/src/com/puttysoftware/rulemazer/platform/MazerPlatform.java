package com.puttysoftware.rulemazer.platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.UIManager;

import apple.dts.samplecode.osxadapter.OSXAdapter;

public class MazerPlatform {
    public void hookLAF() {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            // Mac OS X-specific stuff
            System.setProperty(
                    "com.apple.mrj.application.apple.menu.about.name",
                    "RuleMazer");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
            try {
                // Tell the UIManager to use the Quaqua look and feel
                UIManager
                        .setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
            } catch (final Exception e) {
                // Do nothing
            }
        } else if (System.getProperty("os.name").startsWith("Windows")) {
            // Windows-specific stuff
            try {
                // Tell the UIManager to use the Windows look and feel
                UIManager
                        .setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
                // Hint to the UI that the L&F is decorated
                JFrame.setDefaultLookAndFeelDecorated(true);
            } catch (final Exception e) {
                // Do nothing
            }
        } else {
            // All other platforms
            try {
                // Tell the UIManager to use the GTK look and feel
                UIManager
                        .setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                // Hint to the UI that the L&F is decorated
                JFrame.setDefaultLookAndFeelDecorated(true);
            } catch (final Exception e) {
                try {
                    // Tell the UIManager to use the platform native look and
                    // feel, as a fall-back
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                    // Hint to the UI that the L&F is decorated
                    JFrame.setDefaultLookAndFeelDecorated(true);
                } catch (final Exception e2) {
                    // Do nothing
                }
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
