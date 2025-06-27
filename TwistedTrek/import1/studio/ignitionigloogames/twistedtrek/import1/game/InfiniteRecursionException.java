/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.game;

public class InfiniteRecursionException extends RuntimeException {
    private static final long serialVersionUID = 54364343055203034L;

    /**
     * Creates a new instance of <code>InfiniteRecursionException</code> without
     * detail message.
     */
    public InfiniteRecursionException() {
    }

    /**
     * Constructs an instance of <code>InfiniteRecursionException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InfiniteRecursionException(final String msg) {
        super(msg);
    }
}
