package studio.ignitionigloogames.dungeondiver1.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

public class ListDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private static ListDialog dialog;
    private static String value = null;
    static JList<String> list;

    public static String showDialog(final String labelText, final String title,
            final String[] possibleValues, final String initialValue) {
        ListDialog.value = null;
        final Frame frame = JOptionPane
                .getFrameForComponent(MainWindow.owner());
        ListDialog.dialog = new ListDialog(frame, labelText, title,
                possibleValues, initialValue);
        ListDialog.dialog.setVisible(true);
        return ListDialog.value;
    }

    private static void setValue(final String newValue) {
        ListDialog.value = newValue;
        ListDialog.list.setSelectedValue(ListDialog.value, true);
    }

    private ListDialog(final Frame frame, final String labelText,
            final String title, final String[] data,
            final String initialValue) {
        super(frame, title, true);
        // Create and initialize the buttons.
        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        //
        final JButton setButton = new JButton("OK");
        setButton.setActionCommand("OK");
        setButton.addActionListener(this);
        this.getRootPane().setDefaultButton(setButton);
        // main part of the dialog
        ListDialog.list = new SubJList<>(data);
        ListDialog.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListDialog.list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ListDialog.list.setVisibleRowCount(-1);
        ListDialog.list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    setButton.doClick(); // emulate button click
                }
            }
        });
        final JScrollPane listScroller = new JScrollPane(ListDialog.list);
        listScroller.setPreferredSize(new Dimension(250, 80));
        listScroller.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Create a container so that we can add a title around
        // the scroll pane. Can't add a title directly to the
        // scroll pane because its background would be white.
        // Lay out the label and scroll pane from top to bottom.
        final JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        final JLabel label = new JLabel(labelText);
        label.setLabelFor(ListDialog.list);
        listPane.add(label);
        listPane.add(Box.createRigidArea(new Dimension(0, 5)));
        listPane.add(listScroller);
        listPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
        contentPane.add(listPane, BorderLayout.NORTH);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);
        // Initialize values.
        ListDialog.setValue(initialValue);
        this.setContentPane(contentPane);
    }

    // Handle clicks on the Set and Cancel buttons.
    @Override
    public void actionPerformed(final ActionEvent e) {
        if ("OK".equals(e.getActionCommand())) {
            ListDialog.setValue(ListDialog.list.getSelectedValue());
        } else if ("Cancel".equals(e.getActionCommand())) {
            ListDialog.setValue(null);
        }
        ListDialog.dialog.setVisible(false);
    }

    private static class SubJList<T> extends JList<T> {
        private static final long serialVersionUID = 1L;

        // Subclass JList to workaround bug 4832765, which can cause the
        // scroll pane to not let the user easily scroll up to the beginning
        // of the list. An alternative would be to set the unitIncrement
        // of the JScrollBar to a fixed value. You wouldn't get the nice
        // aligned scrolling, but it should work.
        SubJList(final T[] data) {
            super(data);
        }

        @Override
        public int getScrollableUnitIncrement(final Rectangle visibleRect,
                final int orientation, final int direction) {
            int row;
            if (orientation == SwingConstants.VERTICAL && direction < 0
                    && (row = this.getFirstVisibleIndex()) != -1) {
                final Rectangle r = this.getCellBounds(row, row);
                if (r.y == visibleRect.y && row != 0) {
                    final Point loc = r.getLocation();
                    loc.y--;
                    final int prevIndex = this.locationToIndex(loc);
                    final Rectangle prevR = this.getCellBounds(prevIndex,
                            prevIndex);
                    if (prevR == null || prevR.y >= r.y) {
                        return 0;
                    }
                    return prevR.height;
                }
            }
            return super.getScrollableUnitIncrement(visibleRect, orientation,
                    direction);
        }
    }
}
