/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.game.scripts;

class InternalScriptException extends RuntimeException {
    private static final long serialVersionUID = 14535L;

    InternalScriptException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
