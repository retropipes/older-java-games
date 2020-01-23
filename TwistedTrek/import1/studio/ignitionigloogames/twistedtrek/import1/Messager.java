/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1;

import javax.swing.JOptionPane;

public class Messager {
    public static void showMessage(final String msg) {
	final Application app = Import1.getApplication();
	app.getGameManager().setStatusMessage(msg);
    }

    public static void showDialog(final String msg) {
	final Application app = Import1.getApplication();
	JOptionPane.showMessageDialog(app.getOutputFrame(), msg, "Import1", JOptionPane.INFORMATION_MESSAGE,
		app.getMicroLogo());
    }

    public static void showTitledDialog(final String msg, final String title) {
	final Application app = Import1.getApplication();
	JOptionPane.showMessageDialog(app.getOutputFrame(), msg, title, JOptionPane.INFORMATION_MESSAGE,
		app.getMicroLogo());
    }

    public static void showErrorDialog(final String msg, final String title) {
	final Application app = Import1.getApplication();
	JOptionPane.showMessageDialog(app.getOutputFrame(), msg, title, JOptionPane.ERROR_MESSAGE, app.getMicroLogo());
    }

    public static String showInputDialog(final String prompt, final String title, final Object[] choices,
	    final String defaultChoice) {
	final Application app = Import1.getApplication();
	return (String) JOptionPane.showInputDialog(app.getOutputFrame(), prompt, title, JOptionPane.QUESTION_MESSAGE,
		app.getMicroLogo(), choices, defaultChoice);
    }

    public static String showTextInputDialog(final String prompt, final String title) {
	final Application app = Import1.getApplication();
	return (String) JOptionPane.showInputDialog(app.getOutputFrame(), prompt, title, JOptionPane.QUESTION_MESSAGE,
		app.getMicroLogo(), null, null);
    }

    public static String showTextInputDialogWithDefault(final String prompt, final String title,
	    final String defaultValue) {
	final Application app = Import1.getApplication();
	return (String) JOptionPane.showInputDialog(app.getOutputFrame(), prompt, title, JOptionPane.QUESTION_MESSAGE,
		app.getMicroLogo(), null, defaultValue);
    }

    public static int showConfirmDialog(final String prompt, final String title) {
	final Application app = Import1.getApplication();
	return JOptionPane.showConfirmDialog(app.getOutputFrame(), prompt, title, JOptionPane.YES_NO_OPTION,
		JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo());
    }

    public static int showYNCConfirmDialog(final String prompt, final String title) {
	final Application app = Import1.getApplication();
	return JOptionPane.showConfirmDialog(app.getOutputFrame(), prompt, title, JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo());
    }
}
