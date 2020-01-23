/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.map.objects;

import com.puttysoftware.riskyrescue.assets.ObjectImage;
import com.puttysoftware.riskyrescue.assets.SoundConstants;
import com.puttysoftware.riskyrescue.map.MapConstants;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScript;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptEntry;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptEntryArgument;

public class Button extends MapObject {
    // Fields
    private final WallOff offState;
    private final WallOn onState;
    private final InternalScript postMove;

    public Button() {
        super(ObjectImage.BUTTON, false, false);
        this.offState = new WallOff();
        this.onState = new WallOn();
        // Create post-move script
        this.postMove = new InternalScript();
        final InternalScriptEntry act0 = new InternalScriptEntry();
        act0.setActionCode(InternalScriptActionCode.SWAP_PAIRS);
        act0.addActionArg(
                new InternalScriptEntryArgument(this.offState.getName()));
        act0.addActionArg(
                new InternalScriptEntryArgument(this.onState.getName()));
        act0.finalizeActionArgs();
        this.postMove.addAction(act0);
        final InternalScriptEntry act1 = new InternalScriptEntry();
        act1.setActionCode(InternalScriptActionCode.REDRAW);
        this.postMove.addAction(act1);
        final InternalScriptEntry act2 = new InternalScriptEntry();
        act2.setActionCode(InternalScriptActionCode.SOUND);
        act2.addActionArg(
                new InternalScriptEntryArgument(SoundConstants.BUTTON));
        act2.finalizeActionArgs();
        this.postMove.addAction(act2);
        this.postMove.finalizeActions();
    }

    @Override
    public InternalScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ) {
        return this.postMove;
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
    public String getEditorImageNameHook() {
        return "button";
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }

    @Override
    public String getName() {
        return "Blue Button";
    }

    @Override
    public String getPluralName() {
        return "Blue Buttons";
    }

    @Override
    public String getDescription() {
        return "Blue Buttons will cause all Blue Walls Off to become On, and all Blue Walls On to become Off.";
    }
}
