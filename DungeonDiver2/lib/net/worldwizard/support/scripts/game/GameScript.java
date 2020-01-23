package net.worldwizard.support.scripts.game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class GameScript {
    // Fields
    private GameScriptEntry[] actions;
    private ArrayList<GameScriptEntry> tempActions;

    // Constructors
    protected GameScript(final GameScript gs) {
        this.actions = new GameScriptEntry[gs.actions.length];
        System.arraycopy(gs.actions, 0, this.actions, 0, gs.actions.length);
    }

    public GameScript() {
        this.actions = null;
        this.tempActions = new ArrayList<>();
    }

    // Methods
    public int getActionCount() {
        return this.actions.length;
    }

    public void addAction(final GameScriptEntry act) {
        this.tempActions.add(act);
    }

    public void finalizeActions() {
        if (!this.tempActions.isEmpty()) {
            this.tempActions.trimToSize();
            final GameScriptEntry[] acts = this.tempActions
                    .toArray(new GameScriptEntry[this.tempActions.size()]);
            this.actions = acts;
            this.tempActions = null;
        }
    }

    public GameScriptEntry getAction(final int index) {
        return this.actions[index];
    }

    public static GameScript read(final XDataReader reader) throws IOException {
        final GameScript s = new GameScript();
        final int len = reader.readInt();
        if (len == 0) {
            s.actions = null;
        } else {
            final GameScriptEntry[] acts = new GameScriptEntry[len];
            for (int x = 0; x < len; x++) {
                acts[x] = GameScriptEntry.read(reader);
            }
            s.actions = acts;
        }
        return s;
    }

    public void write(final XDataWriter writer) throws IOException {
        if (this.actions == null) {
            writer.writeInt(0);
        } else {
            writer.writeInt(this.actions.length);
            for (final GameScriptEntry action : this.actions) {
                action.write(writer);
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(this.actions);
        result = prime * result
                + (this.tempActions == null ? 0 : this.tempActions.hashCode());
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
        if (!(obj instanceof GameScript)) {
            return false;
        }
        final GameScript other = (GameScript) obj;
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
