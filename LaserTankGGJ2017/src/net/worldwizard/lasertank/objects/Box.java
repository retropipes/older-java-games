package net.worldwizard.lasertank.objects;

import net.worldwizard.lasertank.assets.GameImageCache;

public class Box extends GameObject {
    public Box() {
	super();
	this.setAppearance(GameImageCache.get("box"));
	this.setSolid();
	this.setLaserMoves();
    }
}
