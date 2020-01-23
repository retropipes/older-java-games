/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon;

import java.io.IOException;

import com.puttysoftware.llds.CloneableObject;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class DungeonNote extends CloneableObject {
    // Fields
    private String contents;

    // Constructor
    public DungeonNote() {
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
        DungeonNote copy = new DungeonNote();
        copy.contents = this.contents;
        return copy;
    }

    static DungeonNote readNote(XDataReader reader) throws IOException {
        DungeonNote mn = new DungeonNote();
        mn.contents = reader.readString();
        return mn;
    }

    void writeNote(XDataWriter writer) throws IOException {
        writer.writeString(this.contents);
    }
}
