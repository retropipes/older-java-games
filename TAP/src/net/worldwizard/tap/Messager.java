/*  TAP: A Text Adventure Parser
Copyright (C) 2010 Eric Ahnell

Any questions should be directed to the author via email at: tap@worldwizard.net
 */
package net.worldwizard.tap;

import javax.swing.JOptionPane;

public class Messager {
    public static void showMessage(final String msg) {
        final Application app = TAP.getApplication();
        app.getGUIManager().updateCommandOutput(msg);
    }

    public static void showTitledDialog(final String msg, final String title) {
        final Application app = TAP.getApplication();
        JOptionPane.showMessageDialog(app.getOutputFrame(), msg, title,
                JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo());
    }

    public static void showErrorDialog(final String msg, final String title) {
        final Application app = TAP.getApplication();
        JOptionPane.showMessageDialog(app.getOutputFrame(), msg, title,
                JOptionPane.ERROR_MESSAGE, app.getMicroLogo());
    }

    public static int showConfirmDialog(final String prompt, final String title) {
        final Application app = TAP.getApplication();
        return JOptionPane.showConfirmDialog(app.getOutputFrame(), prompt,
                title, JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo());
    }
}
