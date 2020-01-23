package net.worldwizard.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceStreamReader implements DataConstants {
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

    public int readInt() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            return Integer.parseInt(line);
        } else {
            throw new IOException("End of file!");
        }
    }

    public float readFloat() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            return Float.parseFloat(line);
        } else {
            throw new IOException("End of file!");
        }
    }

    public double readDouble() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            return Double.parseDouble(line);
        } else {
            throw new IOException("End of file!");
        }
    }

    public long readLong() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            return Long.parseLong(line);
        } else {
            throw new IOException("End of file!");
        }
    }

    public byte readByte() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            return Byte.parseByte(line);
        } else {
            throw new IOException("End of file!");
        }
    }

    public boolean readBoolean() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            return Boolean.parseBoolean(line);
        } else {
            throw new IOException("End of file!");
        }
    }

    public short readShort() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            return Short.parseShort(line);
        } else {
            throw new IOException("End of file!");
        }
    }

    public String readString() throws IOException {
        return this.br.readLine();
    }
}
