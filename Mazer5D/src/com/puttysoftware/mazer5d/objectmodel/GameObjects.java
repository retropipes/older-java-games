/*  Mazer5D: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objectmodel;

import java.io.IOException;

import com.puttysoftware.mazer5d.files.versions.MazeVersions;
import com.puttysoftware.xio.XDataReader;

public final class GameObjects {
    // Constructor
    private GameObjects() {
    }

    // Methods
    public static MazeObjectModel getEmptySpace() {
        return GameObjects.createObject(-1);
    }

    public static MazeObjectModel createObject(final int uid) {
        return new MazeObject(uid);
    }

    public static MazeObjectModel readObject(final XDataReader reader,
            final int formatVersion) throws IOException {
        int UID = -1;
        if (formatVersion == MazeVersions.LATEST) {
            UID = reader.readInt();
        }
        MazeObjectModel o = GameObjects.createObject(UID);
        o.loadState(reader, UID);
        return o;
    }
}
