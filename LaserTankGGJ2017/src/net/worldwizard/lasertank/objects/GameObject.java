package net.worldwizard.lasertank.objects;

import java.util.ArrayList;

import net.worldwizard.lasertank.assets.GameImage;

public abstract class GameObject {
    // Fields
    private final ArrayList<GameImage> appearances;
    private int currentFrame;
    private int totalFrames;
    private boolean hasAnimation;
    private boolean solid;
    private boolean kills;
    private boolean goal;
    private boolean laserMoves;

    // Constructor
    protected GameObject() {
	super();
	this.appearances = new ArrayList<>();
	this.currentFrame = 0;
	this.totalFrames = 1;
	this.hasAnimation = false;
	this.solid = false;
	this.kills = false;
	this.goal = false;
	this.laserMoves = false;
    }

    // Methods
    public GameImage getAppearance() {
	return this.appearances.get(this.currentFrame);
    }

    public void animate() {
	if (this.hasAnimation) {
	    this.currentFrame++;
	    if (this.currentFrame >= this.totalFrames) {
		this.currentFrame = 0;
	    }
	}
    }

    public boolean isSolid() {
	return this.solid;
    }

    public boolean killsPlayer() {
	return this.kills;
    }

    public boolean isGoal() {
	return this.goal;
    }

    public boolean laserMoves() {
	return this.laserMoves;
    }

    protected void setFrames(final int newFrames) {
	this.hasAnimation = newFrames > 1;
	this.totalFrames = newFrames;
    }

    protected void setAppearance(final GameImage image) {
	this.appearances.clear();
	this.appearances.add(image);
    }

    protected void setFrameAppearances(final ArrayList<GameImage> frameApps) {
	this.appearances.clear();
	this.appearances.addAll(frameApps);
    }

    protected void setSolid() {
	this.solid = true;
    }

    protected void setKills() {
	this.kills = true;
    }

    protected void setGoal() {
	this.goal = true;
    }

    protected void setLaserMoves() {
	this.laserMoves = true;
    }
}
