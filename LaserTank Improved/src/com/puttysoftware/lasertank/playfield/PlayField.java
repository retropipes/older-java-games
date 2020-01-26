package com.puttysoftware.lasertank.playfield;

import com.puttysoftware.storage.NumberStorage;

public final class PlayField extends NumberStorage {
    // Constants
    private static final int SIZE = 24;

    // Constructor
    public PlayField() {
        super(PlayField.SIZE, PlayField.SIZE);
    }

    // Copy constructor
    public PlayField(final PlayField inSource) {
        super(inSource);
    }
}
