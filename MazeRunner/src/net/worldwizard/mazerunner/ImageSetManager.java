package net.worldwizard.mazerunner;

import javax.swing.ImageIcon;

public class ImageSetManager {
    public static ImageIcon getImage(final String set, final int size,
            final String filename) {
        final ImageIcon icon = new ImageIcon("./ImageSets" + "/" + set + "/"
                + filename + ".image/image" + String.valueOf(size) + ".png");
        return icon;
    }

    public static ImageIcon getLogo() {
        final ImageIcon icon = new ImageIcon("./ImageSets/Logo.png");
        return icon;
    }

    public static String getDefaultSet() {
        return "Modern";
    }

    public static int getDefaultSize() {
        return 32;
    }
}