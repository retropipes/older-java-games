/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank.arena;

import java.io.IOException;

import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public interface AbstractPrefixIO {
    void writePrefix(XDataWriter writer) throws IOException;

    int readPrefix(XDataReader reader) throws IOException;
}
