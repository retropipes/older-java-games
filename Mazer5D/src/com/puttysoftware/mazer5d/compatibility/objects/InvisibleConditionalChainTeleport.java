/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericConditionalTeleport;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class InvisibleConditionalChainTeleport
        extends GenericConditionalTeleport {
    // Constructors
    public InvisibleConditionalChainTeleport() {
        super();
    }

    @Override
    public final void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final BagOStuff app = Mazer5D.getBagOStuff();
        int testVal;
        if (this.getSunMoon() == GenericConditionalTeleport.TRIGGER_SUN) {
            testVal = inv.getItemCount(MazeObjects.SUN_STONE);
        } else if (this
                .getSunMoon() == GenericConditionalTeleport.TRIGGER_MOON) {
            testVal = inv.getItemCount(MazeObjects.MOON_STONE);
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
        Mazer5D.getBagOStuff().showMessage("Invisible Teleport!");
        SoundPlayer.playSound(SoundIndex.TELEPORT, SoundGroup.GAME);
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


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.INVISIBLE_CONDITIONAL_CHAIN_TELEPORT;
    }}