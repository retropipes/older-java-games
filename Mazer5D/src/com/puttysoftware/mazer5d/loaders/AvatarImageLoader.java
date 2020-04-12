/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2020 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.loaders;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.ColorReplaceRules;

public class AvatarImageLoader {
    private static Properties fileExtensions;
    private static final int MAX_FAMILY_INDEX = 7;

    public static BufferedImageIcon load(final int familyID,
            final ColorReplaceRules rules) {
        if (AvatarImageLoader.fileExtensions == null) {
            AvatarImageLoader.fileExtensions = new Properties();
            try (final InputStream stream = AvatarImageLoader.class
                    .getResourceAsStream(
                            "/assets/data/extension/extension.properties")) {
                AvatarImageLoader.fileExtensions.load(stream);
            } catch (final IOException e) {
                Mazer5D.logError(e);
            }
        }
        final String imageExt = AvatarImageLoader.fileExtensions
                .getProperty("images");
        final String name = "/assets/image/avatars/"
                + Integer.toString(familyID) + imageExt;
        return rules.applyAll(ImageLoader.load(name,
                AvatarImageLoader.class.getResource(name)));
    }

    public static void cacheAll() {
        AvatarImageLoader.fileExtensions = new Properties();
        try (final InputStream stream = AvatarImageLoader.class
                .getResourceAsStream(
                        "/assets/data/extension/extension.properties")) {
            AvatarImageLoader.fileExtensions.load(stream);
        } catch (final IOException e) {
            Mazer5D.logError(e);
        }
        final String imageExt = AvatarImageLoader.fileExtensions
                .getProperty("images");
        for (int familyID = 0; familyID <= AvatarImageLoader.MAX_FAMILY_INDEX; familyID++) {
            final String name = "/assets/image/avatar/"
                    + Integer.toString(familyID) + imageExt;
            ImageLoader.load(name, AvatarImageLoader.class.getResource(name));
        }
    }
}
