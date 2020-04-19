/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericCarpet;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class BlueCarpet extends GenericCarpet {
    // Constructors
    public BlueCarpet() {
        super("Blue");
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.BLUE_CARPET;
    }}