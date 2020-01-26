package net.dynamicdungeon.fileutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class ResourceStreamReader implements AutoCloseable {
    // Fields
    private transient final InputStreamReader isr;
    private transient final BufferedReader br;

    // Constructors
    public ResourceStreamReader(final InputStream is) {
        this.isr = new InputStreamReader(is);
        this.br = new BufferedReader(this.isr);
    }

    public ResourceStreamReader(final InputStream is, final String encoding)
            throws UnsupportedEncodingException {
        this.isr = new InputStreamReader(is, encoding);
        this.br = new BufferedReader(this.isr);
    }

    // Methods
    @Override
    public void close() throws IOException {
        this.br.close();
        this.isr.close();
    }

    public String readString() throws IOException {
        return this.br.readLine();
    }

    public int readInt() throws IOException {
        final String line = this.br.readLine();
        if (line == null) {
            throw new IOException("Input == null!");
        }
        return Integer.parseInt(line);
    }
}
