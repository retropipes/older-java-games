/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.creatures.monsters;

import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.manager.asset.BossImageManager;
import studio.ignitionigloogames.chrystalz.names.BossNames;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.common.images.BufferedImageIcon;
import studio.ignitionigloogames.common.random.RandomLongRange;
import studio.ignitionigloogames.common.random.RandomRange;

class BossMonster extends AbstractMonster {
    // Constants
    private static final int STAT_MULT_VERY_EASY = 4;
    private static final int STAT_MULT_EASY = 5;
    private static final int STAT_MULT_NORMAL = 6;
    private static final int STAT_MULT_HARD = 7;
    private static final int STAT_MULT_VERY_HARD = 8;
    private static final double EXP_MULT_VERY_EASY = 1.6;
    private static final double EXP_MULT_EASY = 1.4;
    private static final double EXP_MULT_NORMAL = 1.2;
    private static final double EXP_MULT_HARD = 1.0;
    private static final double EXP_MULT_VERY_HARD = 0.8;

    // Constructors
    BossMonster() {
        super();
        this.image = this.getInitialImage();
    }

    @Override
    protected BufferedImageIcon getInitialImage() {
        final int zoneID = PartyManager.getParty().getZone();
        return BossImageManager.getBossImage(zoneID);
    }

    @Override
    public void loadCreature() {
        final int zoneID = PartyManager.getParty().getZone();
        final String bossName = BossNames.getName(zoneID);
        this.overrideDefaults(zoneID, bossName);
        final int newLevel = zoneID + 1;
        this.setLevel(newLevel);
        this.setVitality(this.getInitialVitality());
        this.setCurrentHP(this.getMaximumHP());
        this.setIntelligence(this.getInitialIntelligence());
        this.setCurrentMP(this.getMaximumMP());
        this.setStrength(this.getInitialStrength());
        this.setBlock(this.getInitialBlock());
        this.setAgility(this.getInitialAgility());
        this.setLuck(this.getInitialLuck());
        this.setGold(BossMonster.getInitialGold());
        this.setExperience((long) (this.getInitialExperience()
                * this.adjustForLevelDifference()));
        this.setAttacksPerRound(1);
        this.setSpellsPerRound(1);
        this.image = this.getInitialImage();
    }

    private int getInitialStrength() {
        final RandomRange r = new RandomRange(1,
                Math.max(
                        this.getLevel()
                                * BossMonster.getStatMultiplierForDifficulty(),
                        1));
        return r.generate();
    }

    private int getInitialBlock() {
        final RandomRange r = new RandomRange(0,
                this.getLevel() * BossMonster.getStatMultiplierForDifficulty());
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
                        * BossMonster.getExpMultiplierForDifficulty());
    }

    private static int getInitialGold() {
        return 0;
    }

    private int getInitialAgility() {
        final RandomRange r = new RandomRange(1,
                Math.max(
                        this.getLevel()
                                * BossMonster.getStatMultiplierForDifficulty(),
                        1));
        return r.generate();
    }

    private int getInitialVitality() {
        final RandomRange r = new RandomRange(1,
                Math.max(
                        this.getLevel()
                                * BossMonster.getStatMultiplierForDifficulty(),
                        1));
        return r.generate();
    }

    private int getInitialIntelligence() {
        final RandomRange r = new RandomRange(0,
                this.getLevel() * BossMonster.getStatMultiplierForDifficulty());
        return r.generate();
    }

    private int getInitialLuck() {
        final RandomRange r = new RandomRange(0,
                this.getLevel() * BossMonster.getStatMultiplierForDifficulty());
        return r.generate();
    }

    private static int getStatMultiplierForDifficulty() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return BossMonster.STAT_MULT_VERY_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return BossMonster.STAT_MULT_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return BossMonster.STAT_MULT_NORMAL;
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return BossMonster.STAT_MULT_HARD;
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return BossMonster.STAT_MULT_VERY_HARD;
        } else {
            return BossMonster.STAT_MULT_NORMAL;
        }
    }

    private static double getExpMultiplierForDifficulty() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return BossMonster.EXP_MULT_VERY_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return BossMonster.EXP_MULT_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return BossMonster.EXP_MULT_NORMAL;
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return BossMonster.EXP_MULT_HARD;
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return BossMonster.EXP_MULT_VERY_HARD;
        } else {
            return BossMonster.EXP_MULT_NORMAL;
        }
    }
}
