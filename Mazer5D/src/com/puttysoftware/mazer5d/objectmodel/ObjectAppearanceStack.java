/*  Fantastle Reboot
 * A world-solving RPG
 * This code is licensed under the terms of the
 * GPLv3, or at your option, any later version.
 */
package com.puttysoftware.mazer5d.objectmodel;

import java.util.ArrayList;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.mazer5d.assets.ColorShader;
import com.puttysoftware.mazer5d.assets.ObjectImageIndex;

class ObjectAppearanceStack {
    // Fields
    private final String cacheName;
    private final ArrayList<ObjectAppearance> layers;

    public ObjectAppearanceStack(final String name,
            final ObjectAppearance... layerStack) {
        this.cacheName = name;
        this.layers = new ArrayList<>();
        for (ObjectAppearance oal : layerStack) {
            this.layers.add(oal);
        }
    }

    public ObjectAppearanceStack(final String name, final ObjectImageIndex oii) {
        this.cacheName = name;
        this.layers = new ArrayList<>();
        ObjectAppearance oal = new ObjectAppearance(name + "_0", oii);
        this.layers.add(oal);
    }

    public ObjectAppearanceStack(final String name, final ObjectImageIndex oii,
            final ColorShader shader) {
        this.cacheName = name;
        this.layers = new ArrayList<>();
        ObjectAppearance oal = new ObjectAppearance(name + "_0", oii,
                shader);
        this.layers.add(oal);
    }

    public final String getCacheName() {
        return this.cacheName;
    }

    public BufferedImageIcon getImage() {
        // FIXME: Stub
        return null;
    }
}
