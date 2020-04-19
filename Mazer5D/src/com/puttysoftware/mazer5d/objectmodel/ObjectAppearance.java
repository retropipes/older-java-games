/*  Fantastle Reboot
 * A world-solving RPG
 * This code is licensed under the terms of the
 * GPLv3, or at your option, any later version.
 */
package com.puttysoftware.mazer5d.objectmodel;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.assets.ColorShader;
import com.puttysoftware.mazer5d.assets.ImageShader;
import com.puttysoftware.mazer5d.assets.ObjectImageIndex;
import com.puttysoftware.mazer5d.loaders.ObjectImageLoader;

class ObjectAppearance extends Appearance {
    public ObjectAppearance(final String name,
            final ObjectImageIndex inImageIndex) {
        super(name, inImageIndex);
    }

    public ObjectAppearance(final String name,
            final ObjectImageIndex inImageIndex, final ColorShader inShader) {
        super(name, inImageIndex, inShader);
    }

    private ObjectImageIndex getWhichObjectImage() {
        return (ObjectImageIndex) super.getWhichImage();
    }

    @Override
    public BufferedImageIcon getImage() {
        BufferedImageIcon image = ObjectImageLoader.load(this
                .getWhichObjectImage());
        if (this.hasShading()) {
            image = ImageShader.shade(this.getCacheName(), image, this
                    .getShading());
        }
        return image;
    }
}
