/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.generic;

import com.puttysoftware.dungeondiver3.support.map.MapConstants;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScript;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntryArgument;

public abstract class GenericShop extends MapObject {
    // Fields
    private final InternalScript postMove;

    // Constructors
    public GenericShop(final int newShopType) {
        super(false);
        this.setTemplateTransform(new TemplateTransform(1.0, 0.75, 0.5));
        // Create post-move script
        this.postMove = new InternalScript();
        final InternalScriptEntry act0 = new InternalScriptEntry();
        act0.setActionCode(InternalScriptActionCode.SHOP);
        act0.addActionArg(new InternalScriptEntryArgument(newShopType));
        act0.finalizeActionArgs();
        this.postMove.addAction(act0);
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
