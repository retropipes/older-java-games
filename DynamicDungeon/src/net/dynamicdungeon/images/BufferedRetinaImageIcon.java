package net.dynamicdungeon.images;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

public class BufferedRetinaImageIcon extends BufferedImageIcon {
    // Constructors
    public BufferedRetinaImageIcon(final int w, final int h) {
        super(w, h);
    }

    /**
     * Creates a BufferedRetinaImageIcon based on an Image object.
     *
     * @param i
     */
    public BufferedRetinaImageIcon(final Image i) {
        super(i);
    }

    /**
     * Paints the BufferedRetinaImageIcon, using the given Graphics, on the
     * given Component at the given x, y location.
     *
     * @param c
     * @param g
     * @param x
     * @param y
     */
    @Override
    public void paintIcon(final Component c, final Graphics g, final int x,
            final int y) {
        if (g != null) {
            final Image image = this;
            final int width = image.getWidth(c);
            final int height = image.getHeight(c);
            final Graphics2D g2d = (Graphics2D) g.create(x, y, width, height);
            g2d.scale(0.5, 0.5);
            g2d.drawImage(image, 0, 0, c);
            g2d.scale(1, 1);
            g2d.dispose();
        }
    }

    /**
     * @return the width of this BufferedRetinaImageIcon, in pixels
     */
    @Override
    public int getIconWidth() {
        return this.getWidth() / 2;
    }

    /**
     * @return the height of this BufferedRetinaImageIcon, in pixels
     */
    @Override
    public int getIconHeight() {
        return this.getHeight() / 2;
    }
}