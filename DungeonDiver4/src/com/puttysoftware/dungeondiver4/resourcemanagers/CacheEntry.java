/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.resourcemanagers;

import com.puttysoftware.images.BufferedImageIcon;

final class CacheEntry {
    // Fields
    private final BufferedImageIcon image;
    private final String name;

    // Constructor
    CacheEntry(final BufferedImageIcon newImage, final String newName) {
        this.image = newImage;
        this.name = newName;
    }

    // Methods
    BufferedImageIcon getImage() {
        return this.image;
    }

    String getName() {
        return this.name;
    }
}