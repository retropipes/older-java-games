/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1;

public class PreferencesException extends Exception {
    private static final long serialVersionUID = 2935395223L;

    /**
     * Creates a new instance of <code>PreferencesException</code> without detail
     * message.
     */
    public PreferencesException() {
    }

    /**
     * Constructs an instance of <code>PreferencesException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public PreferencesException(final String msg) {
        super(msg);
    }
}
