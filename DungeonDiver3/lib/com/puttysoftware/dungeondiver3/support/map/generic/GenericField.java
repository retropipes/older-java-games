/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map.generic;

import com.puttysoftware.dungeondiver3.support.creatures.PartyManager;
import com.puttysoftware.dungeondiver3.support.map.Map;
import com.puttysoftware.dungeondiver3.support.map.MapConstants;
import com.puttysoftware.dungeondiver3.support.map.objects.BattleCharacter;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScript;

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
    public InternalScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ) {
        final InternalScript gs = this.playSoundHook();
        PartyManager.getParty().hurtPartyPercentage(this.damagePercent);
        return gs;
    }

    @Override
    public InternalScript getBattlePostMoveScript(
            final BattleCharacter invoker) {
        final InternalScript gs = this.playSoundHook();
        invoker.getTemplate().doDamagePercentage(this.damagePercent);
        return gs;
    }

    protected abstract InternalScript playSoundHook();

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
    public int getMinimumRequiredQuantity(final Map map) {
        final int regionSizeSquared = map.getRegionSize() ^ 2;
        final int mapSize = map.getRows() * map.getColumns();
        final int regionsPerMap = mapSize / regionSizeSquared;
        return regionsPerMap / (int) Math.sqrt(Math.sqrt(mapSize));
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

    @Override
    public int getMinimumRequiredQuantityInBattle(final Map map) {
        final int regionSizeSquared = map.getRegionSize() ^ 2;
        final int mapSize = map.getRows() * map.getColumns();
        final int regionsPerMap = mapSize / regionSizeSquared;
        return regionsPerMap / (int) Math.sqrt(Math.sqrt(mapSize));
    }

    @Override
    public int getMaximumRequiredQuantityInBattle(final Map map) {
        final int regionSizeSquared = map.getRegionSize() ^ 2;
        final int mapSize = map.getRows() * map.getColumns();
        final int regionsPerMap = mapSize / regionSizeSquared;
        return regionsPerMap / (int) Math.sqrt(mapSize);
    }

    @Override
    public boolean isRequiredInBattle() {
        return true;
    }
}
