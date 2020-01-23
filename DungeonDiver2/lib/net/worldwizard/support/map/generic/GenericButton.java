/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.generic;

import net.worldwizard.support.map.Map;
import net.worldwizard.support.map.MapConstants;
import net.worldwizard.support.scripts.game.GameActionCode;
import net.worldwizard.support.scripts.game.GameScript;
import net.worldwizard.support.scripts.game.GameScriptEntry;
import net.worldwizard.support.scripts.game.GameScriptEntryArgument;

public abstract class GenericButton extends MapObject {
    // Fields
    private GenericToggleWall offState;
    private GenericToggleWall onState;
    private final GameScript postMove;

    // Constructors
    protected GenericButton(final GenericToggleWall off,
            final GenericToggleWall on, final TemplateTransform tt) {
        super(false);
        this.offState = off;
        this.onState = on;
        this.setTemplateTransform(tt);
        // Create post-move script
        this.postMove = new GameScript();
        final GameScriptEntry act0 = new GameScriptEntry();
        act0.setActionCode(GameActionCode.SWAP_PAIRS);
        act0.addActionArg(new GameScriptEntryArgument(this.offState.getName()));
        act0.addActionArg(new GameScriptEntryArgument(this.onState.getName()));
        act0.finalizeActionArgs();
        this.postMove.addAction(act0);
        final GameScriptEntry act1 = new GameScriptEntry();
        act1.setActionCode(GameActionCode.REDRAW);
        this.postMove.addAction(act1);
        final GameScriptEntry act2 = new GameScriptEntry();
        act2.setActionCode(GameActionCode.SOUND);
        act2.addActionArg(new GameScriptEntryArgument(
                GameSoundConstants.SOUND_BUTTON));
        act2.finalizeActionArgs();
        this.postMove.addAction(act2);
        this.postMove.finalizeActions();
    }

    @Override
    public boolean equals(final Object obj) {
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
        hash = 13 * hash + (this.onState != null ? this.onState.hashCode() : 0);
        return hash;
    }

    @Override
    public GenericButton clone() {
        final GenericButton copy = (GenericButton) super.clone();
        copy.offState = (GenericToggleWall) this.offState.clone();
        copy.onState = (GenericToggleWall) this.onState.clone();
        return copy;
    }

    // Scriptability
    @Override
    public GameScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ, final Map map) {
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
    public int getCustomProperty(final int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public String getGameImageNameHook() {
        return "button";
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }
}
