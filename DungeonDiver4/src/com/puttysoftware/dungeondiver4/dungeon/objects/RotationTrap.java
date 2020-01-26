/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import java.io.IOException;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTrap;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class RotationTrap extends AbstractTrap {
    // Fields
    private int radius;
    private boolean direction;
    private static final boolean CLOCKWISE = true;
    private static final boolean COUNTERCLOCKWISE = false;
    private static final String[] rChoices = new String[] { "1", "2", "3" };
    private static final String[] dChoices = new String[] { "Clockwise",
            "Counterclockwise" };

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
        DungeonDiver4.getApplication().showMessage(this.getName() + " (Radius "
                + this.radius + ", Direction " + dir + ")");
    }

    @Override
    public AbstractDungeonObject editorPropertiesHook() {
        int r = this.radius;
        final String rres = CommonDialogs.showInputDialog("Rotation Radius:",
                "Editor", RotationTrap.rChoices, RotationTrap.rChoices[r - 1]);
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
        final String dres = CommonDialogs.showInputDialog("Rotation Direction:",
                "Editor", RotationTrap.dChoices, RotationTrap.dChoices[di]);
        if (dres.equals(RotationTrap.dChoices[0])) {
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
    protected AbstractDungeonObject readDungeonObjectHook(
            final XDataReader reader, final int formatVersion)
            throws IOException {
        this.radius = reader.readInt();
        this.direction = reader.readBoolean();
        return this;
    }

    @Override
    protected void writeDungeonObjectHook(final XDataWriter writer)
            throws IOException {
        writer.writeInt(this.radius);
        writer.writeBoolean(this.direction);
    }

    @Override
    public int getCustomFormat() {
        return AbstractDungeonObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        if (this.direction) {
            DungeonDiver4.getApplication().getGameManager()
                    .doClockwiseRotate(this.radius);
        } else {
            DungeonDiver4.getApplication().getGameManager()
                    .doCounterclockwiseRotate(this.radius);
        }
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
    }

    @Override
    public String getDescription() {
        return "Rotation Traps rotate part of the dungeon when stepped on.";
    }
}
