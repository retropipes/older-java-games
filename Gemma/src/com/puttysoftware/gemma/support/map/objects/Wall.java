/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map.objects;

import com.puttysoftware.gemma.support.map.Map;
import com.puttysoftware.gemma.support.map.generic.GenericWall;
import com.puttysoftware.gemma.support.map.generic.TypeConstants;

public class Wall extends GenericWall {
    // Constructors
    public Wall() {
        super();
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
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_PLAIN_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
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