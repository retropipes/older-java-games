package net.worldwizard.lasertank.objects;

import net.worldwizard.lasertank.assets.GameImageCache;

public class Empty extends GameObject {
    public Empty() {
	super();
	this.setAppearance(GameImageCache.get("empty"));
    }
}
