package com.puttysoftware.xio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XDataWriter {
    // Fields
    private final BufferedWriter bw;
    private final File file;
    private final String docTag;
    private static final String END_OF_LINE = "\r\n";

    // Constructors
    public XDataWriter(final String filename, final String newDocTag)
            throws IOException {
        this.bw = new BufferedWriter(new FileWriter(filename));
        this.file = new File(filename);
        this.docTag = newDocTag;
        this.writeXHeader();
        this.writeOpeningDocTag();
    }

    // Methods
    public File getFile() {
        return this.file;
    }

    public void close() throws IOException {
        this.writeClosingDocTag();
        this.bw.close();
    }

    public void writeInt(final int i) throws IOException {
        this.bw.write("<" + XDataConstants.INT_TAG + ">" + Integer.toString(i)
                + "</" + XDataConstants.INT_TAG + ">"
                + XDataWriter.END_OF_LINE);
    }

    public void writeLong(final long l) throws IOException {
        this.bw.write("<" + XDataConstants.LONG_TAG + ">" + Long.toString(l)
                + "</" + XDataConstants.LONG_TAG + ">"
                + XDataWriter.END_OF_LINE);
    }

    public void writeByte(final byte b) throws IOException {
        this.bw.write("<" + XDataConstants.BYTE_TAG + ">" + Byte.toString(b)
                + "</" + XDataConstants.BYTE_TAG + ">"
                + XDataWriter.END_OF_LINE);
    }

    public void writeBoolean(final boolean b) throws IOException {
        this.bw.write("<" + XDataConstants.BOOLEAN_TAG + ">"
                + Boolean.toString(b) + "</" + XDataConstants.BOOLEAN_TAG + ">"
                + XDataWriter.END_OF_LINE);
    }

    public void writeString(final String s) throws IOException {
        this.bw.write("<" + XDataConstants.STRING_TAG + ">" + s + "</"
                + XDataConstants.STRING_TAG + ">" + XDataWriter.END_OF_LINE);
    }

    private void writeXHeader() throws IOException {
        this.bw.write(XDataConstants.X_HEADER + XDataWriter.END_OF_LINE);
    }

    private void writeOpeningDocTag() throws IOException {
        this.bw.write("<" + this.docTag + ">" + XDataWriter.END_OF_LINE);
    }

    private void writeClosingDocTag() throws IOException {
        this.bw.write("</" + this.docTag + ">");
    }
}
