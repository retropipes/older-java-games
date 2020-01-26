/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank.arena;

import java.io.IOException;

import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public interface AbstractSuffixIO {
    void writeSuffix(XDataWriter writer) throws IOException;

    void readSuffix(XDataReader reader, int formatVersion) throws IOException;
}
