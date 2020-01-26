/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.help;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import net.worldwizard.images.BufferedImageIcon;

public final class GraphicalHelpViewer {
    // Fields
    private BufferedImageIcon[] choices;
    private String[] choiceNames;
    private JLabel[] choiceArray;
    private final Container helpContainer;
    private final Container choiceContainer;
    private final JScrollPane scrollPane;

    // Constructor
    public GraphicalHelpViewer(final BufferedImageIcon[] pictures,
            final String[] descriptions) {
        this.helpContainer = new Container();
        this.helpContainer.setLayout(new BorderLayout());
        this.choiceContainer = new Container();
        this.scrollPane = new JScrollPane(this.choiceContainer);
        this.scrollPane.setHorizontalScrollBarPolicy(
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.helpContainer.add(this.scrollPane, BorderLayout.CENTER);
        this.updateHelp(pictures, descriptions);
    }

    // Methods
    public Container getHelp() {
        return this.helpContainer;
    }

    private void updateHelp(final BufferedImageIcon[] newImages,
            final String[] newNames) {
        this.choices = newImages;
        this.choiceNames = newNames;
        this.choiceContainer.removeAll();
        this.choiceContainer.setLayout(new GridLayout(this.choices.length, 1));
        this.choiceArray = new JLabel[this.choices.length];
        for (int x = 0; x < this.choices.length; x++) {
            this.choiceArray[x] = new JLabel(this.choiceNames[x],
                    this.choices[x], SwingConstants.LEFT);
            this.choiceContainer.add(this.choiceArray[x]);
        }
    }

    public void setHelpSize(final int horz, final int vert) {
        this.helpContainer.setPreferredSize(new Dimension(horz, vert));
        this.scrollPane.setPreferredSize(new Dimension(horz, vert));
    }
}
