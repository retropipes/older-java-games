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
import com.puttysoftware.mazer5d.assets.BossImageIndex;

public class BossImageLoader {
    private static String[] allFilenames;
    private static Properties fileExtensions;

    public static BufferedImageIcon load() {
        final String imageExt = BossImageLoader.fileExtensions
                .getProperty("images");
        final BossImageIndex image = BossImageIndex.BOSS;
        final String name = "/assets/images/boss/"
                + BossImageLoader.allFilenames[image.ordinal()] + imageExt;
        return ImageLoader.load(name, BossImageLoader.class.getResource(name));
    }

    public static void cacheAll() {
        BossImageLoader.allFilenames = DataLoader.loadBossImageData();
        try (final InputStream stream = BossImageLoader.class
                .getResourceAsStream(
                        "/assets/data/extensions/extensions.properties")) {
            BossImageLoader.fileExtensions = new Properties();
            BossImageLoader.fileExtensions.load(stream);
        } catch (final IOException e) {
            Mazer5D.logError(e);
        }
        final String imageExt = BossImageLoader.fileExtensions
                .getProperty("images");
        final BossImageIndex image = BossImageIndex.BOSS;
        final String name = "/assets/images/boss/"
                + BossImageLoader.allFilenames[image.ordinal()] + imageExt;
        ImageLoader.load(name, BossImageLoader.class.getResource(name));
    }
}
