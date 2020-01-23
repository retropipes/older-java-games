/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.generic.GenericInfiniteKey;

public class Axe extends GenericInfiniteKey {
    // Constructors
    public Axe() {
        super();
    }

    @Override
    public String getName() {
        return "Axe";
    }

    @Override
    public String getPluralName() {
        return "Axe";
    }

    @Override
    public String getDescription() {
        return "With an Axe, Trees can be cut down. Axes never lose their ability to cut trees.";
    }
}