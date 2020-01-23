/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.scenario;

import java.io.IOException;

import com.puttysoftware.gemma.game.FileHooks;
import com.puttysoftware.gemma.support.map.SuffixIO;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(XDataReader reader, int formatVersion)
            throws IOException {
        FileHooks.loadGameHook(reader);
    }

    @Override
    public void writeSuffix(XDataWriter writer) throws IOException {
        FileHooks.saveGameHook(writer);
    }
}
