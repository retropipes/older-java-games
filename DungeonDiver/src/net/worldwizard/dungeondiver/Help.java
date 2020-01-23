package net.worldwizard.dungeondiver;

import java.net.MalformedURLException;
import java.net.URL;

public class Help {
    // Fields
    private URL helpURL;
    private String helpLink;

    // Constructor
    public Help() {
        try {
            this.helpURL = new URL(
                    "http://www.puttysoftware.com/oldgames/dungeondiver/help/");
            this.helpLink = this.helpURL.toExternalForm();
        } catch (final MalformedURLException e) {
            // Ignore exception
        }
    }

    // Method
    public void showHelp() {
        BrowserLauncher.openURL(this.helpLink);
    }
}
