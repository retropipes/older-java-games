package net.worldwizard.lasertank.objects;

import java.util.ArrayList;

import net.worldwizard.lasertank.assets.GameImage;
import net.worldwizard.lasertank.assets.GameImageCache;

public class Flag extends GameObject {
    public Flag() {
	super();
	this.setFrames(3);
	final ArrayList<GameImage> frames = new ArrayList<>();
	frames.add(GameImageCache.get("flag_1"));
	frames.add(GameImageCache.get("flag_2"));
	frames.add(GameImageCache.get("flag_3"));
	this.setFrameAppearances(frames);
	this.setGoal();
    }
}
