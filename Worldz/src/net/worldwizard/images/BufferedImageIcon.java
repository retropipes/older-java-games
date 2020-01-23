// BufferedImageIcon.java
/*  Worldz: A World-Exploring Game
 Copyright (C) 2008-2010 Eric Ahnell

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.

 Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.images;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.Icon;

public class BufferedImageIcon extends BufferedImage implements Icon {
    // Fields
    private static final int DEFAULT_TYPE = BufferedImage.TYPE_INT_ARGB;

    // Constructors
    /**
     * Creates a BufferedImageIcon of a fixed size.
     *
     * @param width
     * @param height
     */
    public BufferedImageIcon(final int width, final int height) {
        super(width, height, BufferedImageIcon.DEFAULT_TYPE);
    }

    /**
     * Creates a BufferedImageIcon based on a BufferedImage object.
     *
     * @param bi
     */
    public BufferedImageIcon(final BufferedImage bi) {
        super(bi.getWidth(), bi.getHeight(), BufferedImageIcon.DEFAULT_TYPE);
        for (int x = 0; x < bi.getWidth(); x++) {
            for (int y = 0; y < bi.getHeight(); y++) {
                this.setRGB(x, y, bi.getRGB(x, y));
            }
        }
    }

    /**
     * Paints the BufferedImageIcon, using the given Graphics, on the given
     * Component at the given x, y location.
     *
     * @param c
     * @param g
     * @param x
     * @param y
     */
    @Override
    public void paintIcon(final Component c, final Graphics g, final int x,
            final int y) {
        g.drawImage(this, x, y, c);
    }

    /**
     * @return the width of this BufferedImageIcon, in pixels
     */
    @Override
    public int getIconWidth() {
        return this.getWidth();
    }

    /**
     * @return the height of this BufferedImageIcon, in pixels
     */
    @Override
    public int getIconHeight() {
        return this.getHeight();
    }
}