package studio.ignitionigloogames.common.fileio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileIOReader implements AutoCloseable {
    // Fields
    private final BufferedReader br;
    private final String docTag;

    // Constructors
    public FileIOReader(final String filename, final String newDocTag)
            throws IOException {
        this.br = new BufferedReader(new FileReader(filename));
        this.docTag = newDocTag;
        this.readXHeader();
        this.readOpeningDocTag();
    }

    public FileIOReader(final InputStream stream, final String newDocTag)
            throws IOException {
        this.br = new BufferedReader(new InputStreamReader(stream));
        this.docTag = newDocTag;
        this.readXHeader();
        this.readOpeningDocTag();
    }

    // Methods
    @Override
    public void close() throws IOException {
        this.readClosingDocTag();
        this.br.close();
    }

    public double readDouble() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            final String[] split = FileIOReader.splitLine(line);
            FileIOReader.validateOpeningTag(split[0],
                    FileIOConstants.DOUBLE_TAG);
            FileIOReader.validateClosingTag(split[2],
                    FileIOConstants.DOUBLE_TAG);
            return Double.parseDouble(split[1]);
        } else {
            throw new IOException("End of file!");
        }
    }

    public int readInt() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            final String[] split = FileIOReader.splitLine(line);
            FileIOReader.validateOpeningTag(split[0], FileIOConstants.INT_TAG);
            FileIOReader.validateClosingTag(split[2], FileIOConstants.INT_TAG);
            return Integer.parseInt(split[1]);
        } else {
            throw new IOException("End of file!");
        }
    }

    public long readLong() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            final String[] split = FileIOReader.splitLine(line);
            FileIOReader.validateOpeningTag(split[0], FileIOConstants.LONG_TAG);
            FileIOReader.validateClosingTag(split[2], FileIOConstants.LONG_TAG);
            return Long.parseLong(split[1]);
        } else {
            throw new IOException("End of file!");
        }
    }

    public byte readByte() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            final String[] split = FileIOReader.splitLine(line);
            FileIOReader.validateOpeningTag(split[0], FileIOConstants.BYTE_TAG);
            FileIOReader.validateClosingTag(split[2], FileIOConstants.BYTE_TAG);
            return Byte.parseByte(split[1]);
        } else {
            throw new IOException("End of file!");
        }
    }

    public boolean readBoolean() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            final String[] split = FileIOReader.splitLine(line);
            FileIOReader.validateOpeningTag(split[0],
                    FileIOConstants.BOOLEAN_TAG);
            FileIOReader.validateClosingTag(split[2],
                    FileIOConstants.BOOLEAN_TAG);
            return Boolean.parseBoolean(split[1]);
        } else {
            throw new IOException("End of file!");
        }
    }

    public String readString() throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            final String[] split = FileIOReader.splitLine(line);
            FileIOReader.validateOpeningTag(split[0],
                    FileIOConstants.STRING_TAG);
            FileIOReader.validateClosingTag(split[2],
                    FileIOConstants.STRING_TAG);
            return FileIOReader.replaceSpecialCharacters(split[1]);
        } else {
            throw new IOException("End of file!");
        }
    }

    public void readOpeningGroup(final String groupName) throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            FileIOReader.validateOpeningTag(
                    FileIOReader.replaceSpecialCharacters(line), groupName);
        } else {
            throw new IOException("End of file!");
        }
    }

    public void readClosingGroup(final String groupName) throws IOException {
        final String line = this.br.readLine();
        if (line != null) {
            FileIOReader.validateClosingTag(
                    FileIOReader.replaceSpecialCharacters(line), groupName);
        } else {
            throw new IOException("End of file!");
        }
    }

    private static void validateOpeningTag(final String tag,
            final String tagType) throws IOException {
        if (!tag.equals("<" + tagType + ">")) {
            throw new FileIOException("Expected opening tag of <" + tagType
                    + ">, found " + tag + "!");
        }
    }

    private static void validateClosingTag(final String tag,
            final String tagType) throws IOException {
        if (!tag.equals("</" + tagType + ">")) {
            throw new FileIOException("Expected closing tag of </" + tagType
                    + ">, found " + tag + "!");
        }
    }

    private static String[] splitLine(final String line) {
        final String[] split = new String[3];
        final int loc0 = line.indexOf('>') + 1;
        final int loc2 = line.indexOf('<', loc0);
        split[0] = line.substring(0, loc0);
        split[1] = line.substring(loc0, loc2);
        split[2] = line.substring(loc2);
        return split;
    }

    private void readXHeader() throws IOException {
        final String header = this.br.readLine();
        if (header == null) {
            throw new FileIOException("Corrupt or invalid header!");
        }
        if (!header.equals(FileIOConstants.X_HEADER)) {
            throw new FileIOException("Corrupt or invalid header!");
        }
    }

    private void readOpeningDocTag() throws IOException {
        final String line = this.br.readLine();
        if (line != null && !line.equals("<" + this.docTag + ">")) {
            throw new FileIOException(
                    "Opening doc tag does not match: expected <" + this.docTag
                            + ">, found " + line + "!");
        }
    }

    private void readClosingDocTag() throws IOException {
        final String line = this.br.readLine();
        if (line != null && !line.equals("</" + this.docTag + ">")) {
            throw new FileIOException(
                    "Closing doc tag does not match: expected </" + this.docTag
                            + ">, found " + line + "!");
        }
    }

    private static String replaceSpecialCharacters(final String s) {
        String r = s;
        r = r.replace("&amp;", "&");
        r = r.replace("&lt;", "<");
        r = r.replace("&gt;", ">");
        r = r.replace("&quot;", "\"");
        r = r.replace("&apos;", "\'");
        return r.replace("&#xA;", "\n");
    }
}
