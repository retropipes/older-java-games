/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.map.objects;

import com.puttysoftware.riskyrescue.assets.ObjectImage;
import com.puttysoftware.riskyrescue.items.ShopTypes;
import com.puttysoftware.riskyrescue.map.MapConstants;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScript;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptEntry;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptEntryArgument;

public class Regenerator extends MapObject {
    // Fields
    private final InternalScript postMove;

    // Constructors
    public Regenerator() {
        super(ObjectImage.REGENERATOR, false, false);
        // Create post-move script
        this.postMove = new InternalScript();
        final InternalScriptEntry act0post = new InternalScriptEntry();
        act0post.setActionCode(InternalScriptActionCode.SHOP);
        act0post.addActionArg(new InternalScriptEntryArgument(
                ShopTypes.SHOP_TYPE_REGENERATOR));
        act0post.finalizeActionArgs();
        this.postMove.addAction(act0post);
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
    public boolean enabledInBattle() {
        return false;
    }

    @Override
    public String getName() {
        return "Regenerator";
    }

    @Override
    public String getPluralName() {
        return "Regenerators";
    }

    @Override
    public String getDescription() {
        return null;
    }
}
