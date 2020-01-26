/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz;

import javax.swing.JOptionPane;

public class Messager {
    public static void showMessage(final String msg) {
        final Application app = Worldz.getApplication();
        app.getGameManager().setStatusMessage(msg);
    }

    public static void showDialog(final String msg) {
        final Application app = Worldz.getApplication();
        JOptionPane.showMessageDialog(app.getOutputFrame(), msg, "Worldz",
                JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo());
    }

    public static void showTitledDialog(final String msg, final String title) {
        final Application app = Worldz.getApplication();
        JOptionPane.showMessageDialog(app.getOutputFrame(), msg, title,
                JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo());
    }

    public static void showErrorDialog(final String msg, final String title) {
        final Application app = Worldz.getApplication();
        JOptionPane.showMessageDialog(app.getOutputFrame(), msg, title,
                JOptionPane.ERROR_MESSAGE, app.getMicroLogo());
    }

    public static String showInputDialog(final String prompt,
            final String title, final Object[] choices,
            final String defaultChoice) {
        final Application app = Worldz.getApplication();
        return (String) JOptionPane.showInputDialog(app.getOutputFrame(),
                prompt, title, JOptionPane.QUESTION_MESSAGE, app.getMicroLogo(),
                choices, defaultChoice);
    }

    public static String showTextInputDialog(final String prompt,
            final String title) {
        final Application app = Worldz.getApplication();
        return (String) JOptionPane.showInputDialog(app.getOutputFrame(),
                prompt, title, JOptionPane.QUESTION_MESSAGE, app.getMicroLogo(),
                null, null);
    }

    public static String showTextInputDialogWithDefault(final String prompt,
            final String title, final String defaultValue) {
        final Application app = Worldz.getApplication();
        return (String) JOptionPane.showInputDialog(app.getOutputFrame(),
                prompt, title, JOptionPane.QUESTION_MESSAGE, app.getMicroLogo(),
                null, defaultValue);
    }

    public static int showConfirmDialog(final String prompt,
            final String title) {
        final Application app = Worldz.getApplication();
        return JOptionPane.showConfirmDialog(app.getOutputFrame(), prompt,
                title, JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo());
    }

    public static int showYNCConfirmDialog(final String prompt,
            final String title) {
        final Application app = Worldz.getApplication();
        return JOptionPane.showConfirmDialog(app.getOutputFrame(), prompt,
                title, JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo());
    }

    public static int showCustomDialog(final String prompt, final String title,
            final String[] buttonNames, final String defaultButton) {
        final Application app = Worldz.getApplication();
        return JOptionPane.showOptionDialog(app.getOutputFrame(), prompt, title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo(),
                buttonNames, defaultButton);
    }
}
