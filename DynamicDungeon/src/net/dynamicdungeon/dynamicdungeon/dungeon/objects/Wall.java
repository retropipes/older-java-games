/*  DynamicDungeon: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractWall;
import net.dynamicdungeon.dynamicdungeon.dungeon.utilities.TypeConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;

public class Wall extends AbstractWall {
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
    public int getBaseID() {
	return ObjectImageConstants.OBJECT_IMAGE_WALL;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_PLAIN_WALL);
	this.type.set(TypeConstants.TYPE_WALL);
    }
}