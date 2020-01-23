/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map;

public class InvalidMapException extends Exception {
    // Serialization
    private static final long serialVersionUID = 999L;

    // Constructors
    public InvalidMapException() {
        super();
    }

    public InvalidMapException(final String msg) {
        super(msg);
    }
}
