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
import javax.swing.JTextField;

public class IntegerInputDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static IntegerInputDialog dialog;
    private static int value;
    public static int DEFAULT_VALUE = 0;
    public static int CANCEL_VALUE = -1;
    private static final JLabel errorMessage = new JLabel();
    static JTextField input;

    public static int showDialog(final String labelText, final String title) {
        IntegerInputDialog.value = IntegerInputDialog.DEFAULT_VALUE;
        final Frame frame = JOptionPane
                .getFrameForComponent(MainWindow.owner());
        IntegerInputDialog.dialog = new IntegerInputDialog(frame, labelText,
                title);
        IntegerInputDialog.dialog.setVisible(true);
        return IntegerInputDialog.value;
    }

    private static void setValue(final int newValue) {
        IntegerInputDialog.input.setText(Integer.toString(newValue));
        IntegerInputDialog.value = newValue;
    }

    private IntegerInputDialog(final Frame frame, final String labelText,
            final String title) {
        super(frame, title, true);
        // Create and initialize the buttons.
        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        final JButton setButton = new JButton("OK");
        setButton.setActionCommand("OK");
        setButton.addActionListener(this);
        this.getRootPane().setDefaultButton(setButton);
        // main part of the dialog
        final JPanel inputPane = new JPanel();
        inputPane.setLayout(new BoxLayout(inputPane, BoxLayout.PAGE_AXIS));
        final JLabel label = new JLabel(labelText);
        label.setLabelFor(IntegerInputDialog.input);
        inputPane.add(label);
        inputPane.add(Box.createRigidArea(new Dimension(10, 0)));
        inputPane.add(IntegerInputDialog.errorMessage);
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
            try {
                IntegerInputDialog.setValue(
                        Integer.parseInt(IntegerInputDialog.input.getText()));
                IntegerInputDialog.errorMessage.setText("");
                IntegerInputDialog.dialog.setVisible(false);
            } catch (NumberFormatException nfe) {
                IntegerInputDialog.errorMessage
                        .setText("Invalid input (not an integer)!");
            }
        } else if ("Cancel".equals(e.getActionCommand())) {
            IntegerInputDialog.setValue(IntegerInputDialog.CANCEL_VALUE);
            IntegerInputDialog.dialog.setVisible(false);
        }
    }
}
