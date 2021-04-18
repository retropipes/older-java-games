package studio.ignitionigloogames.dungeondiver1.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;

public class MessageDialog {
    static boolean IN_DIALOG = false;

    // Constructor
    private MessageDialog() {
        // Do nothing
    }

    // Methods
    public static void showDialog(final String msg, final String title) {
        IN_DIALOG = true;
        Thread dThr = new Thread() {
            @Override
            public void run() {
                EventHandler hndl = new EventHandler();
                MainWindow main = MainWindow.getMainWindow();
                final JButton setButton = new JButton("OK");
                setButton.setActionCommand("OK");
                setButton.addActionListener(hndl);
                final JPanel messagePane = new JPanel();
                messagePane.setLayout(
                        new BoxLayout(messagePane, BoxLayout.PAGE_AXIS));
                final JLabel label = new JLabel(msg);
                messagePane.add(label);
                final JPanel buttonPane = new JPanel();
                buttonPane.setLayout(
                        new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
                buttonPane.setBorder(
                        BorderFactory.createEmptyBorder(0, 10, 10, 10));
                buttonPane.add(Box.createHorizontalGlue());
                buttonPane.add(setButton);
                // Put everything together, using the content pane's
                // BorderLayout.
                final JPanel contentPane = new JPanel();
                contentPane.add(messagePane, BorderLayout.NORTH);
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
    }

    private static class EventHandler implements ActionListener {
        // Handle clicks on the Set button.
        @Override
        public void actionPerformed(final ActionEvent e) {
            MainWindow.getMainWindow().restoreSaved();
            IN_DIALOG = false;
        }
    }
}
