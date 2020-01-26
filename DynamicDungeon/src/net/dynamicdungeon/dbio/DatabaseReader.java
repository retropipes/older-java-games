package net.dynamicdungeon.dbio;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseReader implements AutoCloseable {
    // Fields
    private final FileInputStream dbrf;
    private final BufferedInputStream dbrb;
    private final DataInputStream dbr;
    private static final int BUFFER_SIZE = 1048576;

    // Constructors
    public DatabaseReader(final String filename) throws IOException {
        this.dbrf = new FileInputStream(filename);
        this.dbrb = new BufferedInputStream(this.dbrf,
                DatabaseReader.BUFFER_SIZE);
        this.dbr = new DataInputStream(this.dbrb);
    }

    // Methods
    @Override
    public void close() throws IOException {
        this.dbr.close();
        this.dbrb.close();
        this.dbrf.close();
    }

    public double readDouble() throws IOException {
        return this.dbr.readDouble();
    }

    public long readLong() throws IOException {
        return this.dbr.readLong();
    }

    public int readInt() throws IOException {
        return this.dbr.readInt();
    }

    public int readShort() throws IOException {
        return this.dbr.readShort();
    }

    public int readByte() throws IOException {
        return this.dbr.readByte();
    }

    public boolean readBoolean() throws IOException {
        return this.dbr.readBoolean();
    }

    public String readString() throws IOException {
        return this.dbr.readUTF();
    }
}
