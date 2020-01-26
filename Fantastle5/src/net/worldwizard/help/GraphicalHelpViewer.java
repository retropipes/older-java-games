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
