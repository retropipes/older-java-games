package net.worldwizard.lasertank.objects;

import net.worldwizard.lasertank.assets.GameImageCache;

public class TankSouth extends GameObject {
    public TankSouth() {
	super();
	this.setAppearance(GameImageCache.get("tank_south"));
    }
}
