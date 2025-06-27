package net.worldwizard.worldz.creatures;

import java.io.IOException;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.worldz.creatures.castes.Caste;
import net.worldwizard.worldz.creatures.castes.CasteConstants;
import net.worldwizard.worldz.creatures.castes.CasteManager;
import net.worldwizard.worldz.creatures.faiths.Faith;
import net.worldwizard.worldz.creatures.genders.Gender;
import net.worldwizard.worldz.creatures.genders.GenderConstants;
import net.worldwizard.worldz.creatures.personalities.Personality;
import net.worldwizard.worldz.creatures.personalities.PersonalityConstants;
import net.worldwizard.worldz.creatures.races.Race;
import net.worldwizard.worldz.creatures.races.RaceConstants;
import net.worldwizard.worldz.items.ItemInventory;
import net.worldwizard.worldz.resourcemanagers.GraphicsManager;
import net.worldwizard.worldz.spells.SpellBook;

public class PartyMember extends Creature {
        // Fields
        private final Race race;
        private final Caste caste;
        private final Gender gender;
        private final Faith faith;
        private final Personality personality;
        private long toNextLevel;
        private final RandomRange rSTR, rBLK, rAGI, rVIT, rINT, rLUC;
        private final int mSTR, mBLK, mAGI, mVIT, mINT, mLUC;
        private final String name;
        private boolean isPlayer;
        private static final int START_GOLD = 0;

        // Constructors
        public PartyMember(final Race r, final Caste c, final Faith f,
                        final Gender g, final Personality p, final String n) {
                super();
                this.name = n;
                this.race = r;
                this.caste = c;
                this.gender = g;
                this.faith = f;
                this.personality = p;
                this.setLevel(1);
                final int STR = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_STRENGTH);
                this.rSTR = new RandomRange(-STR, STR);
                final int BLK = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_BLOCK);
                this.rBLK = new RandomRange(-BLK, BLK);
                final int AGI = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_AGILITY);
                this.rAGI = new RandomRange(-AGI, AGI);
                final int VIT = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_VITALITY);
                this.rVIT = new RandomRange(-VIT, VIT);
                final int INT = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_INTELLIGENCE);
                this.rINT = new RandomRange(-INT, INT);
                final int LUC = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_LUCK);
                this.rLUC = new RandomRange(-LUC, LUC);
                this.mSTR = this.gender.getAttribute(
                                GenderConstants.GENDER_ATTRIBUTE_STRENGTH_MODIFIER);
                this.mBLK = this.gender
                                .getAttribute(GenderConstants.GENDER_ATTRIBUTE_BLOCK_MODIFIER);
                this.mAGI = this.gender.getAttribute(
                                GenderConstants.GENDER_ATTRIBUTE_AGILITY_MODIFIER);
                this.mVIT = this.gender.getAttribute(
                                GenderConstants.GENDER_ATTRIBUTE_VITALITY_MODIFIER);
                this.mINT = this.gender.getAttribute(
                                GenderConstants.GENDER_ATTRIBUTE_INTELLIGENCE_MODIFIER);
                this.mLUC = this.gender
                                .getAttribute(GenderConstants.GENDER_ATTRIBUTE_LUCK_MODIFIER);
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
                this.toNextLevel = this.getExpToNextLevel(this.getLevel() + 1);
                this.setSpellBook(
                                CasteManager.getSpellBookByID(this.caste.getCasteID()));
                this.isPlayer = true;
        }

        public PartyMember(final Race r, final Caste c, final Faith f,
                        final Gender g, final Personality p, final String n,
                        final int level) {
                super();
                this.name = n;
                this.race = r;
                this.caste = c;
                this.gender = g;
                this.faith = f;
                this.personality = p;
                this.setLevel(1);
                final int STR = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_STRENGTH);
                this.rSTR = new RandomRange(-STR, STR);
                final int BLK = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_BLOCK);
                this.rBLK = new RandomRange(-BLK, BLK);
                final int AGI = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_AGILITY);
                this.rAGI = new RandomRange(-AGI, AGI);
                final int VIT = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_VITALITY);
                this.rVIT = new RandomRange(-VIT, VIT);
                final int INT = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_INTELLIGENCE);
                this.rINT = new RandomRange(-INT, INT);
                final int LUC = this.personality.getAttribute(
                                PersonalityConstants.PERSONALITY_ATTRIBUTE_RANDOM_LUCK);
                this.rLUC = new RandomRange(-LUC, LUC);
                this.mSTR = this.gender.getAttribute(
                                GenderConstants.GENDER_ATTRIBUTE_STRENGTH_MODIFIER);
                this.mBLK = this.gender
                                .getAttribute(GenderConstants.GENDER_ATTRIBUTE_BLOCK_MODIFIER);
                this.mAGI = this.gender.getAttribute(
                                GenderConstants.GENDER_ATTRIBUTE_AGILITY_MODIFIER);
                this.mVIT = this.gender.getAttribute(
                                GenderConstants.GENDER_ATTRIBUTE_VITALITY_MODIFIER);
                this.mINT = this.gender.getAttribute(
                                GenderConstants.GENDER_ATTRIBUTE_INTELLIGENCE_MODIFIER);
                this.mLUC = this.gender
                                .getAttribute(GenderConstants.GENDER_ATTRIBUTE_LUCK_MODIFIER);
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
                this.toNextLevel = this.getExpToNextLevel(this.getLevel() + 1);
                this.setSpellBook(
                                CasteManager.getSpellBookByID(this.caste.getCasteID()));
                this.isPlayer = false;
                for (int x = 1; x < level; x++) {
                        this.levelUp();
                }
        }

        // Methods
        @Override
        public boolean checkLevelUp() {
                return this.getExperience() >= this.toNextLevel;
        }

        @Override
        public int getAttack() {
                return super.getAttack() + this.getWeaponPower() + this.caste
                                .getAttribute(CasteConstants.CASTE_ATTRIBUTE_BONUS_ATTACK);
        }

        @Override
        public int getDefense() {
                return super.getDefense() + this.getArmorBlock() + this.caste
                                .getAttribute(CasteConstants.CASTE_ATTRIBUTE_BONUS_DEFENSE);
        }

        public long getExpToNextLevel(final int x) {
                if (x == 1) {
                        return 0L;
                } else {
                        return 10 * x * x * x + 10 * x * x + 10 * x + 10;
                }
        }

        @Override
        public BufferedImageIcon getInitialImage() {
                return GraphicsManager.getImage("Player");
        }

        public long getToNextLevel() {
                return this.toNextLevel;
        }

        public String getXPString() {
                return this.getExperience() + "/" + this.toNextLevel;
        }

        // Transformers
        @Override
        public void levelUp() {
                this.offsetLevel(1);
                this.offsetStrength(StatConstants.GAIN_STRENGTH
                                + this.race.getAttribute(
                                                RaceConstants.RACE_ATTRIBUTE_STRENGTH_PER_LEVEL)
                                + Math.min(0, this.mSTR + this.rSTR.generate()));
                this.offsetBlock(StatConstants.GAIN_BLOCK
                                + this.race.getAttribute(
                                                RaceConstants.RACE_ATTRIBUTE_BLOCK_PER_LEVEL)
                                + Math.min(0, this.mBLK + this.rBLK.generate()));
                this.offsetVitality(StatConstants.GAIN_VITALITY
                                + this.race.getAttribute(
                                                RaceConstants.RACE_ATTRIBUTE_VITALITY_PER_LEVEL)
                                + Math.min(0, this.mVIT + this.rVIT.generate()));
                this.offsetIntelligence(StatConstants.GAIN_INTELLIGENCE
                                + this.race.getAttribute(
                                                RaceConstants.RACE_ATTRIBUTE_INTELLIGENCE_PER_LEVEL)
                                + Math.min(0, this.mINT + this.rINT.generate()));
                this.offsetAgility(StatConstants.GAIN_AGILITY
                                + this.race.getAttribute(
                                                RaceConstants.RACE_ATTRIBUTE_AGILITY_PER_LEVEL)
                                + Math.min(0, this.mAGI + this.rAGI.generate()));
                this.offsetLuck(StatConstants.GAIN_LUCK
                                + this.race.getAttribute(
                                                RaceConstants.RACE_ATTRIBUTE_LUCK_PER_LEVEL)
                                + Math.min(0, this.mLUC + this.rLUC.generate()));
                this.healAndRegenerateFully();
                this.toNextLevel = this.getExpToNextLevel(this.getLevel() + 1);
        }

        public void loadPartyMember(final int newLevel, final int chp,
                        final int cmp, final int newGold, final long newExperience,
                        final int bookID, final boolean[] known, final boolean pcflag) {
                this.setLevel(newLevel);
                this.setCurrentHP(chp);
                this.setCurrentMP(cmp);
                this.setGold(newGold);
                this.setExperience(newExperience);
                this.toNextLevel = this.getExpToNextLevel(this.getLevel() + 1);
                final SpellBook book = CasteManager.getSpellBookByID(bookID);
                for (int x = 0; x < known.length; x++) {
                        if (known[x]) {
                                book.learnSpellByID(x);
                        }
                }
                this.setSpellBook(book);
                this.isPlayer = pcflag;
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
                return this.faith;
        }

        public Gender getGender() {
                return this.gender;
        }

        public Personality getPersonality() {
                return this.personality;
        }

        public boolean isPlayerCharacter() {
                return this.isPlayer;
        }

        public void setToPC() {
                this.isPlayer = true;
        }

        public void setToNPC() {
                this.isPlayer = false;
        }

        public static PartyMember read(final DataReader worldFile)
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
                final long exp = worldFile.readLong();
                final int r = worldFile.readInt();
                final int c = worldFile.readInt();
                final int f = worldFile.readInt();
                final int g = worldFile.readInt();
                final int p = worldFile.readInt();
                final int max = worldFile.readInt();
                final boolean[] known = new boolean[max];
                for (int x = 0; x < max; x++) {
                        known[x] = worldFile.readBoolean();
                }
                final String n = worldFile.readString();
                final boolean pcflag = worldFile.readBoolean();
                final PartyMember pm = PartyManager.getNewPCInstance(r, c, f, g, p, n);
                pm.setStrength(strength);
                pm.setBlock(block);
                pm.setAgility(agility);
                pm.setVitality(vitality);
                pm.setIntelligence(intelligence);
                pm.setLuck(luck);
                pm.setAttacksPerRound(apr);
                pm.setSpellsPerRound(spr);
                pm.setItems(ItemInventory.readItemInventory(worldFile));
                pm.loadPartyMember(lvl, cHP, cMP, gld, exp, c, known, pcflag);
                return pm;
        }

        public void write(final DataWriter worldFile) throws IOException {
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
                worldFile.writeLong(this.getExperience());
                worldFile.writeInt(this.getRace().getRaceID());
                worldFile.writeInt(this.getCaste().getCasteID());
                worldFile.writeInt(this.getFaith().getFaithID());
                worldFile.writeInt(this.getGender().getGenderID());
                worldFile.writeInt(this.getPersonality().getPersonalityID());
                final int max = this.getSpellBook().getSpellCount();
                worldFile.writeInt(max);
                for (int x = 0; x < max; x++) {
                        worldFile.writeBoolean(this.getSpellBook().isSpellKnown(x));
                }
                worldFile.writeString(this.getName());
                worldFile.writeBoolean(this.isPlayer);
                this.getItems().writeItemInventory(worldFile);
        }

        public static PartyMember readCharacter(final DataReader worldFile)
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
                final long exp = worldFile.readLong();
                final int r = worldFile.readInt();
                final int c = worldFile.readInt();
                final int f = worldFile.readInt();
                final int g = worldFile.readInt();
                final int p = worldFile.readInt();
                final int max = worldFile.readInt();
                final boolean[] known = new boolean[max];
                for (int x = 0; x < max; x++) {
                        known[x] = worldFile.readBoolean();
                }
                final String n = worldFile.readString();
                final boolean pcflag = worldFile.readBoolean();
                final PartyMember pm = PartyManager.getNewPCInstance(r, c, f, g, p, n);
                pm.setStrength(strength);
                pm.setBlock(block);
                pm.setAgility(agility);
                pm.setVitality(vitality);
                pm.setIntelligence(intelligence);
                pm.setLuck(luck);
                pm.setAttacksPerRound(apr);
                pm.setSpellsPerRound(spr);
                pm.loadPartyMember(lvl, cHP, cMP, gld, exp, c, known, pcflag);
                return pm;
        }

        public void writeCharacter(final DataWriter worldFile) throws IOException {
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
                worldFile.writeLong(this.getExperience());
                worldFile.writeInt(this.getRace().getRaceID());
                worldFile.writeInt(this.getCaste().getCasteID());
                worldFile.writeInt(this.getFaith().getFaithID());
                worldFile.writeInt(this.getGender().getGenderID());
                worldFile.writeInt(this.getPersonality().getPersonalityID());
                final int max = this.getSpellBook().getSpellCount();
                worldFile.writeInt(max);
                for (int x = 0; x < max; x++) {
                        worldFile.writeBoolean(this.getSpellBook().isSpellKnown(x));
                }
                worldFile.writeString(this.getName());
                worldFile.writeBoolean(this.isPlayer);
        }
}
