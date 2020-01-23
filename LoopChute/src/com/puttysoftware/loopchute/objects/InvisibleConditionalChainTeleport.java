/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.generic.GenericInvisibleConditionalTeleport;

public class InvisibleConditionalChainTeleport extends
        GenericInvisibleConditionalTeleport {
    // Constructors
    public InvisibleConditionalChainTeleport() {
        super("conditional_chain");
    }

    @Override
    public String getName() {
        return "Invisible Conditional Chain Teleport";
    }

    @Override
    public String getPluralName() {
        return "Invisible Conditional Chain Teleports";
    }

    @Override
    public String getDescription() {
        return "Invisible Conditional Chain Teleports send you to one of two predetermined destinations when stepped on, depending on how many Sun or Moon Stones are in your inventory, and cannot be seen.";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }
}