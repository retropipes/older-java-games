/*  LaserTank: An Arena-Solving Game
 Copyright (C) 2008-2013 Eric Ahnell

 Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.lasertank.arena.objects;

import com.puttysoftware.lasertank.LaserTank;
import com.puttysoftware.lasertank.arena.abstractobjects.AbstractArenaObject;
import com.puttysoftware.lasertank.arena.abstractobjects.AbstractDisruptedObject;
import com.puttysoftware.lasertank.resourcemanagers.SoundConstants;
import com.puttysoftware.lasertank.resourcemanagers.SoundManager;
import com.puttysoftware.lasertank.utilities.Direction;
import com.puttysoftware.lasertank.utilities.LaserTypeConstants;
import com.puttysoftware.lasertank.utilities.MaterialConstants;
import com.puttysoftware.lasertank.utilities.TypeConstants;

public class DisruptedWoodenWall extends AbstractDisruptedObject {
    private static final int DISRUPTION_START = 20;
    // Fields
    private int disruptionLeft;

    // Constructors
    public DisruptedWoodenWall() {
        super();
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.disruptionLeft = DisruptedWoodenWall.DISRUPTION_START;
        this.activateTimer(1);
        this.setMaterial(MaterialConstants.MATERIAL_WOODEN);
    }

    @Override
    public AbstractArenaObject changesToOnExposure(final int materialID) {
        switch (materialID) {
            case MaterialConstants.MATERIAL_FIRE:
                return new Ground();
            case MaterialConstants.MATERIAL_ICE:
                return new DisruptedIcyWall();
            default:
                return this;
        }
    }

    @Override
    public final int getStringBaseID() {
        return 57;
    }

    @Override
    public Direction laserEnteredAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int laserType,
            final int forceUnits) {
        if (laserType == LaserTypeConstants.LASER_TYPE_MISSILE) {
            // Destroy disrupted wooden wall
            SoundManager.playSound(SoundConstants.SOUND_BOOM);
            LaserTank.getApplication().getGameManager().morph(new Empty(), locX,
                    locY, locZ, this.getLayer());
            return Direction.NONE;
        } else if (laserType == LaserTypeConstants.LASER_TYPE_STUNNER) {
            // Freeze disrupted wooden wall
            SoundManager.playSound(SoundConstants.SOUND_FROZEN);
            LaserTank.getApplication().getGameManager().morph(
                    new DisruptedIcyWall(this.disruptionLeft), locX, locY, locZ,
                    this.getLayer());
            return Direction.NONE;
        } else {
            // Stop laser
            return super.laserEnteredAction(locX, locY, locZ, dirX, dirY,
                    laserType, forceUnits);
        }
    }

    @Override
    public void timerExpiredAction(final int locX, final int locY) {
        this.disruptionLeft--;
        if (this.disruptionLeft == 0) {
            SoundManager.playSound(SoundConstants.SOUND_DISRUPT_END);
            final int z = LaserTank.getApplication().getGameManager()
                    .getPlayerManager().getPlayerLocationZ();
            LaserTank.getApplication().getGameManager().morph(new WoodenWall(),
                    locX, locY, z, this.getLayer());
        } else {
            this.activateTimer(1);
        }
    }
}