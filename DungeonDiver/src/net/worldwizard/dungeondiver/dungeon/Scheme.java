package net.worldwizard.dungeondiver.dungeon;

import java.awt.Color;

public class Scheme {
    // Fields
    private final double transformRed;
    private final double transformGreen;
    private final double transformBlue;

    // Constructor
    public Scheme(final double red, final double green, final double blue) {
        this.transformRed = red;
        this.transformGreen = green;
        this.transformBlue = blue;
    }

    // Methods
    public Color applyTransform(final Color source) {
        final int red = source.getRed();
        final int green = source.getGreen();
        final int blue = source.getBlue();
        final int transformedRed = (int) (red * this.transformRed);
        final int transformedGreen = (int) (green * this.transformGreen);
        final int transformedBlue = (int) (blue * this.transformBlue);
        final Color transformed = new Color(transformedRed, transformedGreen,
                transformedBlue);
        return transformed;
    }
}
