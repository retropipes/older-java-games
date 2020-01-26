package net.worldwizard.support.creatures;

import java.math.BigInteger;
import java.util.Arrays;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.IDGenerator;
import net.worldwizard.support.Identifiable;
import net.worldwizard.support.ai.AIRoutine;
import net.worldwizard.support.creatures.faiths.Faith;
import net.worldwizard.support.effects.Effect;
import net.worldwizard.support.items.ItemInventory;
import net.worldwizard.support.spells.SpellBook;

public abstract class Creature extends Identifiable implements StatConstants {
    // Fields
    protected BufferedImageIcon image;
    private final int[] stats;
    private final int[] dynamism;
    private final boolean[] hasMax;
    private final int[] maxID;
    private final boolean[] hasMin;
    private final int[] minVal;
    private long experience;
    private final Effect[] effectList;
    private SpellBook spellsKnown;
    private AIRoutine ai;
    private ItemInventory items;
    private static final int MAX_EFFECTS = 100;
    private static final int FUMBLE_BASE = 10;
    private static final int HIT_BASE = 8000;
    private static final int HIT_MAX = 10000;
    private static final int EVADE_BASE = 0;
    private static final int EVADE_MAX = 2000;
    private static final int MAX_AGILITY_CONTRIB = 200;
    private static final int MAX_LUCK_CONTRIB = 200;
    public static final int FULL_HEAL_PERCENTAGE = 100;
    public static final int TEAM_PARTY = 0;

    // Constructor
    protected Creature() {
        this.stats = new int[StatConstants.MAX_STORED_STATS];
        this.dynamism = new int[StatConstants.MAX_STORED_STATS];
        this.hasMax = new boolean[StatConstants.MAX_STORED_STATS];
        this.maxID = new int[StatConstants.MAX_STORED_STATS];
        this.hasMin = new boolean[StatConstants.MAX_STORED_STATS];
        this.minVal = new int[StatConstants.MAX_STORED_STATS];
        for (int x = 0; x < StatConstants.MAX_STORED_STATS; x++) {
            this.stats[x] = 0;
            this.hasMax[x] = false;
            this.maxID[x] = StatConstants.STAT_NONE;
            this.hasMin[x] = true;
            this.minVal[x] = 0;
        }
        this.hasMax[StatConstants.STAT_CURRENT_HP] = true;
        this.hasMax[StatConstants.STAT_CURRENT_MP] = true;
        this.maxID[StatConstants.STAT_CURRENT_HP] = StatConstants.STAT_MAXIMUM_HP;
        this.maxID[StatConstants.STAT_CURRENT_MP] = StatConstants.STAT_MAXIMUM_MP;
        this.minVal[StatConstants.STAT_VITALITY] = 1;
        this.minVal[StatConstants.STAT_AGILITY] = 1;
        this.setStat(StatConstants.STAT_VITALITY, 1);
        this.setStat(StatConstants.STAT_AGILITY, 1);
        this.effectList = new Effect[Creature.MAX_EFFECTS];
        this.spellsKnown = null;
        this.ai = null;
        this.items = new ItemInventory();
    }

    public void applyEffect(final Effect e) {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            if (this.get(x) == null) {
                this.set(x, e);
                e.scaleEffect(Effect.EFFECT_ADD, this);
                return;
            }
        }
    }

    public abstract boolean checkLevelUp();

    public void cullInactiveEffects() {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            try {
                final Effect e = this.get(x);
                if (!e.isActive()) {
                    this.set(x, null);
                }
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }

    public void doDamage(final int damage) {
        this.offsetCurrentHP(-damage);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public void doDamagePercentage(final int percent) {
        int fP = percent;
        if (fP > Creature.FULL_HEAL_PERCENTAGE) {
            fP = Creature.FULL_HEAL_PERCENTAGE;
        }
        if (fP < 0) {
            fP = 0;
        }
        final double fPMultiplier = fP / (double) Creature.FULL_HEAL_PERCENTAGE;
        int modValue = (int) (this.getEffectedMaximumHP() * fPMultiplier);
        if (modValue <= 0) {
            modValue = 1;
        }
        this.offsetCurrentHP(-modValue);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public void drain(final int cost) {
        this.offsetCurrentMP(-cost);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    public void extendEffect(final Effect e, final int rounds) {
        final int index = this.indexOf(e);
        if (index != -1) {
            this.get(index).extendEffect(rounds);
        }
    }

    private void fixStatValue(final int stat) {
        if (this.hasMin[stat]) {
            if (this.stats[stat] < this.minVal[stat]) {
                this.stats[stat] = this.minVal[stat];
            }
        }
        if (this.hasMax[stat]) {
            if (this.stats[stat] > this.getStat(this.maxID[stat])) {
                this.stats[stat] = this.getStat(this.maxID[stat]);
            }
        }
    }

    protected Effect get(final int x) {
        return this.effectList[x];
    }

    public int getActionsPerRound() {
        return (int) Math.ceil(this.getEffectedStat(StatConstants.STAT_AGILITY)
                * StatConstants.FACTOR_AGILITY_ACTIONS_PER_ROUND);
    }

    public int getAgility() {
        return this.getStat(StatConstants.STAT_AGILITY);
    }

    public AIRoutine getAI() {
        return this.ai;
    }

    public boolean hasAI() {
        return this.ai != null;
    }

    public String getAllCurrentEffectMessages() {
        int x;
        final StringBuilder sb = new StringBuilder(Effect.getNullMessage());
        for (x = 0; x < this.effectList.length; x++) {
            try {
                sb.append(this.get(x).getCurrentMessage());
                sb.append("\n");
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        String s = sb.toString();
        // Strip final newline character, if it exists
        if (!s.equals(Effect.getNullMessage())) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public int getArmorBlock() {
        return (int) (this.getItems().getTotalAbsorb()
                * StatConstants.FACTOR_ABSORB_DEFENSE);
    }

    public int getAttack() {
        return (int) (this.getStrength() * StatConstants.FACTOR_STRENGTH_ATTACK
                + this.getItems().getTotalPower()
                        * StatConstants.FACTOR_POWER_ATTACK);
    }

    public String getAttackString() {
        return "Attack: " + this.getEffectedStat(StatConstants.STAT_ATTACK)
                + " (" + this.getAttack() + ")";
    }

    public int getAttacksPerRound() {
        return this.getStat(StatConstants.STAT_ATTACKS_PER_ROUND);
    }

    public int getBlock() {
        return this.getStat(StatConstants.STAT_BLOCK);
    }

    public String getCompleteEffectString() {
        int x;
        final StringBuilder sb = new StringBuilder();
        for (x = 0; x < this.effectList.length; x++) {
            try {
                sb.append(this.get(x).getEffectString());
                sb.append("\n");
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        String s = sb.toString();
        // Strip final newline character, if it exists
        if (!s.equals(Effect.getNullMessage())) {
            s = s.substring(0, s.length() - 1);
        }
        return s;
    }

    public int getCurrentHP() {
        return this.getStat(StatConstants.STAT_CURRENT_HP);
    }

    public int getCurrentMP() {
        return this.getStat(StatConstants.STAT_CURRENT_MP);
    }

    public int getDefense() {
        return (int) (this.getBlock() * StatConstants.FACTOR_BLOCK_DEFENSE
                + this.getItems().getTotalAbsorb()
                        * StatConstants.FACTOR_ABSORB_DEFENSE);
    }

    public String getDefenseString() {
        return "Defense: " + this.getEffectedStat(StatConstants.STAT_DEFENSE)
                + " (" + this.getDefense() + ")";
    }

    protected int[] getDynamism() {
        return this.dynamism;
    }

    public double getEffectedStat(final int stat) {
        int x, s, p;
        s = 0;
        p = this.getStat(stat);
        for (x = 0; x < this.effectList.length; x++) {
            try {
                final Effect e = this.get(x);
                p *= e.getEffect(Effect.EFFECT_MULTIPLY, stat);
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        for (x = 0; x < this.effectList.length; x++) {
            try {
                final Effect e = this.get(x);
                s += e.getEffect(Effect.EFFECT_ADD, stat);
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        return p + s;
    }

    public int getEvade() {
        final int chance = Creature.EVADE_BASE;
        final double agilityContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_AGILITY))
                * StatConstants.FACTOR_AGILITY_EVADE;
        final double luckContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_LUCK))
                * StatConstants.FACTOR_LUCK_EVADE;
        final int modifier = (int) Math.round(agilityContrib + luckContrib);
        return Math.min(chance + modifier, Creature.EVADE_MAX);
    }

    public long getExperience() {
        return this.experience;
    }

    public Faith getFaith() {
        return null;
    }

    public int getFumbleChance() {
        final int chance = Creature.FUMBLE_BASE;
        final double agilityContrib = Math.min(
                this.getEffectedStat(StatConstants.STAT_AGILITY),
                Creature.MAX_AGILITY_CONTRIB)
                / (Creature.MAX_AGILITY_CONTRIB / (double) Creature.FUMBLE_BASE)
                * StatConstants.FACTOR_AGILITY_FUMBLE;
        final double luckContrib = Math.min(
                this.getEffectedStat(StatConstants.STAT_LUCK),
                Creature.MAX_LUCK_CONTRIB)
                / (Creature.MAX_LUCK_CONTRIB / (double) Creature.FUMBLE_BASE)
                * StatConstants.FACTOR_LUCK_FUMBLE;
        final int modifier = (int) (agilityContrib + luckContrib);
        return chance - modifier;
    }

    public int getGold() {
        return this.getStat(StatConstants.STAT_GOLD);
    }

    public int getHit() {
        final int chance = Creature.HIT_BASE;
        final double strengthContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_STRENGTH))
                * StatConstants.FACTOR_STRENGTH_HIT;
        final double luckContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_LUCK))
                * StatConstants.FACTOR_LUCK_HIT;
        final int modifier = (int) Math.round(strengthContrib + luckContrib);
        return Math.min(chance + modifier, Creature.HIT_MAX);
    }

    public String getHPString() {
        return this.getCurrentHP() + "/" + this.getEffectedMaximumHP();
    }

    public BufferedImageIcon getImage() {
        if (this.image == null) {
            this.image = this.getInitialImage();
        }
        return this.image;
    }

    protected abstract BufferedImageIcon getInitialImage();

    public int getIntelligence() {
        return this.getStat(StatConstants.STAT_INTELLIGENCE);
    }

    public ItemInventory getItems() {
        return this.items;
    }

    public int getLevel() {
        return this.getStat(StatConstants.STAT_LEVEL);
    }

    public int getLuck() {
        return this.getStat(StatConstants.STAT_LUCK);
    }

    public int getMaximumHP() {
        return (int) (this.getVitality()
                * StatConstants.FACTOR_VITALITY_HEALTH);
    }

    public int getMaximumMP() {
        return (int) (this.getIntelligence()
                * StatConstants.FACTOR_INTELLIGENCE_MAGIC);
    }

    public int getEffectedMaximumHP() {
        return (int) this.getEffectedStat(StatConstants.STAT_MAXIMUM_HP);
    }

    public int getEffectedMaximumMP() {
        return (int) this.getEffectedStat(StatConstants.STAT_MAXIMUM_MP);
    }

    public int getEffectedAttacksPerRound() {
        return (int) this.getEffectedStat(StatConstants.STAT_ATTACKS_PER_ROUND);
    }

    public int getEffectedSpellsPerRound() {
        return (int) this.getEffectedStat(StatConstants.STAT_SPELLS_PER_ROUND);
    }

    public String getMPString() {
        return this.getCurrentMP() + "/" + this.getEffectedMaximumMP();
    }

    @Override
    public abstract String getName();

    public int getEffectedSpeed() {
        return (int) (this.getEffectedStat(StatConstants.STAT_AGILITY)
                * StatConstants.FACTOR_AGILITY_SPEED
                - this.items.getTotalEquipmentWeight()
                        * StatConstants.FACTOR_WEIGHT_SPEED);
    }

    public int getSpeed() {
        return (int) (this.getAgility() * StatConstants.FACTOR_AGILITY_SPEED
                - this.items.getTotalEquipmentWeight()
                        * StatConstants.FACTOR_WEIGHT_SPEED);
    }

    public SpellBook getSpellBook() {
        return this.spellsKnown;
    }

    public int getSpellsPerRound() {
        return this.getStat(StatConstants.STAT_SPELLS_PER_ROUND);
    }

    public int getStat(final int stat) {
        try {
            return this.stats[stat];
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            switch (stat) {
            case StatConstants.STAT_ATTACK:
                return this.getAttack();
            case StatConstants.STAT_DEFENSE:
                return this.getDefense();
            case StatConstants.STAT_MAXIMUM_HP:
                return this.getMaximumHP();
            case StatConstants.STAT_MAXIMUM_MP:
                return this.getMaximumMP();
            case StatConstants.STAT_FUMBLE_CHANCE:
                return this.getFumbleChance();
            case StatConstants.STAT_SPEED:
                return this.getSpeed();
            case StatConstants.STAT_HIT:
                return this.getHit();
            case StatConstants.STAT_EVADE:
                return this.getEvade();
            default:
                return 0;
            }
        }
    }

    public int getStrength() {
        return this.getStat(StatConstants.STAT_STRENGTH);
    }

    public int getEffectedUnfactoredAttack() {
        int x, s, p;
        s = 0;
        p = this.getStrength() + this.getItems().getTotalPower();
        for (x = 0; x < this.effectList.length; x++) {
            try {
                final Effect e = this.get(x);
                p *= e.getEffect(Effect.EFFECT_MULTIPLY,
                        StatConstants.STAT_ATTACK);
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        for (x = 0; x < this.effectList.length; x++) {
            try {
                final Effect e = this.get(x);
                s += e.getEffect(Effect.EFFECT_ADD, StatConstants.STAT_ATTACK);
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        return p + s;
    }

    public int getVitality() {
        return this.getStat(StatConstants.STAT_VITALITY);
    }

    public int getWeaponPower() {
        return (int) (this.getItems().getTotalPower()
                * StatConstants.FACTOR_POWER_ATTACK);
    }

    public int getUnfactoredWeaponPower() {
        return this.getItems().getTotalPower();
    }

    public void heal(final int amount) {
        this.offsetCurrentHP(amount);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public void healAndRegenerateFully() {
        this.healFully();
        this.regenerateFully();
    }

    public void healFully() {
        this.setCurrentHP(this.getEffectedMaximumHP());
    }

    public void healPercentage(final int percent) {
        int fP = percent;
        if (fP > Creature.FULL_HEAL_PERCENTAGE) {
            fP = Creature.FULL_HEAL_PERCENTAGE;
        }
        if (fP < 0) {
            fP = 0;
        }
        final double fPMultiplier = fP / (double) Creature.FULL_HEAL_PERCENTAGE;
        final int difference = this.getEffectedMaximumHP()
                - this.getCurrentHP();
        int modValue = (int) (difference * fPMultiplier);
        if (modValue <= 0) {
            modValue = 1;
        }
        this.offsetCurrentHP(modValue);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    protected int indexOf(final Effect e) {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            final Effect le = this.get(x);
            if (le != null) {
                if (e.equals(le)) {
                    return x;
                }
            } else {
                return -1;
            }
        }
        return -1;
    }

    public boolean isAlive() {
        return this.getCurrentHP() > 0;
    }

    public boolean isEffectActive(final Effect e) {
        final int index = this.indexOf(e);
        if (index != -1) {
            return this.get(index).isActive();
        } else {
            return false;
        }
    }

    public abstract void levelUp();

    public void offsetAgility(final int value) {
        this.stats[StatConstants.STAT_AGILITY] += value;
    }

    public void offsetBlock(final int value) {
        this.stats[StatConstants.STAT_BLOCK] += value;
    }

    public void offsetCurrentHP(final int value) {
        this.stats[StatConstants.STAT_CURRENT_HP] += value;
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public void offsetCurrentMP(final int value) {
        this.stats[StatConstants.STAT_CURRENT_MP] += value;
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    public void offsetExperience(final long value) {
        this.experience += value;
    }

    public void offsetGold(final int value) {
        this.stats[StatConstants.STAT_GOLD] += value;
    }

    public void offsetIntelligence(final int value) {
        this.stats[StatConstants.STAT_INTELLIGENCE] += value;
    }

    public void offsetLevel(final int value) {
        this.stats[StatConstants.STAT_LEVEL] += value;
    }

    public void offsetLuck(final int value) {
        this.stats[StatConstants.STAT_LUCK] += value;
    }

    public void offsetStrength(final int value) {
        this.stats[StatConstants.STAT_STRENGTH] += value;
    }

    public void offsetVitality(final int value) {
        this.stats[StatConstants.STAT_VITALITY] += value;
    }

    public void regenerate(final int amount) {
        this.offsetCurrentMP(amount);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    public void regenerateFully() {
        this.setCurrentMP(this.getMaximumMP());
    }

    public void regeneratePercentage(final int percent) {
        int fP = percent;
        if (fP > Creature.FULL_HEAL_PERCENTAGE) {
            fP = Creature.FULL_HEAL_PERCENTAGE;
        }
        if (fP < 0) {
            fP = 0;
        }
        final double fPMultiplier = fP / (double) Creature.FULL_HEAL_PERCENTAGE;
        final int difference = this.getMaximumMP() - this.getCurrentMP();
        int modValue = (int) (difference * fPMultiplier);
        if (modValue <= 0) {
            modValue = 1;
        }
        this.offsetCurrentMP(modValue);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    protected void set(final int x, final Effect e) {
        this.effectList[x] = e;
    }

    public void setAgility(final int value) {
        this.stats[StatConstants.STAT_AGILITY] = value;
    }

    public void setAI(final AIRoutine newAI) {
        this.ai = newAI;
    }

    public void setAttacksPerRound(final int value) {
        this.setStat(StatConstants.STAT_ATTACKS_PER_ROUND, value);
    }

    public void setBlock(final int value) {
        this.setStat(StatConstants.STAT_BLOCK, value);
    }

    public void setCurrentHP(final int value) {
        this.setStat(StatConstants.STAT_CURRENT_HP, value);
    }

    public void setCurrentMP(final int value) {
        this.setStat(StatConstants.STAT_CURRENT_MP, value);
    }

    public void setExperience(final long value) {
        this.experience = value;
    }

    public void setGold(final int value) {
        this.setStat(StatConstants.STAT_GOLD, value);
    }

    public void setIntelligence(final int value) {
        this.setStat(StatConstants.STAT_INTELLIGENCE, value);
    }

    public void setItems(final ItemInventory newItems) {
        this.items = newItems;
    }

    public void setLevel(final int value) {
        this.setStat(StatConstants.STAT_LEVEL, value);
    }

    public void setLuck(final int value) {
        this.setStat(StatConstants.STAT_LUCK, value);
    }

    public void setSpellBook(final SpellBook book) {
        this.spellsKnown = book;
    }

    public void setSpellsPerRound(final int value) {
        this.setStat(StatConstants.STAT_SPELLS_PER_ROUND, value);
    }

    public void setStat(final int stat, final int value) {
        int dynValue;
        if (this.dynamism[stat] != 0) {
            final RandomRange r = new RandomRange(-this.dynamism[stat],
                    this.dynamism[stat]);
            dynValue = value + r.generate();
        } else {
            dynValue = value;
        }
        this.stats[stat] = dynValue;
        this.fixStatValue(stat);
    }

    public void setStrength(final int value) {
        this.setStat(StatConstants.STAT_STRENGTH, value);
    }

    public void setVitality(final int value) {
        this.setStat(StatConstants.STAT_VITALITY, value);
    }

    public void stripAllEffects() {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            this.set(x, null);
        }
    }

    public void useEffects() {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            try {
                this.get(x).useEffect(this);
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (this.ai == null ? 0 : this.ai.hashCode());
        result = prime * result + Arrays.hashCode(this.dynamism);
        result = prime * result + Arrays.hashCode(this.effectList);
        result = prime * result
                + (int) (this.experience ^ this.experience >>> 32);
        result = prime * result + Arrays.hashCode(this.hasMax);
        result = prime * result + Arrays.hashCode(this.hasMin);
        result = prime * result
                + (this.items == null ? 0 : this.items.hashCode());
        result = prime * result + Arrays.hashCode(this.maxID);
        result = prime * result + Arrays.hashCode(this.minVal);
        result = prime * result
                + (this.spellsKnown == null ? 0 : this.spellsKnown.hashCode());
        result = prime * result + Arrays.hashCode(this.stats);
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Creature)) {
            return false;
        }
        final Creature other = (Creature) obj;
        if (this.ai == null) {
            if (other.ai != null) {
                return false;
            }
        } else if (!this.ai.equals(other.ai)) {
            return false;
        }
        if (!Arrays.equals(this.dynamism, other.dynamism)) {
            return false;
        }
        if (!Arrays.equals(this.effectList, other.effectList)) {
            return false;
        }
        if (this.experience != other.experience) {
            return false;
        }
        if (!Arrays.equals(this.hasMax, other.hasMax)) {
            return false;
        }
        if (!Arrays.equals(this.hasMin, other.hasMin)) {
            return false;
        }
        if (this.items == null) {
            if (other.items != null) {
                return false;
            }
        } else if (!this.items.equals(other.items)) {
            return false;
        }
        if (!Arrays.equals(this.maxID, other.maxID)) {
            return false;
        }
        if (!Arrays.equals(this.minVal, other.minVal)) {
            return false;
        }
        if (this.spellsKnown == null) {
            if (other.spellsKnown != null) {
                return false;
            }
        } else if (!this.spellsKnown.equals(other.spellsKnown)) {
            return false;
        }
        if (!Arrays.equals(this.stats, other.stats)) {
            return false;
        }
        return true;
    }

    @Override
    public BigInteger computeLongHash() {
        BigInteger longHash = BigInteger.ZERO;
        longHash = longHash
                .add(IDGenerator.computeStringLongHash(this.getName())
                        .multiply(BigInteger.valueOf(2)));
        return longHash;
    }
}
