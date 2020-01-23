package com.puttysoftware.riskyrescue.utilities;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.riskyrescue.assets.ImageManager;

public class PCImagePickerDialog extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    private final JLabel imageLabel = new JLabel();
    private static PCImagePickerDialog dialog;
    private static int clothingValue, skinValue, hairValue;
    private static boolean cancel = false;
    private final JComboBox<String> clothing, skin, hair;

    /**
     * Set up and show the dialog. The first Component argument determines which
     * frame the dialog depends on; it should be a component in the dialog's
     * controlling frame. The second Component argument should be null if you
     * want the dialog to come up with its left corner in the center of the
     * screen; otherwise, it should be the component on top of which the dialog
     * should appear.
     */
    public static PCImage showDialog(final Component frameComp,
            final String title) {
        final Frame frame = JOptionPane.getFrameForComponent(frameComp);
        PCImagePickerDialog.dialog = new PCImagePickerDialog(frame, title);
        PCImagePickerDialog.dialog.setVisible(true);
        if (PCImagePickerDialog.cancel) {
            return null;
        }
        return new PCImage(PCImagePickerDialog.clothingValue,
                PCImagePickerDialog.skinValue, PCImagePickerDialog.hairValue);
    }

    private PCImagePickerDialog(final Frame frame, final String title) {
        super(frame, title, true);
        // Initialize the combo boxes
        final String[] cNames = PCImage.getClothingNames();
        this.clothing = new JComboBox<>(new DefaultComboBoxModel<>(cNames));
        final int cStart = new RandomRange(0, cNames.length - 1).generate();
        this.clothing.setSelectedIndex(cStart);
        PCImagePickerDialog.clothingValue = cStart;
        this.clothing.addItemListener(il -> {
            final int c = this.clothing.getSelectedIndex();
            PCImagePickerDialog.clothingValue = c;
            this.imageLabel.setIcon(ImageManager.getPCPickerImage(
                    PCImage.getPCImageName(c, PCImagePickerDialog.skinValue,
                            PCImagePickerDialog.hairValue)));
        });
        final String[] sNames = PCImage.getSkinNames();
        this.skin = new JComboBox<>(new DefaultComboBoxModel<>(sNames));
        final int sStart = new RandomRange(0, sNames.length - 1).generate();
        this.skin.setSelectedIndex(sStart);
        PCImagePickerDialog.skinValue = sStart;
        this.skin.addItemListener(il -> {
            final int s = this.skin.getSelectedIndex();
            PCImagePickerDialog.skinValue = s;
            this.imageLabel.setIcon(ImageManager.getPCPickerImage(
                    PCImage.getPCImageName(PCImagePickerDialog.clothingValue, s,
                            PCImagePickerDialog.hairValue)));
        });
        final String[] hNames = PCImage.getHairNames();
        this.hair = new JComboBox<>(new DefaultComboBoxModel<>(hNames));
        final int hStart = new RandomRange(0, hNames.length - 1).generate();
        this.hair.setSelectedIndex(hStart);
        PCImagePickerDialog.hairValue = hStart;
        this.hair.addItemListener(il -> {
            final int h = this.hair.getSelectedIndex();
            PCImagePickerDialog.hairValue = h;
            this.imageLabel.setIcon(ImageManager.getPCPickerImage(
                    PCImage.getPCImageName(PCImagePickerDialog.clothingValue,
                            PCImagePickerDialog.skinValue, h)));
        });
        final BufferedImageIcon image = ImageManager
                .getPCPickerImage("" + cStart + sStart + hStart);
        // Create and initialize the buttons.
        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);
        final JButton setButton = new JButton("OK");
        setButton.setActionCommand("OK");
        setButton.addActionListener(this);
        this.getRootPane().setDefaultButton(setButton);
        // image preview thing
        final JPanel imagePane = new JPanel();
        imagePane.setLayout(new FlowLayout());
        this.imageLabel.setIcon(image);
        imagePane.add(this.imageLabel);
        // main part of the dialog
        final JPanel pickerPane = new JPanel();
        pickerPane.setLayout(new BoxLayout(pickerPane, BoxLayout.PAGE_AXIS));
        final JLabel clothingLabel = new JLabel("Clothing:");
        clothingLabel.setLabelFor(this.clothing);
        pickerPane.add(clothingLabel);
        pickerPane.add(this.clothing);
        pickerPane.add(Box.createRigidArea(new Dimension(0, 5)));
        final JLabel skinLabel = new JLabel("Skin:");
        skinLabel.setLabelFor(this.skin);
        pickerPane.add(skinLabel);
        pickerPane.add(this.skin);
        pickerPane.add(Box.createRigidArea(new Dimension(0, 5)));
        final JLabel hairLabel = new JLabel("Hair:");
        hairLabel.setLabelFor(this.hair);
        pickerPane.add(hairLabel);
        pickerPane.add(this.hair);
        pickerPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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
        contentPane.add(imagePane, BorderLayout.WEST);
        contentPane.add(pickerPane, BorderLayout.CENTER);
        contentPane.add(buttonPane, BorderLayout.PAGE_END);
        // Finalize layout
        this.pack();
    }

    // Handle clicks on the Set and Cancel buttons.
    @Override
    public void actionPerformed(final ActionEvent e) {
        if ("OK".equals(e.getActionCommand())) {
            PCImagePickerDialog.cancel = false;
        } else if ("Cancel".equals(e.getActionCommand())) {
            PCImagePickerDialog.cancel = true;
        }
        PCImagePickerDialog.dialog.setVisible(false);
    }
}
