package net.worldwizard.worldz.scripts;

import java.io.IOException;
import java.util.Vector;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;

public class ScriptEntry {
    // Fields
    private ActionCode actionCode;
    private ScriptEntryArgument[] actionArgs;
    private Vector<ScriptEntryArgument> tempArgs;

    // Constructors
    public ScriptEntry() {
        this.actionCode = ActionCode.NONE;
        this.actionArgs = null;
        this.tempArgs = new Vector<>();
    }

    // Methods
    public ScriptEntryArgument getFirstActionArg() {
        return this.actionArgs[0];
    }

    public ScriptEntryArgument getActionArg(final int index) {
        return this.actionArgs[index];
    }

    public ScriptEntryArgument[] getActionArgs() {
        return this.actionArgs;
    }

    public boolean hasActionArgs() {
        return this.actionArgs != null;
    }

    public boolean hasOneActionArg() {
        if (this.hasActionArgs()) {
            if (this.actionArgs.length == 1) {
                return true;
            }
        }
        return false;
    }

    public void addActionArg(final ScriptEntryArgument arg) {
        this.tempArgs.add(arg);
    }

    public void finalizeActionArgs() {
        if (!this.tempArgs.isEmpty()) {
            this.tempArgs.trimToSize();
            final ScriptEntryArgument[] args = this.tempArgs
                    .toArray(new ScriptEntryArgument[this.tempArgs.size()]);
            this.setActionArgs(args);
            this.tempArgs = null;
        }
    }

    public void setActionArgs(final ScriptEntryArgument... newActionArgs) {
        this.actionArgs = newActionArgs;
    }

    public ActionCode getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(final ActionCode newActionCode) {
        this.actionCode = newActionCode;
    }

    public static ScriptEntry read(final DataReader reader) throws IOException {
        final ScriptEntry se = new ScriptEntry();
        se.actionCode = ActionCode.valueOf(reader.readString());
        final int len = reader.readInt();
        if (len == 0) {
            se.actionArgs = null;
        } else {
            final ScriptEntryArgument[] args = new ScriptEntryArgument[len];
            for (int x = 0; x < len; x++) {
                args[x] = ScriptEntryArgument.read(reader);
            }
            se.actionArgs = args;
        }
        return se;
    }

    public void write(final DataWriter writer) throws IOException {
        writer.writeString(this.actionCode.toString());
        if (this.actionArgs == null) {
            writer.writeInt(0);
        } else {
            writer.writeInt(this.actionArgs.length);
            for (final ScriptEntryArgument actionArg : this.actionArgs) {
                actionArg.write(writer);
            }
        }
    }
}
