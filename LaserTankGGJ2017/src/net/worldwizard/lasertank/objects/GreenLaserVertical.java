package net.worldwizard.lasertank.objects;

import net.worldwizard.lasertank.assets.GameImageCache;

public class GreenLaserVertical extends GameObject {
    public GreenLaserVertical() {
	super();
	this.setAppearance(GameImageCache.get("green_laser_vertical"));
    }
}
