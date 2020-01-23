/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.generic;

import com.puttysoftware.gemma.support.Support;
import com.puttysoftware.gemma.support.map.MapConstants;
import com.puttysoftware.gemma.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.gemma.support.scripts.internal.InternalScript;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntryArgument;
import com.puttysoftware.randomrange.RandomRange;

public abstract class GenericTeleport extends MapObject {
    // Fields
    private int destRow;
    private int destCol;
    private int destFloor;
    private final InternalScript postMove;

    // Constructors
    protected GenericTeleport() {
        super(false);
        this.setTemplateTransform(new TemplateTransform(0.25, 0.5, 1.0));
        RandomRange r = new RandomRange(0, Support.getGameMapSize() - 1);
        RandomRange rf = new RandomRange(0, Support.getGameMapFloorSize() - 1);
        this.destRow = r.generate();
        this.destCol = r.generate();
        this.destFloor = rf.generate();
        // Create post-move script
        this.postMove = new InternalScript();
        InternalScriptEntry act0 = new InternalScriptEntry();
        act0.setActionCode(InternalScriptActionCode.MOVE);
        act0.addActionArg(new InternalScriptEntryArgument(true));
        act0.addActionArg(new InternalScriptEntryArgument(false));
        act0.addActionArg(new InternalScriptEntryArgument(this.destRow));
        act0.addActionArg(new InternalScriptEntryArgument(this.destCol));
        act0.addActionArg(new InternalScriptEntryArgument(this.destFloor));
        act0.finalizeActionArgs();
        this.postMove.addAction(act0);
        InternalScriptEntry act1 = new InternalScriptEntry();
        act1.setActionCode(InternalScriptActionCode.SOUND);
        act1.addActionArg(new InternalScriptEntryArgument(
                GameSoundConstants.SOUND_TELEPORT));
        act1.finalizeActionArgs();
        this.postMove.addAction(act1);
        this.postMove.finalizeActions();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        final GenericTeleport other = (GenericTeleport) obj;
        if (this.destRow != other.destRow) {
            return false;
        }
        if (this.destCol != other.destCol) {
            return false;
        }
        if (this.destFloor != other.destFloor) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.destRow;
        hash = 67 * hash + this.destCol;
        return 67 * hash + this.destFloor;
    }

    @Override
    public GenericTeleport clone() {
        GenericTeleport copy = (GenericTeleport) super.clone();
        copy.destCol = this.destCol;
        copy.destFloor = this.destFloor;
        copy.destRow = this.destRow;
        return copy;
    }

    // Scriptability
    @Override
    public InternalScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ) {
        return this.postMove;
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MapConstants.LAYER_OBJECT;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TELEPORT);
    }

    @Override
    public boolean defersSetProperties() {
        return true;
    }

    @Override
    public int getCustomProperty(int propID) {
        switch (propID) {
        case 1:
            return this.destRow;
        case 2:
            return this.destCol;
        case 3:
            return this.destFloor;
        default:
            return MapObject.DEFAULT_CUSTOM_VALUE;
        }
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        switch (propID) {
        case 1:
            this.destRow = value;
            break;
        case 2:
            this.destCol = value;
            break;
        case 3:
            this.destFloor = value;
            break;
        default:
            break;
        }
    }

    @Override
    public int getCustomFormat() {
        return 3;
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }
}
