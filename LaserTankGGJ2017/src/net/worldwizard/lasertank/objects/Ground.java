package net.worldwizard.lasertank.objects;

import net.worldwizard.lasertank.assets.GameImageCache;

public class Ground extends GameObject {
    public Ground() {
	super();
	this.setAppearance(GameImageCache.get("ground"));
    }
}
