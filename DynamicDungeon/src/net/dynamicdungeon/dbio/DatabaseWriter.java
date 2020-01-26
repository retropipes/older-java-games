package net.dynamicdungeon.dbio;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class DatabaseWriter implements AutoCloseable {
    // Fields
    private final FileOutputStream dbwf;
    private final BufferedOutputStream dbwb;
    private final DataOutputStream dbw;
    private static final int BUFFER_SIZE = 1048576;

    // Constructors
    public DatabaseWriter(final String filename) throws IOException {
        this.dbwf = new FileOutputStream(filename);
        this.dbwb = new BufferedOutputStream(this.dbwf,
                DatabaseWriter.BUFFER_SIZE);
        this.dbw = new DataOutputStream(this.dbwb);
    }

    // Methods
    @Override
    public void close() throws IOException {
        this.dbw.close();
        this.dbwb.close();
        this.dbwf.close();
    }

    public void writeDouble(final double d) throws IOException {
        this.dbw.writeDouble(d);
    }

    public void writeLong(final long l) throws IOException {
        this.dbw.writeLong(l);
    }

    public void writeInt(final int i) throws IOException {
        this.dbw.writeInt(i);
    }

    public void writeShort(final int s) throws IOException {
        this.dbw.writeShort(s);
    }

    public void writeByte(final int b) throws IOException {
        this.dbw.writeByte(b);
    }

    public void writeBoolean(final boolean b) throws IOException {
        this.dbw.writeBoolean(b);
    }

    public void writeString(final String s) throws IOException {
        this.dbw.writeUTF(s);
    }
}
