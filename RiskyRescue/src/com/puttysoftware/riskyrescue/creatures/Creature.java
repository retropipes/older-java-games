/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.creatures;

import java.awt.Color;
import java.util.Arrays;

import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.page.Page;
import com.puttysoftware.riskyrescue.ai.AIRoutine;
import com.puttysoftware.riskyrescue.effects.Effect;
import com.puttysoftware.riskyrescue.items.ItemInventory;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScript;
import com.puttysoftware.riskyrescue.spells.SpellBook;

public abstract class Creature {
    // Fields
    protected BufferedImageIcon image;
    private final long[] prestige;
    private final Statistic[] stats;
    private long experience;
    private final Effect[] effectList;
    private SpellBook spellsKnown;
    private AIRoutine ai;
    private ItemInventory items;
    private Page toNextLevel;
    private int teamID;
    private int xLoc, yLoc;
    private int saveX, saveY;
    private static int ACTION_CAP = 1;
    private static final int MAX_EFFECTS = 100;
    private static final int FULL_HEAL_PERCENTAGE = 100;
    private static final double EXP_ADJUST = 0.2;
    public static final int TEAM_PARTY = 0;

    // Constructor
    protected Creature() {
        this.prestige = new long[PrestigeConstants.MAX_PRESTIGE];
        this.stats = new Statistic[StatConstants.MAX_STORED_STATS];
        for (int x = 0; x < StatConstants.MAX_STORED_STATS; x++) {
            this.stats[x] = new Statistic();
        }
        this.stats[StatConstants.STAT_CURRENT_HP].setHasMax(true);
        this.stats[StatConstants.STAT_CURRENT_MP].setHasMax(true);
        this.stats[StatConstants.STAT_LEVEL].setHasMax(true);
        this.stats[StatConstants.STAT_LOAD].setHasMax(true);
        this.stats[StatConstants.STAT_CURRENT_HP]
                .setMaxID(StatConstants.STAT_MAXIMUM_HP);
        this.stats[StatConstants.STAT_CURRENT_MP]
                .setMaxID(StatConstants.STAT_MAXIMUM_MP);
        this.stats[StatConstants.STAT_LEVEL]
                .setMaxID(StatConstants.STAT_MAX_LEVEL);
        this.stats[StatConstants.STAT_LOAD]
                .setMaxID(StatConstants.STAT_CAPACITY);
        this.stats[StatConstants.STAT_VITALITY].setMinVal(1);
        this.stats[StatConstants.STAT_AGILITY].setMinVal(1);
        this.stats[StatConstants.STAT_VITALITY].setValue(1);
        this.stats[StatConstants.STAT_AGILITY].setValue(1);
        this.stats[StatConstants.STAT_ATTACKS_PER_ROUND].setMinVal(1);
        this.stats[StatConstants.STAT_SPELLS_PER_ROUND].setMinVal(1);
        this.stats[StatConstants.STAT_ITEMS_PER_ROUND].setMinVal(1);
        this.stats[StatConstants.STAT_STEALS_PER_ROUND].setMinVal(1);
        this.stats[StatConstants.STAT_ATTACKS_PER_ROUND].setValue(1);
        this.stats[StatConstants.STAT_SPELLS_PER_ROUND].setValue(1);
        this.stats[StatConstants.STAT_ITEMS_PER_ROUND].setValue(1);
        this.stats[StatConstants.STAT_STEALS_PER_ROUND].setValue(1);
        this.effectList = new Effect[Creature.MAX_EFFECTS];
        this.spellsKnown = null;
        this.ai = null;
        this.items = new ItemInventory();
        this.toNextLevel = null;
        this.xLoc = -1;
        this.yLoc = -1;
        this.saveX = -1;
        this.saveY = -1;
    }

    public final int getX() {
        return this.xLoc;
    }

    public final int getY() {
        return this.yLoc;
    }

    public final void setX(final int newX) {
        this.xLoc = newX;
    }

    public final void setY(final int newY) {
        this.yLoc = newY;
    }

    public final void offsetX(final int newX) {
        this.xLoc += newX;
    }

    public final void offsetY(final int newY) {
        this.yLoc += newY;
    }

    public final void saveLocation() {
        this.saveX = this.xLoc;
        this.saveY = this.yLoc;
    }

    public final void restoreLocation() {
        this.xLoc = this.saveX;
        this.yLoc = this.saveY;
    }

    public final int getTeamID() {
        return this.teamID;
    }

    public final void setTeamID(final int team) {
        this.teamID = team;
    }

    public final void applyEffect(final Effect e) {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            if (this.get(x) == null && e != null) {
                this.set(x, e);
                e.scaleEffect(this);
                return;
            }
        }
    }

    public boolean checkLevelUp() {
        if (this.toNextLevel != null) {
            return this.experience >= this.getToNextLevelValue();
        } else {
            return false;
        }
    }

    public final long computePrestige() {
        final long damageGiven = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_GIVEN);
        final long damageTaken = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_TAKEN);
        final long hitsGiven = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_HITS_GIVEN);
        final long hitsTaken = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_HITS_TAKEN);
        final long attacksDodged = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_ATTACKS_DODGED);
        final long missedAttacks = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_MISSED_ATTACKS);
        final long monstersKilled = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_MONSTERS_KILLED);
        final long spellsCast = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_SPELLS_CAST);
        final long timesKilled = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_KILLED);
        final long timesRanAway = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_RAN_AWAY);
        return (damageGiven - damageTaken) / 10 + hitsGiven + attacksDodged
                + 2 * monstersKilled - hitsTaken - 2 * missedAttacks
                - 3 * spellsCast - 10 * timesKilled - 50 * timesRanAway;
    }

    public final int getActiveEffectCount() {
        int x, c;
        c = 0;
        for (x = 0; x < this.effectList.length; x++) {
            try {
                final Effect e = this.get(x);
                if (e != null && e.isActive()) {
                    c++;
                }
            } catch (final ArrayIndexOutOfBoundsException np) {
                // Do nothing
            }
        }
        return c;
    }

    public final void cullInactiveEffects() {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            try {
                final Effect e = this.get(x);
                if (e != null && !e.isActive()) {
                    this.set(x, null);
                }
            } catch (final ArrayIndexOutOfBoundsException np) {
                // Do nothing
            }
        }
    }

    public final void doDamage(final int damage) {
        this.offsetCurrentHP(-damage);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public final void doDamagePercentage(final int percent) {
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

    public final void drain(final int cost) {
        this.offsetCurrentMP(-cost);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    public final void extendEffect(final Effect which, final int rounds) {
        final int index = this.indexOf(which);
        if (index != -1) {
            final Effect e = this.get(index);
            if (e != null) {
                e.extendEffect(rounds);
            }
        }
    }

    private void fixStatValue(final int stat) {
        if (this.getHasStatMin(stat)) {
            if (this.getStat(stat) < this.getStatMin(stat)) {
                this.setStatFixed(stat, this.getStatMin(stat));
            }
        }
        if (this.getHasStatMax(stat)) {
            if (this.getStat(stat) > this.getStat(this.getStatMax(stat))) {
                this.setStatFixed(stat, this.getStat(this.getStatMax(stat)));
            }
        }
    }

    private Effect get(final int x) {
        return this.effectList[x];
    }

    public int getActionsPerRound() {
        final int value = (int) Math
                .sqrt(Math.ceil(this.getEffectedStat(StatConstants.STAT_AGILITY)
                        * StatConstants.FACTOR_AGILITY_ACTIONS_PER_ROUND));
        if (value > Creature.ACTION_CAP) {
            return Creature.ACTION_CAP;
        } else {
            return value;
        }
    }

    public static void computeActionCap(final int rows, final int cols) {
        final int avg = (rows + cols) / 2;
        final int mult = (int) Math.sqrt(avg);
        final double temp = avg * mult;
        Creature.ACTION_CAP = (int) (Math.round(temp / 10.0) * 10.0);
    }

    public final int getAgility() {
        return this.getStat(StatConstants.STAT_AGILITY);
    }

    public final AIRoutine getAI() {
        return this.ai;
    }

    public final boolean hasAI() {
        return this.ai != null;
    }

    public final String getAllCurrentEffectMessages() {
        int x;
        final StringBuilder sb = new StringBuilder(Effect.getNullMessage());
        for (x = 0; x < this.effectList.length; x++) {
            try {
                final Effect e = this.get(x);
                if (e != null) {
                    sb.append(e.getCurrentMessage());
                    sb.append("\n");
                }
            } catch (final ArrayIndexOutOfBoundsException np) {
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

    public final int getArmorBlock() {
        return (int) (this.getItems().getTotalAbsorb()
                * StatConstants.FACTOR_ABSORB_DEFENSE);
    }

    public int getAttack() {
        return (int) (this.getStrength() * StatConstants.FACTOR_STRENGTH_ATTACK
                + this.getItems().getTotalPower()
                        * StatConstants.FACTOR_POWER_ATTACK);
    }

    public final String getAttackString() {
        return "Attack: " + this.getEffectedStat(StatConstants.STAT_ATTACK)
                + " (" + this.getAttack() + ")";
    }

    public int getMagicPower() {
        return (int) (this.getIntelligence()
                * StatConstants.FACTOR_INTELLIGENCE_MAGIC_POWER);
    }

    public final String getMagicPowerString() {
        return " Magic Power: "
                + this.getEffectedStat(StatConstants.STAT_MAGIC_POWER) + " ("
                + this.getMagicPower() + ")";
    }

    protected final int getAttacksPerRound() {
        return this.getStat(StatConstants.STAT_ATTACKS_PER_ROUND);
    }

    protected final int getItemsPerRound() {
        return this.getStat(StatConstants.STAT_ITEMS_PER_ROUND);
    }

    protected final int getStealsPerRound() {
        return this.getStat(StatConstants.STAT_STEALS_PER_ROUND);
    }

    public final int getBlock() {
        return this.getStat(StatConstants.STAT_BLOCK);
    }

    public int getCapacity() {
        return Math.max(StatConstants.MIN_CAPACITY, (int) (this.getStrength()
                * StatConstants.FACTOR_STRENGTH_CAPACITY
                + this.getAgility() * StatConstants.FACTOR_AGILITY_CAPACITY));
    }

    public final String[] getCompleteEffectString() {
        int x, z;
        z = this.getActiveEffectCount();
        final String[] s = new String[z];
        int counter = 0;
        for (x = 0; x < z; x++) {
            if (this.effectList[x] != null) {
                s[counter] = this.effectList[x].getEffectString();
                counter++;
            }
        }
        return s;
    }

    public final int getCurrentHP() {
        return this.getStat(StatConstants.STAT_CURRENT_HP);
    }

    public final int getCurrentMP() {
        return this.getStat(StatConstants.STAT_CURRENT_MP);
    }

    public int getDefense() {
        return (int) (this.getBlock() * StatConstants.FACTOR_BLOCK_DEFENSE
                + this.getItems().getTotalAbsorb()
                        * StatConstants.FACTOR_ABSORB_DEFENSE);
    }

    public final String getDefenseString() {
        return "Defense: " + this.getEffectedStat(StatConstants.STAT_DEFENSE)
                + " (" + this.getDefense() + ")";
    }

    public int getMagicDefense() {
        return (int) (this.getBlock()
                * StatConstants.FACTOR_BLOCK_MAGIC_DEFENSE);
    }

    public final String getMagicDefenseString() {
        return "Magic Defense: "
                + this.getEffectedStat(StatConstants.STAT_MAGIC_DEFENSE) + " ("
                + this.getMagicDefense() + ")";
    }

    public final double getEffectedStat(final int stat) {
        int x, s, p;
        s = 0;
        p = this.getStat(stat);
        for (x = 0; x < this.effectList.length; x++) {
            try {
                final Effect e = this.get(x);
                if (e != null && e.getAffectedStat() == stat) {
                    if (e.isMultiply()) {
                        p *= e.getEffect();
                    } else {
                        s += e.getEffect();
                    }
                }
            } catch (final ArrayIndexOutOfBoundsException np) {
                // Do nothing
            }
        }
        return s + p;
    }

    public final int getEvade() {
        final int chance = StatConstants.EVADE_BASE;
        final double agilityContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_AGILITY))
                * StatConstants.FACTOR_AGILITY_EVADE;
        final double luckContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_LUCK))
                * StatConstants.FACTOR_LUCK_EVADE;
        final int modifier = (int) Math.round(agilityContrib + luckContrib);
        return Math.min(chance + modifier, StatConstants.EVADE_MAX);
    }

    public final long getExperience() {
        return this.experience;
    }

    public final int getGold() {
        return this.getStat(StatConstants.STAT_GOLD);
    }

    public final int getHit() {
        final int chance = StatConstants.HIT_BASE;
        final double strengthContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_STRENGTH))
                * StatConstants.FACTOR_STRENGTH_HIT;
        final double luckContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_LUCK))
                * StatConstants.FACTOR_LUCK_HIT;
        final int modifier = (int) Math.round(strengthContrib + luckContrib);
        return Math.min(chance + modifier, StatConstants.HIT_MAX);
    }

    public final String getHPString() {
        return this.getCurrentHP() + "/" + this.getEffectedMaximumHP();
    }

    public final BufferedImageIcon getImage() {
        if (this.image == null) {
            this.image = this.getInitialImage();
        }
        return this.image;
    }

    protected abstract BufferedImageIcon getInitialImage();

    public final int getIntelligence() {
        return this.getStat(StatConstants.STAT_INTELLIGENCE);
    }

    public final ItemInventory getItems() {
        return this.items;
    }

    public final int getLevel() {
        return this.getStat(StatConstants.STAT_LEVEL);
    }

    public final int getLuck() {
        return this.getStat(StatConstants.STAT_LUCK);
    }

    public final int getMaximumHP() {
        return (int) (this.getVitality()
                * StatConstants.FACTOR_VITALITY_HEALTH);
    }

    public final int getMaximumMP() {
        return (int) (this.getIntelligence()
                * StatConstants.FACTOR_INTELLIGENCE_MAGIC);
    }

    public static int getMaximumLevel() {
        return StatConstants.LEVEL_MAX;
    }

    public final long getPrestigeValue(final int which) {
        return this.prestige[which];
    }

    public static Color getPrestigeColor(final int which) {
        switch (which) {
        case PrestigeConstants.PRESTIGE_DAMAGE_GIVEN:
            return Color.BLUE;
        case PrestigeConstants.PRESTIGE_DAMAGE_TAKEN:
            return Color.RED;
        case PrestigeConstants.PRESTIGE_HITS_GIVEN:
            return Color.BLUE;
        case PrestigeConstants.PRESTIGE_HITS_TAKEN:
            return Color.RED;
        case PrestigeConstants.PRESTIGE_ATTACKS_DODGED:
            return Color.BLUE;
        case PrestigeConstants.PRESTIGE_MISSED_ATTACKS:
            return Color.RED;
        case PrestigeConstants.PRESTIGE_MONSTERS_KILLED:
            return Color.BLUE;
        case PrestigeConstants.PRESTIGE_SPELLS_CAST:
            return Color.RED;
        case PrestigeConstants.PRESTIGE_TIMES_KILLED:
            return Color.RED;
        case PrestigeConstants.PRESTIGE_TIMES_RAN_AWAY:
            return Color.RED;
        default:
            return Color.BLACK;
        }
    }

    protected final int getLoad() {
        return this.getStat(StatConstants.STAT_LOAD);
    }

    private int getEffectedMaximumHP() {
        return (int) this.getEffectedStat(StatConstants.STAT_MAXIMUM_HP);
    }

    private int getEffectedMaximumMP() {
        return (int) this.getEffectedStat(StatConstants.STAT_MAXIMUM_MP);
    }

    public final int getEffectedAttacksPerRound() {
        return (int) this.getEffectedStat(StatConstants.STAT_ATTACKS_PER_ROUND);
    }

    public final int getEffectedSpellsPerRound() {
        return (int) this.getEffectedStat(StatConstants.STAT_SPELLS_PER_ROUND);
    }

    public final int getEffectedItemsPerRound() {
        return (int) this.getEffectedStat(StatConstants.STAT_ITEMS_PER_ROUND);
    }

    public final int getEffectedStealsPerRound() {
        return (int) this.getEffectedStat(StatConstants.STAT_STEALS_PER_ROUND);
    }

    public final String getMPString() {
        return this.getCurrentMP() + "/" + this.getEffectedMaximumMP();
    }

    public abstract String getName();

    public final int getEffectedSpeed() {
        return (int) (this.getEffectedStat(StatConstants.STAT_AGILITY)
                * StatConstants.FACTOR_AGILITY_SPEED
                - this.items.getTotalEquipmentWeight()
                        * StatConstants.FACTOR_LOAD_SPEED);
    }

    private int getSpeed() {
        return (int) (this.getAgility() * StatConstants.FACTOR_AGILITY_SPEED
                - this.items.getTotalEquipmentWeight()
                        * StatConstants.FACTOR_LOAD_SPEED);
    }

    public final SpellBook getSpellBook() {
        return this.spellsKnown;
    }

    protected final int getSpellsPerRound() {
        return this.getStat(StatConstants.STAT_SPELLS_PER_ROUND);
    }

    public final int getStat(final int stat) {
        try {
            return this.stats[stat].getValue();
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
            case StatConstants.STAT_SPEED:
                return this.getSpeed();
            case StatConstants.STAT_HIT:
                return this.getHit();
            case StatConstants.STAT_EVADE:
                return this.getEvade();
            case StatConstants.STAT_CAPACITY:
                return this.getCapacity();
            case StatConstants.STAT_MAGIC_POWER:
                return this.getMagicPower();
            case StatConstants.STAT_MAGIC_DEFENSE:
                return this.getMagicDefense();
            case StatConstants.STAT_MAX_LEVEL:
                return Creature.getMaximumLevel();
            default:
                return 0;
            }
        }
    }

    private boolean getHasStatMin(final int stat) {
        try {
            return this.stats[stat].hasMin();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return false;
        }
    }

    private boolean getHasStatMax(final int stat) {
        try {
            return this.stats[stat].hasMax();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return false;
        }
    }

    private int getStatMin(final int stat) {
        try {
            return this.stats[stat].getMinVal();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return 0;
        }
    }

    private int getStatMax(final int stat) {
        try {
            return this.stats[stat].getMaxID();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            return 0;
        }
    }

    public final int getStrength() {
        return this.getStat(StatConstants.STAT_STRENGTH);
    }

    public final long getToNextLevelValue() {
        if (this.toNextLevel != null) {
            if (this.getLevel() == Creature.getMaximumLevel()) {
                return this.getExperience();
            } else {
                return this.toNextLevel.evaluate(this.getLevel() + 1);
            }
        } else {
            return 0;
        }
    }

    private long getMaximumExperience() {
        if (this.toNextLevel != null) {
            return this.toNextLevel.evaluate(Creature.getMaximumLevel());
        } else {
            return Long.MAX_VALUE;
        }
    }

    public final double getEffectedAttack() {
        return this.getEffectedStat(StatConstants.STAT_ATTACK);
    }

    public static long getAdjustedExperience(final long baseExp,
            final int baseLevel, final int otherLevel) {
        return Math.max((long) (baseExp
                + (otherLevel - baseLevel) * baseExp * Creature.EXP_ADJUST), 0);
    }

    public final int getVitality() {
        return this.getStat(StatConstants.STAT_VITALITY);
    }

    public final void heal(final int amount) {
        this.offsetCurrentHP(amount);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public final void healAndRegenerateFully() {
        this.healFully();
        this.regenerateFully();
    }

    private void healFully() {
        this.setCurrentHP(this.getEffectedMaximumHP());
    }

    public final void healPercentage(final int percent) {
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

    private int indexOf(final Effect e) {
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

    public final boolean isAlive() {
        return this.getCurrentHP() > 0;
    }

    public final boolean isEffectActive(final Effect e) {
        final int index = this.indexOf(e);
        if (index != -1) {
            final Effect le = this.get(index);
            if (le != null) {
                return le.isActive();
            }
        }
        return false;
    }

    public final InternalScript levelUp() {
        this.offsetLevel(1);
        return this.levelUpHook();
    }

    protected abstract InternalScript levelUpHook();

    protected final void offsetAgility(final int value) {
        this.stats[StatConstants.STAT_AGILITY].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_AGILITY);
    }

    protected final void offsetBlock(final int value) {
        this.stats[StatConstants.STAT_BLOCK].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_BLOCK);
    }

    private void offsetCurrentHP(final int value) {
        this.stats[StatConstants.STAT_CURRENT_HP].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public final void offsetCurrentMP(final int value) {
        this.stats[StatConstants.STAT_CURRENT_MP].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    public final void offsetExperience(final long value) {
        if (this.experience + value > this.getMaximumExperience()) {
            this.experience = this.getMaximumExperience();
        } else {
            this.experience += value;
        }
    }

    public void offsetGold(final int value) {
        this.stats[StatConstants.STAT_GOLD].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_GOLD);
    }

    protected final void offsetIntelligence(final int value) {
        this.stats[StatConstants.STAT_INTELLIGENCE].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_INTELLIGENCE);
    }

    private void offsetLevel(final int value) {
        this.stats[StatConstants.STAT_LEVEL].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_LEVEL);
    }

    protected final void offsetLuck(final int value) {
        this.stats[StatConstants.STAT_LUCK].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_LUCK);
    }

    public final void offsetLoad(final int value) {
        this.stats[StatConstants.STAT_LOAD].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_LOAD);
    }

    public final void offsetPrestigeValue(final int which, final long value) {
        this.prestige[which] += value;
    }

    protected final void offsetStrength(final int value) {
        this.stats[StatConstants.STAT_STRENGTH].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_STRENGTH);
    }

    protected final void offsetVitality(final int value) {
        this.stats[StatConstants.STAT_VITALITY].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_VITALITY);
    }

    public final void regenerate(final int amount) {
        this.offsetCurrentMP(amount);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    private void regenerateFully() {
        this.setCurrentMP(this.getMaximumMP());
    }

    public final void regeneratePercentage(final int percent) {
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

    private void set(final int x, final Effect e) {
        this.effectList[x] = e;
    }

    public final void setAgility(final int value) {
        this.setStat(StatConstants.STAT_AGILITY, value);
    }

    public final void setAI(final AIRoutine newAI) {
        this.ai = newAI;
    }

    public final void setAttacksPerRound(final int value) {
        this.setStat(StatConstants.STAT_ATTACKS_PER_ROUND, value);
    }

    public final void setItemsPerRound(final int value) {
        this.setStat(StatConstants.STAT_ITEMS_PER_ROUND, value);
    }

    public final void setStealsPerRound(final int value) {
        this.setStat(StatConstants.STAT_STEALS_PER_ROUND, value);
    }

    public final void setBlock(final int value) {
        this.setStat(StatConstants.STAT_BLOCK, value);
    }

    public final void setCurrentHP(final int value) {
        this.setStat(StatConstants.STAT_CURRENT_HP, value);
    }

    public final void setCurrentMP(final int value) {
        this.setStat(StatConstants.STAT_CURRENT_MP, value);
    }

    public final void setExperience(final long value) {
        if (value > this.getMaximumExperience()) {
            this.experience = this.getMaximumExperience();
        } else {
            this.experience = value;
        }
    }

    public final void setGold(final int value) {
        this.setStat(StatConstants.STAT_GOLD, value);
    }

    public final void setIntelligence(final int value) {
        this.setStat(StatConstants.STAT_INTELLIGENCE, value);
    }

    protected final void setItems(final ItemInventory newItems) {
        this.items = newItems;
    }

    public final void setLevel(final int value) {
        this.setStat(StatConstants.STAT_LEVEL, value);
    }

    public final void setLuck(final int value) {
        this.setStat(StatConstants.STAT_LUCK, value);
    }

    protected final void setLoad(final int value) {
        this.setStat(StatConstants.STAT_LOAD, value);
    }

    protected final void setPrestigeValue(final int which, final long value) {
        this.prestige[which] = value;
    }

    public final void setSpellBook(final SpellBook book) {
        this.spellsKnown = book;
    }

    public final void setSpellsPerRound(final int value) {
        this.setStat(StatConstants.STAT_SPELLS_PER_ROUND, value);
    }

    private void setStat(final int stat, final int value) {
        this.stats[stat].setValue(value);
        this.fixStatValue(stat);
    }

    private void setStatFixed(final int stat, final int value) {
        try {
            this.stats[stat].setValue(value);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }

    public final void setStrength(final int value) {
        this.setStat(StatConstants.STAT_STRENGTH, value);
    }

    protected final void setToNextLevel(final Page nextLevelEquation) {
        this.toNextLevel = nextLevelEquation;
    }

    public final void setVitality(final int value) {
        this.setStat(StatConstants.STAT_VITALITY, value);
    }

    public final void stripAllEffects() {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            this.set(x, null);
        }
    }

    public final void useEffects() {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            try {
                final Effect e = this.get(x);
                if (e != null) {
                    e.useEffect(this);
                }
            } catch (final ArrayIndexOutOfBoundsException np) {
                // Do nothing
            }
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (this.ai == null ? 0 : this.ai.hashCode());
        result = prime * result + Arrays.hashCode(this.effectList);
        result = prime * result
                + (int) (this.experience ^ this.experience >>> 32);
        result = prime * result
                + (this.items == null ? 0 : this.items.hashCode());
        result = prime * result
                + (this.spellsKnown == null ? 0 : this.spellsKnown.hashCode());
        result = prime * result + Arrays.hashCode(this.stats);
        result = prime * result + this.teamID;
        return prime * result
                + (this.toNextLevel == null ? 0 : this.toNextLevel.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
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
        if (!Arrays.equals(this.effectList, other.effectList)) {
            return false;
        }
        if (this.experience != other.experience) {
            return false;
        }
        if (this.items == null) {
            if (other.items != null) {
                return false;
            }
        } else if (!this.items.equals(other.items)) {
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
        if (this.teamID != other.teamID) {
            return false;
        }
        if (this.toNextLevel == null) {
            if (other.toNextLevel != null) {
                return false;
            }
        } else if (!this.toNextLevel.equals(other.toNextLevel)) {
            return false;
        }
        return true;
    }
}
