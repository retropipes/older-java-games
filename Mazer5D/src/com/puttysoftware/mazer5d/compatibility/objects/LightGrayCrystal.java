/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericProgrammableKey;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class LightGrayCrystal extends GenericProgrammableKey {
    // Constructors
    public LightGrayCrystal() {
        super("Light Gray");
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.LIGHT_GRAY_CRYSTAL;
    }}