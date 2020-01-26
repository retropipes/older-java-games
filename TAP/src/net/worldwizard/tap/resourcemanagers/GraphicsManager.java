/*  TAP: A Text Adventure Parser
Copyright (C) 2010 Eric Ahnell

Any questions should be directed to the author via email at: tap@worldwizard.net
 */
package net.worldwizard.tap.resourcemanagers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import net.worldwizard.images.BufferedImageIcon;

public class GraphicsManager {
    public static BufferedImageIcon getMiniatureLogo() {
        try {
            final URL url = GraphicsManager.class.getResource(
                    "/net/worldwizard/tap/resources/graphics/logo/minilogo.png");
            final BufferedImage image = ImageIO.read(url);
            final BufferedImageIcon icon = new BufferedImageIcon(image);
            return icon;
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    public static BufferedImageIcon getMicroLogo() {
        try {
            final URL url = GraphicsManager.class.getResource(
                    "/net/worldwizard/tap/resources/graphics/logo/micrologo.png");
            final BufferedImage image = ImageIO.read(url);
            final BufferedImageIcon icon = new BufferedImageIcon(image);
            return icon;
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }

    public static Image getIconLogo() {
        try {
            final URL url = GraphicsManager.class.getResource(
                    "/net/worldwizard/tap/resources/graphics/logo/iconlogo.png");
            final BufferedImage image = ImageIO.read(url);
            return image;
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }
}
