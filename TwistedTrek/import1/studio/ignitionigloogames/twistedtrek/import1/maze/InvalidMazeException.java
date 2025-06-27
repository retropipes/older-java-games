/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.maze;

public class InvalidMazeException extends Exception {
    // Serialization
    private static final long serialVersionUID = 999L;

    // Constructors
    public InvalidMazeException() {
        super();
    }

    public InvalidMazeException(final String msg) {
        super(msg);
    }
}