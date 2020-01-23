/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.generic;

import com.puttysoftware.gemma.support.creatures.PartyManager;
import com.puttysoftware.gemma.support.map.Map;
import com.puttysoftware.gemma.support.map.MapConstants;
import com.puttysoftware.gemma.support.map.objects.BattleCharacter;
import com.puttysoftware.gemma.support.scripts.internal.InternalScript;

public abstract class GenericField extends MapObject {
    // Fields
    private final int damagePercent;

    // Constructors
    protected GenericField(int dp) {
        super(false);
        this.damagePercent = dp;
    }

    // Scriptability
    @Override
    public InternalScript getPostMoveScript(final boolean ie, final int dirX,
            final int dirY, final int dirZ) {
        InternalScript gs = this.playSoundHook();
        PartyManager.getParty().hurtPartyPercentage(this.damagePercent);
        return gs;
    }

    @Override
    public InternalScript getBattlePostMoveScript(
            final BattleCharacter invoker) {
        InternalScript gs = this.playSoundHook();
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
    public int getCustomProperty(int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }

    @Override
    public int getMinimumRequiredQuantity(Map map) {
        int regionSizeSquared = map.getRegionSize() ^ 2;
        int mapSize = map.getRows() * map.getColumns();
        int regionsPerMap = mapSize / regionSizeSquared;
        return regionsPerMap / (int) (Math.sqrt(Math.sqrt(mapSize)));
    }

    @Override
    public int getMaximumRequiredQuantity(Map map) {
        int regionSizeSquared = map.getRegionSize() ^ 2;
        int mapSize = map.getRows() * map.getColumns();
        int regionsPerMap = mapSize / regionSizeSquared;
        return regionsPerMap / (int) (Math.sqrt(mapSize));
    }

    @Override
    public boolean isRequired() {
        return true;
    }

    @Override
    public int getMinimumRequiredQuantityInBattle(Map map) {
        int regionSizeSquared = map.getRegionSize() ^ 2;
        int mapSize = map.getRows() * map.getColumns();
        int regionsPerMap = mapSize / regionSizeSquared;
        return regionsPerMap / (int) (Math.sqrt(Math.sqrt(mapSize)));
    }

    @Override
    public int getMaximumRequiredQuantityInBattle(Map map) {
        int regionSizeSquared = map.getRegionSize() ^ 2;
        int mapSize = map.getRows() * map.getColumns();
        int regionsPerMap = mapSize / regionSizeSquared;
        return regionsPerMap / (int) (Math.sqrt(mapSize));
    }

    @Override
    public boolean isRequiredInBattle() {
        return true;
    }
}
