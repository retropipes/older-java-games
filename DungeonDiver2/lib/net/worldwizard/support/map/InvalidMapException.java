/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map;

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
