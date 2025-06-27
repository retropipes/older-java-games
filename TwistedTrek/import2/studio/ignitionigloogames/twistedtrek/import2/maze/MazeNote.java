/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze;

import java.io.IOException;

import studio.ignitionigloogames.llds.CloneableObject;
import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public class MazeNote extends CloneableObject {
    // Fields
    private String contents;

    // Constructor
    public MazeNote() {
        this.contents = "Empty Note";
    }

    // Methods
    public String getContents() {
        return this.contents;
    }

    public void setContents(final String newContents) {
        this.contents = newContents;
    }

    @Override
    public MazeNote clone() {
        final MazeNote copy = (MazeNote) super.clone();
        copy.contents = this.contents;
        return copy;
    }

    static MazeNote readNote(final XDataReader reader) throws IOException {
        final MazeNote mn = new MazeNote();
        mn.contents = reader.readString();
        return mn;
    }

    void writeNote(final XDataWriter writer) throws IOException {
        writer.writeString(this.contents);
    }
}
