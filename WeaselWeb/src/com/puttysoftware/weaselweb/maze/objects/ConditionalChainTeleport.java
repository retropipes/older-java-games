/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericConditionalTeleport;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class ConditionalChainTeleport extends GenericConditionalTeleport {
    // Constructors
    public ConditionalChainTeleport() {
        super();
    }

    @Override
    public final void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = WeaselWeb.getApplication();
        int testVal;
        if (this.getSunMoon() == GenericConditionalTeleport.TRIGGER_SUN) {
            testVal = inv.getItemCount(new SunStone());
        } else if (this
                .getSunMoon() == GenericConditionalTeleport.TRIGGER_MOON) {
            testVal = inv.getItemCount(new MoonStone());
        } else {
            testVal = 0;
        }
        if (testVal >= this.getTriggerValue()) {
            app.getGameManager().updatePositionAbsoluteNoEvents(
                    this.getDestinationRow2(), this.getDestinationColumn2(),
                    this.getDestinationFloor2(), this.getDestinationLevel());
        } else {
            app.getGameManager().updatePositionAbsoluteNoEvents(
                    this.getDestinationRow(), this.getDestinationColumn(),
                    this.getDestinationFloor(), this.getDestinationLevel());
        }
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
        this.postMoveActionHook();
    }

    @Override
    public String getName() {
        return "Conditional Chain Teleport";
    }

    @Override
    public String getPluralName() {
        return "Conditional Chain Teleports";
    }

    @Override
    public String getDescription() {
        return "Conditional Chain Teleports send you to one of two predetermined destinations when stepped on, depending on how many Sun or Moon Stones are in your inventory.";
    }
}