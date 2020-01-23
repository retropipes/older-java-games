/*  LTRemix: An Arena-Solving Game
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ltremix.arena;

import java.io.IOException;

import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public interface AbstractSuffixIO {
    public void writeSuffix(XDataWriter writer) throws IOException;

    public void readSuffix(XDataReader reader, int formatVersion)
            throws IOException;
}
