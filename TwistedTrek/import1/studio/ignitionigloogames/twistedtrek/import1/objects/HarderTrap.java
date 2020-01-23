/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.SoundManager;

public class HarderTrap extends GenericTrap {
    // Constructors
    public HarderTrap() {
	super();
    }

    @Override
    public String getName() {
	return "Harder Trap";
    }

    @Override
    public String getPluralName() {
	return "Harder Traps";
    }

    @Override
    public byte getObjectID() {
	return (byte) 13;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
	Messager.showMessage("The monsters get stronger...");
	PCManager.getPlayer().incrementMonsterLevel();
	if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
	    SoundManager.play("harder");
	}
    }

    @Override
    public String getDescription() {
	return "Harder Traps make the monsters harder to defeat.";
    }
}