/*  FantastleReboot: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlereboot.creatures;

import java.util.ArrayList;
import java.util.Arrays;

import com.puttysoftware.fantastlereboot.ai.map.AbstractMapAIRoutine;
import com.puttysoftware.fantastlereboot.creatures.faiths.Faith;
import com.puttysoftware.fantastlereboot.creatures.jobs.Job;
import com.puttysoftware.fantastlereboot.creatures.jobs.JobConstants;
import com.puttysoftware.fantastlereboot.creatures.party.PartyManager;
import com.puttysoftware.fantastlereboot.creatures.races.Race;
import com.puttysoftware.fantastlereboot.creatures.races.RaceConstants;
import com.puttysoftware.fantastlereboot.effects.Effect;
import com.puttysoftware.fantastlereboot.items.ItemInventory;
import com.puttysoftware.fantastlereboot.spells.SpellBook;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.page.Page;
import com.puttysoftware.randomrange.RandomRange;

public abstract class Creature {
  // Fields
  protected BufferedImageIcon image;
  private final Statistic[] stats;
  private long experience;
  private final ArrayList<Effect> effectList;
  private SpellBook spellsKnown;
  private AbstractMapAIRoutine mapAI;
  private ItemInventory items;
  private Page toNextLevel;
  private final int teamID;
  private final int perfectBonusGold;
  private int xLoc, yLoc;
  private int saveX, saveY;
  private Race race;
  private Job job;
  private Faith faith;
  private static int ACTION_CAP = 1;
  private static final int MAX_EFFECTS = 100;
  private static final int BAR_SPEED_MIN = 100;
  private static final int BAR_SPEED_MAX = 1;
  public static final int FULL_HEAL_PERCENTAGE = 100;
  public static final int TEAM_PARTY = 0;
  public static final int TEAM_ENEMY_FIRST = 1;
  public static final int TEAM_ENEMY_LAST = 10;
  public static final int TEAM_BOSS = 11;
  private static final int FUMBLE_BASE = 10;
  private static final int MAX_AGILITY_CONTRIB = 200;
  private static final int MAX_LUCK_CONTRIB = 200;
  public static final double SPEED_ADJUST_SLOWEST = 0.5;
  public static final double SPEED_ADJUST_SLOW = 0.75;
  public static final double SPEED_ADJUST_NORMAL = 1.0;
  public static final double SPEED_ADJUST_FAST = 1.5;
  public static final double SPEED_ADJUST_FASTEST = 2.0;

  // Constructor
  protected Creature(final ItemInventory ii, final int tid, final Faith f,
      final Job j, final Race r) {
    this.faith = f;
    this.job = j;
    this.race = r;
    this.teamID = tid;
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
    this.stats[StatConstants.STAT_LEVEL].setMaxID(StatConstants.STAT_MAX_LEVEL);
    this.stats[StatConstants.STAT_LOAD].setMaxID(StatConstants.STAT_CAPACITY);
    this.stats[StatConstants.STAT_VITALITY].setMinVal(1);
    this.stats[StatConstants.STAT_AGILITY].setMinVal(1);
    this.stats[StatConstants.STAT_VITALITY].setValue(1);
    this.stats[StatConstants.STAT_AGILITY].setValue(1);
    this.effectList = new ArrayList<>(Creature.MAX_EFFECTS);
    this.spellsKnown = null;
    this.items = ii;
    this.toNextLevel = null;
    this.perfectBonusGold = this.getInitialPerfectBonusGold();
    this.xLoc = -1;
    this.yLoc = -1;
    this.saveX = -1;
    this.saveY = -1;
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

  public int getPerfectBonusGold() {
    return this.perfectBonusGold;
  }

  public final int getTeamID() {
    return this.teamID;
  }

  public final void applyEffect(final Effect ie) {
    for (final Effect e : this.effectList) {
      if (e.isActive() && ie.equals(e)) {
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

  public final void cullInactiveEffects() {
    for (final Effect e : this.effectList) {
      e.deactivateEffect();
    }
  }

  public final void doDamage(final int damage) {
    this.offsetCurrentHP(-damage);
    this.fixStatValue(StatConstants.STAT_CURRENT_HP);
  }

  public final void drain(final int cost) {
    this.offsetCurrentMP(-cost);
    this.fixStatValue(StatConstants.STAT_CURRENT_MP);
  }

  public final void extendEffect(final Effect ie, final int rounds) {
    final Effect e = this.get(ie);
    if (e != null) {
      e.extendEffect(rounds);
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

  private final Effect get(final Effect ie) {
    for (final Effect e : this.effectList) {
      if (e.isActive() && ie.equals(e)) {
        return e;
      }
    }
    return null;
  }

  public int getMapBattleActionsPerRound() {
    final int value = (int) Math
        .sqrt(Math.ceil(this.getEffectedStat(StatConstants.STAT_SPEED)
            * StatConstants.FACTOR_SPEED_MAP_ACTIONS_PER_ROUND));
    return Math.max(1, Math.min(Creature.ACTION_CAP, value));
  }

  public int getWindowBattleActionsPerRound() {
    final int value = (int) Math
        .sqrt(Math.ceil(this.getEffectedStat(StatConstants.STAT_SPEED)
            * StatConstants.FACTOR_SPEED_WINDOW_ACTIONS_PER_ROUND));
    return Math.max(1, Math.min(Creature.ACTION_CAP, value));
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

  public final AbstractMapAIRoutine getMapAI() {
    return this.mapAI;
  }

  public final String getAllCurrentEffectMessages() {
    final StringBuilder sb = new StringBuilder(Effect.getNullMessage());
    for (final Effect e : this.effectList) {
      sb.append(e.getCurrentMessage());
      sb.append("\n");
    }
    String s = sb.toString();
    // Strip final newline character, if it exists
    if (!s.equals(Effect.getNullMessage())) {
      s = s.substring(0, s.length() - 1);
    }
    return s;
  }

  public int getAttack() {
    return (int) (this.getStrength() * StatConstants.FACTOR_STRENGTH_ATTACK
        + this.getItems().getTotalPower() * StatConstants.FACTOR_POWER_ATTACK)
        + this.job.getAttribute(JobConstants.ATTRIBUTE_BONUS_ATTACK);
  }

  public final int getAttacksPerRound() {
    return this.getStat(StatConstants.STAT_ATTACKS_PER_ROUND);
  }

  public final int getBlock() {
    return this.getStat(StatConstants.STAT_BLOCK);
  }

  public int getCapacity() {
    return Math.max(StatConstants.MIN_CAPACITY,
        (int) (this.getStrength() * StatConstants.FACTOR_STRENGTH_CAPACITY
            + this.getAgility() * StatConstants.FACTOR_AGILITY_CAPACITY));
  }

  public final String getCompleteEffectString() {
    String s = "";
    for (final Effect e : this.effectList) {
      s += e.getEffectString() + "\n";
    }
    // Strip final newline character, if it exists
    if (!s.equals(Effect.getNullMessage())) {
      s = s.substring(0, s.length() - 1);
    }
    return s;
  }

  public final int getActiveEffectCount() {
    int c;
    c = 0;
    for (final Effect e : this.effectList) {
      if (e.isActive()) {
        c++;
      }
    }
    return c;
  }

  public final String[] getCompleteEffectStringArray() {
    int z;
    z = this.getActiveEffectCount();
    final String[] s = new String[z];
    int counter = 0;
    for (final Effect e : this.effectList) {
      s[counter] = e.getEffectString();
      counter++;
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
            * StatConstants.FACTOR_ABSORB_DEFENSE)
        + this.job.getAttribute(JobConstants.ATTRIBUTE_BONUS_DEFENSE);
  }

  public final double getEffectedStat(final int stat) {
    int s, p;
    s = 0;
    p = this.getStat(stat);
    for (final Effect e : this.effectList) {
      p *= e.getEffect(Effect.EFFECT_MULTIPLY);
      s += e.getEffect(Effect.EFFECT_ADD);
    }
    return p + s;
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

  public final Faith getFaith() {
    return this.faith;
  }

  public final Job getJob() {
    return this.job;
  }

  public final Race getRace() {
    return this.race;
  }

  protected final void setFaith(final Faith f) {
    this.faith = f;
  }

  protected final void setJob(final Job j) {
    this.job = j;
  }

  protected final void setRace(final Race r) {
    this.race = r;
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

  public abstract void loadCreature();

  protected int getInitialPerfectBonusGold() {
    return 0;
  }

  public int getLevelDifference() {
    return this.getLevel() - PartyManager.getParty().getLeader().getLevel();
  }

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
    return (int) (this.getIntelligence()
        * StatConstants.FACTOR_INTELLIGENCE_MAGIC);
  }

  static int getMaximumLevel() {
    return StatConstants.LEVEL_MAX;
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

  public final String getMPString() {
    return this.getCurrentMP() + "/" + this.getEffectedMaximumMP();
  }

  public abstract String getName();

  public final int getActionBarSpeed() {
    return Math.max(Creature.BAR_SPEED_MIN, Math.min(
        (Creature.BAR_SPEED_MAX - this.getBaseSpeed()) / Creature.BAR_SPEED_MIN,
        Creature.BAR_SPEED_MAX));
  }

  protected final int getBaseSpeed() {
    return (int) (this.getEffectedStat(StatConstants.STAT_AGILITY)
        * StatConstants.FACTOR_AGILITY_SPEED
        - (this.items.getTotalEquipmentWeight()
            + this.items.getTotalInventoryWeight())
            * StatConstants.FACTOR_LOAD_SPEED);
  }

  public abstract int getSpeed();

  public final SpellBook getSpellBook() {
    return this.spellsKnown;
  }

  public final int getSpellsPerRound() {
    return this.getStat(StatConstants.STAT_SPELLS_PER_ROUND);
  }

  public final int getStat(final int stat) {
    if (Creature.statBoundsCheck(stat)) {
      return this.stats[stat].getValue();
    } else {
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
        return Creature.getMaximumLevel();
      case StatConstants.STAT_FUMBLE_CHANCE:
        return this.getFumbleChance();
      default:
        return 0;
      }
    }
  }

  private static boolean statBoundsCheck(final int stat) {
    return stat >= 0 && stat < StatConstants.MAX_STORED_STATS;
  }

  private boolean getHasStatMin(final int stat) {
    return Creature.statBoundsCheck(stat) && this.stats[stat].hasMin();
  }

  private boolean getHasStatMax(final int stat) {
    return Creature.statBoundsCheck(stat) && this.stats[stat].hasMax();
  }

  private int getStatMin(final int stat) {
    if (Creature.statBoundsCheck(stat)) {
      return this.stats[stat].getMinVal();
    }
    return 0;
  }

  private int getStatMax(final int stat) {
    if (Creature.statBoundsCheck(stat)) {
      return this.stats[stat].getMaxID();
    }
    return 0;
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

  public final int getVitality() {
    return this.getStat(StatConstants.STAT_VITALITY);
  }

  public final boolean hasMapAI() {
    return this.mapAI != null;
  }

  public final void heal(final int amount) {
    this.offsetCurrentHP(amount);
    this.fixStatValue(StatConstants.STAT_CURRENT_HP);
  }

  public final void healAndRegenerateFully() {
    this.healFully();
    this.regenerateFully();
  }

  protected final void healFully() {
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
    final int difference = this.getEffectedMaximumHP() - this.getCurrentHP();
    int modValue = (int) (difference * fPMultiplier);
    if (modValue <= 0) {
      modValue = 1;
    }
    this.offsetCurrentHP(modValue);
    this.fixStatValue(StatConstants.STAT_CURRENT_HP);
  }

  public final boolean isAlive() {
    return this.getCurrentHP() > 0;
  }

  public final boolean isEffectActive(final Effect ie) {
    final Effect e = this.get(ie);
    return e != null;
  }

  public final void levelUp() {
    this.offsetLevel(1);
    this.onLevelUp();
  }

  protected abstract void onLevelUp();

  public final void offsetAgility(final int value) {
    this.stats[StatConstants.STAT_AGILITY].offsetValue(value);
    this.fixStatValue(StatConstants.STAT_AGILITY);
  }

  public final void offsetBlock(final int value) {
    this.stats[StatConstants.STAT_BLOCK].offsetValue(value);
    this.fixStatValue(StatConstants.STAT_BLOCK);
  }

  private final void offsetCurrentHP(final int value) {
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
    } else if (this.experience + value < 0) {
      this.experience = 0;
    } else {
      this.experience += value;
    }
  }

  public void offsetGold(final int value) {
    this.stats[StatConstants.STAT_GOLD].offsetValue(value);
    this.fixStatValue(StatConstants.STAT_GOLD);
  }

  public final void offsetIntelligence(final int value) {
    this.stats[StatConstants.STAT_INTELLIGENCE].offsetValue(value);
    this.fixStatValue(StatConstants.STAT_INTELLIGENCE);
  }

  private final void offsetLevel(final int value) {
    this.stats[StatConstants.STAT_LEVEL].offsetValue(value);
    this.fixStatValue(StatConstants.STAT_LEVEL);
  }

  public final void offsetLuck(final int value) {
    this.stats[StatConstants.STAT_LUCK].offsetValue(value);
    this.fixStatValue(StatConstants.STAT_LUCK);
  }

  public final void offsetLoad(final int value) {
    this.stats[StatConstants.STAT_LOAD].offsetValue(value);
    this.fixStatValue(StatConstants.STAT_LOAD);
  }

  public final void offsetStrength(final int value) {
    this.stats[StatConstants.STAT_STRENGTH].offsetValue(value);
    this.fixStatValue(StatConstants.STAT_STRENGTH);
  }

  public final void offsetVitality(final int value) {
    this.stats[StatConstants.STAT_VITALITY].offsetValue(value);
    this.fixStatValue(StatConstants.STAT_VITALITY);
  }

  public abstract void onKillEnemy();

  public abstract void onGotKilled();

  public abstract void onAnnihilateEnemy();

  public abstract void onGotAnnihilated();

  public final void regenerate(final int amount) {
    this.offsetCurrentMP(amount);
    this.fixStatValue(StatConstants.STAT_CURRENT_MP);
  }

  private final void regenerateFully() {
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

  public final void setAgility(final int value) {
    this.setStat(StatConstants.STAT_AGILITY, value);
  }

  public final void setMapAI(final AbstractMapAIRoutine newAI) {
    this.mapAI = newAI;
  }

  public final void setAttacksPerRound(final int value) {
    this.setStat(StatConstants.STAT_ATTACKS_PER_ROUND, value);
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

  public final void setItems(final ItemInventory newItems) {
    this.items = newItems;
  }

  public final void setLevel(final int value) {
    this.setStat(StatConstants.STAT_LEVEL, value);
  }

  protected final void setInitialStats() {
    this.setStrength(this.getInitialStrength());
    this.setBlock(this.getInitialBlock());
    this.setVitality(this.getInitialVitality());
    this.setIntelligence(this.getInitialIntelligence());
    this.setAgility(this.getInitialAgility());
    this.setLuck(this.getInitialLuck());
    this.setCurrentHP(this.getMaximumHP());
    this.setCurrentMP(this.getMaximumMP());
  }

  protected int getInitialStrength() {
    int level = this.getLevel();
    Race r = this.getRace();
    Job j = this.getJob();
    int base = StatConstants.GAIN_STRENGTH * level;
    int raceFixed = r.getAttribute(RaceConstants.ATTRIBUTE_STRENGTH_PER_LEVEL)
        * level;
    int jobFixed = j.getAttribute(JobConstants.ATTRIBUTE_STRENGTH_MODIFIER);
    double raceMult = RaceConstants.getLookupTableEntry(
        r.getAttribute(RaceConstants.ATTRIBUTE_RANDOM_STRENGTH));
    return (int) Math.round((base + raceFixed + jobFixed) * raceMult);
  }

  protected int getInitialBlock() {
    int level = this.getLevel();
    Race r = this.getRace();
    Job j = this.getJob();
    int base = StatConstants.GAIN_BLOCK * level;
    int raceFixed = r.getAttribute(RaceConstants.ATTRIBUTE_BLOCK_PER_LEVEL)
        * level;
    int jobFixed = j.getAttribute(JobConstants.ATTRIBUTE_BLOCK_MODIFIER);
    double raceMult = RaceConstants.getLookupTableEntry(
        r.getAttribute(RaceConstants.ATTRIBUTE_RANDOM_BLOCK));
    return (int) Math.round((base + raceFixed + jobFixed) * raceMult);
  }

  protected int getInitialVitality() {
    int level = this.getLevel();
    Race r = this.getRace();
    Job j = this.getJob();
    int base = StatConstants.GAIN_VITALITY * level;
    int raceFixed = r.getAttribute(RaceConstants.ATTRIBUTE_VITALITY_PER_LEVEL)
        * level;
    int jobFixed = j.getAttribute(JobConstants.ATTRIBUTE_VITALITY_MODIFIER);
    double raceMult = RaceConstants.getLookupTableEntry(
        r.getAttribute(RaceConstants.ATTRIBUTE_RANDOM_VITALITY));
    return (int) Math.round((base + raceFixed + jobFixed) * raceMult);
  }

  protected int getInitialIntelligence() {
    int level = this.getLevel();
    Race r = this.getRace();
    Job j = this.getJob();
    int base = StatConstants.GAIN_INTELLIGENCE * level;
    int raceFixed = r
        .getAttribute(RaceConstants.ATTRIBUTE_INTELLIGENCE_PER_LEVEL) * level;
    int jobFixed = j.getAttribute(JobConstants.ATTRIBUTE_INTELLIGENCE_MODIFIER);
    double raceMult = RaceConstants.getLookupTableEntry(
        r.getAttribute(RaceConstants.ATTRIBUTE_RANDOM_INTELLIGENCE));
    return (int) Math.round((base + raceFixed + jobFixed) * raceMult);
  }

  protected int getInitialAgility() {
    int level = this.getLevel();
    Race r = this.getRace();
    Job j = this.getJob();
    int base = StatConstants.GAIN_AGILITY * level;
    int raceFixed = r.getAttribute(RaceConstants.ATTRIBUTE_AGILITY_PER_LEVEL)
        * level;
    int jobFixed = j.getAttribute(JobConstants.ATTRIBUTE_AGILITY_MODIFIER);
    double raceMult = RaceConstants.getLookupTableEntry(
        r.getAttribute(RaceConstants.ATTRIBUTE_RANDOM_AGILITY));
    return (int) Math.round((base + raceFixed + jobFixed) * raceMult);
  }

  protected int getInitialLuck() {
    int level = this.getLevel();
    Race r = this.getRace();
    Job j = this.getJob();
    int base = StatConstants.GAIN_LUCK * level;
    int raceFixed = r.getAttribute(RaceConstants.ATTRIBUTE_LUCK_PER_LEVEL)
        * level;
    int jobFixed = j.getAttribute(JobConstants.ATTRIBUTE_LUCK_MODIFIER);
    double raceMult = RaceConstants.getLookupTableEntry(
        r.getAttribute(RaceConstants.ATTRIBUTE_RANDOM_LUCK));
    return (int) Math.round((base + raceFixed + jobFixed) * raceMult);
  }

  public final void setLuck(final int value) {
    this.setStat(StatConstants.STAT_LUCK, value);
  }

  public final void setLoad(final int value) {
    this.setStat(StatConstants.STAT_LOAD, value);
  }

  public final void setSpellBook(final SpellBook book) {
    this.spellsKnown = book;
  }

  public final void setSpellsPerRound(final int value) {
    this.setStat(StatConstants.STAT_SPELLS_PER_ROUND, value);
  }

  private final void setStat(final int stat, final int value) {
    int dynValue;
    if (this.stats[stat].getDynamism() != 0) {
      final RandomRange r = new RandomRange(-this.stats[stat].getDynamism(),
          this.stats[stat].getDynamism());
      dynValue = value + r.generate();
    } else {
      dynValue = value;
    }
    this.stats[stat].setValue(dynValue);
    this.fixStatValue(stat);
  }

  private void setStatFixed(final int stat, final int value) {
    if (Creature.statBoundsCheck(stat)) {
      this.stats[stat].setValue(value);
    }
  }

  public final void setStrength(final int value) {
    this.setStat(StatConstants.STAT_STRENGTH, value);
  }

  public final void setToNextLevel(final Page nextLevelEquation) {
    this.toNextLevel = nextLevelEquation;
  }

  public final void setVitality(final int value) {
    this.setStat(StatConstants.STAT_VITALITY, value);
  }

  public final void stripAllEffects() {
    for (final Effect e : this.effectList) {
      e.deactivateEffect();
    }
  }

  public final void useEffects() {
    for (final Effect e : this.effectList) {
      e.useEffect(this);
    }
  }

  public String getFightingWhatString() {
    final String enemyName = this.getName();
    final boolean vowel = this.isFirstLetterVowel(enemyName);
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
    if (firstLetter.equalsIgnoreCase("A") || firstLetter.equalsIgnoreCase("E")
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
    result = prime * result + this.effectList.hashCode();
    result = prime * result + (int) (this.experience ^ this.experience >>> 32);
    result = prime * result + (this.items == null ? 0 : this.items.hashCode());
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
    if (!this.effectList.equals(other.effectList)) {
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

  public String getEffectedHPString() {
    return this.getCurrentHP() + "/" + this.getEffectedMaximumHP();
  }

  public String getEffectedMPString() {
    return this.getCurrentMP() + "/" + this.getEffectedMaximumMP();
  }

  public int getWeaponPower() {
    return (int) (this.getItems().getTotalPower()
        * StatConstants.FACTOR_POWER_ATTACK);
  }
}
