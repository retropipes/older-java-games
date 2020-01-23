/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon;

import java.io.IOException;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;
import net.dynamicdungeon.llds.CloneableObject;

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

    public void setContents(final String newContents) {
	this.contents = newContents;
    }

    @Override
    public DungeonNote clone() {
	final DungeonNote copy = (DungeonNote) super.clone();
	copy.contents = this.contents;
	return copy;
    }

    static DungeonNote readNote(final DatabaseReader reader) throws IOException {
	final DungeonNote mn = new DungeonNote();
	mn.contents = reader.readString();
	return mn;
    }

    void writeNote(final DatabaseWriter writer) throws IOException {
	writer.writeString(this.contents);
    }
}
