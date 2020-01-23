package net.worldwizard.worldz.scripts;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;

public class ScriptEntryArgument {
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
    public ScriptEntryArgument(final int data) {
        this.useCode = ScriptEntryArgument.USES_INTEGER;
        this.intArg = data;
    }

    public ScriptEntryArgument(final double data) {
        this.useCode = ScriptEntryArgument.USES_DECIMAL;
        this.decArg = data;
    }

    public ScriptEntryArgument(final String data) {
        this.useCode = ScriptEntryArgument.USES_STRING;
        this.strArg = data;
    }

    public ScriptEntryArgument(final boolean data) {
        this.useCode = ScriptEntryArgument.USES_BOOLEAN;
        this.booArg = data;
    }

    // Methods
    public boolean isInteger() {
        return this.useCode == ScriptEntryArgument.USES_INTEGER;
    }

    public boolean isDecimal() {
        return this.useCode == ScriptEntryArgument.USES_DECIMAL;
    }

    public boolean isString() {
        return this.useCode == ScriptEntryArgument.USES_STRING;
    }

    public boolean isBoolean() {
        return this.useCode == ScriptEntryArgument.USES_BOOLEAN;
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

    public static ScriptEntryArgument read(final DataReader reader)
            throws IOException {
        ScriptEntryArgument sea = null;
        final byte code = reader.readByte();
        if (code == ScriptEntryArgument.USES_INTEGER) {
            final int data = reader.readInt();
            sea = new ScriptEntryArgument(data);
        } else if (code == ScriptEntryArgument.USES_DECIMAL) {
            final double data = reader.readDouble();
            sea = new ScriptEntryArgument(data);
        } else if (code == ScriptEntryArgument.USES_STRING) {
            final String data = reader.readString();
            sea = new ScriptEntryArgument(data);
        } else if (code == ScriptEntryArgument.USES_BOOLEAN) {
            final boolean data = reader.readBoolean();
            sea = new ScriptEntryArgument(data);
        }
        return sea;
    }

    public void write(final DataWriter writer) throws IOException {
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
}
