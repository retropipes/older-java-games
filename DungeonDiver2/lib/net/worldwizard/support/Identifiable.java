package net.worldwizard.support;

import java.io.IOException;

public abstract class Identifiable implements LongHashable {
    // Fields
    private boolean systemObject;
    private boolean localObject;
    public static final String SYSTEM_PREFIX = "$";
    public static final String LOCAL_PREFIX = "@";

    // Constructors
    protected Identifiable() {
        this.systemObject = false;
        this.localObject = true;
    }

    protected Identifiable(final boolean so) {
        this.systemObject = so;
        this.localObject = true;
    }

    // Methods
    public abstract void dumpContentsToFile() throws IOException;

    public abstract String getName();

    public abstract String getTypeName();

    public abstract String getPluralTypeName();

    public final boolean isLocalObject() {
        return this.localObject;
    }

    public final void setLocalObject(final boolean lo) {
        this.localObject = lo;
    }

    public final boolean isSystemObject() {
        return this.systemObject;
    }

    public final void setSystemObject(final boolean so) {
        this.systemObject = so;
    }

    public final String getIDPrefix() {
        if (this.systemObject) {
            return Identifiable.SYSTEM_PREFIX;
        } else if (this.localObject) {
            return Identifiable.LOCAL_PREFIX;
        } else {
            return Support.getVariables().getVariablesID();
        }
    }

    public final String getIDSuffix() {
        return this.computeLongHash().toString(36).toUpperCase();
    }

    public final String getID() {
        return this.getIDPrefix() + this.getIDSuffix();
    }
}