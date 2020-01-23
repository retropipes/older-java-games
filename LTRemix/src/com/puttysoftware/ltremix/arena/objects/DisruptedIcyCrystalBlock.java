/*  LTRemix: An Arena-Solving Game
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ltremix.arena.objects;

import com.puttysoftware.ltremix.LTRemix;
import com.puttysoftware.ltremix.arena.abstractobjects.AbstractReactionDisruptedObject;
import com.puttysoftware.ltremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ltremix.resourcemanagers.SoundManager;
import com.puttysoftware.ltremix.utilities.DirectionConstants;
import com.puttysoftware.ltremix.utilities.DirectionResolver;
import com.puttysoftware.ltremix.utilities.LaserTypeConstants;
import com.puttysoftware.ltremix.utilities.MaterialConstants;
import com.puttysoftware.ltremix.utilities.RangeTypeConstants;

public class DisruptedIcyCrystalBlock extends AbstractReactionDisruptedObject {
    // Fields
    private int disruptionLeft;
    private static final int DISRUPTION_START = 20;

    // Constructors
    public DisruptedIcyCrystalBlock() {
        super();
        this.disruptionLeft = DisruptedIcyCrystalBlock.DISRUPTION_START;
        this.activateTimer(1);
        this.setMaterial(MaterialConstants.MATERIAL_ICE);
    }

    @Override
    public int laserEnteredActionHook(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int laserType,
            final int forceUnits) {
        if (laserType == LaserTypeConstants.LASER_TYPE_MISSILE) {
            // Destroy disrupted icy crystal block
            SoundManager.playSound(SoundConstants.SOUND_BOOM);
            LTRemix.getApplication().getGameManager().morph(new Empty(), locX,
                    locY, locZ, this.getPrimaryLayer());
            return DirectionConstants.NONE;
        } else if (laserType == LaserTypeConstants.LASER_TYPE_BLUE) {
            // Reflect laser
            return DirectionResolver.resolveRelativeDirectionInvert(dirX, dirY);
        } else {
            // Pass laser through
            return DirectionResolver.resolveRelativeDirection(dirX, dirY);
        }
    }

    @Override
    public void timerExpiredAction(final int locX, final int locY) {
        this.disruptionLeft--;
        if (this.disruptionLeft == 0) {
            SoundManager.playSound(SoundConstants.SOUND_DISRUPT_END);
            final int z = LTRemix.getApplication().getGameManager()
                    .getPlayerManager().getPlayerLocationZ();
            final IcyCrystalBlock icb = new IcyCrystalBlock();
            if (this.hasPreviousState()) {
                icb.setPreviousState(this.getPreviousState());
            }
            LTRemix.getApplication().getGameManager().morph(icb, locX, locY, z,
                    this.getPrimaryLayer());
        } else {
            this.activateTimer(1);
        }
    }

    @Override
    public int laserExitedAction(final int locX, final int locY, final int locZ,
            final int dirX, final int dirY, final int laserType) {
        return DirectionResolver.resolveRelativeDirection(dirX, dirY);
    }

    @Override
    public boolean rangeActionHook(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int rangeType,
            final int forceUnits) {
        if (rangeType == RangeTypeConstants.RANGE_TYPE_BOMB
                || RangeTypeConstants.getMaterialForRangeType(
                        rangeType) == MaterialConstants.MATERIAL_METALLIC) {
            // Destroy disrupted icy crystal block
            LTRemix.getApplication().getGameManager().morph(new Empty(),
                    locX + dirX, locY + dirY, locZ, this.getPrimaryLayer());
            return true;
        } else {
            // Do nothing
            return true;
        }
    }

    @Override
    public boolean doLasersPassThrough() {
        return true;
    }

    @Override
    public final int getStringBaseID() {
        return 129;
    }
}