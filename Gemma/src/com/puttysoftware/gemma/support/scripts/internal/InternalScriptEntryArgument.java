/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.scripts.internal;

public class InternalScriptEntryArgument {
    // Constants
    private static final byte USES_INTEGER = 1;
    private static final byte USES_STRING = 2;
    private static final byte USES_BOOLEAN = 3;
    // Fields
    private final byte useCode;
    private final int intArg;
    private final String strArg;
    private final boolean booArg;

    // Constructors
    public InternalScriptEntryArgument(final int data) {
        this.useCode = InternalScriptEntryArgument.USES_INTEGER;
        this.intArg = data;
        this.booArg = false;
        this.strArg = null;
    }

    public InternalScriptEntryArgument(final String data) {
        this.useCode = InternalScriptEntryArgument.USES_STRING;
        this.strArg = data;
        this.intArg = 0;
        this.booArg = false;
    }

    public InternalScriptEntryArgument(final boolean data) {
        this.useCode = InternalScriptEntryArgument.USES_BOOLEAN;
        this.booArg = data;
        this.intArg = 0;
        this.strArg = null;
    }

    // Methods
    private boolean isInteger() {
        return this.useCode == InternalScriptEntryArgument.USES_INTEGER;
    }

    private boolean isString() {
        return this.useCode == InternalScriptEntryArgument.USES_STRING;
    }

    private boolean isBoolean() {
        return this.useCode == InternalScriptEntryArgument.USES_BOOLEAN;
    }

    public Class<?> getArgumentClass() {
        if (this.isInteger()) {
            return int.class;
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

    public String getString() {
        return this.strArg;
    }

    public boolean getBoolean() {
        return this.booArg;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.booArg ? 1231 : 1237);
        result = prime * result + this.intArg;
        result = prime * result
                + (this.strArg == null ? 0 : this.strArg.hashCode());
        return prime * result + this.useCode;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof InternalScriptEntryArgument)) {
            return false;
        }
        final InternalScriptEntryArgument other = (InternalScriptEntryArgument) obj;
        if (this.booArg != other.booArg) {
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
