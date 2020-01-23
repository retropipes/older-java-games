package net.worldwizard.dungeondiver.creatures;

import java.awt.Color;

public class Element {
    // Fields
    private static Color makeTrans;
    private static Color eye;
    private Color transformedEye;
    private final double transformRed;
    private final double transformGreen;
    private final double transformBlue;
    private final String name;

    // Constructor
    public Element(final double red, final double green, final double blue,
            final boolean darkEye, final String newName) {
        this.transformRed = red;
        this.transformGreen = green;
        this.transformBlue = blue;
        if (darkEye) {
            this.transformedEye = Color.BLACK;
        } else {
            this.transformedEye = Color.WHITE;
        }
        this.name = newName;
    }

    // Static methods
    public static void setTrans(final Color newTrans) {
        Element.makeTrans = newTrans;
    }

    public static void setEye(final Color newEye) {
        Element.eye = newEye;
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
        if (source.equals(Element.makeTrans)) {
            transformed = new Color(red, green, blue, 0);
        } else if (source.equals(Element.eye)) {
            transformed = this.transformedEye;
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
