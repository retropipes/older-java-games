package net.worldwizard.dungeondiver;

import javax.swing.UIManager;

import net.worldwizard.ghosted.Ghosted;

public class DungeonDiver {
    // Constants
    private static HoldingBag bag;
    private static final String PROGRAM_NAME = "Dungeon Diver";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the debug log with your bug report.\n"
            + "Email bug reports to: dungeondiver@worldwizard.net\n"
            + "Subject: Dungeon Diver Bug Report";
    private static final String ERROR_TITLE = "Dungeon Diver Error";
    private static final Ghosted debug = new Ghosted(DungeonDiver.PROGRAM_NAME,
            DungeonDiver.ERROR_MESSAGE, DungeonDiver.ERROR_TITLE);

    // Methods
    public static HoldingBag getHoldingBag() {
        return DungeonDiver.bag;
    }

    public static Ghosted getDebug() {
        return DungeonDiver.debug;
    }

    public static void main(final String[] args) {
        // Mac OS X-specific stuff
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            System.setProperty(
                    "com.apple.mrj.application.apple.menu.about.name",
                    "Dungeon Diver");
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        }
        try {
            // Tell the UIManager to use the platform native look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (final Exception e) {
            // Do nothing
        }
        try {
            DungeonDiver.bag = new HoldingBag();
            DungeonDiver.bag.showGUI();
        } catch (final Throwable t) {
            DungeonDiver.getDebug().debug(t);
        }
    }
}
