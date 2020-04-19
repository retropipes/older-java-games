/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GenericConditionalTeleport;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class OneShotConditionalTeleport extends GenericConditionalTeleport {
    // Constructors
    public OneShotConditionalTeleport() {
        super();
    }

    @Override
    public String getName() {
        return "One-Shot Conditional Teleport";
    }

    @Override
    public String getPluralName() {
        return "One-Shot Conditional Teleports";
    }

    @Override
    public String getDescription() {
        return "One-Shot Conditional Teleports send you to one of two predetermined destinations when stepped on, depending on how many Sun or Moon Stones are in your inventory, then disappear.";
    }

    @Override
    public void postMoveActionHook() {
        Mazer5D.getBagOStuff().getGameManager().decay();
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.ONE_SHOT_CONDITIONAL_TELEPORT;
    }
}