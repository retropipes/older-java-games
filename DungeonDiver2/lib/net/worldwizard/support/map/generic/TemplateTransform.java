package net.worldwizard.support.map.generic;

import java.awt.Color;

public final class TemplateTransform {
    // Fields
    private static final Color makeTrans = new Color(200, 100, 100);
    private final double transformRed;
    private final double transformGreen;
    private final double transformBlue;
    private final String name;

    // Constructor
    public TemplateTransform(final double tr, final double tg, final double tb,
            final String namePrefix) {
        this.transformRed = tr;
        this.transformGreen = tg;
        this.transformBlue = tb;
        this.name = namePrefix;
    }

    // Methods
    public String getName() {
        return this.name;
    }

    public Color applyTransform(final Color source) {
        final int red = source.getRed();
        final int green = source.getGreen();
        final int blue = source.getBlue();
        Color transformed = null;
        if (source.equals(TemplateTransform.makeTrans)) {
            transformed = new Color(red, green, blue, 0);
        } else {
            final int transformedRed = (int) (red * this.transformRed);
            final int transformedGreen = (int) (green * this.transformGreen);
            final int transformedBlue = (int) (blue * this.transformBlue);
            transformed = new Color(transformedRed, transformedGreen,
                    transformedBlue);
        }
        return transformed;
    }
}
