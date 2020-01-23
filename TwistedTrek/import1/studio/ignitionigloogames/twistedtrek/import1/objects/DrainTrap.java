/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.SoundManager;

public class DrainTrap extends GenericTrap {
    // Fields
    private RandomRange amountDrained;
    private static final int MIN_DRAIN = -1;
    private int maxDrain;

    // Constructors
    public DrainTrap() {
	super();
    }

    @Override
    public String getName() {
	return "Drain Trap";
    }

    @Override
    public String getPluralName() {
	return "Drain Traps";
    }

    @Override
    public byte getObjectID() {
	return (byte) 11;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	this.maxDrain = PCManager.getPlayer().getMaximumMP() / 10;
	this.amountDrained = new RandomRange(this.maxDrain, DrainTrap.MIN_DRAIN);
	PCManager.getPlayer().regenerate(this.amountDrained.generate());
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    SoundManager.play("barrier");
	}
	Import1.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
	return "Drain Traps drain your magic when stepped on, then disappear.";
    }
}