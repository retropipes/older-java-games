package studio.ignitionigloogames.dungeondiver1.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

public class ConfirmDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static ConfirmDialog dialog;
    private static int value;
    public static int YES_OPTION = 1;
    public static int CLOSED_OPTION = 0;
    public static int NO_OPTION = -1;

    public static int showDialog(final String labelText, final String title) {
        ConfirmDialog.value = ConfirmDialog.CLOSED_OPTION;
        final Frame frame = JOptionPane
                .getFrameForComponent(MainWindow.owner());
        ConfirmDialog.dialog = new ConfirmDialog(frame, labelText, title);
        ConfirmDialog.dialog.setVisible(true);
        return ConfirmDialog.value;
    }

    private static void setValue(final int newValue) {
        ConfirmDialog.value = newValue;
    }

    private ConfirmDialog(final Frame frame, final String labelText,
            final String title) {
        super(frame, title, true);
        // Create and initialize the buttons.
        final JButton cancelButton = new JButton("No");
        cancelButton.addActionListener(this);
        final JButton setButton = new JButton("Yes");
        setButton.setActionCommand("Yes");
        setButton.addActionListener(this);
        this.getRootPane().setDefaultButton(cancelButton);
        // main part of the dialog
        final JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.PAGE_AXIS));
        final JLabel label = new JLabel(labelText);
        inputPane.add(label);
        inputPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // Lay out the buttons from left to right.
        final JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        buttonPane.add(Box.createHorizontalGlue());
        buttonPane.add(cancelButton);
        buttonPane.add(Box.createRigidArea(new Dimension(10, 0)));
        buttonPane.add(setButton);
        // Put everything together, using the content pane's BorderLayout.
        final JPanel contentPane = new JPanel();
        contentPane.add(inputPane, BorderLayout.NORTH);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);
        this.setContentPane(contentPane);
    }

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
        ConfirmDialog.dialog.setVisible(false);
    }
}
