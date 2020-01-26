package net.dynamicdungeon.platform;

import java.awt.Image;
import java.awt.Window;
import java.lang.reflect.Method;

import apple.dts.samplecode.osxadapter.OSXAdapter;

public class Platform {
    // Constructor
    private Platform() {
        // Do nothing
    }

    // Methods
    public static void hookLAF(final String programName) {
        System.setProperty("com.apple.mrj.application.apple.menu.about.name",
                programName);
        System.setProperty("apple.laf.useScreenMenuBar", "true");
    }

    public static void hookFileOpen(final Object o, final Method m) {
        OSXAdapter.setFileHandler(o, m);
    }

    public static void hookQuit(final Object o, final Method m) {
        OSXAdapter.setQuitHandler(o, m);
    }

    public static void hookPreferences(final Object o, final Method m) {
        OSXAdapter.setPreferencesHandler(o, m);
    }

    public static void hookAbout(final Object o, final Method m) {
        OSXAdapter.setAboutHandler(o, m);
    }

    public static void hookFullScreen(final Window w, final boolean fsCapable) {
        OSXAdapter.setWindowCanFullScreen(w, fsCapable);
    }

    public static void hookDockIcon(final Image i) {
        OSXAdapter.setDockIconImage(i);
    }

    public static void hookDockIconBadge(final String badgeText) {
        OSXAdapter.setDockIconBadge(badgeText);
    }
}
