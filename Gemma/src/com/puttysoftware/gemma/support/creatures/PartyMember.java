/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.creatures;

import java.io.IOException;

import com.puttysoftware.gemma.support.creatures.faiths.Faith;
import com.puttysoftware.gemma.support.creatures.genders.Gender;
import com.puttysoftware.gemma.support.creatures.monsters.Element;
import com.puttysoftware.gemma.support.creatures.personalities.Personality;
import com.puttysoftware.gemma.support.creatures.personalities.PersonalityConstants;
import com.puttysoftware.gemma.support.creatures.races.Race;
import com.puttysoftware.gemma.support.creatures.races.RaceConstants;
import com.puttysoftware.gemma.support.items.ItemInventory;
import com.puttysoftware.gemma.support.resourcemanagers.ImageManager;
import com.puttysoftware.gemma.support.scripts.internal.InternalScript;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntryArgument;
import com.puttysoftware.gemma.support.spells.SpellBook;
import com.puttysoftware.images.BufferedImageIcon;
import com.puttysoftware.page.Page;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class PartyMember extends Creature {
    // Fields
    private final Race race;
    private final Faith faith;
    private final Personality personality;
    private final Gender gender;
    private final Element element;
    private final String name;
    private static final int START_GOLD = 0;
    private static final double BASE_COEFF = 10.0;

    // Constructors
    PartyMember(final Race r, final Faith f, final Personality p,
            final Gender g, final String n) {
        super(true);
        this.name = n;
        this.race = r;
        this.faith = f;
        this.personality = p;
        this.gender = g;
        this.element = new Element(f);
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
        this.healAndRegenerateFully();
        this.setGold(PartyMember.START_GOLD);
        this.setExperience(0L);
        final Page nextLevelEquation = new Page(3, 1, 0, true);
        final double value = PartyMember.BASE_COEFF
                * this.personality.getAttribute(
                        PersonalityConstants.PERSONALITY_ATTRIBUTE_LEVEL_UP_SPEED);
        nextLevelEquation.setCoefficient(1, value);
        nextLevelEquation.setCoefficient(2, value);
        nextLevelEquation.setCoefficient(3, value);
        this.setToNextLevel(nextLevelEquation);
        this.setSpellBook(new PartyMemberSpellBook());
    }

    // Methods
    @Override
    public BufferedImageIcon getInitialImage() {
        return ImageManager.getPlayerImage(this);
    }

    public String getXPString() {
        return this.getExperience() + "/" + this.getToNextLevelValue();
    }

    // Transformers
    @Override
    protected InternalScript levelUpHook() {
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
        final InternalScript levelUpScript = new InternalScript();
        final InternalScriptEntry act0 = new InternalScriptEntry();
        act0.setActionCode(InternalScriptActionCode.UPDATE_GSA);
        act0.addActionArg(new InternalScriptEntryArgument(
                PartyManager.getParty().getDungeonLevel() - this.getLevel()));
        act0.finalizeActionArgs();
        levelUpScript.addAction(act0);
        levelUpScript.finalizeActions();
        return levelUpScript;
    }

    private void loadPartyMember(final int newLevel, final int chp,
            final int cmp, final int newGold, final int newLoad,
            final long newExperience, final boolean[] known,
            final long[] pres) {
        this.setLevel(newLevel);
        this.setCurrentHP(chp);
        this.setCurrentMP(cmp);
        this.setGold(newGold);
        this.setLoad(newLoad);
        this.setExperience(newExperience);
        final SpellBook book = new PartyMemberSpellBook();
        for (int x = 0; x < known.length; x++) {
            if (known[x]) {
                book.learnSpellByID(x);
            }
        }
        for (int x = 0; x < pres.length; x++) {
            this.setPrestigeValue(x, pres[x]);
        }
        this.setSpellBook(book);
    }

    @Override
    public int getActionsPerRound() {
        return (int) (super.getActionsPerRound()
                * this.getPersonality().getAttribute(
                        PersonalityConstants.PERSONALITY_ATTRIBUTE_MOVE_MOD));
    }

    @Override
    public int getCapacity() {
        return Math.max(StatConstants.MIN_CAPACITY,
                (int) (super.getCapacity() * this.getPersonality().getAttribute(
                        PersonalityConstants.PERSONALITY_ATTRIBUTE_CAPACITY_MOD)));
    }

    @Override
    public void offsetGold(final int value) {
        int fixedValue = value;
        if (value > 0) {
            fixedValue = (int) (fixedValue * this.getPersonality().getAttribute(
                    PersonalityConstants.PERSONALITY_ATTRIBUTE_WEALTH_MOD));
        }
        super.offsetGold(fixedValue);
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Race getRace() {
        return this.race;
    }

    @Override
    public Faith getFaith() {
        return this.faith;
    }

    private Personality getPersonality() {
        return this.personality;
    }

    private Gender getGender() {
        return this.gender;
    }

    public Element getElement() {
        return this.element;
    }

    public static PartyMember read(final XDataReader worldFile)
            throws IOException {
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
        final int ipr = worldFile.readInt();
        final int tpr = worldFile.readInt();
        final int load = worldFile.readInt();
        final long exp = worldFile.readLong();
        final int r = worldFile.readInt();
        final int f = worldFile.readInt();
        final int p = worldFile.readInt();
        final int g = worldFile.readInt();
        final int max = worldFile.readInt();
        final boolean[] known = new boolean[max];
        for (int x = 0; x < max; x++) {
            known[x] = worldFile.readBoolean();
        }
        final long[] prestige = new long[PrestigeConstants.MAX_PRESTIGE];
        for (int x = 0; x < PrestigeConstants.MAX_PRESTIGE; x++) {
            prestige[x] = worldFile.readLong();
        }
        final String n = worldFile.readString();
        final PartyMember pm = PartyManager.getNewPCInstance(r, f, p, g, n);
        pm.setStrength(strength);
        pm.setBlock(block);
        pm.setAgility(agility);
        pm.setVitality(vitality);
        pm.setIntelligence(intelligence);
        pm.setLuck(luck);
        pm.setAttacksPerRound(apr);
        pm.setSpellsPerRound(spr);
        pm.setItemsPerRound(ipr);
        pm.setStealsPerRound(tpr);
        pm.setItems(ItemInventory.readItemInventory(worldFile));
        pm.loadPartyMember(lvl, cHP, cMP, gld, load, exp, known, prestige);
        return pm;
    }

    public void write(final XDataWriter worldFile) throws IOException {
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
        worldFile.writeInt(this.getItemsPerRound());
        worldFile.writeInt(this.getStealsPerRound());
        worldFile.writeInt(this.getLoad());
        worldFile.writeLong(this.getExperience());
        worldFile.writeInt(this.getRace().getRaceID());
        worldFile.writeInt(this.getFaith().getFaithID());
        worldFile.writeInt(this.getPersonality().getPersonalityID());
        worldFile.writeInt(this.getGender().getGenderID());
        final int max = this.getSpellBook().getSpellCount();
        worldFile.writeInt(max);
        for (int x = 0; x < max; x++) {
            worldFile.writeBoolean(this.getSpellBook().isSpellKnown(x));
        }
        for (int x = 0; x < PrestigeConstants.MAX_PRESTIGE; x++) {
            worldFile.writeLong(this.getPrestigeValue(x));
        }
        worldFile.writeString(this.getName());
        this.getItems().writeItemInventory(worldFile);
    }
}
