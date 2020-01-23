/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.generic;

import com.puttysoftware.gemma.support.map.MapConstants;
import com.puttysoftware.gemma.support.scripts.internal.InternalScript;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntryArgument;

public abstract class GenericShop extends MapObject {
    // Fields
    private final InternalScript postMove;

    // Constructors
    public GenericShop(int newShopType) {
        super(false);
        this.setTemplateTransform(new TemplateTransform(1.0, 0.75, 0.5));
        // Create post-move script
        this.postMove = new InternalScript();
        InternalScriptEntry act0post = new InternalScriptEntry();
        act0post.setActionCode(InternalScriptActionCode.SHOP);
        act0post.addActionArg(new InternalScriptEntryArgument(newShopType));
        act0post.finalizeActionArgs();
        this.postMove.addAction(act0post);
        this.postMove.finalizeActions();
    }

    // Methods
    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_SHOP);
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
    public int getCustomProperty(int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }

    @Override
    public boolean enabledInBattle() {
        return false;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getPluralName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }
}
