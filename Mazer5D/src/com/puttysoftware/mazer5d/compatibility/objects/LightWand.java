/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericWand;
import com.puttysoftware.mazer5d.compatibility.abc.MazeObjectModel;
import com.puttysoftware.mazer5d.compatibility.maze.MazeModel;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.Layers;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class LightWand extends GenericWand {
    // Constructors
    public LightWand() {
        super();
    }

    @Override
    public String getName() {
        return "Light Wand";
    }

    @Override
    public String getPluralName() {
        return "Light Wands";
    }

    @Override
    public void useHelper(final int x, final int y, final int z) {
        final MazeModel m = Mazer5D.getBagOStuff().getMazeManager().getMaze();
        final MazeObjectModel obj = m.getCell(x, y, z, Layers.OBJECT);
        if (obj.getName().equals("Empty")) {
            // Create a Light Gem
            this.useAction(new LightGem(), x, y, z);
            SoundPlayer.playSound(SoundIndex.LIGHT, SoundGroup.GAME);
        } else if (obj.getName().equals("Dark Gem")) {
            // Destroy the Dark Gem
            this.useAction(new Empty(), x, y, z);
            SoundPlayer.playSound(SoundIndex.SHATTER, SoundGroup.GAME);
        }
    }

    @Override
    public String getDescription() {
        return "Light Wands have 2 uses. When aimed at an empty space, they create a Light Gem. When aimed at a Dark Gem, it is destroyed.";
    }


    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.LIGHT_WAND;
    }}
