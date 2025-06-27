package studio.ignitionigloogames.dungeondiver1.creatures.ai;

import studio.ignitionigloogames.dungeondiver1.DungeonDiver;
import studio.ignitionigloogames.dungeondiver1.creatures.Battle;
import studio.ignitionigloogames.dungeondiver1.creatures.Creature;
import studio.ignitionigloogames.dungeondiver1.creatures.spells.Spell;
import studio.ignitionigloogames.dungeondiver1.utilities.RandomRange;

public abstract class AIRoutine {
    // Fields
    protected Spell spell;
    protected boolean lastActionResult;
    public static final int ACTION_ATTACK = 0;
    public static final int ACTION_FLEE = 1;
    public static final int ACTION_CAST_SPELL = 2;
    private static final int ENEMY_FLEE_CHANCE = 60;

    // Methods
    public abstract int getNextAction(Creature c);

    public final void performAction(final int action) {
        final Battle b = DungeonDiver.getHoldingBag().getBattle();
        switch (action) {
            case ACTION_ATTACK:
                this.lastActionResult = b.doEnemyAttack();
                break;
            case ACTION_FLEE:
                final RandomRange r = new RandomRange(1, 100);
                final int chance = r.generate();
                if (chance <= AIRoutine.ENEMY_FLEE_CHANCE) {
                    this.lastActionResult = b.doEnemyFlee();
                } else {
                    this.lastActionResult = b.doEnemyFleeFailure();
                }
                break;
            case ACTION_CAST_SPELL:
                this.lastActionResult = b.doEnemyCastSpell(this.spell);
                break;
            default:
                break;
        }
    }
}
