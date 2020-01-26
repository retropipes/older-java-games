/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.scenario;

import java.io.IOException;

import com.puttysoftware.dungeondiver3.game.FileHooks;
import com.puttysoftware.dungeondiver3.support.map.SuffixIO;
import com.puttysoftware.dungeondiver3.support.scenario.FormatConstants;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final XDataReader reader, final int formatVersion)
            throws IOException {
        if (formatVersion == FormatConstants.SCENARIO_FORMAT_1) {
            FileHooks.loadGameHookV1(reader);
        } else {
            FileHooks.loadGameHook(reader);
        }
    }

    @Override
    public void writeSuffix(final XDataWriter writer) throws IOException {
        FileHooks.saveGameHook(writer);
    }
}
