/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2020 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.assets;

import java.awt.Color;
import java.util.Objects;

import com.puttysoftware.images.BufferedImageIcon;

final class ColorReplaceRule {
    // Fields
    private final Color findColor;
    private final Color replaceColor;

    // Constructor
    public ColorReplaceRule(final Color find, final Color replace) {
        this.findColor = find;
        this.replaceColor = replace;
    }

    // Methods
    public BufferedImageIcon apply(final BufferedImageIcon input) {
        if (input == null) {
            throw new IllegalArgumentException("input == NULL!");
        }
        final int width = input.getWidth();
        final int height = input.getHeight();
        final BufferedImageIcon result = new BufferedImageIcon(input);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final Color c = new Color(input.getRGB(x, y), true);
                if (c.equals(this.findColor)) {
                    result.setRGB(x, y, this.replaceColor.getRGB());
                }
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.findColor, this.replaceColor);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorReplaceRule)) {
            return false;
        }
        final ColorReplaceRule other = (ColorReplaceRule) obj;
        return Objects.equals(this.findColor, other.findColor) && Objects
                .equals(this.replaceColor, other.replaceColor);
    }
}
