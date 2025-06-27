/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.PreferencesManager;
import studio.ignitionigloogames.twistedtrek.import1.creatures.PCManager;
import studio.ignitionigloogames.twistedtrek.import1.game.ObjectInventory;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericTrap;
import studio.ignitionigloogames.twistedtrek.import1.resourcemanagers.SoundManager;

public class DamageTrap extends GenericTrap {
    // Fields
    private RandomRange damageDealt;
    private static final int MIN_DAMAGE = 1;
    private int maxDamage;

    // Constructors
    public DamageTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Damage Trap";
    }

    @Override
    public String getPluralName() {
        return "Damage Traps";
    }

    @Override
    public byte getObjectID() {
        return (byte) 10;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY, final ObjectInventory inv) {
        this.maxDamage = PCManager.getPlayer().getMaximumHP() / 10;
        if (this.maxDamage < DamageTrap.MIN_DAMAGE) {
            this.maxDamage = DamageTrap.MIN_DAMAGE;
        }
        this.damageDealt = new RandomRange(DamageTrap.MIN_DAMAGE, this.maxDamage);
        PCManager.getPlayer().doDamage(this.damageDealt.generate());
        if (Import1.getApplication().getPrefsManager().getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            SoundManager.play("barrier");
        }
        Import1.getApplication().getGameManager().decay();
    }

    @Override
    public String getDescription() {
        return "Damage Traps hurt you when stepped on, then disappear.";
    }
}