package com.puttysoftware.mazer5d;

public final class Mazer5DException extends Exception {
    private static final long serialVersionUID = 1L;

    private Mazer5DException(Throwable inCause) {
        super(inCause);
    }

    public static Mazer5DException from(final Throwable cause) {
        return new Mazer5DException(cause);
    }
}
