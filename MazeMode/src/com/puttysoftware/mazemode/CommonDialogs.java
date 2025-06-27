/*  MazeMode: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazemode;

import javax.swing.JOptionPane;

public class CommonDialogs {
        public static void showDialog(final String msg) {
                final Application app = MazeMode.getApplication();
                JOptionPane.showMessageDialog(app.getOutputFrame(), msg,
                                MazeMode.getProgramName(), JOptionPane.INFORMATION_MESSAGE,
                                app.getMicroLogo());
        }

        public static void showTitledDialog(final String msg, final String title) {
                final Application app = MazeMode.getApplication();
                JOptionPane.showMessageDialog(app.getOutputFrame(), msg, title,
                                JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo());
        }

        public static void showErrorDialog(final String msg, final String title) {
                final Application app = MazeMode.getApplication();
                JOptionPane.showMessageDialog(app.getOutputFrame(), msg, title,
                                JOptionPane.ERROR_MESSAGE, app.getMicroLogo());
        }

        public static String showInputDialog(final String prompt,
                        final String title, final Object[] choices,
                        final String defaultChoice) {
                final Application app = MazeMode.getApplication();
                return (String) JOptionPane.showInputDialog(app.getOutputFrame(),
                                prompt, title, JOptionPane.QUESTION_MESSAGE, app.getMicroLogo(),
                                choices, defaultChoice);
        }

        public static String showTextInputDialog(final String prompt,
                        final String title) {
                final Application app = MazeMode.getApplication();
                return (String) JOptionPane.showInputDialog(app.getOutputFrame(),
                                prompt, title, JOptionPane.QUESTION_MESSAGE, app.getMicroLogo(),
                                null, null);
        }

        public static String showTextInputDialogWithDefault(final String prompt,
                        final String title, final String defaultValue) {
                final Application app = MazeMode.getApplication();
                return (String) JOptionPane.showInputDialog(app.getOutputFrame(),
                                prompt, title, JOptionPane.QUESTION_MESSAGE, app.getMicroLogo(),
                                null, defaultValue);
        }

        public static int showConfirmDialog(final String prompt,
                        final String title) {
                final Application app = MazeMode.getApplication();
                return JOptionPane.showConfirmDialog(app.getOutputFrame(), prompt,
                                title, JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo());
        }

        public static int showYNCConfirmDialog(final String prompt,
                        final String title) {
                final Application app = MazeMode.getApplication();
                return JOptionPane.showConfirmDialog(app.getOutputFrame(), prompt,
                                title, JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, app.getMicroLogo());
        }
}
