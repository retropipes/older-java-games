package com.puttysoftware.xmlio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class XMLDataReader {
    // Fields
    private final BufferedReader br;
    private final File file;
    private final String docTag;

    // Constructors
    public XMLDataReader(final String filename, final String newDocTag)
            throws IOException {
        this.br = new BufferedReader(new FileReader(filename));
        this.file = new File(filename);
        this.docTag = newDocTag;
        this.readXMLHeader();
        this.readOpeningDocTag();
    }

    // Methods
    public File getFile() {
        return this.file;
    }

    public void close() throws IOException {
        this.readClosingDocTag();
        this.br.close();
    }

    public int readInt() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            final String[] split = XMLDataReader.splitLine(line);
            XMLDataReader
                    .validateOpeningTag(split[0], XMLDataConstants.INT_TAG);
            XMLDataReader
                    .validateClosingTag(split[2], XMLDataConstants.INT_TAG);
            return Integer.parseInt(split[1]);
        } else {
            throw new IOException("End of file!");
        }
    }

    public long readLong() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            final String[] split = XMLDataReader.splitLine(line);
            XMLDataReader.validateOpeningTag(split[0],
                    XMLDataConstants.LONG_TAG);
            XMLDataReader.validateClosingTag(split[2],
                    XMLDataConstants.LONG_TAG);
            return Long.parseLong(split[1]);
        } else {
            throw new IOException("End of file!");
        }
    }

    public byte readByte() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            final String[] split = XMLDataReader.splitLine(line);
            XMLDataReader.validateOpeningTag(split[0],
                    XMLDataConstants.BYTE_TAG);
            XMLDataReader.validateClosingTag(split[2],
                    XMLDataConstants.BYTE_TAG);
            return Byte.parseByte(split[1]);
        } else {
            throw new IOException("End of file!");
        }
    }

    public boolean readBoolean() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            final String[] split = XMLDataReader.splitLine(line);
            XMLDataReader.validateOpeningTag(split[0],
                    XMLDataConstants.BOOLEAN_TAG);
            XMLDataReader.validateClosingTag(split[2],
                    XMLDataConstants.BOOLEAN_TAG);
            return Boolean.parseBoolean(split[1]);
        } else {
            throw new IOException("End of file!");
        }
    }

    public String readString() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            final String[] split = XMLDataReader.splitLine(line);
            XMLDataReader.validateOpeningTag(split[0],
                    XMLDataConstants.STRING_TAG);
            XMLDataReader.validateClosingTag(split[2],
                    XMLDataConstants.STRING_TAG);
            return split[1];
        } else {
            throw new IOException("End of file!");
        }
    }

    private static void validateOpeningTag(final String tag,
            final String tagType) throws IOException {
        if (!tag.equals("<" + tagType + ">")) {
            throw new UnexpectedTagException("Expected opening tag of <"
                    + tagType + ">, found " + tag + "!");
        }
    }

    private static void validateClosingTag(final String tag,
            final String tagType) throws IOException {
        if (!tag.equals("</" + tagType + ">")) {
            throw new UnexpectedTagException("Expected closing tag of </"
                    + tagType + ">, found " + tag + "!");
        }
    }

    private static String[] splitLine(final String line) {
        final String[] split = new String[3];
        final int loc0 = line.indexOf(">") + 1;
        final int loc2 = line.indexOf("<", loc0);
        split[0] = line.substring(0, loc0);
        split[1] = line.substring(loc0, loc2);
        split[2] = line.substring(loc2);
        return split;
    }

    private void readXMLHeader() throws IOException {
        final String header = this.br.readLine();
        if (header == null) {
            throw new UnexpectedTagException("Corrupt or invalid header!");
        }
        if (!header.equals(XMLDataConstants.XML_HEADER)) {
            throw new UnexpectedTagException("Corrupt or invalid header!");
        }
    }

    private void readOpeningDocTag() throws IOException {
        final String line = this.br.readLine();
        if (!line.equals("<" + this.docTag + ">")) {
            throw new UnexpectedTagException(
                    "Opening doc tag does not match: expected <" + this.docTag
                            + ">, found " + line + "!");
        }
    }

    private void readClosingDocTag() throws IOException {
        final String line = this.br.readLine();
        if (!line.equals("</" + this.docTag + ">")) {
            throw new UnexpectedTagException(
                    "Closing doc tag does not match: expected </" + this.docTag
                            + ">, found " + line + "!");
        }
    }
}
