package studio.ignitionigloogames.common.fileio;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileIOWriter implements AutoCloseable {
    // Fields
    private final BufferedWriter bw;
    private final String docTag;
    private static final String END_OF_LINE = "\r\n";

    // Constructors
    public FileIOWriter(final String filename, final String newDocTag)
            throws IOException {
        this.bw = new BufferedWriter(new FileWriter(filename));
        this.docTag = newDocTag;
        this.writeXHeader();
        this.writeOpeningDocTag();
    }

    // Methods
    @Override
    public void close() throws IOException {
        this.writeClosingDocTag();
        this.bw.close();
    }

    public void writeDouble(final double d) throws IOException {
        this.bw.write("<" + FileIOConstants.DOUBLE_TAG + ">"
                + Double.toString(d) + "</" + FileIOConstants.DOUBLE_TAG + ">"
                + FileIOWriter.END_OF_LINE);
    }

    public void writeInt(final int i) throws IOException {
        this.bw.write("<" + FileIOConstants.INT_TAG + ">" + Integer.toString(i)
                + "</" + FileIOConstants.INT_TAG + ">"
                + FileIOWriter.END_OF_LINE);
    }

    public void writeLong(final long l) throws IOException {
        this.bw.write("<" + FileIOConstants.LONG_TAG + ">" + Long.toString(l)
                + "</" + FileIOConstants.LONG_TAG + ">"
                + FileIOWriter.END_OF_LINE);
    }

    public void writeByte(final byte b) throws IOException {
        this.bw.write("<" + FileIOConstants.BYTE_TAG + ">" + Byte.toString(b)
                + "</" + FileIOConstants.BYTE_TAG + ">"
                + FileIOWriter.END_OF_LINE);
    }

    public void writeBoolean(final boolean b) throws IOException {
        this.bw.write("<" + FileIOConstants.BOOLEAN_TAG + ">"
                + Boolean.toString(b) + "</" + FileIOConstants.BOOLEAN_TAG + ">"
                + FileIOWriter.END_OF_LINE);
    }

    public void writeString(final String s) throws IOException {
        this.bw.write("<" + FileIOConstants.STRING_TAG + ">"
                + FileIOWriter.replaceSpecialCharacters(s) + "</"
                + FileIOConstants.STRING_TAG + ">" + FileIOWriter.END_OF_LINE);
    }

    public void writeOpeningGroup(final String groupName) throws IOException {
        this.bw.write("<" + FileIOWriter.replaceSpecialCharacters(groupName)
                + ">" + FileIOWriter.END_OF_LINE);
    }

    public void writeClosingGroup(final String groupName) throws IOException {
        this.bw.write("</" + FileIOWriter.replaceSpecialCharacters(groupName)
                + ">" + FileIOWriter.END_OF_LINE);
    }

    private void writeXHeader() throws IOException {
        this.bw.write(FileIOConstants.X_HEADER + FileIOWriter.END_OF_LINE);
    }

    private void writeOpeningDocTag() throws IOException {
        this.bw.write("<" + this.docTag + ">" + FileIOWriter.END_OF_LINE);
    }

    private void writeClosingDocTag() throws IOException {
        this.bw.write("</" + this.docTag + ">");
    }

    private static String replaceSpecialCharacters(final String s) {
        String r = s;
        r = r.replace("&", "&amp;");
        r = r.replace("<", "&lt;");
        r = r.replace(">", "&gt;");
        r = r.replace("\"", "&quot;");
        r = r.replace("\'", "&apos;");
        r = r.replace("\r", "");
        return r.replace("\n", "&#xA;");
    }
}
