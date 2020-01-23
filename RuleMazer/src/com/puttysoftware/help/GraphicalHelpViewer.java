/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.help;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.CommonDialogs;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.maze.FilenameChecker;

public final class GraphicalHelpViewer {
    // Fields
    private BufferedImageIcon[] choices;
    private String[] choiceNames;
    private JLabel[] choiceArray;
    private final Container helpContainer;
    private final Container choiceContainer;
    private final JScrollPane scrollPane;
    private final Color fill;

    // Constructor
    public GraphicalHelpViewer(final BufferedImageIcon[] pictures,
            final String[] descriptions, final Color fillColor) {
        this.helpContainer = new Container();
        this.helpContainer.setLayout(new BorderLayout());
        this.choiceContainer = new Container();
        this.scrollPane = new JScrollPane(this.choiceContainer);
        this.scrollPane
                .setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane
                .setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.helpContainer.add(this.scrollPane, BorderLayout.CENTER);
        this.fill = fillColor;
        this.helpContainer.setBackground(fillColor);
        this.choiceContainer.setBackground(fillColor);
        this.scrollPane.setBackground(fillColor);
        this.updateHelp(pictures, descriptions);
    }

    // Methods
    public Container getHelp() {
        return this.helpContainer;
    }

    public void updateHelp(final BufferedImageIcon[] newImages,
            final String[] newNames) {
        this.choices = newImages;
        this.choiceNames = newNames;
        this.choiceContainer.removeAll();
        this.choiceContainer.setLayout(new GridLayout(this.choices.length, 1));
        this.choiceArray = new JLabel[this.choices.length];
        for (int x = 0; x < this.choices.length; x++) {
            this.choiceArray[x] = new JLabel(this.choiceNames[x],
                    this.choices[x], SwingConstants.LEFT);
            this.choiceArray[x].setOpaque(true);
            this.choiceArray[x].setBackground(this.fill);
            this.choiceContainer.add(this.choiceArray[x]);
        }
    }

    public void exportHelp() {
        final Application app = Main.getApplication();
        String filename = "";
        String fileOnly = "\\";
        String extension;
        final JFileChooser fc = new JFileChooser();
        final PNGImageFilter pif = new PNGImageFilter();
        fc.setAcceptAllFileFilterUsed(false);
        fc.addChoosableFileFilter(pif);
        fc.setFileFilter(pif);
        while (!FilenameChecker.isFilenameOK(fileOnly)) {
            final int returnVal = fc.showSaveDialog(app.getOutputFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fc.getSelectedFile();
                extension = GraphicalHelpViewer.getExtension(file);
                filename = file.getAbsolutePath();
                final String dirOnly = fc.getCurrentDirectory()
                        .getAbsolutePath();
                fileOnly = filename.substring(dirOnly.length() + 1);
                if (!FilenameChecker.isFilenameOK(fileOnly)) {
                    CommonDialogs
                            .showErrorDialog(
                                    "The file name you entered contains illegal characters.\n"
                                            + "These characters are not allowed: /?<>\\:|\"\n"
                                            + "Files named con, nul, or prn are illegal, as are files\n"
                                            + "named com1 through com9 and lpt1 through lpt9.",
                                    "Save");
                } else {
                    if (extension != null) {
                        if (!extension.equals("png")) {
                            filename = GraphicalHelpViewer
                                    .getNameWithoutExtension(file) + ".png";
                        }
                    } else {
                        filename += ".png";
                    }
                    final Container c = this.choiceContainer;
                    final Dimension d = c.getPreferredSize();
                    final BufferedImage bi = new BufferedImage(d.width,
                            d.height, BufferedImage.TYPE_INT_ARGB);
                    for (int x = 0; x < d.width; x++) {
                        for (int y = 0; y < d.height; y++) {
                            bi.setRGB(x, y, this.fill.getRGB());
                        }
                    }
                    c.paintComponents(bi.createGraphics());
                    try {
                        ImageIO.write(bi, "PNG", new File(filename));
                        CommonDialogs.showDialog("Export Successful!");
                    } catch (final IOException io) {
                        CommonDialogs.showDialog("Export Failed!");
                    }
                }
            } else {
                break;
            }
        }
    }

    public void setHelpSize(final int horz, final int vert) {
        this.helpContainer.setPreferredSize(new Dimension(horz, vert));
        this.scrollPane.setPreferredSize(new Dimension(horz, vert));
    }

    private static String getExtension(final File f) {
        String ext = null;
        final String s = f.getName();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    private static String getNameWithoutExtension(final File f) {
        String ext = null;
        final String s = f.getAbsolutePath();
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }
}
