/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.scripts.internal;

import java.util.ArrayList;
import java.util.Arrays;

public class InternalScriptEntry {
    // Fields
    private InternalScriptActionCode actionCode;
    private InternalScriptEntryArgument[] actionArgs;
    private ArrayList<InternalScriptEntryArgument> tempArgs;

    // Constructors
    public InternalScriptEntry() {
        this.actionCode = InternalScriptActionCode.NONE;
        this.actionArgs = null;
        this.tempArgs = new ArrayList<>();
    }

    // Methods
    public InternalScriptEntryArgument getFirstActionArg() {
        return this.actionArgs[0];
    }

    public InternalScriptEntryArgument getActionArg(int index) {
        return this.actionArgs[index];
    }

    public InternalScriptEntryArgument[] getActionArgs() {
        return this.actionArgs;
    }

    public void addActionArg(InternalScriptEntryArgument arg) {
        this.tempArgs.add(arg);
    }

    public void finalizeActionArgs() {
        if (!this.tempArgs.isEmpty()) {
            this.tempArgs.trimToSize();
            InternalScriptEntryArgument[] args = this.tempArgs
                    .toArray(new InternalScriptEntryArgument[this.tempArgs
                            .size()]);
            this.setActionArgs(args);
            this.tempArgs = null;
        }
    }

    private void setActionArgs(InternalScriptEntryArgument... newActionArgs) {
        this.actionArgs = newActionArgs;
    }

    public InternalScriptActionCode getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(InternalScriptActionCode newActionCode) {
        this.actionCode = newActionCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(this.actionArgs);
        result = prime * result
                + ((this.actionCode == null) ? 0 : this.actionCode.hashCode());
        return prime * result
                + ((this.tempArgs == null) ? 0 : this.tempArgs.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof InternalScriptEntry)) {
            return false;
        }
        InternalScriptEntry other = (InternalScriptEntry) obj;
        if (!Arrays.equals(this.actionArgs, other.actionArgs)) {
            return false;
        }
        if (this.actionCode != other.actionCode) {
            return false;
        }
        if (this.tempArgs == null) {
            if (other.tempArgs != null) {
                return false;
            }
        } else if (!this.tempArgs.equals(other.tempArgs)) {
            return false;
        }
        return true;
    }
}
