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

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

public final class HTMLHelpViewer {
    // Fields
    private JEditorPane helpContents;
    private final Container helpContainer;
    private final JScrollPane scrollPane;

    // Constructor
    public HTMLHelpViewer(final URL helpPage) {
        this.helpContainer = new Container();
        this.helpContainer.setLayout(new FlowLayout());
        try {
            this.helpContents = new JEditorPane(helpPage);
        } catch (final Exception e) {
            this.helpContents = new JEditorPane("text/plain",
                    "An error occurred while fetching the help contents.");
        }
        this.helpContents.setEditable(false);
        this.scrollPane = new JScrollPane(this.helpContents);
        this.helpContainer.add(this.scrollPane);
    }

    // Methods
    public Container getHelp() {
        return this.helpContainer;
    }

    public void setHelpSize(final int horz, final int vert) {
        this.helpContents.setPreferredSize(new Dimension(horz, vert));
        this.scrollPane.setPreferredSize(new Dimension(horz, vert));
    }
}
