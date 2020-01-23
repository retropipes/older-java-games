package net.worldwizard.lasertank.objects;

import net.worldwizard.lasertank.assets.GameImageCache;

public class TankNorth extends GameObject {
    public TankNorth() {
	super();
	this.setAppearance(GameImageCache.get("tank_north"));
    }
}
