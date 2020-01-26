/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map;

import java.io.IOException;

import com.puttysoftware.llds.CloneableObject;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class MapNote extends CloneableObject {
    // Fields
    private String contents;

    // Constructor
    public MapNote() {
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
    public Object clone() {
        final MapNote copy = new MapNote();
        copy.contents = this.contents;
        return copy;
    }

    static MapNote readNote(final XDataReader reader) throws IOException {
        final MapNote mn = new MapNote();
        mn.contents = reader.readString();
        return mn;
    }

    void writeNote(final XDataWriter writer) throws IOException {
        writer.writeString(this.contents);
    }
}
