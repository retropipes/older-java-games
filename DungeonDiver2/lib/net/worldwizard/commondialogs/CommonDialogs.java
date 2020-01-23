/* CommonDialogs.java
 * A frequently used dialog class.
 */
package net.worldwizard.commondialogs;

import javax.swing.JOptionPane;

import net.worldwizard.images.BufferedImageIcon;

public class CommonDialogs {
    // Fields
    private static BufferedImageIcon ICON = null;
    private static String DEFAULT_TITLE = null;

    // Constructor
    private CommonDialogs() {
        // Do nothing
    }

    // Methods
    /**
     * Displays a dialog.
     *
     * @param msg
     *            The dialog message.
     */
    public static void showDialog(final String msg) {
        JOptionPane.showMessageDialog(null, msg, CommonDialogs.DEFAULT_TITLE,
                JOptionPane.INFORMATION_MESSAGE, CommonDialogs.ICON);
    }

    /**
     * Displays a dialog with a title.
     *
     * @param msg
     *            The dialog message.
     * @param title
     *            The dialog title.
     */
    public static void showTitledDialog(final String msg, final String title) {
        JOptionPane.showMessageDialog(null, msg, title,
                JOptionPane.INFORMATION_MESSAGE, CommonDialogs.ICON);
    }

    /**
     * Displays an error dialog with a title.
     *
     * @param msg
     *            The dialog message.
     * @param title
     *            The dialog title.
     */
    public static void showErrorDialog(final String msg, final String title) {
        JOptionPane.showMessageDialog(null, msg, title,
                JOptionPane.ERROR_MESSAGE, CommonDialogs.ICON);
    }

    /**
     * Displays an input dialog, allowing the user to pick from a list.
     *
     * @param prompt
     *            The input prompt.
     * @param title
     *            The dialog title.
     * @param choices
     *            The list of choices.
     * @param defaultChoice
     *            The default choice, which should be one of the list entries.
     * @return The choice picked
     */
    public static String showInputDialog(final String prompt,
            final String title, final Object[] choices,
            final String defaultChoice) {
        return (String) JOptionPane.showInputDialog(null, prompt, title,
                JOptionPane.QUESTION_MESSAGE, CommonDialogs.ICON, choices,
                defaultChoice);
    }

    /**
     * Displays a text input dialog, allowing the user to enter a value.
     *
     * @param prompt
     *            The input prompt.
     * @param title
     *            The dialog title.
     * @return The value the user input.
     */
    public static String showTextInputDialog(final String prompt,
            final String title) {
        return (String) JOptionPane.showInputDialog(null, prompt, title,
                JOptionPane.QUESTION_MESSAGE, CommonDialogs.ICON, null, null);
    }

    /**
     * Displays a yes/no confirm dialog.
     *
     * @param prompt
     *            The confirmation prompt.
     * @param title
     *            The dialog title.
     * @return A JOptionPane constant specifying what the user clicked.
     */
    public static int showConfirmDialog(final String prompt, final String title) {
        return JOptionPane.showConfirmDialog(null, prompt, title,
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
                CommonDialogs.ICON);
    }

    /**
     * Displays a yes/no/cancel confirm dialog.
     *
     * @param prompt
     *            The confirmation prompt.
     * @param title
     *            The dialog title.
     * @return A JOptionPane constant specifying what the user clicked.
     */
    public static int showYNCConfirmDialog(final String prompt,
            final String title) {
        return JOptionPane.showConfirmDialog(null, prompt, title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, CommonDialogs.ICON);
    }

    public static int showCustomDialog(final String prompt, final String title,
            final String[] buttonNames, final String defaultButton) {
        return JOptionPane.showOptionDialog(null, prompt, title,
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.INFORMATION_MESSAGE, CommonDialogs.ICON,
                buttonNames, defaultButton);
    }

    /**
     * Sets the default title for dialogs.
     *
     * @param title
     *            The default title
     */
    public static void setDefaultTitle(final String title) {
        CommonDialogs.DEFAULT_TITLE = title;
    }

    /**
     * Sets the image to use instead of the default icons.
     *
     * @param icon
     *            The image - should be a BufferedImageIcon from the Graphics
     *            library.
     */
    public static void setIcon(final BufferedImageIcon icon) {
        CommonDialogs.ICON = icon;
    }
}
