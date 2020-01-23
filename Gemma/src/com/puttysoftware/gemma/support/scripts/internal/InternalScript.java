/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.scripts.internal;

import java.util.ArrayList;
import java.util.Arrays;

public class InternalScript {
    // Fields
    private InternalScriptEntry[] actions;
    private ArrayList<InternalScriptEntry> tempActions;

    // Constructors
    public InternalScript() {
        this.actions = null;
        this.tempActions = new ArrayList<>();
    }

    // Methods
    public final int getActionCount() {
        return this.actions.length;
    }

    public final void addAction(InternalScriptEntry act) {
        this.tempActions.add(act);
    }

    public final void finalizeActions() {
        if (!this.tempActions.isEmpty()) {
            this.tempActions.trimToSize();
            InternalScriptEntry[] acts = this.tempActions
                    .toArray(new InternalScriptEntry[this.tempActions.size()]);
            this.actions = acts;
            this.tempActions = null;
        }
    }

    public final InternalScriptEntry getAction(final int index) {
        return this.actions[index];
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(this.actions);
        return prime * result + ((this.tempActions == null) ? 0
                : this.tempActions.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof InternalScript)) {
            return false;
        }
        InternalScript other = (InternalScript) obj;
        if (!Arrays.equals(this.actions, other.actions)) {
            return false;
        }
        if (this.tempActions == null) {
            if (other.tempActions != null) {
                return false;
            }
        } else if (!this.tempActions.equals(other.tempActions)) {
            return false;
        }
        return true;
    }
}
