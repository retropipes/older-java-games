/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dialogs;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class ListWithImageDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    static BufferedImageIcon[] imgs;
    private static ListWithImageDialog dialog;
    private static String value = null;
    static JList<String> list;

    /**
     * Set up and show the dialog. The first Component argument determines which
     * frame the dialog depends on; it should be a component in the dialog's
     * controlling frame. The second Component argument should be null if you
     * want the dialog to come up with its left corner in the center of the
     * screen; otherwise, it should be the component on top of which the dialog
     * should appear.
     */
    public static String showDialog(final String labelText, final String title,
            final String initialValue, final String[] possibleValues,
            final BufferedImageIcon imgValue,
            final BufferedImageIcon... possibleImages) {
        ListWithImageDialog.value = null;
        ListWithImageDialog.dialog = new ListWithImageDialog(labelText, title,
                initialValue, possibleValues, imgValue, possibleImages);
        ListWithImageDialog.dialog.setVisible(true);
        return ListWithImageDialog.value;
    }

    private static void setValue(final String newValue) {
        ListWithImageDialog.value = newValue;
        ListWithImageDialog.list.setSelectedValue(ListWithImageDialog.value,
                true);
    }

    private ListWithImageDialog(final String labelText, final String title,
            final String initialValue, final String[] possibleValues,
            final BufferedImageIcon imgValue,
            final BufferedImageIcon... possibleImages) {
        super((Frame) null, title, true);
        // Initialize the images
        ListWithImageDialog.imgs = possibleImages;
        // Create and initialize the buttons.
        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        //
        final JButton setButton = new JButton("OK");
        setButton.setActionCommand("OK");
        setButton.addActionListener(this);
        this.getRootPane().setDefaultButton(setButton);
        // Create a label to hold the image
        final JPanel imgPane = new JPanel();
        final JLabel imgLabel = new JLabel(imgValue);
        imgPane.add(imgLabel);
        // main part of the dialog
        ListWithImageDialog.list = new SubJList<>(possibleValues);
        ListWithImageDialog.list
                .setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        ListWithImageDialog.list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        ListWithImageDialog.list.setVisibleRowCount(-1);
        ListWithImageDialog.list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    setButton.doClick(); // emulate button click
                }
            }
        });
        ListWithImageDialog.list.addListSelectionListener(e -> imgLabel
                .setIcon(ListWithImageDialog.imgs[ListWithImageDialog.list
                        .getSelectedIndex()]));
        final JScrollPane listScroller = new JScrollPane(
                ListWithImageDialog.list);
        listScroller.setPreferredSize(new Dimension(250, 80));
        listScroller.setAlignmentX(Component.LEFT_ALIGNMENT);
        // Create a container so that we can add a title around
        // the scroll pane. Can't add a title directly to the
        // scroll pane because its background would be white.
        // Lay out the label and scroll pane from top to bottom.
        final JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
        final JLabel label = new JLabel(labelText);
        label.setLabelFor(ListWithImageDialog.list);
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
        final Container contentPane = this.getContentPane();
        contentPane.add(listPane, BorderLayout.CENTER);
        contentPane.add(imgPane, BorderLayout.WEST);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);
        // Initialize values.
        ListWithImageDialog.setValue(initialValue);
        this.pack();
    }

    // Handle clicks on the Set and Cancel buttons.
    @Override
    public void actionPerformed(final ActionEvent e) {
        if ("OK".equals(e.getActionCommand())) {
            ListWithImageDialog
                    .setValue(ListWithImageDialog.list.getSelectedValue());
        } else if ("Cancel".equals(e.getActionCommand())) {
            ListWithImageDialog.setValue(null);
        }
        ListWithImageDialog.dialog.setVisible(false);
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
