/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.generic.GenericConditionalTeleport;

public class ConditionalTeleport extends GenericConditionalTeleport {
    // Constructors
    public ConditionalTeleport() {
        super("conditional");
    }

    @Override
    public String getName() {
        return "Conditional Teleport";
    }

    @Override
    public String getPluralName() {
        return "Conditional Teleports";
    }

    @Override
    public String getDescription() {
        return "Conditional Teleports send you to one of two predetermined destinations when stepped on, depending on how many Sun or Moon Stones are in your inventory.";
    }
}