package net.worldwizard.worldz.scripts;

import java.io.IOException;
import java.util.Vector;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;

public class Script {
    // Fields
    private ScriptEntry[] actions;
    private Vector<ScriptEntry> tempActions;

    // Constructors
    public Script() {
        this.actions = null;
        this.tempActions = new Vector<>();
    }

    // Methods
    public int getActionCount() {
        return this.actions.length;
    }

    public ScriptEntry getFirstAction() {
        return this.actions[0];
    }

    public boolean hasActions() {
        return this.actions != null;
    }

    public boolean hasOneAction() {
        if (this.hasActions()) {
            if (this.actions.length == 1) {
                return true;
            }
        }
        return false;
    }

    public void addAction(final ScriptEntry act) {
        this.tempActions.add(act);
    }

    public void finalizeActions() {
        if (!this.tempActions.isEmpty()) {
            this.tempActions.trimToSize();
            final ScriptEntry[] acts = this.tempActions
                    .toArray(new ScriptEntry[this.tempActions.size()]);
            this.actions = acts;
            this.tempActions = null;
        }
    }

    public void setActions(final ScriptEntry... newActions) {
        this.actions = newActions;
    }

    public ScriptEntry getAction(final int index) {
        return this.actions[index];
    }

    public void setAction(final int index, final ScriptEntry action) {
        this.actions[index] = action;
    }

    public static Script read(final DataReader reader) throws IOException {
        final Script s = new Script();
        final int len = reader.readInt();
        if (len == 0) {
            s.actions = null;
        } else {
            final ScriptEntry[] acts = new ScriptEntry[len];
            for (int x = 0; x < len; x++) {
                acts[x] = ScriptEntry.read(reader);
            }
            s.actions = acts;
        }
        return s;
    }

    public void write(final DataWriter writer) throws IOException {
        if (this.actions == null) {
            writer.writeInt(0);
        } else {
            writer.writeInt(this.actions.length);
            for (final ScriptEntry action : this.actions) {
                action.write(writer);
            }
        }
    }
}
