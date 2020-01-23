/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.generic.GenericConditionalTeleport;

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
        return "One-Shot Conditional Teleports send you to one of two predetermined destinations when stepped on, depending on how many Sun Stones are in your inventory, then disappear.";
    }

    @Override
    public void postMoveActionHook() {
        Main.getApplication().getGameManager().decay();
    }
}