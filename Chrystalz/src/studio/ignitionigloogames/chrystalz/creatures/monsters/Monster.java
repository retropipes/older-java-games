/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.creatures.monsters;

import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyMember;
import studio.ignitionigloogames.chrystalz.manager.asset.MonsterImageManager;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.chrystalz.shops.Shop;
import studio.ignitionigloogames.common.images.BufferedImageIcon;
import studio.ignitionigloogames.common.random.RandomLongRange;
import studio.ignitionigloogames.common.random.RandomRange;

class Monster extends AbstractMonster {
    // Constants
    private static final int STAT_MULT_VERY_EASY = 3;
    private static final int STAT_MULT_EASY = 4;
    private static final int STAT_MULT_NORMAL = 5;
    private static final int STAT_MULT_HARD = 6;
    private static final int STAT_MULT_VERY_HARD = 7;
    private static final double GOLD_MULT_VERY_EASY = 2.0;
    private static final double GOLD_MULT_EASY = 1.5;
    private static final double GOLD_MULT_NORMAL = 1.0;
    private static final double GOLD_MULT_HARD = 0.75;
    private static final double GOLD_MULT_VERY_HARD = 0.5;
    private static final double EXP_MULT_VERY_EASY = 1.2;
    private static final double EXP_MULT_EASY = 1.1;
    private static final double EXP_MULT_NORMAL = 1.0;
    private static final double EXP_MULT_HARD = 0.9;
    private static final double EXP_MULT_VERY_HARD = 0.8;

    // Constructors
    Monster() {
        super();
        this.image = this.getInitialImage();
    }

    @Override
    protected BufferedImageIcon getInitialImage() {
        final int zoneID = PartyManager.getParty().getZone();
        final int monID = this.getMonsterID();
        return MonsterImageManager.getImage(zoneID, monID);
    }

    @Override
    public void loadCreature() {
        this.configureDefaults();
        final int newLevel = PartyManager.getParty().getZone() + 1;
        this.setLevel(newLevel);
        this.setVitality(this.getInitialVitality());
        this.setCurrentHP(this.getMaximumHP());
        this.setIntelligence(this.getInitialIntelligence());
        this.setCurrentMP(this.getMaximumMP());
        this.setStrength(this.getInitialStrength());
        this.setBlock(this.getInitialBlock());
        this.setAgility(this.getInitialAgility());
        this.setLuck(this.getInitialLuck());
        this.setGold(this.getInitialGold());
        this.setExperience((long) (this.getInitialExperience()
                * this.adjustForLevelDifference()));
        this.setAttacksPerRound(1);
        this.setSpellsPerRound(1);
        this.image = this.getInitialImage();
    }

    private int getInitialStrength() {
        final RandomRange r = new RandomRange(1, Math.max(
                this.getLevel() * Monster.getStatMultiplierForDifficulty(), 1));
        return r.generate();
    }

    private int getInitialBlock() {
        final RandomRange r = new RandomRange(0,
                this.getLevel() * Monster.getStatMultiplierForDifficulty());
        return r.generate();
    }

    private long getInitialExperience() {
        int minvar, maxvar;
        minvar = (int) (this.getLevel()
                * AbstractMonster.MINIMUM_EXPERIENCE_RANDOM_VARIANCE);
        maxvar = (int) (this.getLevel()
                * AbstractMonster.MAXIMUM_EXPERIENCE_RANDOM_VARIANCE);
        final RandomLongRange r = new RandomLongRange(minvar, maxvar);
        final long expbase = PartyManager.getParty().getPartyMaxToNextLevel();
        final long factor = this.getBattlesToNextLevel();
        return (int) (expbase / factor
                + r.generate() * this.adjustForLevelDifference()
                        * Monster.getExpMultiplierForDifficulty());
    }

    private int getInitialGold() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final int needed = Shop.getEquipmentCost(playerCharacter.getLevel() + 1)
                * 4;
        final int factor = this.getBattlesToNextLevel();
        final int min = 0;
        final int max = needed / factor * 2;
        final RandomRange r = new RandomRange(min, max);
        return (int) (r.generate() * this.adjustForLevelDifference()
                * Monster.getGoldMultiplierForDifficulty());
    }

    private int getInitialAgility() {
        final RandomRange r = new RandomRange(1, Math.max(
                this.getLevel() * Monster.getStatMultiplierForDifficulty(), 1));
        return r.generate();
    }

    private int getInitialVitality() {
        final RandomRange r = new RandomRange(1, Math.max(
                this.getLevel() * Monster.getStatMultiplierForDifficulty(), 1));
        return r.generate();
    }

    private int getInitialIntelligence() {
        final RandomRange r = new RandomRange(0,
                this.getLevel() * Monster.getStatMultiplierForDifficulty());
        return r.generate();
    }

    private int getInitialLuck() {
        final RandomRange r = new RandomRange(0,
                this.getLevel() * Monster.getStatMultiplierForDifficulty());
        return r.generate();
    }

    private static int getStatMultiplierForDifficulty() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return Monster.STAT_MULT_VERY_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return Monster.STAT_MULT_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return Monster.STAT_MULT_NORMAL;
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return Monster.STAT_MULT_HARD;
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return Monster.STAT_MULT_VERY_HARD;
        } else {
            return Monster.STAT_MULT_NORMAL;
        }
    }

    private static double getGoldMultiplierForDifficulty() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return Monster.GOLD_MULT_VERY_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return Monster.GOLD_MULT_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return Monster.GOLD_MULT_NORMAL;
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return Monster.GOLD_MULT_HARD;
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return Monster.GOLD_MULT_VERY_HARD;
        } else {
            return Monster.GOLD_MULT_NORMAL;
        }
    }

    private static double getExpMultiplierForDifficulty() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return Monster.EXP_MULT_VERY_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return Monster.EXP_MULT_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return Monster.EXP_MULT_NORMAL;
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return Monster.EXP_MULT_HARD;
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return Monster.EXP_MULT_VERY_HARD;
        } else {
            return Monster.EXP_MULT_NORMAL;
        }
    }
}
