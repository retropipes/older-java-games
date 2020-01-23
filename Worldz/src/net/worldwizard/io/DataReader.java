package net.worldwizard.io;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class DataReader implements DataConstants {
    // Fields
    private final boolean dataMode;
    private BufferedReader br;
    private DataInputStream dis;
    private final File file;

    // Constructors
    public DataReader(final String filename, final boolean mode)
            throws IOException {
        this.dataMode = mode;
        if (mode == DataConstants.DATA_MODE_TEXT) {
            this.br = new BufferedReader(new FileReader(filename));
        } else {
            this.dis = new DataInputStream(new FileInputStream(filename));
        }
        this.file = new File(filename);
    }

    // Methods
    public File getFile() {
        return this.file;
    }

    public void close() throws IOException {
        if (this.dataMode == DataConstants.DATA_MODE_TEXT) {
            this.br.close();
        } else {
            this.dis.close();
        }
    }

    public int readInt() throws IOException {
        if (this.dataMode == DataConstants.DATA_MODE_TEXT) {
            final String line = this.br.readLine();
            if (line != null) {
                return Integer.parseInt(line);
            } else {
                throw new IOException("End of file!");
            }
        } else {
            return this.dis.readInt();
        }
    }

    public float readFloat() throws IOException {
        if (this.dataMode == DataConstants.DATA_MODE_TEXT) {
            final String line = this.br.readLine();
            if (line != null) {
                return Float.parseFloat(line);
            } else {
                throw new IOException("End of file!");
            }
        } else {
            return this.dis.readFloat();
        }
    }

    public double readDouble() throws IOException {
        if (this.dataMode == DataConstants.DATA_MODE_TEXT) {
            final String line = this.br.readLine();
            if (line != null) {
                return Double.parseDouble(line);
            } else {
                throw new IOException("End of file!");
            }
        } else {
            return this.dis.readDouble();
        }
    }

    public long readLong() throws IOException {
        if (this.dataMode == DataConstants.DATA_MODE_TEXT) {
            final String line = this.br.readLine();
            if (line != null) {
                return Long.parseLong(line);
            } else {
                throw new IOException("End of file!");
            }
        } else {
            return this.dis.readLong();
        }
    }

    public byte readByte() throws IOException {
        if (this.dataMode == DataConstants.DATA_MODE_TEXT) {
            final String line = this.br.readLine();
            if (line != null) {
                return Byte.parseByte(line);
            } else {
                throw new IOException("End of file!");
            }
        } else {
            return this.dis.readByte();
        }
    }

    public boolean readBoolean() throws IOException {
        if (this.dataMode == DataConstants.DATA_MODE_TEXT) {
            final String line = this.br.readLine();
            if (line != null) {
                return Boolean.parseBoolean(line);
            } else {
                throw new IOException("End of file!");
            }
        } else {
            return this.dis.readBoolean();
        }
    }

    public short readShort() throws IOException {
        if (this.dataMode == DataConstants.DATA_MODE_TEXT) {
            final String line = this.br.readLine();
            if (line != null) {
                return Short.parseShort(line);
            } else {
                throw new IOException("End of file!");
            }
        } else {
            return this.dis.readShort();
        }
    }

    public String readString() throws IOException {
        if (this.dataMode == DataConstants.DATA_MODE_TEXT) {
            return this.br.readLine();
        } else {
            return this.dis.readUTF();
        }
    }
}
