package com.puttysoftware.help;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

public final class HTMLHelpViewer {
    // Fields
    private JEditorPane helpContents;
    private Container helpContainer;
    private JScrollPane scrollPane;

    // Constructor
    public HTMLHelpViewer(URL helpPage) {
        this.helpContainer = new Container();
        this.helpContainer.setLayout(new FlowLayout());
        try {
            this.helpContents = new JEditorPane(helpPage);
        } catch (Exception e) {
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

    public void setHelpSize(int horz, int vert) {
        this.helpContents.setPreferredSize(new Dimension(horz, vert));
        this.scrollPane.setPreferredSize(new Dimension(horz, vert));
    }
}
