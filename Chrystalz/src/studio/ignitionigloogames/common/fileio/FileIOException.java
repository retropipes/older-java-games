package studio.ignitionigloogames.common.fileio;

import java.io.IOException;

public class FileIOException extends IOException {
    private static final long serialVersionUID = 23250505322336L;

    /**
     * Constructs an instance of <code>UnexpectedTagException</code> with the
     * specified detail message.
     *
     * @param msg
     *            the detail message.
     */
    public FileIOException(final String msg) {
        super(msg);
    }
}
