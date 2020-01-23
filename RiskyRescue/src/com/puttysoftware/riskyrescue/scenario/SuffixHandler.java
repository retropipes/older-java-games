/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.scenario;

import java.io.IOException;

import com.puttysoftware.riskyrescue.game.FileHooks;
import com.puttysoftware.riskyrescue.map.SuffixIO;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        FileHooks.loadGameHook(reader);
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        FileHooks.saveGameHook(writer);
    }
}
