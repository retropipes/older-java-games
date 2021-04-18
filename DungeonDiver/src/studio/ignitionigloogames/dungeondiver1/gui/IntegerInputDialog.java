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
import javax.swing.JTextField;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;

public class IntegerInputDialog {
    private static int value;
    static boolean IN_DIALOG = false;
    public static int DEFAULT_VALUE = 0;
    public static int CANCEL_VALUE = -1;
    private static final JLabel errorMessage = new JLabel();
    static JTextField input;

    public static int showDialog(final String labelText, final String title) {
        IN_DIALOG = true;
        Thread dThr = new Thread() {
            @Override
            public void run() {
                EventHandler hndl = new EventHandler();
                MainWindow main = MainWindow.getMainWindow();
                // Create and initialize the buttons.
                final JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(hndl);
                final JButton setButton = new JButton("OK");
                setButton.setActionCommand("OK");
                setButton.addActionListener(hndl);
                // main part of the dialog
                final JPanel inputPane = new JPanel();
                inputPane.setLayout(
                        new BoxLayout(inputPane, BoxLayout.PAGE_AXIS));
                final JLabel label = new JLabel(labelText);
                label.setLabelFor(IntegerInputDialog.input);
                inputPane.add(label);
                inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
                inputPane.add(IntegerInputDialog.errorMessage);
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
        return IntegerInputDialog.value;
    }

    private static void setValue(final int newValue) {
        IntegerInputDialog.input.setText(Integer.toString(newValue));
        IntegerInputDialog.value = newValue;
    }

    private IntegerInputDialog() {
        // Do nothing
    }

    // Handle clicks on the Set and Cancel buttons.
    private static class EventHandler implements ActionListener {
        @Override
        public void actionPerformed(final ActionEvent e) {
            if ("OK".equals(e.getActionCommand())) {
                try {
                    IntegerInputDialog.setValue(Integer
                            .parseInt(IntegerInputDialog.input.getText()));
                    IntegerInputDialog.errorMessage.setText("");
                    MainWindow.getMainWindow().restoreSaved();
                    IN_DIALOG = false;
                } catch (NumberFormatException nfe) {
                    IntegerInputDialog.errorMessage
                            .setText("Invalid input (not an integer)!");
                }
            } else if ("Cancel".equals(e.getActionCommand())) {
                IntegerInputDialog.setValue(IntegerInputDialog.CANCEL_VALUE);
                MainWindow.getMainWindow().restoreSaved();
                IN_DIALOG = false;
            }
        }
    }
}
