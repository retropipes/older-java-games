package net.worldwizard.support.scripts.game;

import java.io.IOException;

import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class GameScriptEntryArgument {
    // Constants
    private static final byte USES_INTEGER = 1;
    private static final byte USES_DECIMAL = 2;
    private static final byte USES_STRING = 3;
    private static final byte USES_BOOLEAN = 4;
    // Fields
    private final byte useCode;
    private int intArg;
    private double decArg;
    private String strArg;
    private boolean booArg;

    // Constructors
    public GameScriptEntryArgument(final int data) {
        this.useCode = GameScriptEntryArgument.USES_INTEGER;
        this.intArg = data;
    }

    public GameScriptEntryArgument(final double data) {
        this.useCode = GameScriptEntryArgument.USES_DECIMAL;
        this.decArg = data;
    }

    public GameScriptEntryArgument(final String data) {
        this.useCode = GameScriptEntryArgument.USES_STRING;
        this.strArg = data;
    }

    public GameScriptEntryArgument(final boolean data) {
        this.useCode = GameScriptEntryArgument.USES_BOOLEAN;
        this.booArg = data;
    }

    // Methods
    public boolean isInteger() {
        return this.useCode == GameScriptEntryArgument.USES_INTEGER;
    }

    public boolean isDecimal() {
        return this.useCode == GameScriptEntryArgument.USES_DECIMAL;
    }

    public boolean isString() {
        return this.useCode == GameScriptEntryArgument.USES_STRING;
    }

    public boolean isBoolean() {
        return this.useCode == GameScriptEntryArgument.USES_BOOLEAN;
    }

    public Class<?> getArgumentClass() {
        if (this.isInteger()) {
            return int.class;
        } else if (this.isDecimal()) {
            return double.class;
        } else if (this.isString()) {
            return String.class;
        } else if (this.isBoolean()) {
            return boolean.class;
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        final Class<?> argt = this.getArgumentClass();
        if (argt.equals(String.class)) {
            return this.getString();
        } else if (argt.equals(int.class)) {
            return Integer.toString(this.getInteger());
        } else if (argt.equals(double.class)) {
            return Double.toString(this.getDecimal());
        } else if (argt.equals(boolean.class)) {
            return Boolean.toString(this.getBoolean());
        } else {
            // Shouldn't ever get here
            return "";
        }
    }

    public int getInteger() {
        return this.intArg;
    }

    public double getDecimal() {
        return this.decArg;
    }

    public String getString() {
        return this.strArg;
    }

    public boolean getBoolean() {
        return this.booArg;
    }

    public static GameScriptEntryArgument read(final XDataReader reader)
            throws IOException {
        GameScriptEntryArgument sea = null;
        final byte code = reader.readByte();
        if (code == GameScriptEntryArgument.USES_INTEGER) {
            final int data = reader.readInt();
            sea = new GameScriptEntryArgument(data);
        } else if (code == GameScriptEntryArgument.USES_DECIMAL) {
            final double data = reader.readDouble();
            sea = new GameScriptEntryArgument(data);
        } else if (code == GameScriptEntryArgument.USES_STRING) {
            final String data = reader.readString();
            sea = new GameScriptEntryArgument(data);
        } else if (code == GameScriptEntryArgument.USES_BOOLEAN) {
            final boolean data = reader.readBoolean();
            sea = new GameScriptEntryArgument(data);
        }
        return sea;
    }

    public void write(final XDataWriter writer) throws IOException {
        writer.writeByte(this.useCode);
        if (this.isInteger()) {
            writer.writeInt(this.intArg);
        } else if (this.isDecimal()) {
            writer.writeDouble(this.decArg);
        } else if (this.isString()) {
            writer.writeString(this.strArg);
        } else if (this.isBoolean()) {
            writer.writeBoolean(this.booArg);
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.booArg ? 1231 : 1237);
        long temp;
        temp = Double.doubleToLongBits(this.decArg);
        result = prime * result + (int) (temp ^ temp >>> 32);
        result = prime * result + this.intArg;
        result = prime * result
                + (this.strArg == null ? 0 : this.strArg.hashCode());
        result = prime * result + this.useCode;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GameScriptEntryArgument)) {
            return false;
        }
        final GameScriptEntryArgument other = (GameScriptEntryArgument) obj;
        if (this.booArg != other.booArg) {
            return false;
        }
        if (Double.doubleToLongBits(this.decArg) != Double
                .doubleToLongBits(other.decArg)) {
            return false;
        }
        if (this.intArg != other.intArg) {
            return false;
        }
        if (this.strArg == null) {
            if (other.strArg != null) {
                return false;
            }
        } else if (!this.strArg.equals(other.strArg)) {
            return false;
        }
        if (this.useCode != other.useCode) {
            return false;
        }
        return true;
    }
}
