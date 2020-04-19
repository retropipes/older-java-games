/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericWand;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class WallMakingWand extends GenericWand {
    public WallMakingWand() {
        super();
    }

    @Override
    public String getName() {
        return "Wall-Making Wand";
    }

    @Override
    public String getPluralName() {
        return "Wall-Making Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        this.useAction(new Wall(), x, y, z);
        SoundPlayer.playSound(SoundIndex.CREATE, SoundGroup.GAME);
    }

    @Override
    public String getDescription() {
        return "Wall-Making Wands will create an ordinary wall in the target square when used.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.WALL_MAKING_WAND;
    }}
