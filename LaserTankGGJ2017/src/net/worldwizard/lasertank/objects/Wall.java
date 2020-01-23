package net.worldwizard.lasertank.objects;

import net.worldwizard.lasertank.assets.GameImageCache;

public class Wall extends GameObject {
    public Wall() {
	super();
	this.setAppearance(GameImageCache.get("wall"));
	this.setSolid();
    }
}
