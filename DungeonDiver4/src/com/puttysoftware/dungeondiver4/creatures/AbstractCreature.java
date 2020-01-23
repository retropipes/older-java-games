/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.creatures;

import java.awt.Color;
import java.util.Arrays;

import com.puttysoftware.dungeondiver4.ai.map.AbstractMapAIRoutine;
import com.puttysoftware.dungeondiver4.ai.window.AbstractWindowAIRoutine;
import com.puttysoftware.dungeondiver4.creatures.faiths.Faith;
import com.puttysoftware.dungeondiver4.effects.Effect;
import com.puttysoftware.dungeondiver4.items.ItemInventory;
import com.puttysoftware.dungeondiver4.spells.SpellBook;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.page.Page;
import com.puttysoftware.randomrange.RandomRange;

public abstract class AbstractCreature {
    // Fields
    protected BufferedImageIcon image;
    private final long[] prestige;
    private final Statistic[] stats;
    private long experience;
    private final Effect[] effectList;
    private SpellBook spellsKnown;
    private AbstractMapAIRoutine mapAI;
    private AbstractWindowAIRoutine windowAI;
    private ItemInventory items;
    private Page toNextLevel;
    private int teamID;
    private int xLoc, yLoc;
    private int saveX, saveY;
    private static int ACTION_CAP = 1;
    private static final int MAX_EFFECTS = 100;
    public static final int FULL_HEAL_PERCENTAGE = 100;
    public static final int TEAM_PARTY = 0;

    // Constructor
    protected AbstractCreature(boolean hasCombatItems) {
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
        this.effectList = new Effect[AbstractCreature.MAX_EFFECTS];
        this.spellsKnown = null;
        this.mapAI = null;
        this.windowAI = null;
        this.items = new ItemInventory(hasCombatItems);
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

    public final void setX(int newX) {
        this.xLoc = newX;
    }

    public final void setY(int newY) {
        this.yLoc = newY;
    }

    public final void offsetX(int newX) {
        this.xLoc += newX;
    }

    public final void offsetY(int newY) {
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

    public final void setTeamID(int team) {
        this.teamID = team;
    }

    public final void applyEffect(final Effect e) {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            if (this.get(x) == null) {
                this.set(x, e);
                e.scaleEffect(Effect.EFFECT_ADD, this);
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
        long damageGiven = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_GIVEN);
        long damageTaken = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_TAKEN);
        long hitsGiven = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_HITS_GIVEN);
        long hitsTaken = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_HITS_TAKEN);
        long attacksDodged = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_ATTACKS_DODGED);
        long missedAttacks = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_MISSED_ATTACKS);
        long monstersKilled = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_MONSTERS_KILLED);
        long spellsCast = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_SPELLS_CAST);
        long timesKilled = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_KILLED);
        long timesRanAway = this
                .getPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_RAN_AWAY);
        return (damageGiven - damageTaken) / 10 + hitsGiven + attacksDodged
                + (2 * monstersKilled) - hitsTaken - (2 * missedAttacks)
                - (3 * spellsCast) - (10 * timesKilled) - (50 * timesRanAway);
    }

    public final int getActiveEffectCount() {
        int x, c;
        c = 0;
        for (x = 0; x < this.effectList.length; x++) {
            try {
                Effect e = this.get(x);
                if (e.isActive()) {
                    c++;
                }
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        return c;
    }

    public final void cullInactiveEffects() {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            try {
                Effect e = this.get(x);
                if (!(e.isActive())) {
                    this.set(x, null);
                }
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }

    public final void doDamage(final int damage) {
        this.offsetCurrentHP(-damage);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public final void doDamageMultiply(final double damage, boolean max) {
        this.offsetCurrentHPMultiply(damage, max);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public final void doDamagePercentage(final int percent) {
        int fP = percent;
        if (fP > AbstractCreature.FULL_HEAL_PERCENTAGE) {
            fP = AbstractCreature.FULL_HEAL_PERCENTAGE;
        }
        if (fP < 0) {
            fP = 0;
        }
        final double fPMultiplier = fP
                / (double) AbstractCreature.FULL_HEAL_PERCENTAGE;
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

    public void drainPercentage(int percent) {
        int fP = percent;
        if (fP > AbstractCreature.FULL_HEAL_PERCENTAGE) {
            fP = AbstractCreature.FULL_HEAL_PERCENTAGE;
        }
        if (fP < 0) {
            fP = 0;
        }
        final double fPMultiplier = fP
                / (double) AbstractCreature.FULL_HEAL_PERCENTAGE;
        int modValue = (int) (this.getMaximumMP() * fPMultiplier);
        if (modValue <= 0) {
            modValue = 1;
        }
        this.offsetCurrentMP(-modValue);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    public final void drainMultiply(final double cost, final boolean max) {
        this.offsetCurrentMPMultiply(cost, max);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    public final void extendEffect(final Effect e, final int rounds) {
        final int index = this.indexOf(e);
        if (index != -1) {
            this.get(index).extendEffect(rounds);
        }
    }

    private void fixStatValue(int stat) {
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

    private final Effect get(int x) {
        return this.effectList[x];
    }

    public int getMapBattleActionsPerRound() {
        int value = (int) Math.sqrt(Math.ceil(this
                .getEffectedStat(StatConstants.STAT_AGILITY)
                * StatConstants.FACTOR_AGILITY_MAP_ACTIONS_PER_ROUND));
        if (value > AbstractCreature.ACTION_CAP) {
            return AbstractCreature.ACTION_CAP;
        } else {
            return value;
        }
    }

    public int getWindowBattleActionsPerRound() {
        int value = (int) Math.sqrt(Math.ceil(this
                .getEffectedStat(StatConstants.STAT_AGILITY)
                * StatConstants.FACTOR_AGILITY_WINDOW_ACTIONS_PER_ROUND));
        if (value > AbstractCreature.ACTION_CAP) {
            return AbstractCreature.ACTION_CAP;
        } else {
            return value;
        }
    }

    public static void computeActionCap(int rows, int cols) {
        int avg = (rows + cols) / 2;
        int mult = (int) Math.sqrt(avg);
        double temp = avg * mult;
        AbstractCreature.ACTION_CAP = (int) (Math.round(temp / 10.0) * 10.0);
    }

    public final int getAgility() {
        return this.getStat(StatConstants.STAT_AGILITY);
    }

    public final AbstractMapAIRoutine getMapAI() {
        return this.mapAI;
    }

    public AbstractWindowAIRoutine getWindowAI() {
        return this.windowAI;
    }

    public final boolean hasAI() {
        return this.mapAI != null;
    }

    public final String getAllCurrentEffectMessages() {
        int x;
        StringBuilder sb = new StringBuilder(Effect.getNullMessage());
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

    public final int getArmorBlock() {
        return (int) (this.getItems().getTotalAbsorb() * StatConstants.FACTOR_ABSORB_DEFENSE);
    }

    public int getAttack() {
        return (int) (this.getStrength() * StatConstants.FACTOR_STRENGTH_ATTACK + this
                .getItems().getTotalPower() * StatConstants.FACTOR_POWER_ATTACK);
    }

    public final String getAttackString() {
        return "Attack: " + this.getEffectedStat(StatConstants.STAT_ATTACK)
                + " (" + this.getAttack() + ")";
    }

    public final int getAttacksPerRound() {
        return this.getStat(StatConstants.STAT_ATTACKS_PER_ROUND);
    }

    public final int getBlock() {
        return this.getStat(StatConstants.STAT_BLOCK);
    }

    public int getCapacity() {
        return Math.max(StatConstants.MIN_CAPACITY, (int) (this.getStrength()
                * StatConstants.FACTOR_STRENGTH_CAPACITY + this.getAgility()
                * StatConstants.FACTOR_AGILITY_CAPACITY));
    }

    public final String[] getCompleteEffectStringArray() {
        int x, z;
        z = this.getActiveEffectCount();
        String[] s = new String[z];
        int counter = 0;
        for (x = 0; x < z; x++) {
            if (this.effectList[x] != null) {
                s[counter] = this.effectList[x].getEffectString();
                counter++;
            }
        }
        return s;
    }

    public final String getCompleteEffectString() {
        int x;
        String s = "";
        for (x = 0; x < this.effectList.length; x++) {
            try {
                s += this.get(x).getEffectString() + "\n";
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
        // Strip final newline character, if it exists
        if (!s.equals(Effect.getNullMessage())) {
            s = s.substring(0, s.length() - 1);
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
        return (int) (this.getBlock() * StatConstants.FACTOR_BLOCK_DEFENSE + this
                .getItems().getTotalAbsorb()
                * StatConstants.FACTOR_ABSORB_DEFENSE);
    }

    public final String getDefenseString() {
        return "Defense: " + this.getEffectedStat(StatConstants.STAT_DEFENSE)
                + " (" + this.getDefense() + ")";
    }

    public final double getEffectedStat(int stat) {
        int x, s, p;
        s = 0;
        p = this.getStat(stat);
        for (x = 0; x < this.effectList.length; x++) {
            Effect e = this.get(x);
            if (e != null) {
                p *= e.getEffect(Effect.EFFECT_MULTIPLY, stat);
                s += e.getEffect(Effect.EFFECT_ADD, stat);
            }
        }
        return p + s;
    }

    public final int getEvade() {
        int chance = StatConstants.EVADE_BASE;
        double agilityContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_AGILITY))
                * StatConstants.FACTOR_AGILITY_EVADE;
        double luckContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_LUCK))
                * StatConstants.FACTOR_LUCK_EVADE;
        int modifier = (int) Math.round(agilityContrib + luckContrib);
        return Math.min(chance + modifier, StatConstants.EVADE_MAX);
    }

    public final long getExperience() {
        return this.experience;
    }

    public Faith getFaith() {
        return null;
    }

    public final int getGold() {
        return this.getStat(StatConstants.STAT_GOLD);
    }

    public final int getHit() {
        int chance = StatConstants.HIT_BASE;
        double strengthContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_STRENGTH))
                * StatConstants.FACTOR_STRENGTH_HIT;
        double luckContrib = Math.max(0,
                this.getEffectedStat(StatConstants.STAT_LUCK))
                * StatConstants.FACTOR_LUCK_HIT;
        int modifier = (int) Math.round(strengthContrib + luckContrib);
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

    public int getMaximumHP() {
        return (int) (this.getVitality() * StatConstants.FACTOR_VITALITY_HEALTH);
    }

    public int getMaximumMP() {
        return (int) (this.getIntelligence() * StatConstants.FACTOR_INTELLIGENCE_MAGIC);
    }

    static int getMaximumLevel() {
        return StatConstants.LEVEL_MAX;
    }

    public final long getPrestigeValue(int which) {
        return this.prestige[which];
    }

    public final static Color getPrestigeColor(int which) {
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

    public final int getLoad() {
        return this.getStat(StatConstants.STAT_LOAD);
    }

    private final int getEffectedMaximumHP() {
        return (int) this.getEffectedStat(StatConstants.STAT_MAXIMUM_HP);
    }

    private final int getEffectedMaximumMP() {
        return (int) this.getEffectedStat(StatConstants.STAT_MAXIMUM_MP);
    }

    public final int getEffectedAttacksPerRound() {
        return (int) this.getEffectedStat(StatConstants.STAT_ATTACKS_PER_ROUND);
    }

    public final int getEffectedSpellsPerRound() {
        return (int) this.getEffectedStat(StatConstants.STAT_SPELLS_PER_ROUND);
    }

    public final String getMPString() {
        return this.getCurrentMP() + "/" + this.getEffectedMaximumMP();
    }

    public abstract String getName();

    public final int getEffectedSpeed() {
        return (int) (this.getEffectedStat(StatConstants.STAT_AGILITY)
                * StatConstants.FACTOR_AGILITY_SPEED - (this.items
                .getTotalEquipmentWeight() + this.items
                .getTotalInventoryWeight())
                * StatConstants.FACTOR_LOAD_SPEED);
    }

    public final int getSpeed() {
        return (int) (this.getAgility() * StatConstants.FACTOR_AGILITY_SPEED - (this.items
                .getTotalEquipmentWeight() + this.items
                .getTotalInventoryWeight())
                * StatConstants.FACTOR_LOAD_SPEED);
    }

    public final SpellBook getSpellBook() {
        return this.spellsKnown;
    }

    public final int getSpellsPerRound() {
        return this.getStat(StatConstants.STAT_SPELLS_PER_ROUND);
    }

    public final int getStat(int stat) {
        try {
            return this.stats[stat].getValue();
        } catch (ArrayIndexOutOfBoundsException aioob) {
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
            case StatConstants.STAT_MAX_LEVEL:
                return AbstractCreature.getMaximumLevel();
            default:
                return 0;
            }
        }
    }

    private boolean getHasStatMin(int stat) {
        try {
            return this.stats[stat].hasMin();
        } catch (ArrayIndexOutOfBoundsException aioob) {
            return false;
        }
    }

    private boolean getHasStatMax(int stat) {
        try {
            return this.stats[stat].hasMax();
        } catch (ArrayIndexOutOfBoundsException aioob) {
            return false;
        }
    }

    private int getStatMin(int stat) {
        try {
            return this.stats[stat].getMinVal();
        } catch (ArrayIndexOutOfBoundsException aioob) {
            return 0;
        }
    }

    private int getStatMax(int stat) {
        try {
            return this.stats[stat].getMaxID();
        } catch (ArrayIndexOutOfBoundsException aioob) {
            return 0;
        }
    }

    public final int getStrength() {
        return this.getStat(StatConstants.STAT_STRENGTH);
    }

    public final long getToNextLevelValue() {
        if (this.toNextLevel != null) {
            if (this.getLevel() == AbstractCreature.getMaximumLevel()) {
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
            return this.toNextLevel
                    .evaluate(AbstractCreature.getMaximumLevel());
        } else {
            return Long.MAX_VALUE;
        }
    }

    public final double getEffectedAttack() {
        return this.getEffectedStat(StatConstants.STAT_ATTACK);
    }

    public static long getAdjustedExperience(long value) {
        return value;
    }

    public final int getVitality() {
        return this.getStat(StatConstants.STAT_VITALITY);
    }

    public final void heal(final int amount) {
        this.offsetCurrentHP(amount);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public final void healMultiply(final double amount, final boolean max) {
        this.offsetCurrentHPMultiply(amount, max);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public final void healAndRegenerateFully() {
        this.healFully();
        this.regenerateFully();
    }

    private final void healFully() {
        this.setCurrentHP(this.getEffectedMaximumHP());
    }

    public final void healPercentage(final int percent) {
        int fP = percent;
        if (fP > AbstractCreature.FULL_HEAL_PERCENTAGE) {
            fP = AbstractCreature.FULL_HEAL_PERCENTAGE;
        }
        if (fP < 0) {
            fP = 0;
        }
        final double fPMultiplier = fP
                / (double) AbstractCreature.FULL_HEAL_PERCENTAGE;
        final int difference = this.getEffectedMaximumHP()
                - this.getCurrentHP();
        int modValue = (int) (difference * fPMultiplier);
        if (modValue <= 0) {
            modValue = 1;
        }
        this.offsetCurrentHP(modValue);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    private final int indexOf(Effect e) {
        int x;
        for (x = 0; x < this.effectList.length; x++) {
            Effect le = this.get(x);
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
            return this.get(index).isActive();
        } else {
            return false;
        }
    }

    public final void levelUp() {
        this.offsetLevel(1);
        this.levelUpHook();
    }

    protected abstract void levelUpHook();

    public final void offsetAgility(int value) {
        this.stats[StatConstants.STAT_AGILITY].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_AGILITY);
    }

    public final void offsetBlock(int value) {
        this.stats[StatConstants.STAT_BLOCK].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_BLOCK);
    }

    private final void offsetCurrentHP(int value) {
        this.stats[StatConstants.STAT_CURRENT_HP].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    private final void offsetCurrentHPMultiply(double value, boolean max) {
        this.stats[StatConstants.STAT_CURRENT_HP].offsetValueMultiply(value,
                max, this.getStat(StatConstants.STAT_MAXIMUM_HP));
        this.fixStatValue(StatConstants.STAT_CURRENT_HP);
    }

    public final void offsetCurrentMP(int value) {
        this.stats[StatConstants.STAT_CURRENT_MP].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    private final void offsetCurrentMPMultiply(double value, boolean max) {
        this.stats[StatConstants.STAT_CURRENT_MP].offsetValueMultiply(value,
                max, this.getStat(StatConstants.STAT_MAXIMUM_MP));
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    public final void offsetExperience(long value) {
        if (this.experience + value > this.getMaximumExperience()) {
            this.experience = this.getMaximumExperience();
        } else {
            this.experience += value;
        }
    }

    public void offsetGold(int value) {
        this.stats[StatConstants.STAT_GOLD].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_GOLD);
    }

    public final void offsetIntelligence(int value) {
        this.stats[StatConstants.STAT_INTELLIGENCE].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_INTELLIGENCE);
    }

    private final void offsetLevel(int value) {
        this.stats[StatConstants.STAT_LEVEL].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_LEVEL);
    }

    public final void offsetLuck(int value) {
        this.stats[StatConstants.STAT_LUCK].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_LUCK);
    }

    public final void offsetLoad(int value) {
        this.stats[StatConstants.STAT_LOAD].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_LOAD);
    }

    public final void offsetPrestigeValue(int which, long value) {
        this.prestige[which] += value;
    }

    public final void offsetStrength(int value) {
        this.stats[StatConstants.STAT_STRENGTH].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_STRENGTH);
    }

    public final void offsetVitality(int value) {
        this.stats[StatConstants.STAT_VITALITY].offsetValue(value);
        this.fixStatValue(StatConstants.STAT_VITALITY);
    }

    public final void regenerate(final int amount) {
        this.offsetCurrentMP(amount);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    public final void regenerateMultiply(final double amount, final boolean max) {
        this.offsetCurrentMPMultiply(amount, max);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    private final void regenerateFully() {
        this.setCurrentMP(this.getMaximumMP());
    }

    public final void regeneratePercentage(final int percent) {
        int fP = percent;
        if (fP > AbstractCreature.FULL_HEAL_PERCENTAGE) {
            fP = AbstractCreature.FULL_HEAL_PERCENTAGE;
        }
        if (fP < 0) {
            fP = 0;
        }
        final double fPMultiplier = fP
                / (double) AbstractCreature.FULL_HEAL_PERCENTAGE;
        final int difference = this.getMaximumMP() - this.getCurrentMP();
        int modValue = (int) (difference * fPMultiplier);
        if (modValue <= 0) {
            modValue = 1;
        }
        this.offsetCurrentMP(modValue);
        this.fixStatValue(StatConstants.STAT_CURRENT_MP);
    }

    private final void set(int x, Effect e) {
        this.effectList[x] = e;
    }

    public final void setAgility(int value) {
        this.setStat(StatConstants.STAT_AGILITY, value);
    }

    public final void setMapAI(AbstractMapAIRoutine newAI) {
        this.mapAI = newAI;
    }

    public final void setWindowAI(AbstractWindowAIRoutine newAI) {
        this.windowAI = newAI;
    }

    public final void setAttacksPerRound(int value) {
        this.setStat(StatConstants.STAT_ATTACKS_PER_ROUND, value);
    }

    public final void setBlock(int value) {
        this.setStat(StatConstants.STAT_BLOCK, value);
    }

    public final void setCurrentHP(int value) {
        this.setStat(StatConstants.STAT_CURRENT_HP, value);
    }

    public final void setCurrentMP(int value) {
        this.setStat(StatConstants.STAT_CURRENT_MP, value);
    }

    public final void setExperience(long value) {
        if (value > this.getMaximumExperience()) {
            this.experience = this.getMaximumExperience();
        } else {
            this.experience = value;
        }
    }

    public final void setGold(int value) {
        this.setStat(StatConstants.STAT_GOLD, value);
    }

    public final void setIntelligence(int value) {
        this.setStat(StatConstants.STAT_INTELLIGENCE, value);
    }

    public final void setItems(ItemInventory newItems) {
        this.items = newItems;
    }

    public final void setLevel(int value) {
        this.setStat(StatConstants.STAT_LEVEL, value);
    }

    public final void setLuck(int value) {
        this.setStat(StatConstants.STAT_LUCK, value);
    }

    public final void setLoad(int value) {
        this.setStat(StatConstants.STAT_LOAD, value);
    }

    public final void setPrestigeValue(int which, long value) {
        this.prestige[which] = value;
    }

    public final void setSpellBook(SpellBook book) {
        this.spellsKnown = book;
    }

    public final void setSpellsPerRound(int value) {
        this.setStat(StatConstants.STAT_SPELLS_PER_ROUND, value);
    }

    private final void setStat(int stat, int value) {
        int dynValue;
        if (this.stats[stat].getDynamism() != 0) {
            RandomRange r = new RandomRange(-this.stats[stat].getDynamism(),
                    this.stats[stat].getDynamism());
            dynValue = value + r.generate();
        } else {
            dynValue = value;
        }
        this.stats[stat].setValue(dynValue);
        this.fixStatValue(stat);
    }

    private void setStatFixed(int stat, int value) {
        try {
            this.stats[stat].setValue(value);
        } catch (ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }

    public final void setStrength(int value) {
        this.setStat(StatConstants.STAT_STRENGTH, value);
    }

    public final void setToNextLevel(Page nextLevelEquation) {
        this.toNextLevel = nextLevelEquation;
    }

    public final void setVitality(int value) {
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
                this.get(x).useEffect(this);
            } catch (final NullPointerException np) {
                // Do nothing
            } catch (final ArrayIndexOutOfBoundsException aioob) {
                // Do nothing
            }
        }
    }

    public String getFightingWhatString() {
        String enemyName = this.getName();
        boolean vowel = this.isFirstLetterVowel(enemyName);
        String fightingWhat = null;
        if (vowel) {
            fightingWhat = "You're fighting an " + enemyName;
        } else {
            fightingWhat = "You're fighting a " + enemyName;
        }
        return fightingWhat;
    }

    protected boolean isFirstLetterVowel(final String s) {
        final String firstLetter = s.substring(0, 1);
        if (firstLetter.equalsIgnoreCase("A")
                || firstLetter.equalsIgnoreCase("E")
                || firstLetter.equalsIgnoreCase("I")
                || firstLetter.equalsIgnoreCase("O")
                || firstLetter.equalsIgnoreCase("U")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + ((this.mapAI == null) ? 0 : this.mapAI.hashCode());
        result = prime * result + Arrays.hashCode(this.effectList);
        result = prime * result
                + (int) (this.experience ^ (this.experience >>> 32));
        result = prime * result
                + ((this.items == null) ? 0 : this.items.hashCode());
        result = prime
                * result
                + ((this.spellsKnown == null) ? 0 : this.spellsKnown.hashCode());
        result = prime * result + Arrays.hashCode(this.stats);
        result = prime * result + this.teamID;
        return prime
                * result
                + ((this.toNextLevel == null) ? 0 : this.toNextLevel.hashCode());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof AbstractCreature)) {
            return false;
        }
        AbstractCreature other = (AbstractCreature) obj;
        if (this.mapAI == null) {
            if (other.mapAI != null) {
                return false;
            }
        } else if (!this.mapAI.equals(other.mapAI)) {
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
