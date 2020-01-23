/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.generic;

import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.StatConstants;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.maze.Maze;
import studio.ignitionigloogames.twistedtrek.import1.objects.Empty;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.SoundManager;

public abstract class GenericPotion extends MazeObject implements StatConstants {
    // Fields
    private int effectValue;
    private RandomRange effect;
    private int statAffected;
    private boolean effectValueIsPercentage;

    // Constructors
    protected GenericPotion(final int stat, final boolean usePercent) {
	super(false);
	this.statAffected = stat;
	this.effectValueIsPercentage = usePercent;
    }

    protected GenericPotion(final int stat, final boolean usePercent, final int min, final int max) {
	super(false);
	this.statAffected = stat;
	this.effectValueIsPercentage = usePercent;
	this.effect = new RandomRange(min, max);
    }

    @Override
    public GenericPotion clone() {
	final GenericPotion copy = (GenericPotion) super.clone();
	copy.effectValue = this.effectValue;
	copy.effectValueIsPercentage = this.effectValueIsPercentage;
	copy.statAffected = this.statAffected;
	copy.effect = this.effect;
	return copy;
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
	return Maze.LAYER_OBJECT;
    }

    @Override
    public byte getGroupID() {
	return (byte) 30;
    }

    @Override
    protected void setTypes() {
	this.type.set(TypeConstants.TYPE_POTION);
	this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public final void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	if (this.effect != null) {
	    this.effectValue = this.effect.generate();
	} else {
	    this.effectValue = this.getEffectValue();
	}
	if (this.effectValueIsPercentage) {
	    if (this.statAffected == StatConstants.STAT_CURRENT_HP) {
		if (this.effectValue >= 0) {
		    PCManager.getPlayer().healPercentage(this.effectValue);
		} else {
		    PCManager.getPlayer().doDamagePercentage(-this.effectValue);
		}
	    } else if (this.statAffected == StatConstants.STAT_CURRENT_MP) {
		if (this.effectValue >= 0) {
		    PCManager.getPlayer().regeneratePercentage(this.effectValue);
		} else {
		    PCManager.getPlayer().drainPercentage(-this.effectValue);
		}
	    }
	} else {
	    if (this.statAffected == StatConstants.STAT_CURRENT_HP) {
		if (this.effectValue >= 0) {
		    PCManager.getPlayer().heal(this.effectValue);
		} else {
		    PCManager.getPlayer().doDamage(-this.effectValue);
		}
	    } else if (this.statAffected == StatConstants.STAT_CURRENT_MP) {
		if (this.effectValue >= 0) {
		    PCManager.getPlayer().regenerate(this.effectValue);
		} else {
		    PCManager.getPlayer().drain(-this.effectValue);
		}
	    }
	}
	Import1.getApplication().getGameManager().decay();
	if (this.effectValue >= 0) {
	    if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
		SoundManager.play("heal");
	    }
	} else {
	    if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
		SoundManager.play("hurt");
	    }
	}
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY, final int locZ, final int locW, final int dirX,
	    final int dirY, final int arrowType, final ObjectInventory inv) {
	Import1.getApplication().getGameManager().morph(new Empty(), locX, locY, locZ, locW);
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    SoundManager.play("shatter");
	}
	return false;
    }

    public int getEffectValue() {
	if (this.effect != null) {
	    return this.effect.generate();
	} else {
	    return 0;
	}
    }

    @Override
    public int getCustomProperty(final int propID) {
	return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
	// Do nothing
    }
}