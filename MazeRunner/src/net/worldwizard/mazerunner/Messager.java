package net.worldwizard.mazerunner;

import javax.swing.JOptionPane;

public class Messager {
    public static void showMessage(final String msg) {
        final MazeRunner app = MazeRunner.getApplication();
        app.setStatusMessage(msg);
    }

    public static void showDialog(final String msg) {
        final MazeRunner app = MazeRunner.getApplication();
        JOptionPane.showMessageDialog(app.getOutputFrame(), msg);
    }

    public static String showInputDialog(final String prompt,
            final String title, final Object[] choices,
            final String defaultChoice) {
        final MazeRunner app = MazeRunner.getApplication();
        return (String) JOptionPane.showInputDialog(app.getOutputFrame(),
                prompt, title, JOptionPane.QUESTION_MESSAGE, null, choices,
                defaultChoice);
    }
}