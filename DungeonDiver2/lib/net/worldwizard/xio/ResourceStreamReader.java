package net.worldwizard.xio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceStreamReader {
    // Fields
    private final BufferedReader br;

    // Constructors
    public ResourceStreamReader(final InputStream is) {
        this.br = new BufferedReader(new InputStreamReader(is));
    }

    // Methods
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
