/*  LLDS: Arbitrary dimension arrays for Java programs
Licensed under Apache 2.0. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/wrldwzrd89/lib-java-low-level-data-storage
 */
package studio.ignitionigloogames.llds;

import java.io.IOException;

public class LowLevelDataIOException extends IOException {
    private static final long serialVersionUID = 23250505322336L;

    /**
     * Constructs an instance of <code>LowLevelDataIOException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LowLevelDataIOException(final String msg) {
	super(msg);
    }
}
