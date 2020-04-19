/*  Fantastle: A World-Solving Game
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

Any questions should be directed to the author via email at: fantastle@worldwizard.net
 */
package com.puttysoftware.mazer5d.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.EffectImageIndex;

public class EffectImageLoader {
    private static String[] allFilenames;
    private static Properties fileExtensions;
    private static final int MAX_INDEX = 46;

    public static BufferedImageIcon load(final EffectImageIndex image) {
        if (image != EffectImageIndex._NONE) {
            final String imageExt = EffectImageLoader.fileExtensions
                    .getProperty("images");
            final String name = "/assets/image/effects/"
                    + EffectImageLoader.allFilenames[image.ordinal()]
                    + imageExt;
            return ImageLoader.load(name, EffectImageLoader.class.getResource(
                    name));
        }
        return null;
    }

    public static void cacheAll() {
        EffectImageLoader.allFilenames = DataLoader.loadEffectImageData();
        try (final InputStream stream = EffectImageLoader.class
                .getResourceAsStream(
                        "/assets/data/extension/extension.properties")) {
            EffectImageLoader.fileExtensions = new Properties();
            EffectImageLoader.fileExtensions.load(stream);
        } catch (final IOException e) {
            Mazer5D.logError(e);
        }
        final String imageExt = EffectImageLoader.fileExtensions.getProperty(
                "images");
        for (int i = 0; i <= EffectImageLoader.MAX_INDEX; i++) {
            final String name = "/assets/image/effects/"
                    + EffectImageLoader.allFilenames[i] + imageExt;
            try {
                ImageLoader.load(name, EffectImageLoader.class.getResource(
                        name));
            } catch (final IllegalArgumentException iae) {
                // Ignore - image unused
            }
        }
    }
}
