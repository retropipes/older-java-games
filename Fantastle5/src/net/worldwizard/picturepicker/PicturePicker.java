/*  Fantastle: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Any questions should be directed to the author via email at: fantastle@worldwizard.net
 */
package net.worldwizard.picturepicker;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import net.worldwizard.images.BufferedImageIcon;

public final class PicturePicker {
    /**
     * @version 1.10
     */
    // Fields
    private BufferedImageIcon[] choices;
    private String[] choiceNames;
    private JLabel[] choiceArray;
    private final Container pickerContainer;
    private final Container choiceContainer;
    private final Container radioContainer;
    private final Container choiceRadioContainer;
    private final ButtonGroup radioGroup;
    private JRadioButton[] radioButtons;
    private final JScrollPane scrollPane;
    int index;
    private final EventHandler handler;

    // Constructor
    public PicturePicker(final BufferedImageIcon[] pictures,
            final String[] names) {
        this.handler = new EventHandler();
        this.pickerContainer = new Container();
        this.pickerContainer.setLayout(new BorderLayout());
        this.choiceContainer = new Container();
        this.radioContainer = new Container();
        this.radioGroup = new ButtonGroup();
        this.choiceRadioContainer = new Container();
        this.choiceRadioContainer.setLayout(new BorderLayout());
        this.choiceRadioContainer.add(this.radioContainer, BorderLayout.WEST);
        this.choiceRadioContainer.add(this.choiceContainer,
                BorderLayout.CENTER);
        this.scrollPane = new JScrollPane(this.choiceRadioContainer);
        this.scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.pickerContainer.add(this.scrollPane, BorderLayout.CENTER);
        this.updatePicker(pictures, names);
        this.index = 0;
    }

    // Methods
    public Container getPicker() {
        return this.pickerContainer;
    }

    public void updatePicker(final BufferedImageIcon[] newImages,
            final String[] newNames) {
        this.choices = newImages;
        this.choiceNames = newNames;
        this.choiceContainer.removeAll();
        this.radioContainer.removeAll();
        this.radioButtons = new JRadioButton[this.choices.length];
        this.choiceContainer.setLayout(new GridLayout(this.choices.length, 1));
        this.radioContainer.setLayout(new GridLayout(this.choices.length, 1));
        this.choiceArray = new JLabel[this.choices.length];
        for (int x = 0; x < this.choices.length; x++) {
            this.choiceArray[x] = new JLabel(this.choiceNames[x],
                    this.choices[x], SwingConstants.LEFT);
            this.choiceContainer.add(this.choiceArray[x]);
            this.radioButtons[x] = new JRadioButton();
            this.radioButtons[x].setActionCommand(Integer.valueOf(x).toString());
            this.radioGroup.add(this.radioButtons[x]);
            this.radioButtons[x].addActionListener(this.handler);
            this.radioContainer.add(this.radioButtons[x]);
        }
        this.radioButtons[0].setSelected(true);
        final int newPreferredWidth = this.pickerContainer.getLayout()
                .preferredLayoutSize(this.pickerContainer).width
                + this.scrollPane.getVerticalScrollBar().getWidth();
        this.pickerContainer.setPreferredSize(new Dimension(newPreferredWidth,
                this.pickerContainer.getPreferredSize().height));
        this.pickerContainer
                .setMinimumSize(this.pickerContainer.getPreferredSize());
    }

    /**
     *
     * @return the index of the picture picked
     */
    public int getPicked() {
        return this.index;
    }

    public void setDefaultSelection(final int newDefault) {
        this.index = newDefault;
        this.radioButtons[newDefault].setSelected(true);
    }

    private class EventHandler implements ActionListener {
        public EventHandler() {
            // TODO Auto-generated constructor stub
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            final String cmd = e.getActionCommand();
            // A radio button
            PicturePicker.this.index = Integer.parseInt(cmd);
        }
    }
}
