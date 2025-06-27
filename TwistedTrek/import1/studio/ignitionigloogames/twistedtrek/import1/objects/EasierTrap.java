/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.Messager;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.SoundManager;

public class EasierTrap extends GenericTrap {
    // Constructors
    public EasierTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Easier Trap";
    }

    @Override
    public String getPluralName() {
        return "Easier Traps";
    }

    @Override
    public byte getObjectID() {
        return (byte) 12;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        Messager.showMessage("The monsters get weaker...");
        PCManager.getPlayer().decrementMonsterLevel();
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            SoundManager.play("easier");
        }
    }

    @Override
    public String getDescription() {
        return "Easier Traps make the monsters easier to defeat.";
    }
}