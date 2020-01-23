package com.puttysoftware.audio.mod;

public class Pattern {
    public int numRows;
    public byte[] data;

    public Pattern(final int numChannels, final int newNumRows) {
        this.numRows = newNumRows;
        this.data = new byte[numChannels * newNumRows * 5];
    }

    public void getNote(final int index, final Note note) {
        final int offset = index * 5;
        note.key = this.data[offset] & 0xFF;
        note.instrument = this.data[offset + 1] & 0xFF;
        note.volume = this.data[offset + 2] & 0xFF;
        note.effect = this.data[offset + 3] & 0xFF;
        note.param = this.data[offset + 4] & 0xFF;
    }
}
