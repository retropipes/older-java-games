/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericConditionalTeleport;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class InvisibleConditionalChainTeleport
        extends GenericConditionalTeleport {
    // Constructors
    public InvisibleConditionalChainTeleport() {
        super();
    }

    @Override
    public final void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = WidgetWarren.getApplication();
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
        WidgetWarren.getApplication().showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
        this.postMoveActionHook();
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