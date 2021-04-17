package studio.ignitionigloogames.dungeondiver1.gui;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MessageDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    // Fields
    private static MessageDialog dialog;

    // Constructor
    private MessageDialog(final Frame frame, final String msg,
            final String title) {
        super(frame, title, true);
        final JButton setButton = new JButton("OK");
        setButton.setActionCommand("OK");
        setButton.addActionListener(this);
        this.getRootPane().setDefaultButton(setButton);
        final JPanel messagePane = new JPanel();
        messagePane.setLayout(new BoxLayout(messagePane, BoxLayout.PAGE_AXIS));
        final JLabel label = new JLabel(msg);
        messagePane.add(label);
        final JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(setButton);
        // Put everything together, using the content pane's BorderLayout.
        final JPanel contentPane = new JPanel();
        contentPane.add(messagePane, BorderLayout.NORTH);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);
    }

    // Methods
    /**
     * Displays a dialog with a title.
     *
     * @param msg
     *            The dialog message.
     * @param title
     *            The dialog title.
     */
    public static void showDialog(final String msg, final String title) {
        final Frame frame = JOptionPane
                .getFrameForComponent(MainWindow.owner());
        MessageDialog.dialog = new MessageDialog(frame, msg, title);
        MessageDialog.dialog.setVisible(true);
        JOptionPane.showMessageDialog(MainWindow.owner(), msg, title,
                JOptionPane.INFORMATION_MESSAGE, null);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        MessageDialog.dialog.setVisible(false);
    }
}
