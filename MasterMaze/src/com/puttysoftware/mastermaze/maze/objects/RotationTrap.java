/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericTrap;
import com.puttysoftware.mastermaze.maze.generic.MazeObject;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class RotationTrap extends GenericTrap {
    // Fields
    private int radius;
    private boolean direction;
    private static final boolean CLOCKWISE = true;
    private static final boolean COUNTERCLOCKWISE = false;

    // Constructors
    public RotationTrap() {
        super(ColorConstants.COLOR_LIGHT_PURPLE,
                ObjectImageConstants.OBJECT_IMAGE_SMALL_ROTATION,
                ColorConstants.COLOR_PURPLE);
        this.radius = 1;
        this.direction = RotationTrap.CLOCKWISE;
    }

    public RotationTrap(final int newRadius, final boolean newDirection) {
        super(ColorConstants.COLOR_LIGHT_PURPLE,
                ObjectImageConstants.OBJECT_IMAGE_SMALL_ROTATION,
                ColorConstants.COLOR_PURPLE);
        this.radius = newRadius;
        this.direction = newDirection;
    }

    @Override
    public RotationTrap clone() {
        final RotationTrap copy = (RotationTrap) super.clone();
        copy.radius = this.radius;
        copy.direction = this.direction;
        return copy;
    }

    @Override
    public void editorProbeHook() {
        String dir;
        if (this.direction == RotationTrap.CLOCKWISE) {
            dir = "Clockwise";
        } else {
            dir = "Counterclockwise";
        }
        MasterMaze.getApplication().showMessage(
                this.getName() + " (Radius " + this.radius + ", Direction "
                        + dir + ")");
    }

    @Override
    public MazeObject editorPropertiesHook() {
        int r = this.radius;
        final String[] rChoices = new String[] { "1", "2", "3" };
        final String rres = CommonDialogs.showInputDialog("Rotation Radius:",
                "Editor", rChoices, rChoices[r - 1]);
        try {
            r = Integer.parseInt(rres);
        } catch (final NumberFormatException nf) {
            // Ignore
        }
        boolean d = this.direction;
        int di;
        if (d) {
            di = 0;
        } else {
            di = 1;
        }
        final String[] dChoices = new String[] { "Clockwise",
                "Counterclockwise" };
        final String dres = CommonDialogs.showInputDialog(
                "Rotation Direction:", "Editor", dChoices, dChoices[di]);
        if (dres.equals(dChoices[0])) {
            d = RotationTrap.CLOCKWISE;
        } else {
            d = RotationTrap.COUNTERCLOCKWISE;
        }
        return new RotationTrap(r, d);
    }

    @Override
    public String getName() {
        return "Rotation Trap";
    }

    @Override
    public String getPluralName() {
        return "Rotation Traps";
    }

    @Override
    protected MazeObject readMazeObjectHook(final XDataReader reader,
            final int formatVersion) throws IOException {
        this.radius = reader.readInt();
        this.direction = reader.readBoolean();
        return this;
    }

    @Override
    protected void writeMazeObjectHook(final XDataWriter writer)
            throws IOException {
        writer.writeInt(this.radius);
        writer.writeBoolean(this.direction);
    }

    @Override
    public int getCustomFormat() {
        return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.direction) {
            MasterMaze.getApplication().getGameManager()
                    .doClockwiseRotate(this.radius);
        } else {
            MasterMaze.getApplication().getGameManager()
                    .doCounterclockwiseRotate(this.radius);
        }
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
    }

    @Override
    public String getDescription() {
        return "Rotation Traps rotate part of the maze when stepped on.";
    }
}
