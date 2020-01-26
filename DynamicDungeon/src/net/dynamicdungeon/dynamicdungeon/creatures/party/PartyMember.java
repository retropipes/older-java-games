/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.creatures.party;

import java.io.IOException;

import net.dynamicdungeon.dbio.DatabaseReader;
import net.dynamicdungeon.dbio.DatabaseWriter;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.VersionException;
import net.dynamicdungeon.dynamicdungeon.creatures.AbstractCreature;
import net.dynamicdungeon.dynamicdungeon.creatures.StatConstants;
import net.dynamicdungeon.dynamicdungeon.creatures.castes.Caste;
import net.dynamicdungeon.dynamicdungeon.creatures.castes.CasteManager;
import net.dynamicdungeon.dynamicdungeon.creatures.faiths.Faith;
import net.dynamicdungeon.dynamicdungeon.creatures.faiths.FaithManager;
import net.dynamicdungeon.dynamicdungeon.creatures.races.Race;
import net.dynamicdungeon.dynamicdungeon.creatures.races.RaceConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.FormatConstants;
import net.dynamicdungeon.dynamicdungeon.dungeon.GenerateTask;
import net.dynamicdungeon.dynamicdungeon.dungeon.objects.Player;
import net.dynamicdungeon.dynamicdungeon.items.ItemInventory;
import net.dynamicdungeon.dynamicdungeon.prefs.PreferencesManager;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.BattleImageManager;
import net.dynamicdungeon.dynamicdungeon.spells.SpellBook;
import net.dynamicdungeon.images.BufferedImageIcon;
import net.dynamicdungeon.page.Page;

public class PartyMember extends AbstractCreature {
    // Fields
    private Race race;
    private Caste caste;
    private final String name;
    private int permanentAttack;
    private int permanentDefense;
    private int permanentHP;
    private int permanentMP;
    private int kills;
    private static final int START_GOLD = 0;
    private static final double BASE_COEFF = 10.0;
    private static final Player ME = new Player();

    // Constructors
    PartyMember(final Race r, final Caste c, final String n) {
        super(true, 0);
        this.name = n;
        this.race = r;
        this.caste = c;
        this.permanentAttack = 0;
        this.permanentDefense = 0;
        this.permanentHP = 0;
        this.permanentMP = 0;
        this.kills = 0;
        this.setLevel(1);
        this.setStrength(StatConstants.GAIN_STRENGTH + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_STRENGTH_PER_LEVEL));
        this.setBlock(StatConstants.GAIN_BLOCK + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_BLOCK_PER_LEVEL));
        this.setVitality(StatConstants.GAIN_VITALITY + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_VITALITY_PER_LEVEL));
        this.setIntelligence(
                StatConstants.GAIN_INTELLIGENCE + this.race.getAttribute(
                        RaceConstants.RACE_ATTRIBUTE_INTELLIGENCE_PER_LEVEL));
        this.setAgility(StatConstants.GAIN_AGILITY + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_AGILITY_PER_LEVEL));
        this.setLuck(StatConstants.GAIN_LUCK + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_LUCK_PER_LEVEL));
        this.setAttacksPerRound(1);
        this.setSpellsPerRound(1);
        this.healAndRegenerateFully();
        this.setGold(PartyMember.START_GOLD);
        this.setExperience(0L);
        final Page nextLevelEquation = new Page(3, 1, 0, true);
        final double value = PartyMember.BASE_COEFF;
        nextLevelEquation.setCoefficient(1, value);
        nextLevelEquation.setCoefficient(2, value);
        nextLevelEquation.setCoefficient(3, value);
        this.setToNextLevel(nextLevelEquation);
        this.setSpellBook(
                CasteManager.getSpellBookByID(this.caste.getCasteID()));
    }

    // Methods
    public String getXPString() {
        return this.getExperience() + "/" + this.getToNextLevelValue();
    }

    // Transformers
    @Override
    protected void levelUpHook() {
        this.offsetStrength(StatConstants.GAIN_STRENGTH + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_STRENGTH_PER_LEVEL));
        this.offsetBlock(StatConstants.GAIN_BLOCK + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_BLOCK_PER_LEVEL));
        this.offsetVitality(StatConstants.GAIN_VITALITY + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_VITALITY_PER_LEVEL));
        this.offsetIntelligence(
                StatConstants.GAIN_INTELLIGENCE + this.race.getAttribute(
                        RaceConstants.RACE_ATTRIBUTE_INTELLIGENCE_PER_LEVEL));
        this.offsetAgility(StatConstants.GAIN_AGILITY + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_AGILITY_PER_LEVEL));
        this.offsetLuck(StatConstants.GAIN_LUCK + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_LUCK_PER_LEVEL));
        this.healAndRegenerateFully();
    }

    private void loadPartyMember(final int newLevel, final int chp,
            final int cmp, final int newGold, final int newLoad,
            final long newExperience, final int bookID, final boolean[] known) {
        this.setLevel(newLevel);
        this.setCurrentHP(chp);
        this.setCurrentMP(cmp);
        this.setGold(newGold);
        this.setLoad(newLoad);
        this.setExperience(newExperience);
        final SpellBook book = CasteManager.getSpellBookByID(bookID);
        for (int x = 0; x < known.length; x++) {
            if (known[x]) {
                book.learnSpellByID(x);
            }
        }
        this.setSpellBook(book);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Race getRace() {
        return this.race;
    }

    public Caste getCaste() {
        return this.caste;
    }

    @Override
    public Faith getFaith() {
        return FaithManager.getFaith(0);
    }

    @Override
    public int getSpeed() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        final int base = this.getBaseSpeed();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_FASTEST);
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_FAST);
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_NORMAL);
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_SLOW);
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_SLOWEST);
        } else {
            return (int) (base * AbstractCreature.SPEED_ADJUST_NORMAL);
        }
    }

    public void initPostKill(final Race r, final Caste c) {
        this.race = r;
        this.caste = c;
        this.setLevel(1);
        this.setStrength(StatConstants.GAIN_STRENGTH + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_STRENGTH_PER_LEVEL));
        this.setBlock(StatConstants.GAIN_BLOCK + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_BLOCK_PER_LEVEL));
        this.setVitality(StatConstants.GAIN_VITALITY + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_VITALITY_PER_LEVEL));
        this.setIntelligence(
                StatConstants.GAIN_INTELLIGENCE + this.race.getAttribute(
                        RaceConstants.RACE_ATTRIBUTE_INTELLIGENCE_PER_LEVEL));
        this.setAgility(StatConstants.GAIN_AGILITY + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_AGILITY_PER_LEVEL));
        this.setLuck(StatConstants.GAIN_LUCK + this.race
                .getAttribute(RaceConstants.RACE_ATTRIBUTE_LUCK_PER_LEVEL));
        this.setAttacksPerRound(1);
        this.setSpellsPerRound(1);
        this.healAndRegenerateFully();
        this.setGold(PartyMember.START_GOLD);
        this.setExperience(0L);
        this.getItems().resetInventory();
        DynamicDungeon.getApplication().getGameManager().deactivateAllEffects();
        final Page nextLevelEquation = new Page(3, 1, 0, true);
        final double value = PartyMember.BASE_COEFF;
        nextLevelEquation.setCoefficient(1, value);
        nextLevelEquation.setCoefficient(2, value);
        nextLevelEquation.setCoefficient(3, value);
        this.setToNextLevel(nextLevelEquation);
        this.setSpellBook(
                CasteManager.getSpellBookByID(this.caste.getCasteID()));
        PartyManager.getParty().resetDungeonLevel();
        new GenerateTask(true).start();
    }

    @Override
    public int getAttack() {
        return super.getAttack() + this.getPermanentAttackPoints();
    }

    @Override
    public int getDefense() {
        return super.getDefense() + this.getPermanentDefensePoints();
    }

    @Override
    public int getMaximumHP() {
        return super.getMaximumHP() + this.getPermanentHPPoints();
    }

    @Override
    public int getMaximumMP() {
        return super.getMaximumMP() + this.getPermanentMPPoints();
    }

    public int getPermanentAttackPoints() {
        return this.permanentAttack;
    }

    public int getPermanentDefensePoints() {
        return this.permanentDefense;
    }

    public int getPermanentHPPoints() {
        return this.permanentHP;
    }

    public int getPermanentMPPoints() {
        return this.permanentMP;
    }

    public void spendPointOnAttack() {
        this.kills++;
        this.permanentAttack++;
    }

    public void spendPointOnDefense() {
        this.kills++;
        this.permanentDefense++;
    }

    public void spendPointOnHP() {
        this.kills++;
        this.permanentHP++;
    }

    public void spendPointOnMP() {
        this.kills++;
        this.permanentMP++;
    }

    public void onDeath(final int penalty) {
        this.offsetExperiencePercentage(penalty);
        this.healAndRegenerateFully();
        this.setGold(0);
    }

    public static PartyMember read(final DatabaseReader worldFile)
            throws IOException {
        final int version = worldFile.readByte();
        if (version < FormatConstants.CHARACTER_FORMAT_1) {
            throw new VersionException(
                    "Invalid character version found: " + version);
        }
        final int k = worldFile.readInt();
        final int pAtk = worldFile.readInt();
        final int pDef = worldFile.readInt();
        final int pHP = worldFile.readInt();
        final int pMP = worldFile.readInt();
        final int strength = worldFile.readInt();
        final int block = worldFile.readInt();
        final int agility = worldFile.readInt();
        final int vitality = worldFile.readInt();
        final int intelligence = worldFile.readInt();
        final int luck = worldFile.readInt();
        final int lvl = worldFile.readInt();
        final int cHP = worldFile.readInt();
        final int cMP = worldFile.readInt();
        final int gld = worldFile.readInt();
        final int apr = worldFile.readInt();
        final int spr = worldFile.readInt();
        final int load = worldFile.readInt();
        final long exp = worldFile.readLong();
        final int r = worldFile.readInt();
        final int c = worldFile.readInt();
        final int max = worldFile.readInt();
        final boolean[] known = new boolean[max];
        for (int x = 0; x < max; x++) {
            known[x] = worldFile.readBoolean();
        }
        final String n = worldFile.readString();
        final PartyMember pm = PartyManager.getNewPCInstance(r, c, n);
        pm.setStrength(strength);
        pm.setBlock(block);
        pm.setAgility(agility);
        pm.setVitality(vitality);
        pm.setIntelligence(intelligence);
        pm.setLuck(luck);
        pm.setAttacksPerRound(apr);
        pm.setSpellsPerRound(spr);
        pm.setItems(ItemInventory.readItemInventory(worldFile));
        pm.kills = k;
        pm.permanentAttack = pAtk;
        pm.permanentDefense = pDef;
        pm.permanentHP = pHP;
        pm.permanentMP = pMP;
        pm.loadPartyMember(lvl, cHP, cMP, gld, load, exp, c, known);
        return pm;
    }

    public void write(final DatabaseWriter worldFile) throws IOException {
        worldFile.writeByte(FormatConstants.CHARACTER_FORMAT_LATEST);
        worldFile.writeInt(this.kills);
        worldFile.writeInt(this.getPermanentAttackPoints());
        worldFile.writeInt(this.getPermanentDefensePoints());
        worldFile.writeInt(this.getPermanentHPPoints());
        worldFile.writeInt(this.getPermanentMPPoints());
        worldFile.writeInt(this.getStrength());
        worldFile.writeInt(this.getBlock());
        worldFile.writeInt(this.getAgility());
        worldFile.writeInt(this.getVitality());
        worldFile.writeInt(this.getIntelligence());
        worldFile.writeInt(this.getLuck());
        worldFile.writeInt(this.getLevel());
        worldFile.writeInt(this.getCurrentHP());
        worldFile.writeInt(this.getCurrentMP());
        worldFile.writeInt(this.getGold());
        worldFile.writeInt(this.getAttacksPerRound());
        worldFile.writeInt(this.getSpellsPerRound());
        worldFile.writeInt(this.getLoad());
        worldFile.writeLong(this.getExperience());
        worldFile.writeInt(this.getRace().getRaceID());
        worldFile.writeInt(this.getCaste().getCasteID());
        final int max = this.getSpellBook().getSpellCount();
        worldFile.writeInt(max);
        for (int x = 0; x < max; x++) {
            worldFile.writeBoolean(this.getSpellBook().isSpellKnown(x));
        }
        worldFile.writeString(this.getName());
        this.getItems().writeItemInventory(worldFile);
    }

    @Override
    protected BufferedImageIcon getInitialImage() {
        return BattleImageManager.getImage(PartyMember.ME.getName(),
                PartyMember.ME.getBaseID(),
                this.getFaith().getColor().getRGB());
    }

    @Override
    public void loadCreature() {
        // Do nothing
    }
}
