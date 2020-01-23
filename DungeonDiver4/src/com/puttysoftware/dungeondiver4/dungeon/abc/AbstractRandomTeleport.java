/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import java.util.Random;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.editor.DungeonEditorLogic;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public abstract class AbstractRandomTeleport extends AbstractDungeonObject {
    // Fields
    private int randomRangeX;
    private int randomRangeY;
    private Random generator;

    // Constructors
    public AbstractRandomTeleport(final int newRandomRangeY,
            final int newRandomRangeX, final int attrName) {
        super(false, false);
        this.randomRangeX = newRandomRangeX;
        this.randomRangeY = newRandomRangeY;
        this.generator = new Random();
        this.setAttributeID(attrName);
        this.setTemplateColor(ColorConstants.COLOR_BLUE);
        this.setAttributeTemplateColor(ColorConstants.COLOR_YELLOW);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TELEPORT_BASE;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final AbstractRandomTeleport other = (AbstractRandomTeleport) obj;
        if (this.randomRangeX != other.randomRangeX) {
            return false;
        }
        if (this.randomRangeY != other.randomRangeY) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + this.randomRangeX;
        return 89 * hash + this.randomRangeY;
    }

    @Override
    public AbstractRandomTeleport clone() {
        AbstractRandomTeleport copy = (AbstractRandomTeleport) super.clone();
        copy.randomRangeX = this.randomRangeX;
        copy.randomRangeY = this.randomRangeY;
        return copy;
    }

    // Methods
    public int getDestinationRow() {
        if (this.randomRangeY == 0) {
            return 0;
        } else {
            int sign = this.generator.nextInt(2);
            final int value = this.generator.nextInt(this.randomRangeY + 1);
            if (sign == 0) {
                sign = -1;
            }
            return sign * value;
        }
    }

    public int getDestinationColumn() {
        if (this.randomRangeX == 0) {
            return 0;
        } else {
            int sign = this.generator.nextInt(2);
            final int value = this.generator.nextInt(this.randomRangeX + 1);
            if (sign == 0) {
                sign = -1;
            }
            return sign * value;
        }
    }

    @Override
    public int getLayer() {
        return DungeonConstants.LAYER_OBJECT;
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        Application app = DungeonDiver4.getApplication();
        int dr, dc;
        do {
            dr = this.getDestinationRow();
            dc = this.getDestinationColumn();
        } while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
        app.getGameManager().updatePositionRelative(dr, dc, 0);
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public void editorProbeHook() {
        DungeonDiver4.getApplication().showMessage(
                this.getName() + ": Row Radius " + this.randomRangeY
                        + ", Column Radius " + this.randomRangeX);
    }

    @Override
    public AbstractDungeonObject editorPropertiesHook() {
        DungeonEditorLogic me = DungeonDiver4.getApplication().getEditor();
        return me
                .editTeleportDestination(DungeonEditorLogic.TELEPORT_TYPE_RANDOM);
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_RANDOM_TELEPORT);
    }

    @Override
    public int getCustomProperty(int propID) {
        switch (propID) {
        case 1:
            return this.randomRangeX;
        case 2:
            return this.randomRangeY;
        default:
            return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
        }
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        switch (propID) {
        case 1:
            this.randomRangeX = value;
            break;
        case 2:
            this.randomRangeY = value;
            break;
        default:
            break;
        }
    }

    @Override
    public int getCustomFormat() {
        return 2;
    }
}
