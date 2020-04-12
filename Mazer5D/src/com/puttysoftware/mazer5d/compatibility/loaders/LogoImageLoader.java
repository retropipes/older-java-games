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
package com.puttysoftware.mazer5d.compatibility.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.LogoImageIndex;
import com.puttysoftware.mazer5d.loaders.DataLoader;
import com.puttysoftware.mazer5d.loaders.ImageLoader;

public class LogoImageLoader {
    private static String[] allFilenames;
    private static Properties fileExtensions;
    private static final int MAX_INDEX = 3;

    public static void preInit() {
        LogoImageLoader.allFilenames = DataLoader.loadLogoImageData();
        try (final InputStream stream = LogoImageLoader.class
                .getResourceAsStream(
                        "/assets/data/extension/extension.properties")) {
            LogoImageLoader.fileExtensions = new Properties();
            LogoImageLoader.fileExtensions.load(stream);
        } catch (final IOException e) {
            Mazer5D.logError(e);
        }
    }

    public static BufferedImageIcon load(final LogoImageIndex image) {
        final String imageExt = LogoImageLoader.fileExtensions
                .getProperty("images");
        final String name = "/assets/image/logo/"
                + LogoImageLoader.allFilenames[image.ordinal()] + imageExt;
        return ImageLoader.load(name, LogoImageLoader.class.getResource(name));
    }

    public static void cacheAll() {
        final String imageExt = LogoImageLoader.fileExtensions
                .getProperty("images");
        for (int i = 1; i <= LogoImageLoader.MAX_INDEX; i++) {
            final String name = "/assets/image/logo/"
                    + LogoImageLoader.allFilenames[i] + imageExt;
            ImageLoader.load(name, LogoImageLoader.class.getResource(name));
        }
    }
}
