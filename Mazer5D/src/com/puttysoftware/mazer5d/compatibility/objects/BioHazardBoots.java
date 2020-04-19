/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericBoots;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class BioHazardBoots extends GenericBoots {
    // Constructors
    public BioHazardBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Bio-Hazard Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Bio-Hazard Boots";
    }

    @Override
    public String getDescription() {
        return "Bio-Hazard Boots allow walking on slime. Note that you can only wear one pair of boots at once.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.BIO_HAZARD_BOOTS;
    }}
