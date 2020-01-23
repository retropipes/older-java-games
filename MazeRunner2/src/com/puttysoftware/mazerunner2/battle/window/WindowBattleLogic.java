package com.puttysoftware.mazerunner2.battle.window;

import javax.swing.JFrame;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.ai.window.AbstractWindowAIRoutine;
import com.puttysoftware.mazerunner2.battle.AbstractBattle;
import com.puttysoftware.mazerunner2.battle.BattlePrestige;
import com.puttysoftware.mazerunner2.battle.BattleResults;
import com.puttysoftware.mazerunner2.battle.damageengines.AbstractDamageEngine;
import com.puttysoftware.mazerunner2.creatures.AbstractCreature;
import com.puttysoftware.mazerunner2.creatures.StatConstants;
import com.puttysoftware.mazerunner2.creatures.monsters.AbstractMonster;
import com.puttysoftware.mazerunner2.creatures.monsters.MonsterFactory;
import com.puttysoftware.mazerunner2.creatures.party.PartyManager;
import com.puttysoftware.mazerunner2.creatures.party.PartyMember;
import com.puttysoftware.mazerunner2.effects.Effect;
import com.puttysoftware.mazerunner2.items.combat.CombatItemChucker;
import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;
import com.puttysoftware.mazerunner2.maze.objects.BattleCharacter;
import com.puttysoftware.mazerunner2.prefs.PreferencesManager;
import com.puttysoftware.mazerunner2.resourcemanagers.MusicManager;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;
import com.puttysoftware.mazerunner2.spells.SpellCaster;
import com.puttysoftware.randomrange.RandomRange;

public class WindowBattleLogic extends AbstractBattle {
    // Fields
    private int stealAmount;
    private int damage;
    private boolean enemyDidDamage;
    private boolean playerDidDamage;
    private AbstractMonster enemy;
    private int result;
    private AbstractDamageEngine de;
    private WindowBattleGUI gui;
    private static final int BASE_RUN_CHANCE = 80;
    private static final int RUN_CHANCE_DIFF_FACTOR = 5;
    private static final int ENEMY_BASE_RUN_CHANCE = 60;
    private static final int ENEMY_RUN_CHANCE_DIFF_FACTOR = 10;

    // Constructor
    public WindowBattleLogic() {
        // Initialize Battle Parameters
        this.de = AbstractDamageEngine.getInstance();
        this.damage = 0;
        this.stealAmount = 0;
        this.enemyDidDamage = false;
        this.playerDidDamage = false;
        // Initialize GUI
        this.gui = new WindowBattleGUI();
    }

    @Override
    public JFrame getOutputFrame() {
        return this.gui.getOutputFrame();
    }

    @Override
    public final boolean doPlayerActions(int actionToPerform) {
        boolean success = true;
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        if (actionToPerform == AbstractWindowAIRoutine.ACTION_ATTACK) {
            int actions = playerCharacter.getWindowBattleActionsPerRound();
            for (int x = 0; x < actions; x++) {
                this.computePlayerDamage();
                this.displayPlayerRoundResults();
            }
        } else if (actionToPerform == AbstractWindowAIRoutine.ACTION_CAST_SPELL) {
            success = this.castSpell();
        } else if (actionToPerform == AbstractWindowAIRoutine.ACTION_FLEE) {
            RandomRange rf = new RandomRange(0, 100);
            int runChance = rf.generate();
            if (runChance <= this.computeRunChance()) {
                // Success
                this.setResult(BattleResults.FLED);
            } else {
                // Failure
                success = false;
                this.updateMessageAreaFleeFailed();
            }
        } else if (actionToPerform == AbstractWindowAIRoutine.ACTION_STEAL) {
            success = this.steal();
            if (success) {
                SoundManager.playSound(SoundConstants.SOUND_DRAIN);
                this.updateMessageAreaPostSteal();
            } else {
                SoundManager.playSound(SoundConstants.SOUND_ACTION_FAILED);
                this.updateMessageAreaStealFailed();
            }
        } else if (actionToPerform == AbstractWindowAIRoutine.ACTION_DRAIN) {
            success = this.drain();
            if (success) {
                SoundManager.playSound(SoundConstants.SOUND_DRAIN);
                this.updateMessageAreaPostDrain();
            } else {
                SoundManager.playSound(SoundConstants.SOUND_ACTION_FAILED);
                this.updateMessageAreaDrainFailed();
            }
        } else if (actionToPerform == AbstractWindowAIRoutine.ACTION_USE_ITEM) {
            success = this.useItem();
        }
        return success;
    }

    @Override
    public final void executeNextAIAction() {
        int actionToPerform = this.enemy.getWindowAI()
                .getNextAction(this.enemy);
        if (actionToPerform == AbstractWindowAIRoutine.ACTION_ATTACK) {
            int actions = this.enemy.getWindowBattleActionsPerRound();
            for (int x = 0; x < actions; x++) {
                this.computeEnemyDamage();
                this.displayEnemyRoundResults();
            }
        } else if (actionToPerform == AbstractWindowAIRoutine.ACTION_CAST_SPELL) {
            SpellCaster.castSpell(this.enemy.getWindowAI().getSpellToCast(),
                    this.enemy);
        } else if (actionToPerform == AbstractWindowAIRoutine.ACTION_FLEE) {
            RandomRange rf = new RandomRange(0, 100);
            int runChance = rf.generate();
            if (runChance <= this.computeEnemyRunChance()) {
                // Success
                this.setResult(BattleResults.ENEMY_FLED);
            } else {
                // Failure
                this.updateMessageAreaEnemyFleeFailed();
            }
        }
    }

    private void computeDamage(AbstractCreature theEnemy,
            AbstractCreature acting) {
        // Compute Damage
        this.damage = 0;
        int actual = this.de.computeDamage(theEnemy, acting);
        // Update Prestige
        if (actual != 0) {
            BattlePrestige.dealtDamage(acting, actual);
            BattlePrestige.tookDamage(theEnemy, actual);
            BattlePrestige.hitEnemy(acting);
            BattlePrestige.hitByEnemy(theEnemy);
        } else {
            BattlePrestige.missedEnemy(acting);
            BattlePrestige.dodgedAttack(theEnemy);
        }
        // Hit or Missed
        this.damage = actual;
        if (this.de.weaponFumble()) {
            acting.doDamage(this.damage);
        } else {
            if (this.damage < 0) {
                acting.doDamage(-this.damage);
            } else {
                theEnemy.doDamage(this.damage);
            }
        }
        // Check damage
        if (acting instanceof PartyMember) {
            if (this.damage > 0) {
                this.playerDidDamage = true;
            } else if (this.damage < 0) {
                this.enemyDidDamage = true;
            }
        } else if (acting instanceof AbstractMonster) {
            if (this.damage > 0) {
                this.enemyDidDamage = true;
            } else if (this.damage < 0) {
                this.playerDidDamage = true;
            }
        }
    }

    final void computePlayerDamage() {
        // Compute Player Damage
        this.computeDamage(this.enemy, PartyManager.getParty().getLeader());
    }

    final void computeEnemyDamage() {
        // Compute Enemy Damage
        this.computeDamage(PartyManager.getParty().getLeader(), this.enemy);
    }

    final int computeRunChance() {
        return WindowBattleLogic.BASE_RUN_CHANCE
                - this.enemy.getLevelDifference()
                * WindowBattleLogic.RUN_CHANCE_DIFF_FACTOR;
    }

    final int computeEnemyRunChance() {
        return WindowBattleLogic.ENEMY_BASE_RUN_CHANCE
                + this.enemy.getLevelDifference()
                * WindowBattleLogic.ENEMY_RUN_CHANCE_DIFF_FACTOR;
    }

    @Override
    public final void displayBattleStats() {
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final String enemyName = this.enemy.getName();
        final String fightingWhat = this.enemy.getFightingWhatString();
        final String monsterLevelString = enemyName + "'s Level: "
                + Integer.toString(this.enemy.getLevel());
        final String monsterHPString = this.enemy.getHPString();
        final String monsterMPString = this.enemy.getMPString();
        final String playerHPString = playerCharacter.getHPString();
        final String playerMPString = playerCharacter.getMPString();
        final String displayMonsterHPString = enemyName + "'s HP: "
                + monsterHPString;
        final String displayMonsterMPString = enemyName + "'s MP: "
                + monsterMPString;
        final String displayPlayerHPString = "Your HP: " + playerHPString;
        final String displayPlayerMPString = "Your MP: " + playerMPString;
        final String displayString = fightingWhat + "\n" + monsterLevelString
                + "\n" + displayMonsterHPString + "\n" + displayMonsterMPString
                + "\n" + displayPlayerHPString + "\n" + displayPlayerMPString;
        this.setStatusMessage(displayString);
    }

    final void displayPlayerRoundResults() {
        // Display player round results
        if (this.result != BattleResults.ENEMY_FLED) {
            final String enemyName = this.enemy.getName();
            final String playerDamageString = Integer.toString(this.damage);
            final String playerFumbleDamageString = Integer
                    .toString(this.damage);
            String displayPlayerDamageString = null;
            String playerWhackString = "";
            if (this.de.weaponFumble()) {
                displayPlayerDamageString = "FUMBLE! You drop your weapon, doing "
                        + playerFumbleDamageString + " damage to yourself!";
                SoundManager.playSound(SoundConstants.SOUND_FUMBLE);
            } else {
                if (this.damage == 0) {
                    displayPlayerDamageString = "You try to hit the "
                            + enemyName + ", but MISS!";
                    SoundManager.playSound(SoundConstants.SOUND_MISSED);
                } else if (this.damage < 0) {
                    displayPlayerDamageString = "You try to hit the "
                            + enemyName + ", but are RIPOSTED for "
                            + (-this.damage) + " damage!";
                    SoundManager.playSound(SoundConstants.SOUND_COUNTER);
                } else {
                    displayPlayerDamageString = "You hit the " + enemyName
                            + " for " + playerDamageString + " damage!";
                    SoundManager.playSound(SoundConstants.SOUND_HIT);
                }
                if (this.de.weaponCrit()) {
                    playerWhackString += "CRITICAL HIT!\n";
                    SoundManager.playSound(SoundConstants.SOUND_CRITICAL);
                }
                if (this.de.weaponPierce()) {
                    playerWhackString += "Your attack pierces the " + enemyName
                            + "'s armor!\n";
                }
            }
            final String displayString = playerWhackString
                    + displayPlayerDamageString;
            this.setStatusMessage(displayString);
        }
    }

    final void displayEnemyRoundResults() {
        // Display enemy round results
        if (this.result != BattleResults.ENEMY_FLED) {
            final String enemyName = this.enemy.getName();
            final String enemyDamageString = Integer.toString(this.damage);
            final String enemyFumbleDamageString = Integer
                    .toString(this.damage);
            String displayEnemyDamageString = null;
            String enemyWhackString = "";
            if (this.de.weaponFumble()) {
                displayEnemyDamageString = "FUMBLE! The " + enemyName
                        + " drops its weapon, doing " + enemyFumbleDamageString
                        + " damage to itself!";
                SoundManager.playSound(SoundConstants.SOUND_FUMBLE);
                enemyWhackString = "";
            } else {
                if (this.damage == 0) {
                    displayEnemyDamageString = "The " + enemyName
                            + " tries to hit you, but MISSES!";
                    SoundManager.playSound(SoundConstants.SOUND_MISSED);
                } else if (this.damage < 0) {
                    displayEnemyDamageString = "The " + enemyName
                            + " tries to hit you, but you RIPOSTE for "
                            + (-this.damage) + " damage!";
                    SoundManager.playSound(SoundConstants.SOUND_COUNTER);
                } else {
                    displayEnemyDamageString = "The " + enemyName
                            + " hits you for " + enemyDamageString + " damage!";
                    SoundManager.playSound(SoundConstants.SOUND_HIT);
                }
                if (this.de.weaponCrit()) {
                    enemyWhackString += "CRITICAL HIT!\n";
                    SoundManager.playSound(SoundConstants.SOUND_CRITICAL);
                }
                if (this.de.weaponPierce()) {
                    enemyWhackString += "The " + enemyName
                            + "'s attack pierces YOUR armor!\n";
                }
            }
            final String displayString = enemyWhackString
                    + displayEnemyDamageString;
            this.setStatusMessage(displayString);
        }
    }

    // Methods
    @Override
    public void doBattle() {
        MazeRunnerII.getApplication().getGameManager().hideOutput();
        if (PreferencesManager.getMusicEnabled(PreferencesManager.MUSIC_BATTLE)) {
            MusicManager.playMusic("battle");
        }
        SoundManager.playSound(SoundConstants.SOUND_BATTLE);
        this.enemy = MonsterFactory.getNewMonsterInstance(true, true, true,
                false);
        this.enemy.loadMonster();
        this.enemyDidDamage = false;
        this.playerDidDamage = false;
        this.setResult(BattleResults.IN_PROGRESS);
        this.gui.initBattle(this.enemy.getImage());
        this.firstUpdateMessageArea();
    }

    @Override
    public void doBattleByProxy() {
        this.enemy = MonsterFactory.getNewMonsterInstance(true, true, true,
                false);
        this.enemy.loadMonster();
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        AbstractMonster m = this.enemy;
        playerCharacter.offsetExperience(m.getExperience());
        playerCharacter.offsetGold(m.getGold());
        MazeRunnerII.getApplication().getGameManager()
                .addToScore(m.getExperience() + m.getGold());
        // Level Up Check
        if (playerCharacter.checkLevelUp()) {
            playerCharacter.levelUp();
            MazeRunnerII.getApplication().getGameManager().keepNextMessage();
            MazeRunnerII.getApplication().showMessage(
                    "You reached level " + playerCharacter.getLevel() + ".");
        }
    }

    @Override
    public final int getResult() {
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        int currResult;
        if (this.result != BattleResults.IN_PROGRESS) {
            return this.result;
        }
        if (this.enemy.isAlive() && !playerCharacter.isAlive()) {
            if (!this.playerDidDamage) {
                currResult = BattleResults.ANNIHILATED;
            } else {
                currResult = BattleResults.LOST;
            }
        } else if (!this.enemy.isAlive() && playerCharacter.isAlive()) {
            if (!this.enemyDidDamage) {
                currResult = BattleResults.PERFECT;
            } else {
                currResult = BattleResults.WON;
            }
        } else if (!this.enemy.isAlive() && !playerCharacter.isAlive()) {
            currResult = BattleResults.DRAW;
        } else {
            currResult = BattleResults.IN_PROGRESS;
        }
        return currResult;
    }

    @Override
    public final void maintainEffects() {
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        playerCharacter.useEffects();
        playerCharacter.cullInactiveEffects();
        this.enemy.useEffects();
        this.enemy.cullInactiveEffects();
    }

    @Override
    public final void displayActiveEffects() {
        boolean flag1 = false, flag2 = false, flag3 = false;
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        String effectString = playerCharacter.getCompleteEffectString();
        String effectMessages = playerCharacter.getAllCurrentEffectMessages();
        String enemyEffectMessages = this.enemy.getAllCurrentEffectMessages();
        if (!(effectString.equals(Effect.getNullMessage()))) {
            flag1 = true;
        }
        if (!(effectMessages.equals(Effect.getNullMessage()))) {
            flag2 = true;
        }
        if (!(enemyEffectMessages.equals(Effect.getNullMessage()))) {
            flag3 = true;
        }
        if (flag1) {
            this.setStatusMessage(effectString);
        }
        if (flag2) {
            this.setStatusMessage(effectMessages);
        }
        if (flag3) {
            this.setStatusMessage(enemyEffectMessages);
        }
    }

    final void clearMessageArea() {
        this.gui.clearMessageArea();
    }

    @Override
    public final void setStatusMessage(String s) {
        this.gui.setStatusMessage(s);
    }

    final void stripExtraNewLine() {
        this.gui.stripExtraNewLine();
    }

    final void firstUpdateMessageArea() {
        this.clearMessageArea();
        this.setStatusMessage("*** Beginning of Round ***");
        this.displayBattleStats();
        this.setStatusMessage("*** Beginning of Round ***\n");
        // Determine initiative
        boolean enemyGotJump = false;
        if (this.enemy.getSpeed() > PartyManager.getParty().getLeader()
                .getSpeed()) {
            // Enemy acts first!
            enemyGotJump = true;
        } else if (this.enemy.getSpeed() < PartyManager.getParty().getLeader()
                .getSpeed()) {
            // You act first!
            enemyGotJump = false;
        } else {
            // Equal, decide randomly
            RandomRange jump = new RandomRange(0, 1);
            int whoFirst = jump.generate();
            if (whoFirst == 1) {
                // Enemy acts first!
                enemyGotJump = true;
            } else {
                // You act first!
                enemyGotJump = false;
            }
        }
        if (enemyGotJump) {
            this.setStatusMessage("The enemy acts first!");
            this.executeNextAIAction();
            // Display Active Effects
            this.displayActiveEffects();
            // Maintain Effects
            this.maintainEffects();
        } else {
            this.setStatusMessage("You act first!");
        }
        this.setStatusMessage("\n*** End of Round ***");
        this.displayBattleStats();
        this.setStatusMessage("*** End of Round ***");
        this.stripExtraNewLine();
        this.gui.getOutputFrame().pack();
    }

    final void updateMessageAreaEnemyFleeFailed() {
        this.setStatusMessage("The enemy tries to run away, but doesn't quite make it!");
    }

    final void updateMessageAreaPostSteal() {
        this.setStatusMessage("You try to steal money, and successfully steal "
                + this.stealAmount + " Gold!");
    }

    final void updateMessageAreaPostDrain() {
        this.setStatusMessage("You try to drain the enemy, and succeed!");
    }

    final void updateMessageAreaFleeFailed() {
        this.setStatusMessage("You try to run away, but don't quite make it!");
    }

    @Override
    public final boolean steal() {
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        int stealChance = StatConstants.CHANCE_STEAL;
        if (stealChance <= 0) {
            // Failed
            this.stealAmount = 0;
            return false;
        } else if (stealChance >= 100) {
            // Succeeded
            RandomRange stole = new RandomRange(0, this.enemy.getGold());
            this.stealAmount = stole.generate();
            playerCharacter.offsetGold(this.stealAmount);
            return true;
        } else {
            RandomRange chance = new RandomRange(0, 100);
            int randomChance = chance.generate();
            if (randomChance <= stealChance) {
                // Succeeded
                RandomRange stole = new RandomRange(0, this.enemy.getGold());
                this.stealAmount = stole.generate();
                playerCharacter.offsetGold(this.stealAmount);
                return true;
            } else {
                // Failed
                this.stealAmount = 0;
                return false;
            }
        }
    }

    @Override
    public final boolean drain() {
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        int drainChance = StatConstants.CHANCE_DRAIN;
        if (drainChance <= 0) {
            // Failed
            return false;
        } else if (drainChance >= 100) {
            // Succeeded
            RandomRange drained = new RandomRange(0, this.enemy.getCurrentMP());
            int drainAmount = drained.generate();
            this.enemy.offsetCurrentMP(-drainAmount);
            playerCharacter.offsetCurrentMP(drainAmount);
            return true;
        } else {
            RandomRange chance = new RandomRange(0, 100);
            int randomChance = chance.generate();
            if (randomChance <= drainChance) {
                // Succeeded
                RandomRange drained = new RandomRange(0,
                        this.enemy.getCurrentMP());
                int drainAmount = drained.generate();
                this.enemy.offsetCurrentMP(-drainAmount);
                playerCharacter.offsetCurrentMP(drainAmount);
                return true;
            } else {
                // Failed
                return false;
            }
        }
    }

    final void updateMessageAreaStealFailed() {
        this.setStatusMessage("You try to steal money from the enemy, but the attempt fails!");
    }

    final void updateMessageAreaDrainFailed() {
        this.setStatusMessage("You try to drain the enemy's MP, but the attempt fails!");
    }

    @Override
    public void doResult() {
        if (PreferencesManager.getMusicEnabled(PreferencesManager.MUSIC_BATTLE)) {
            MusicManager.stopMusic();
        }
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        AbstractMonster m = this.enemy;
        if (this.result == BattleResults.WON) {
            this.setStatusMessage("You gain " + m.getExperience()
                    + " experience and " + m.getGold() + " Gold.");
            playerCharacter.offsetExperience(m.getExperience());
            playerCharacter.offsetGold(m.getGold());
            SoundManager.playSound(SoundConstants.SOUND_VICTORY);
            MazeRunnerII.getApplication().getGameManager()
                    .addToScore(m.getExperience() + m.getGold());
        } else if (this.result == BattleResults.PERFECT) {
            this.setStatusMessage("You gain " + m.getExperience()
                    + " experience and " + m.getGold() + " Gold,\nplus "
                    + m.getPerfectBonusGold()
                    + " extra gold for a perfect fight!");
            playerCharacter.offsetExperience(m.getExperience());
            playerCharacter.offsetGold(m.getGold() + m.getPerfectBonusGold());
            SoundManager.playSound(SoundConstants.SOUND_VICTORY);
            MazeRunnerII
                    .getApplication()
                    .getGameManager()
                    .addToScore(
                            m.getExperience() + m.getGold()
                                    + m.getPerfectBonusGold());
        } else if (this.result == BattleResults.LOST) {
            this.setStatusMessage("You lost...");
        } else if (this.result == BattleResults.ANNIHILATED) {
            this.setStatusMessage("You lost without hurting your foe... you were annihilated!");
        } else if (this.result == BattleResults.DRAW) {
            this.setStatusMessage("The battle was a draw. You are fully healed!");
            playerCharacter
                    .healPercentage(AbstractCreature.FULL_HEAL_PERCENTAGE);
            playerCharacter
                    .regeneratePercentage(AbstractCreature.FULL_HEAL_PERCENTAGE);
        } else if (this.result == BattleResults.FLED) {
            this.setStatusMessage("You ran away successfully!");
        } else if (this.result == BattleResults.ENEMY_FLED) {
            this.setStatusMessage("The enemy runs away!");
            this.setStatusMessage("Since the enemy ran away, you gain nothing for this battle.");
        }
        // Cleanup
        this.gui.doResultCleanup();
        playerCharacter.stripAllEffects();
        this.enemy.stripAllEffects();
        // Level Up Check
        if (playerCharacter.checkLevelUp()) {
            playerCharacter.levelUp();
            if (PreferencesManager.getSoundsEnabled()) {
                SoundManager.playSound(SoundConstants.SOUND_LEVEL_UP);
            }
            this.setStatusMessage("You reached level "
                    + playerCharacter.getLevel() + ".");
        }
        // Final Cleanup
        this.gui.doResultFinalCleanup();
    }

    @Override
    public final void setResult(int newResult) {
        this.result = newResult;
    }

    @Override
    public final void battleDone() {
        this.gui.getOutputFrame().setVisible(false);
        MazeRunnerII.getApplication().getGameManager().showOutput();
        MazeRunnerII.getApplication().getGameManager().redrawMaze();
    }

    @Override
    public boolean getTerminatedEarly() {
        return false;
    }

    @Override
    public boolean getLastAIActionResult() {
        return true;
    }

    @Override
    public boolean updatePosition(int x, int y) {
        return true;
    }

    @Override
    public void fireArrow(int x, int y) {
        // Do nothing
    }

    @Override
    public void arrowDone(BattleCharacter hit) {
        // Do nothing
    }

    @Override
    public boolean castSpell() {
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        return SpellCaster.selectAndCastSpell(playerCharacter);
    }

    @Override
    public boolean useItem() {
        PartyMember playerCharacter = PartyManager.getParty().getLeader();
        return CombatItemChucker.selectAndUseItem(playerCharacter);
    }

    @Override
    public void endTurn() {
        // Do nothing
    }

    @Override
    public void redrawOneBattleSquare(int x, int y, AbstractMazeObject obj3) {
        // Do nothing
    }

    @Override
    public boolean isWaitingForAI() {
        return false;
    }

    @Override
    public AbstractMonster getEnemy() {
        return this.enemy;
    }
}
