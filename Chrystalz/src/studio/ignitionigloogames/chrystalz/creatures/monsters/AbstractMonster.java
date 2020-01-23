/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.creatures.monsters;

import studio.ignitionigloogames.chrystalz.ai.AbstractMapAIRoutine;
import studio.ignitionigloogames.chrystalz.ai.MapAIRoutinePicker;
import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.names.MonsterNames;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.chrystalz.spells.SpellBook;
import studio.ignitionigloogames.common.random.RandomIntRange;
import studio.ignitionigloogames.common.random.RandomRange;

public abstract class AbstractMonster extends AbstractCreature {
    // Fields
    private String type;
    private int monID;
    protected static final double MINIMUM_EXPERIENCE_RANDOM_VARIANCE = -5.0
            / 2.0;
    protected static final double MAXIMUM_EXPERIENCE_RANDOM_VARIANCE = 5.0
            / 2.0;
    protected static final int PERFECT_GOLD_MIN = 1;
    protected static final int PERFECT_GOLD_MAX = 3;
    private static final int BATTLES_SCALE_FACTOR = 2;
    private static final int BATTLES_START = 2;

    // Constructors
    AbstractMonster() {
        super(1);
        this.setMapAI(AbstractMonster.getInitialMapAI());
        final SpellBook spells = new SystemMonsterSpellBook();
        spells.learnAllSpells();
        this.setSpellBook(spells);
    }

    // Methods
    protected void configureDefaults() {
        this.monID = RandomIntRange.generate(0, 99);
        final int zoneID = PartyManager.getParty().getZone();
        this.type = MonsterNames.getType(zoneID, this.monID);
    }

    protected void overrideDefaults(final int oID, final String oType) {
        this.monID = oID;
        this.type = oType;
    }

    @Override
    public String getName() {
        return this.type;
    }

    @Override
    public boolean checkLevelUp() {
        return false;
    }

    @Override
    protected void levelUpHook() {
        // Do nothing
    }

    @Override
    protected final int getInitialPerfectBonusGold() {
        final int tough = this.getToughness();
        final int min = tough * AbstractMonster.PERFECT_GOLD_MIN;
        final int max = tough * AbstractMonster.PERFECT_GOLD_MAX;
        final RandomRange r = new RandomRange(min, max);
        return (int) (r.generate() * this.adjustForLevelDifference());
    }

    @Override
    public int getSpeed() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        final int base = this.getBaseSpeed();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_SLOWEST);
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_SLOW);
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_NORMAL);
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_FAST);
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_FASTEST);
        } else {
            return (int) (base * AbstractCreature.SPEED_ADJUST_NORMAL);
        }
    }

    private int getToughness() {
        return this.getStrength() + this.getBlock() + this.getAgility()
                + this.getVitality() + this.getIntelligence() + this.getLuck();
    }

    public final int getMonsterID() {
        return this.monID;
    }

    final String getType() {
        return this.type;
    }

    protected double adjustForLevelDifference() {
        return Math.max(0.0, this.getLevelDifference() / 4.0 + 1.0);
    }

    // Helper Methods
    private static AbstractMapAIRoutine getInitialMapAI() {
        return MapAIRoutinePicker.getNextRoutine();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int result = super.hashCode();
        return prime * result + (this.type == null ? 0 : this.type.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof AbstractMonster)) {
            return false;
        }
        final AbstractMonster other = (AbstractMonster) obj;
        if (this.type == null) {
            if (other.type != null) {
                return false;
            }
        } else if (!this.type.equals(other.type)) {
            return false;
        }
        return true;
    }

    protected final int getBattlesToNextLevel() {
        return AbstractMonster.BATTLES_START
                + (this.getLevel() + 1) * AbstractMonster.BATTLES_SCALE_FACTOR;
    }
}
