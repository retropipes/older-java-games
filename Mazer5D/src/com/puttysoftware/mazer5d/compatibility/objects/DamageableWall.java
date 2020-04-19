/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericWall;
import com.puttysoftware.mazer5d.compatibility.abc.TypeConstants;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class DamageableWall extends GenericWall {
    // Constructors
    public DamageableWall() {
        super();
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final ObjectInventory inv) {
        this.moveFailedAction(true, locX, locY, inv);
        return false;
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        // Crack the wall
        final int pz = Mazer5D.getBagOStuff().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        Mazer5D.getBagOStuff().getGameManager().morph(new CrackedWall(), dirX,
                dirY, pz);
        SoundPlayer.playSound(SoundIndex.CRACK, SoundGroup.GAME);
    }

    @Override
    public String getName() {
        return "Damageable Wall";
    }

    @Override
    public String getPluralName() {
        return "Damageable Walls";
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getDescription() {
        return "Damageable Walls turn into Cracked Walls when hit.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.DAMAGEABLE_WALL;
    }}