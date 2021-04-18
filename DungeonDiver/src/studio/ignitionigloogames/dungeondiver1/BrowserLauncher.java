package studio.ignitionigloogames.dungeondiver1;

import java.awt.Desktop;
import java.awt.Desktop.Action;
import java.io.IOException;
import java.net.URI;

import javax.swing.JOptionPane;

public class BrowserLauncher {
    private static final String errMsg = "Error attempting to launch web browser";

    public static void openURL(final String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop d = Desktop.getDesktop();
            if (d.isSupported(Action.APP_OPEN_URI)) {
                try {
                    d.browse(URI.create(url));
                } catch (final IOException e) {
                    JOptionPane.showMessageDialog(null,
                            BrowserLauncher.errMsg + ":\n"
                                    + e.getLocalizedMessage(),
                            null, JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        BrowserLauncher.errMsg + ": Function not supported",
                        null, JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null,
                    BrowserLauncher.errMsg + ": Function not supported", null,
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
