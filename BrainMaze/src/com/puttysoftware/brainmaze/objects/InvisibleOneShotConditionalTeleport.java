/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.BrainMaze;
import com.puttysoftware.brainmaze.generic.GenericInvisibleConditionalTeleport;

public class InvisibleOneShotConditionalTeleport
        extends GenericInvisibleConditionalTeleport {
    // Constructors
    public InvisibleOneShotConditionalTeleport() {
        super("one_shot_conditional");
    }

    @Override
    public String getName() {
        return "Invisible One-Shot Conditional Teleport";
    }

    @Override
    public String getPluralName() {
        return "Invisible One-Shot Conditional Teleports";
    }

    @Override
    public String getDescription() {
        return "Invisible One-Shot Conditional Teleports send you to one of two predetermined destinations when stepped on, depending on how many Sun or Moon Stones are in your inventory, then disappear, and cannot be seen.";
    }

    @Override
    public void postMoveActionHook() {
        BrainMaze.getApplication().getGameManager().decay();
    }

    @Override
    public String getGameName() {
        return "Empty";
    }
}