package net.worldwizard.lasertank.objects;

import net.worldwizard.lasertank.assets.GameImageCache;

public class TankWest extends GameObject {
    public TankWest() {
	super();
	this.setAppearance(GameImageCache.get("tank_west"));
    }
}
