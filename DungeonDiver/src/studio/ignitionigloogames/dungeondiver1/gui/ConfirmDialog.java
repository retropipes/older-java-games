package studio.ignitionigloogames.dungeondiver1.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;

public class ConfirmDialog {
    private static int value;
    static boolean IN_DIALOG = false;
    public static int YES_OPTION = 1;
    public static int CLOSED_OPTION = 0;
    public static int NO_OPTION = -1;

    public static int showDialog(final String labelText, final String title) {
        ConfirmDialog.IN_DIALOG = true;
        Thread dThr = new Thread() {
            @Override
            public void run() {
                EventHandler hndl = new EventHandler();
                ConfirmDialog.value = ConfirmDialog.CLOSED_OPTION;
                MainWindow main = MainWindow.getMainWindow();
                // Create and initialize the buttons.
                final JButton cancelButton = new JButton("No");
                cancelButton.addActionListener(hndl);
                final JButton setButton = new JButton("Yes");
                setButton.setActionCommand("Yes");
                setButton.addActionListener(hndl);
                // main part of the dialog
                final JPanel inputPane = new JPanel();
                inputPane.setLayout(
                        new BoxLayout(inputPane, BoxLayout.PAGE_AXIS));
                final JLabel label = new JLabel(labelText);
                inputPane.add(label);
                inputPane.setBorder(
                        BorderFactory.createEmptyBorder(10, 10, 10, 10));
                // Lay out the buttons from left to right.
                final JPanel buttonPane = new JPanel();
                buttonPane.setLayout(
                        new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
                buttonPane.setBorder(
                        BorderFactory.createEmptyBorder(0, 10, 10, 10));
                buttonPane.add(Box.createHorizontalGlue());
                buttonPane.add(cancelButton);
                buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
                buttonPane.add(setButton);
                // Put everything together, using the content pane's
                // BorderLayout.
                final JPanel contentPane = new JPanel();
                contentPane.add(inputPane, BorderLayout.NORTH);
                contentPane.add(buttonPane, BorderLayout.PAGE_END);
                main.attachAndSave(contentPane);
                main.setTitle(title);
                while (IN_DIALOG) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        DungeonDiver.debug(e);
                    }
                }
            }
        };
        dThr.start();
        try {
            dThr.join();
        } catch (InterruptedException e) {
            DungeonDiver.debug(e);
        }
        return ConfirmDialog.value;
    }

    private static void setValue(final int newValue) {
        ConfirmDialog.value = newValue;
    }

    private ConfirmDialog() {
        // Do nothing
    }

    private static class EventHandler implements ActionListener {
        // Handle clicks on the Set and Cancel buttons.
        @Override
        public void actionPerformed(final ActionEvent e) {
            if ("OK".equals(e.getActionCommand())) {
                ConfirmDialog.setValue(ConfirmDialog.YES_OPTION);
            } else if ("Cancel".equals(e.getActionCommand())) {
                ConfirmDialog.setValue(ConfirmDialog.NO_OPTION);
            } else {
                ConfirmDialog.setValue(ConfirmDialog.CLOSED_OPTION);
            }
            MainWindow.getMainWindow().restoreSaved();
            IN_DIALOG = false;
        }
    }
}
