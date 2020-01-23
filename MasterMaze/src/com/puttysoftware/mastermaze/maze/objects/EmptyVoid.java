/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.MazeConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericWall;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;

public class EmptyVoid extends GenericWall {
    // Properties
    private String currAppearance;

    // Constructors
    public EmptyVoid() {
        super(false, false, ColorConstants.COLOR_NONE);
        this.currAppearance = "Void";
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_VOID;
    }

    @Override
    public MazeObject gameRenderHook(final int x, final int y, final int z) {
        this.determineCurrentAppearance(x, y, z);
        if (this.currAppearance.equals(this.getName())) {
            return this;
        } else {
            return new SealingWall();
        }
    }

    @Override
    public boolean isConditionallySolid(final ObjectInventory inv) {
        // Disallow passing through Void under ANY circumstances
        return true;
    }

    @Override
    public void determineCurrentAppearance(final int x, final int y, final int z) {
        final Application app = MasterMaze.getApplication();
        String mo1Name, mo2Name, mo3Name, mo4Name, mo6Name, mo7Name, mo8Name, mo9Name, thisName;
        thisName = this.getName();
        final MazeObject mo1 = app.getMazeManager().getMazeObject(x - 1, y - 1,
                z, MazeConstants.LAYER_OBJECT);
        try {
            mo1Name = mo1.getName();
        } catch (final NullPointerException np) {
            mo1Name = thisName;
        }
        final MazeObject mo2 = app.getMazeManager().getMazeObject(x - 1, y, z,
                MazeConstants.LAYER_OBJECT);
        try {
            mo2Name = mo2.getName();
        } catch (final NullPointerException np) {
            mo2Name = thisName;
        }
        final MazeObject mo3 = app.getMazeManager().getMazeObject(x - 1, y + 1,
                z, MazeConstants.LAYER_OBJECT);
        try {
            mo3Name = mo3.getName();
        } catch (final NullPointerException np) {
            mo3Name = thisName;
        }
        final MazeObject mo4 = app.getMazeManager().getMazeObject(x, y - 1, z,
                MazeConstants.LAYER_OBJECT);
        try {
            mo4Name = mo4.getName();
        } catch (final NullPointerException np) {
            mo4Name = thisName;
        }
        final MazeObject mo6 = app.getMazeManager().getMazeObject(x, y + 1, z,
                MazeConstants.LAYER_OBJECT);
        try {
            mo6Name = mo6.getName();
        } catch (final NullPointerException np) {
            mo6Name = thisName;
        }
        final MazeObject mo7 = app.getMazeManager().getMazeObject(x + 1, y - 1,
                z, MazeConstants.LAYER_OBJECT);
        try {
            mo7Name = mo7.getName();
        } catch (final NullPointerException np) {
            mo7Name = thisName;
        }
        final MazeObject mo8 = app.getMazeManager().getMazeObject(x + 1, y, z,
                MazeConstants.LAYER_OBJECT);
        try {
            mo8Name = mo8.getName();
        } catch (final NullPointerException np) {
            mo8Name = thisName;
        }
        final MazeObject mo9 = app.getMazeManager().getMazeObject(x + 1, y + 1,
                z, MazeConstants.LAYER_OBJECT);
        try {
            mo9Name = mo9.getName();
        } catch (final NullPointerException np) {
            mo9Name = thisName;
        }
        if (!thisName.equals(mo1Name) || !thisName.equals(mo2Name)
                || !thisName.equals(mo3Name) || !thisName.equals(mo4Name)
                || !thisName.equals(mo6Name) || !thisName.equals(mo7Name)
                || !thisName.equals(mo8Name) || !thisName.equals(mo9Name)) {
            this.currAppearance = "Sealing Wall";
        } else {
            this.currAppearance = "Void";
        }
    }

    @Override
    public String getName() {
        return "Void";
    }

    @Override
    public String getGameName() {
        return this.currAppearance;
    }

    @Override
    public String getPluralName() {
        return "Voids";
    }

    @Override
    public String getDescription() {
        return "The Void surrounds the maze, and cannot be altered in any way.";
    }
}
