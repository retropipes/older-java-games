/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.compatibility.abc.GenericBoots;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class PasswallBoots extends GenericBoots {
    // Constructors
    public PasswallBoots() {
        super();
    }

    @Override
    public String getName() {
        return "Passwall Boots";
    }

    @Override
    public String getPluralName() {
        return "Pairs of Passwall Boots";
    }

    @Override
    public String getDescription() {
        return "Passwall Boots allow you to pass through most walls as you walk. Note that you can only wear one pair of boots at once.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.PASSWALL_BOOTS;
    }
}
