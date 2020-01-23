package net.worldwizard.support.scripts.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class GameScriptEntry {
    // Fields
    private GameActionCode actionCode;
    private GameScriptEntryArgument[] actionArgs;
    private ArrayList<GameScriptEntryArgument> tempArgs;

    // Constructors
    public GameScriptEntry() {
        this.actionCode = GameActionCode.NONE;
        this.actionArgs = null;
        this.tempArgs = new ArrayList<>();
    }

    // Methods
    public GameScriptEntryArgument getFirstActionArg() {
        return this.actionArgs[0];
    }

    public GameScriptEntryArgument getActionArg(final int index) {
        return this.actionArgs[index];
    }

    public GameScriptEntryArgument[] getActionArgs() {
        return this.actionArgs;
    }

    public void addActionArg(final GameScriptEntryArgument arg) {
        this.tempArgs.add(arg);
    }

    public void finalizeActionArgs() {
        if (!this.tempArgs.isEmpty()) {
            this.tempArgs.trimToSize();
            final GameScriptEntryArgument[] args = this.tempArgs
                    .toArray(new GameScriptEntryArgument[this.tempArgs.size()]);
            this.setActionArgs(args);
            this.tempArgs = null;
        }
    }

    public void setActionArgs(final GameScriptEntryArgument... newActionArgs) {
        this.actionArgs = newActionArgs;
    }

    public GameActionCode getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(final GameActionCode newActionCode) {
        this.actionCode = newActionCode;
    }

    public static GameScriptEntry read(final XDataReader reader)
            throws IOException {
        final GameScriptEntry se = new GameScriptEntry();
        se.actionCode = GameActionCode.valueOf(reader.readString());
        final int len = reader.readInt();
        if (len == 0) {
            se.actionArgs = null;
        } else {
            final GameScriptEntryArgument[] args = new GameScriptEntryArgument[len];
            for (int x = 0; x < len; x++) {
                args[x] = GameScriptEntryArgument.read(reader);
            }
            se.actionArgs = args;
        }
        return se;
    }

    public void write(final XDataWriter writer) throws IOException {
        writer.writeString(this.actionCode.toString());
        if (this.actionArgs == null) {
            writer.writeInt(0);
        } else {
            writer.writeInt(this.actionArgs.length);
            for (final GameScriptEntryArgument actionArg : this.actionArgs) {
                actionArg.write(writer);
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(this.actionArgs);
        result = prime * result
                + (this.actionCode == null ? 0 : this.actionCode.hashCode());
        result = prime * result
                + (this.tempArgs == null ? 0 : this.tempArgs.hashCode());
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
        if (!(obj instanceof GameScriptEntry)) {
            return false;
        }
        final GameScriptEntry other = (GameScriptEntry) obj;
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
