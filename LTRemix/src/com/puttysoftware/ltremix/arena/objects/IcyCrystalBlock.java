/*  LTRemix: An Arena-Solving Game
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ltremix.arena.objects;

import com.puttysoftware.ltremix.LTRemix;
import com.puttysoftware.ltremix.arena.abstractobjects.AbstractArenaObject;
import com.puttysoftware.ltremix.arena.abstractobjects.AbstractReactionWall;
import com.puttysoftware.ltremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ltremix.resourcemanagers.SoundManager;
import com.puttysoftware.ltremix.utilities.DirectionConstants;
import com.puttysoftware.ltremix.utilities.LaserTypeConstants;
import com.puttysoftware.ltremix.utilities.MaterialConstants;
import com.puttysoftware.ltremix.utilities.RangeTypeConstants;
import com.puttysoftware.ltremix.utilities.TypeConstants;

public class IcyCrystalBlock extends AbstractReactionWall {
    // Constructors
    public IcyCrystalBlock() {
        super();
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.setMaterial(MaterialConstants.MATERIAL_ICE);
    }

    @Override
    public int laserEnteredActionHook(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int laserType,
            final int forceUnits) {
        if (laserType == LaserTypeConstants.LASER_TYPE_MISSILE) {
            // Destroy icy crystal block
            SoundManager.playSound(SoundConstants.SOUND_BOOM);
            LTRemix.getApplication().getGameManager().morph(new Empty(), locX,
                    locY, locZ, this.getPrimaryLayer());
            return DirectionConstants.NONE;
        } else if (laserType == LaserTypeConstants.LASER_TYPE_DISRUPTOR) {
            // Disrupt icy crystal block
            SoundManager.playSound(SoundConstants.SOUND_DISRUPTED);
            final DisruptedIcyCrystalBlock dicb = new DisruptedIcyCrystalBlock();
            if (this.hasPreviousState()) {
                dicb.setPreviousState(this.getPreviousState());
            }
            LTRemix.getApplication().getGameManager().morph(dicb, locX, locY,
                    locZ, this.getPrimaryLayer());
            return DirectionConstants.NONE;
        } else {
            // Stop laser
            SoundManager.playSound(SoundConstants.SOUND_LASER_DIE);
            return DirectionConstants.NONE;
        }
    }

    @Override
    public boolean rangeActionHook(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int rangeType,
            final int forceUnits) {
        if (RangeTypeConstants.getMaterialForRangeType(
                rangeType) == MaterialConstants.MATERIAL_METALLIC) {
            // Destroy icy crystal block
            LTRemix.getApplication().getGameManager().morph(new Empty(),
                    locX + dirX, locY + dirY, locZ, this.getPrimaryLayer());
            return true;
        } else if (RangeTypeConstants.getMaterialForRangeType(
                rangeType) == MaterialConstants.MATERIAL_FIRE) {
            // Heat up crystal block
            SoundManager.playSound(SoundConstants.SOUND_MELT);
            LTRemix.getApplication().getGameManager().morph(
                    this.changesToOnExposure(MaterialConstants.MATERIAL_FIRE),
                    locX + dirX, locY + dirY, locZ, this.getPrimaryLayer());
            return true;
        } else if (RangeTypeConstants.getMaterialForRangeType(
                rangeType) == MaterialConstants.MATERIAL_ICE) {
            // Do nothing
            return true;
        } else {
            // Do nothing
            return true;
        }
    }

    @Override
    public final int getStringBaseID() {
        return 127;
    }

    @Override
    public AbstractArenaObject changesToOnExposure(final int materialID) {
        switch (materialID) {
            case MaterialConstants.MATERIAL_FIRE:
                if (this.hasPreviousState()) {
                    return this.getPreviousState();
                } else {
                    return new CrystalBlock();
                }
            default:
                return this;
        }
    }
}