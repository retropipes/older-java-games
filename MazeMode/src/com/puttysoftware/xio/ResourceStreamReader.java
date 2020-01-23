package com.puttysoftware.xio;

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
}
