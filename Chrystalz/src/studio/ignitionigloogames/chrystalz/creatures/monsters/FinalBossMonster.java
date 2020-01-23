/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.creatures.monsters;

import studio.ignitionigloogames.chrystalz.ai.MapAIRoutinePicker;
import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.manager.asset.BossImageManager;
import studio.ignitionigloogames.chrystalz.names.BossNames;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.chrystalz.spells.SpellBook;
import studio.ignitionigloogames.common.images.BufferedImageIcon;
import studio.ignitionigloogames.common.random.RandomRange;

public class FinalBossMonster extends AbstractMonster {
    // Fields
    private static final int MINIMUM_STAT_VALUE_VERY_EASY = 50;
    private static final int MINIMUM_STAT_VALUE_EASY = 60;
    private static final int MINIMUM_STAT_VALUE_NORMAL = 70;
    private static final int MINIMUM_STAT_VALUE_HARD = 80;
    private static final int MINIMUM_STAT_VALUE_VERY_HARD = 90;
    private static final int STAT_MULT_VERY_EASY = 5;
    private static final int STAT_MULT_EASY = 6;
    private static final int STAT_MULT_NORMAL = 7;
    private static final int STAT_MULT_HARD = 8;
    private static final int STAT_MULT_VERY_HARD = 9;

    // Constructors
    FinalBossMonster() {
        super();
        this.setMapAI(MapAIRoutinePicker.getNextRoutine());
        final SpellBook spells = new SystemMonsterSpellBook();
        spells.learnAllSpells();
        this.setSpellBook(spells);
        this.loadCreature();
    }

    // Methods
    @Override
    public boolean checkLevelUp() {
        return false;
    }

    @Override
    protected void levelUpHook() {
        // Do nothing
    }

    @Override
    protected BufferedImageIcon getInitialImage() {
        return BossImageManager.getFinalBossImage();
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

    // Helper Methods
    @Override
    public void loadCreature() {
        final int zoneID = PartyManager.getParty().getZone();
        final String bossName = BossNames.getName(zoneID);
        this.overrideDefaults(zoneID, bossName);
        final int newLevel = zoneID + 6;
        this.setLevel(newLevel);
        this.setVitality(this.getInitialVitality());
        this.setCurrentHP(this.getMaximumHP());
        this.setIntelligence(this.getInitialIntelligence());
        this.setCurrentMP(this.getMaximumMP());
        this.setStrength(this.getInitialStrength());
        this.setBlock(this.getInitialBlock());
        this.setAgility(this.getInitialAgility());
        this.setLuck(this.getInitialLuck());
        this.setGold(0);
        this.setExperience(0);
        this.setAttacksPerRound(1);
        this.setSpellsPerRound(1);
        this.image = this.getInitialImage();
    }

    private int getInitialStrength() {
        final int min = FinalBossMonster.getMinimumStatForDifficulty();
        final RandomRange r = new RandomRange(min,
                Math.max(this.getLevel()
                        * FinalBossMonster.getStatMultiplierForDifficulty(),
                        min));
        return r.generate();
    }

    private int getInitialBlock() {
        final int min = FinalBossMonster.getMinimumStatForDifficulty();
        final RandomRange r = new RandomRange(min,
                Math.max(this.getLevel()
                        * FinalBossMonster.getStatMultiplierForDifficulty(),
                        min));
        return r.generate();
    }

    private int getInitialAgility() {
        final int min = FinalBossMonster.getMinimumStatForDifficulty();
        final RandomRange r = new RandomRange(min,
                Math.max(this.getLevel()
                        * FinalBossMonster.getStatMultiplierForDifficulty(),
                        min));
        return r.generate();
    }

    private int getInitialVitality() {
        final int min = FinalBossMonster.getMinimumStatForDifficulty();
        final RandomRange r = new RandomRange(min,
                Math.max(this.getLevel()
                        * FinalBossMonster.getStatMultiplierForDifficulty(),
                        min));
        return r.generate();
    }

    private int getInitialIntelligence() {
        final int min = FinalBossMonster.getMinimumStatForDifficulty();
        final RandomRange r = new RandomRange(min,
                Math.max(this.getLevel()
                        * FinalBossMonster.getStatMultiplierForDifficulty(),
                        min));
        return r.generate();
    }

    private int getInitialLuck() {
        final int min = FinalBossMonster.getMinimumStatForDifficulty();
        final RandomRange r = new RandomRange(min,
                Math.max(this.getLevel()
                        * FinalBossMonster.getStatMultiplierForDifficulty(),
                        min));
        return r.generate();
    }

    private static int getStatMultiplierForDifficulty() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return FinalBossMonster.STAT_MULT_VERY_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return FinalBossMonster.STAT_MULT_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return FinalBossMonster.STAT_MULT_NORMAL;
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return FinalBossMonster.STAT_MULT_HARD;
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return FinalBossMonster.STAT_MULT_VERY_HARD;
        } else {
            return FinalBossMonster.STAT_MULT_NORMAL;
        }
    }

    private static int getMinimumStatForDifficulty() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return FinalBossMonster.MINIMUM_STAT_VALUE_VERY_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return FinalBossMonster.MINIMUM_STAT_VALUE_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return FinalBossMonster.MINIMUM_STAT_VALUE_NORMAL;
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return FinalBossMonster.MINIMUM_STAT_VALUE_HARD;
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return FinalBossMonster.MINIMUM_STAT_VALUE_VERY_HARD;
        } else {
            return FinalBossMonster.MINIMUM_STAT_VALUE_NORMAL;
        }
    }
}
