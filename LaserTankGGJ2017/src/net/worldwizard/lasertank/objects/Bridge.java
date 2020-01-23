package net.worldwizard.lasertank.objects;

import net.worldwizard.lasertank.assets.GameImageCache;

public class Bridge extends GameObject {
    public Bridge() {
	super();
	this.setAppearance(GameImageCache.get("bridge"));
    }
}
