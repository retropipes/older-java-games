package com.puttysoftware.weaselweb.maze.generic;

import java.awt.Color;

public final class TemplateTransform {
    // Fields
    private final double mRed, mGreen, mBlue;
    public static final TemplateTransform DEFAULT = new TemplateTransform(1.0,
            1.0, 1.0);

    // Constructor
    public TemplateTransform(final double rm, final double gm, final double bm) {
        if (rm < 0.0 || rm > 1.0) {
            throw new IllegalArgumentException("Red must be 0.0 to 1.0!");
        }
        if (gm < 0.0 || gm > 1.0) {
            throw new IllegalArgumentException("Green must be 0.0 to 1.0!");
        }
        if (bm < 0.0 || bm > 1.0) {
            throw new IllegalArgumentException("Blue must be 0.0 to 1.0!");
        }
        this.mRed = rm;
        this.mGreen = gm;
        this.mBlue = bm;
    }

    public TemplateTransform(final Color base) {
        final int r = base.getRed();
        final int g = base.getGreen();
        final int b = base.getBlue();
        if (r == 0) {
            this.mRed = 0;
        } else {
            this.mRed = r / 256.0 + 1.0 / 256.0;
        }
        if (r == 0) {
            this.mGreen = 0;
        } else {
            this.mGreen = g / 256.0 + 1.0 / 256.0;
        }
        if (r == 0) {
            this.mBlue = 0;
        } else {
            this.mBlue = b / 256.0 + 1.0 / 256.0;
        }
    }

    public final double getRed() {
        return this.mRed;
    }

    public final double getGreen() {
        return this.mGreen;
    }

    public final double getBlue() {
        return this.mBlue;
    }

    public final Color applyTransform(final Color input) {
        final int r = input.getRed();
        final int g = input.getGreen();
        final int b = input.getBlue();
        final int tr = (int) Math.round(r * this.mRed);
        final int tg = (int) Math.round(g * this.mGreen);
        final int tb = (int) Math.round(b * this.mBlue);
        return new Color(tr, tg, tb);
    }
}
