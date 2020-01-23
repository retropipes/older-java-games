package net.worldwizard.dungeondiver2.game.scripts;

public class GameScriptException extends RuntimeException {
    private static final long serialVersionUID = 14535L;

    public GameScriptException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
