package net.worldwizard.lasertank.objects;

import net.worldwizard.lasertank.assets.GameImageCache;

public class GreenLaserHorizontal extends GameObject {
    public GreenLaserHorizontal() {
	super();
	this.setAppearance(GameImageCache.get("green_laser_horizontal"));
    }
}
