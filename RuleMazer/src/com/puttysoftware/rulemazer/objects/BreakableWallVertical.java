/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.generic.GenericWall;
import com.puttysoftware.rulemazer.generic.MazeObject;
import com.puttysoftware.rulemazer.maze.MazeConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public class BreakableWallVertical extends GenericWall {
    // Constructors
    public BreakableWallVertical() {
        super(true, true);
    }

    @Override
    public void chainReactionAction(final int x, final int y, final int z) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CRACK);
        this.doChainReact(x, y, z);
    }

    public void doChainReact(final int x, final int y, final int z) {
        final Application app = Main.getApplication();
        BreakableWallVertical curr = null;
        try {
            curr = (BreakableWallVertical) app.getMazeManager().getMazeObject(
                    x, y, z, MazeConstants.LAYER_OBJECT);
        } catch (final ClassCastException cce) {
            // We're not a breakable wall vertical, so abort
            return;
        }
        String mo2Name, mo8Name, invalidName, currName;
        invalidName = new EmptyVoid().getName();
        currName = curr.getName();
        final MazeObject mo2 = app.getMazeManager().getMazeObject(x, y - 1, z,
                MazeConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = invalidName;
        }
        final MazeObject mo8 = app.getMazeManager().getMazeObject(x, y + 1, z,
                MazeConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = invalidName;
        }
        app.getGameManager().morph(new Empty(), x, y, z);
        if (mo2Name.equals(currName)) {
            curr.doChainReact(x, y - 1, z);
        }
        if (mo8Name.equals(currName)) {
            curr.doChainReact(x, y + 1, z);
        }
    }

    @Override
    public String getName() {
        return "Breakable Wall Vertical";
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Breakable Walls Vertical";
    }

    @Override
    public String getDescription() {
        return "Breakable Walls Vertical disintegrate when touched, causing other Breakable Walls Vertical nearby to also disintegrate.";
    }
}
