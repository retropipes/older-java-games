/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.resourcemanagers;

import com.puttysoftware.images.BufferedImageIcon;

public class ImageConstants {
    private static final int IMAGE_SIZE = 48;

    private ImageConstants() {
        // Do nothing
    }

    public static int getImageSize() {
        return ImageConstants.IMAGE_SIZE;
    }

    public static int getScaledImageSize() {
        return BufferedImageIcon.getScaledValue(ImageConstants.IMAGE_SIZE);
    }
}
