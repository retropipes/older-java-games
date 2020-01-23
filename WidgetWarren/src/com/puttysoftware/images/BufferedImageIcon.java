package com.puttysoftware.images;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.Icon;

/**
 * The BufferedImageIcon class adds scaling features to BufferedImage, and
 * implements the Icon interface for Swing use.
 */
public class BufferedImageIcon extends BufferedImage implements Icon {
    // Fields
    private static final int DEFAULT_TYPE = BufferedImage.TYPE_INT_ARGB;
    private static final int SCALE_MIN = 100;
    private static final double SCALE_MULT = 100.0;
    /**
     * The global scale factor (default: 100 = 100% scaling)
     */
    protected static int SCALE = 100;

    // Constructors
    /**
     * Creates a blank BufferedImageIcon.
     * 
     * @param w
     *            the pixel width of the new object
     * @param h
     *            the pixel height of the new object
     */
    public BufferedImageIcon(final int w, final int h) {
        super(w, h, BufferedImageIcon.DEFAULT_TYPE);
    }

    /**
     * Creates a square BufferedImageIcon of a given color.
     * 
     * @param d
     *            the pixel size of the new object
     * @param c
     *            the Color to fill with
     */
    public BufferedImageIcon(final int d, final Color c) {
        super(d, d, BufferedImageIcon.DEFAULT_TYPE);
        Graphics g = this.getGraphics();
        g.setColor(c);
        g.fillRect(0, 0, d, d);
    }

    /**
     * Creates a BufferedImageIcon based on an Image object.
     * 
     * @param i
     *            the Image to use as a template
     */
    public BufferedImageIcon(final Image i) {
        super(i.getWidth(null), i.getHeight(null),
                BufferedImageIcon.DEFAULT_TYPE);
        this.getGraphics().drawImage(i, 0, 0, null);
    }

    /**
     * Convenience method for scaling fixed values.
     * 
     * @param value
     *            the input
     * @return the output
     */
    public static int getScaledValue(final int value) {
        return (int) (value * SCALE / SCALE_MULT);
    }

    /**
     * Convenience method for determining the normalized scale.
     * 
     * @return the normalized scale value
     */
    public static double getNormalizedScale() {
        return SCALE / SCALE_MULT;
    }

    /**
     * Convenience method for getting the scaling multiplier.
     * 
     * @return the scaling multiplier
     */
    public static int getScaleMult() {
        return (int) SCALE_MULT;
    }

    /**
     * Gets the global scaling factor for image drawing.
     * 
     * @return the global scaling factor
     */
    public static int getScale() {
        return SCALE;
    }

    /**
     * Sets the global scaling factor for image drawing. A value of 100 means
     * 100% scaling (smallest allowed value). A value of 200 means 200% scaling
     * (Apple Retina mode).
     * 
     * @param value
     *            the new global scaling factor
     * @throws IllegalArgumentException
     *             if the global scaling factor isn't valid
     */
    public static void setScale(final int value) {
        if (value < SCALE_MIN) {
            throw new IllegalArgumentException(Integer.toString(value));
        }
        SCALE = value;
    }

    /**
     * Paints the BufferedImageIcon, using the given Graphics, on the given
     * Component at the given x, y location, using the scale factor.
     * 
     * @param c
     *            the Component to paint on
     * @param g
     *            the Graphics to paint with
     * @param x
     *            the horizontal (X) coordinate to start drawing
     * @param y
     *            the vertical (Y) coordinate to start drawing
     */
    @Override
    public void paintIcon(final Component c, final Graphics g, final int x,
            final int y) {
        if (SCALE > SCALE_MIN) {
            if (g != null) {
                final double factor = SCALE_MULT / SCALE;
                final int width = this.getWidth(c);
                final int height = this.getHeight(c);
                final Graphics2D g2d = (Graphics2D) g.create(x, y, width,
                        height);
                g2d.scale(factor, factor);
                g2d.drawImage(this, 0, 0, c);
                g2d.scale(1, 1);
                g2d.dispose();
            }
        } else {
            if (g != null) {
                g.drawImage(this, x, y, c);
            }
        }
    }

    /**
     * Gets the pixel width of this BufferedImageIcon, adjusted for the scale
     * factor.
     * 
     * @return the adjusted width of this BufferedImageIcon, in pixels
     */
    @Override
    public int getIconWidth() {
        return ((int) SCALE_MULT) * this.getWidth() / SCALE;
    }

    /**
     * Gets the pixel height of this BufferedImageIcon, adjusted for the scale
     * factor.
     * 
     * @return the adjusted height of this BufferedImageIcon, in pixels
     */
    @Override
    public int getIconHeight() {
        return ((int) SCALE_MULT) * this.getHeight() / SCALE;
    }
}