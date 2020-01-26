/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.GenericInvisibleConditionalTeleport;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class InvisibleOneShotConditionalTeleport
        extends GenericInvisibleConditionalTeleport {
    // Constructors
    public InvisibleOneShotConditionalTeleport() {
        super(ObjectImageConstants.OBJECT_IMAGE_ONE_SHOT_CONDITIONAL);
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
        MasterMaze.getApplication().getGameManager().decay();
    }

    @Override
    public String getGameName() {
        return "Empty";
    }
}