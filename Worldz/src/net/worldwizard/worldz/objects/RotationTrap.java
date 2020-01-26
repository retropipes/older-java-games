/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericTrap;
import net.worldwizard.worldz.generic.WorldObject;

public class RotationTrap extends GenericTrap implements Cloneable {
    // Fields
    private int radius;
    private boolean direction;
    private static final boolean CLOCKWISE = true;
    private static final boolean COUNTERCLOCKWISE = false;

    // Constructors
    public RotationTrap() {
        super();
        this.radius = 1;
        this.direction = RotationTrap.CLOCKWISE;
    }

    public RotationTrap(final int newRadius, final boolean newDirection) {
        super();
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
        Messager.showMessage(this.getName() + " (Radius " + this.radius
                + ", Direction " + dir + ")");
    }

    @Override
    public WorldObject editorPropertiesHook() {
        int r = this.radius;
        final String[] rChoices = new String[] { "1", "2", "3" };
        final String rres = Messager.showInputDialog("Rotation Radius:",
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
        final String dres = Messager.showInputDialog("Rotation Direction:",
                "Editor", dChoices, dChoices[di]);
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
    protected WorldObject readWorldObjectHook(final DataReader reader,
            final int formatVersion) throws IOException {
        this.radius = reader.readInt();
        this.direction = reader.readBoolean();
        return this;
    }

    @Override
    protected void writeWorldObjectHook(final DataWriter writer)
            throws IOException {
        writer.writeInt(this.radius);
        writer.writeBoolean(this.direction);
    }

    @Override
    public int getCustomFormat() {
        return WorldObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        if (this.direction) {
            Worldz.getApplication().getGameManager()
                    .doClockwiseRotate(this.radius);
        } else {
            Worldz.getApplication().getGameManager()
                    .doCounterclockwiseRotate(this.radius);
        }
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            WorldObject.playRotatedSound();
        }
    }

    @Override
    public String getDescription() {
        return "Rotation Traps rotate part of the world when stepped on.";
    }
}
