/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz;

public class PreferencesException extends Exception {
    private static final long serialVersionUID = 2935395223L;

    /**
     * Creates a new instance of <code>PreferencesException</code> without
     * detail message.
     */
    public PreferencesException() {
    }

    /**
     * Constructs an instance of <code>PreferencesException</code> with the
     * specified detail message.
     *
     * @param msg
     *            the detail message.
     */
    public PreferencesException(final String msg) {
        super(msg);
    }
}
