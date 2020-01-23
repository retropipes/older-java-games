package com.puttysoftware.xmlio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class XMLDataWriter {
    // Fields
    private final BufferedWriter bw;
    private final File file;
    private final String docTag;
    private static final String END_OF_LINE = "\r\n";

    // Constructors
    public XMLDataWriter(final String filename, final String newDocTag)
            throws IOException {
        this.bw = new BufferedWriter(new FileWriter(filename));
        this.file = new File(filename);
        this.docTag = newDocTag;
        this.writeXMLHeader();
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
        this.bw.write("<" + XMLDataConstants.INT_TAG + ">"
                + Integer.toString(i) + "</" + XMLDataConstants.INT_TAG + ">"
                + XMLDataWriter.END_OF_LINE);
    }

    public void writeLong(final long l) throws IOException {
        this.bw.write("<" + XMLDataConstants.LONG_TAG + ">" + Long.toString(l)
                + "</" + XMLDataConstants.LONG_TAG + ">"
                + XMLDataWriter.END_OF_LINE);
    }

    public void writeByte(final byte b) throws IOException {
        this.bw.write("<" + XMLDataConstants.BYTE_TAG + ">" + Byte.toString(b)
                + "</" + XMLDataConstants.BYTE_TAG + ">"
                + XMLDataWriter.END_OF_LINE);
    }

    public void writeBoolean(final boolean b) throws IOException {
        this.bw.write("<" + XMLDataConstants.BOOLEAN_TAG + ">"
                + Boolean.toString(b) + "</" + XMLDataConstants.BOOLEAN_TAG
                + ">" + XMLDataWriter.END_OF_LINE);
    }

    public void writeString(final String s) throws IOException {
        this.bw.write("<" + XMLDataConstants.STRING_TAG + ">" + s + "</"
                + XMLDataConstants.STRING_TAG + ">" + XMLDataWriter.END_OF_LINE);
    }

    private void writeXMLHeader() throws IOException {
        this.bw.write(XMLDataConstants.XML_HEADER + XMLDataWriter.END_OF_LINE);
    }

    private void writeOpeningDocTag() throws IOException {
        this.bw.write("<" + this.docTag + ">" + XMLDataWriter.END_OF_LINE);
    }

    private void writeClosingDocTag() throws IOException {
        this.bw.write("</" + this.docTag + ">");
    }
}
