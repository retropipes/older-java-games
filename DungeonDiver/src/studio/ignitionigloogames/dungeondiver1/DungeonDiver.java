package studio.ignitionigloogames.dungeondiver1;

import javax.swing.UIManager;

public class DungeonDiver {
    // Constants
    private static HoldingBag bag;
    private static final GameErrorHandler debug = new GameErrorHandler();

    // Methods
    public static HoldingBag getHoldingBag() {
        return DungeonDiver.bag;
    }

    public static void debug(final Throwable t) {
        DungeonDiver.debug.handle(t);
    }

    public static void main(final String[] args) {
        // Install error handler
        Thread.setDefaultUncaughtExceptionHandler(DungeonDiver.debug);
        // Mac OS X-specific stuff
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
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
            DungeonDiver.debug(t);
        }
    }
}
