/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.generic;

import java.awt.Color;

import com.puttysoftware.gemma.support.creatures.monsters.Element;

public final class TemplateTransform {
    // Fields
    private static final Color makeTrans = new Color(200, 100, 100);
    private final double transformRed;
    private final double transformGreen;
    private final double transformBlue;

    // Constructors
    public TemplateTransform(final double tr, final double tg,
            final double tb) {
        this.transformRed = tr;
        this.transformGreen = tg;
        this.transformBlue = tb;
    }

    TemplateTransform(final Color src) {
        final double tr = (src.getRed() + 1) / 256.0;
        final double tg = (src.getGreen() + 1) / 256.0;
        final double tb = (src.getBlue() + 1) / 256.0;
        this.transformRed = tr;
        this.transformGreen = tg;
        this.transformBlue = tb;
    }

    public TemplateTransform(final Element e) {
        this.transformRed = e.getTransformRed();
        this.transformGreen = e.getTransformGreen();
        this.transformBlue = e.getTransformBlue();
    }

    // Methods
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(this.transformBlue);
        result = prime * result + (int) (temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.transformGreen);
        result = prime * result + (int) (temp ^ temp >>> 32);
        temp = Double.doubleToLongBits(this.transformRed);
        return prime * result + (int) (temp ^ temp >>> 32);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof TemplateTransform)) {
            return false;
        }
        final TemplateTransform other = (TemplateTransform) obj;
        if (Double.doubleToLongBits(this.transformBlue) != Double
                .doubleToLongBits(other.transformBlue)) {
            return false;
        }
        if (Double.doubleToLongBits(this.transformGreen) != Double
                .doubleToLongBits(other.transformGreen)) {
            return false;
        }
        if (Double.doubleToLongBits(this.transformRed) != Double
                .doubleToLongBits(other.transformRed)) {
            return false;
        }
        return true;
    }
}
