package net.worldwizard.support.map.generic;

import net.worldwizard.support.map.Map;
import net.worldwizard.support.map.MapConstants;
import net.worldwizard.support.scripts.game.GameActionCode;
import net.worldwizard.support.scripts.game.GameScript;
import net.worldwizard.support.scripts.game.GameScriptEntry;
import net.worldwizard.support.scripts.game.GameScriptEntryArgument;

public abstract class GenericShop extends MapObject {
    // Fields
    private final int shopType;
    private final GameScript postMove;

    // Constructors
    public GenericShop(final int newShopType) {
        super(false);
        this.setTemplateTransform(new TemplateTransform(1.0, 0.75, 0.5, ""));
        this.shopType = newShopType;
        // Create post-move script
        this.postMove = new GameScript();
        final GameScriptEntry act0 = new GameScriptEntry();
        act0.setActionCode(GameActionCode.SHOP);
        act0.addActionArg(new GameScriptEntryArgument(this.shopType));
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
    public GameScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ, final Map map) {
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
}
