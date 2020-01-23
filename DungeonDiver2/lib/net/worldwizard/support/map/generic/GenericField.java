/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.generic;

import net.worldwizard.support.creatures.PartyManager;
import net.worldwizard.support.map.Map;
import net.worldwizard.support.map.MapConstants;
import net.worldwizard.support.scripts.game.GameScript;

public abstract class GenericField extends MapObject {
    // Fields
    private final int damagePercent;

    // Constructors
    protected GenericField(final int dp) {
        super(false);
        this.damagePercent = dp;
    }

    // Scriptability
    @Override
    public GameScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ, final Map map) {
        final GameScript gs = this.playSoundHook();
        PartyManager.getParty().hurtPartyPercentage(this.damagePercent);
        return gs;
    }

    protected abstract GameScript playSoundHook();

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MapConstants.LAYER_GROUND;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_FIELD);
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
    public int getMinimumRequiredQuantity(final Map map) {
        return 0;
    }

    @Override
    public int getMaximumRequiredQuantity(final Map map) {
        final int regionSizeSquared = map.getRegionSize() ^ 2;
        final int mapSize = map.getRows() * map.getColumns();
        final int regionsPerMap = mapSize / regionSizeSquared;
        return regionsPerMap / (int) Math.sqrt(mapSize);
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}
