/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.game.scripts;

class InternalScriptException extends RuntimeException {
    private static final long serialVersionUID = 14535L;

    InternalScriptException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
