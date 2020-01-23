package net.worldwizard.lasertank.objects;

import java.util.ArrayList;

import net.worldwizard.lasertank.assets.GameImage;
import net.worldwizard.lasertank.assets.GameImageCache;

public class Water extends GameObject {
    public Water() {
	super();
	this.setFrames(3);
	final ArrayList<GameImage> frames = new ArrayList<>();
	frames.add(GameImageCache.get("water_1"));
	frames.add(GameImageCache.get("water_2"));
	frames.add(GameImageCache.get("water_3"));
	this.setFrameAppearances(frames);
	this.setKills();
    }
}
