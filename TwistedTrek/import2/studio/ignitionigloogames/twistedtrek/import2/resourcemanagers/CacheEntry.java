/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.resourcemanagers;

import studio.ignitionigloogames.images.BufferedImageIcon;

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