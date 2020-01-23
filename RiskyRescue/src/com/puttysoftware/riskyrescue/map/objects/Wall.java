/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.map.objects;

import com.puttysoftware.riskyrescue.assets.ObjectImage;
import com.puttysoftware.riskyrescue.map.Map;
import com.puttysoftware.riskyrescue.map.MapConstants;

public class Wall extends MapObject {
    // Constructors
    public Wall() {
        super(ObjectImage.WALL, true, true);
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
    public String getName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Walls";
    }

    @Override
    public String getDescription() {
        return "Walls are impassable - you'll need to go around them.";
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