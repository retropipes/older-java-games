/*  LLDS: Arbitrary dimension arrays for Java programs
Licensed under Apache 2.0. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/wrldwzrd89/lib-java-low-level-data-storage
 */
package studio.ignitionigloogames.ioutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ResourceStreamReader implements AutoCloseable {
    // Fields
    private transient final BufferedReader br;

    // Constructors
    public ResourceStreamReader(final InputStream is) {
	this.br = new BufferedReader(new InputStreamReader(is));
    }

    public ResourceStreamReader(final InputStream is, final String encoding) throws UnsupportedEncodingException {
	this.br = new BufferedReader(new InputStreamReader(is, encoding));
    }

    // Methods
    @Override
    public void close() throws IOException {
	this.br.close();
    }

    public String readString() throws IOException {
	return this.br.readLine();
    }

    public int readInt() throws IOException {
	final String line = this.br.readLine();
	if (line == null) {
	    throw new IOException("Input == null!");
	} else {
	    return Integer.parseInt(line);
	}
    }
}
