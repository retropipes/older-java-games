/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.maze.generic.GenericConditionalTeleport;

public class InvisibleOneShotConditionalTeleport
        extends GenericConditionalTeleport {
    // Constructors
    public InvisibleOneShotConditionalTeleport() {
        super();
        this.getAttributeGroup().unlinkGame();
        this.getAttributeGroup().getGameBase().setImageName(this, "Empty");
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
        WeaselWeb.getApplication().getGameManager().decay();
    }
}