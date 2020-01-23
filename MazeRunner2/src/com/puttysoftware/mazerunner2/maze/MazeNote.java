/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze;

import java.io.IOException;

import com.puttysoftware.llds.CloneableObject;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;
import com.puttysoftware.xio.legacy.XLegacyDataReader;

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

    public void setContents(String newContents) {
        this.contents = newContents;
    }

    @Override
    public Object clone() {
        MazeNote copy = new MazeNote();
        copy.contents = this.contents;
        return copy;
    }

    static MazeNote readLegacyNote(XLegacyDataReader reader) throws IOException {
        MazeNote mn = new MazeNote();
        mn.contents = reader.readString();
        return mn;
    }

    static MazeNote readNote(XDataReader reader) throws IOException {
        MazeNote mn = new MazeNote();
        mn.contents = reader.readString();
        return mn;
    }

    void writeNote(XDataWriter writer) throws IOException {
        writer.writeString(this.contents);
    }
}
