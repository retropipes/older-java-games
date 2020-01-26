package com.puttysoftware.help;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.net.URL;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;

import org.xhtmlrenderer.simple.XHTMLPanel;

public final class XHTMLHelpViewer {
    // Fields
    private final URL helpDoc;
    private XHTMLPanel helpContents;
    private Container helpContainer;
    private JScrollPane scrollPane;
    private Exception helpException;

    // Constructor
    public XHTMLHelpViewer(final URL helpPage) {
        this.helpDoc = helpPage;
    }

    // Methods
    public Container getHelp() {
        if (this.helpContainer == null) {
            this.helpContainer = new Container();
            this.helpContainer.setLayout(new FlowLayout());
            try {
                this.helpContents = new XHTMLPanel();
                this.helpContents.setDocument(this.helpDoc.toExternalForm());
                this.scrollPane = new JScrollPane(this.helpContents);
            } catch (final Exception e) {
                this.helpException = e;
                final JEditorPane error = new JEditorPane("text/plain",
                        "An error occurred while fetching the help contents.");
                this.scrollPane = new JScrollPane(error);
            } finally {
                this.helpContainer.add(this.scrollPane);
            }
        }
        return this.helpContainer;
    }

    public void setHelpSize(final int horz, final int vert) {
        this.helpContents.setPreferredSize(new Dimension(horz, vert));
        this.scrollPane.setPreferredSize(new Dimension(horz, vert));
    }

    public Exception getHelpError() {
        return this.helpException;
    }
}
