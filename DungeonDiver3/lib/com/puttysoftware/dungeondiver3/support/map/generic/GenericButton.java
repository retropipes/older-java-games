/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.generic;

import com.puttysoftware.dungeondiver3.support.map.MapConstants;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.GameSoundConstants;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScript;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntryArgument;

public abstract class GenericButton extends MapObject {
    // Fields
    private GenericToggleWall offState;
    private GenericToggleWall onState;
    private final InternalScript postMove;

    // Constructors
    protected GenericButton(final GenericToggleWall off,
            final GenericToggleWall on, final TemplateTransform tt) {
        super(false);
        this.offState = off;
        this.onState = on;
        this.setTemplateTransform(tt);
        // Create post-move script
        this.postMove = new InternalScript();
        InternalScriptEntry act0 = new InternalScriptEntry();
        act0.setActionCode(InternalScriptActionCode.SWAP_PAIRS);
        act0.addActionArg(new InternalScriptEntryArgument(this.offState
                .getName()));
        act0.addActionArg(new InternalScriptEntryArgument(this.onState
                .getName()));
        act0.finalizeActionArgs();
        this.postMove.addAction(act0);
        InternalScriptEntry act1 = new InternalScriptEntry();
        act1.setActionCode(InternalScriptActionCode.REDRAW);
        this.postMove.addAction(act1);
        InternalScriptEntry act2 = new InternalScriptEntry();
        act2.setActionCode(InternalScriptActionCode.SOUND);
        act2.addActionArg(new InternalScriptEntryArgument(
                GameSoundConstants.SOUND_CA_CLICK));
        act2.finalizeActionArgs();
        this.postMove.addAction(act2);
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
        final GenericButton other = (GenericButton) obj;
        if (this.offState != other.offState
                && (this.offState == null || !this.offState
                        .equals(other.offState))) {
            return false;
        }
        if (this.onState != other.onState
                && (this.onState == null || !this.onState.equals(other.onState))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash
                + (this.offState != null ? this.offState.hashCode() : 0);
        return 13 * hash + (this.onState != null ? this.onState.hashCode() : 0);
    }

    @Override
    public GenericButton clone() {
        GenericButton copy = (GenericButton) super.clone();
        copy.offState = (GenericToggleWall) this.offState.clone();
        copy.onState = (GenericToggleWall) this.onState.clone();
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
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BUTTON);
    }

    @Override
    public int getLayer() {
        return MapConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }

    @Override
    public String getGameImageNameHook() {
        return "button";
    }

    @Override
    public String getEditorImageNameHook() {
        return "button";
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }
}
