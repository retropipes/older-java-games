package com.puttysoftware.fantastlex.battle.window;

import javax.swing.JFrame;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.ai.window.AbstractWindowAIRoutine;
import com.puttysoftware.fantastlex.battle.AbstractBattle;
import com.puttysoftware.fantastlex.battle.BattlePrestige;
import com.puttysoftware.fantastlex.battle.BattleResults;
import com.puttysoftware.fantastlex.battle.damageengines.AbstractDamageEngine;
import com.puttysoftware.fantastlex.creatures.AbstractCreature;
import com.puttysoftware.fantastlex.creatures.StatConstants;
import com.puttysoftware.fantastlex.creatures.monsters.AbstractMonster;
import com.puttysoftware.fantastlex.creatures.monsters.MonsterFactory;
import com.puttysoftware.fantastlex.creatures.party.PartyManager;
import com.puttysoftware.fantastlex.creatures.party.PartyMember;
import com.puttysoftware.fantastlex.effects.Effect;
import com.puttysoftware.fantastlex.items.combat.CombatItemChucker;
import com.puttysoftware.fantastlex.maze.abc.AbstractMazeObject;
import com.puttysoftware.fantastlex.maze.objects.BattleCharacter;
import com.puttysoftware.fantastlex.prefs.PreferencesManager;
import com.puttysoftware.fantastlex.resourcemanagers.MusicManager;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;
import com.puttysoftware.fantastlex.spells.SpellCaster;
import com.puttysoftware.randomrange.RandomRange;

public class WindowBattleLogic extends AbstractBattle {
    // Fields
    private int stealAmount;
    private int damage;
    private boolean enemyDidDamage;
    private boolean playerDidDamage;
    private AbstractMonster enemy;
    private int result;
    private final AbstractDamageEngine de;
    private final WindowBattleGUI gui;
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
    public final boolean doPlayerActions(final int actionToPerform) {
        boolean success = true;
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        if (actionToPerform == AbstractWindowAIRoutine.ACTION_ATTACK) {
            final int actions = playerCharacter
                    .getWindowBattleActionsPerRound();
            for (int x = 0; x < actions; x++) {
                this.computePlayerDamage();
                this.displayPlayerRoundResults();
            }
        } else if (actionToPerform == AbstractWindowAIRoutine.ACTION_CAST_SPELL) {
            success = this.castSpell();
        } else if (actionToPerform == AbstractWindowAIRoutine.ACTION_FLEE) {
            final RandomRange rf = new RandomRange(0, 100);
            final int runChance = rf.generate();
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
        final int actionToPerform = this.enemy.getWindowAI()
                .getNextAction(this.enemy);
        if (actionToPerform == AbstractWindowAIRoutine.ACTION_ATTACK) {
            final int actions = this.enemy.getWindowBattleActionsPerRound();
            for (int x = 0; x < actions; x++) {
                this.computeEnemyDamage();
                this.displayEnemyRoundResults();
            }
        } else if (actionToPerform == AbstractWindowAIRoutine.ACTION_CAST_SPELL) {
            SpellCaster.castSpell(this.enemy.getWindowAI().getSpellToCast(),
                    this.enemy);
        } else if (actionToPerform == AbstractWindowAIRoutine.ACTION_FLEE) {
            final RandomRange rf = new RandomRange(0, 100);
            final int runChance = rf.generate();
            if (runChance <= this.computeEnemyRunChance()) {
                // Success
                this.setResult(BattleResults.ENEMY_FLED);
            } else {
                // Failure
                this.updateMessageAreaEnemyFleeFailed();
            }
        }
    }

    private void computeDamage(final AbstractCreature theEnemy,
            final AbstractCreature acting) {
        // Compute Damage
        this.damage = 0;
        final int actual = this.de.computeDamage(theEnemy, acting);
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
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
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
                            + -this.damage + " damage!";
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
                            + -this.damage + " damage!";
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
        FantastleX.getApplication().getGameManager().hideOutput();
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_BATTLE)) {
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
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final AbstractMonster m = this.enemy;
        playerCharacter.offsetExperience(m.getExperience());
        playerCharacter.offsetGold(m.getGold());
        FantastleX.getApplication().getGameManager()
                .addToScore(m.getExperience() + m.getGold());
        // Level Up Check
        if (playerCharacter.checkLevelUp()) {
            playerCharacter.levelUp();
            FantastleX.getApplication().getGameManager().keepNextMessage();
            FantastleX.getApplication().showMessage(
                    "You reached level " + playerCharacter.getLevel() + ".");
        }
    }

    @Override
    public final int getResult() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
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
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        playerCharacter.useEffects();
        playerCharacter.cullInactiveEffects();
        this.enemy.useEffects();
        this.enemy.cullInactiveEffects();
    }

    @Override
    public final void displayActiveEffects() {
        boolean flag1 = false, flag2 = false, flag3 = false;
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final String effectString = playerCharacter.getCompleteEffectString();
        final String effectMessages = playerCharacter
                .getAllCurrentEffectMessages();
        final String enemyEffectMessages = this.enemy
                .getAllCurrentEffectMessages();
        if (!effectString.equals(Effect.getNullMessage())) {
            flag1 = true;
        }
        if (!effectMessages.equals(Effect.getNullMessage())) {
            flag2 = true;
        }
        if (!enemyEffectMessages.equals(Effect.getNullMessage())) {
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
    public final void setStatusMessage(final String s) {
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
            final RandomRange jump = new RandomRange(0, 1);
            final int whoFirst = jump.generate();
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
        this.setStatusMessage(
                "The enemy tries to run away, but doesn't quite make it!");
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
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final int stealChance = StatConstants.CHANCE_STEAL;
        final RandomRange chance = new RandomRange(0, 100);
        final int randomChance = chance.generate();
        if (randomChance <= stealChance) {
            // Succeeded
            final RandomRange stole = new RandomRange(0, this.enemy.getGold());
            this.stealAmount = stole.generate();
            playerCharacter.offsetGold(this.stealAmount);
            return true;
        } else {
            // Failed
            this.stealAmount = 0;
            return false;
        }
    }

    @Override
    public final boolean drain() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final int drainChance = StatConstants.CHANCE_DRAIN;
        final RandomRange chance = new RandomRange(0, 100);
        final int randomChance = chance.generate();
        if (randomChance <= drainChance) {
            // Succeeded
            final RandomRange drained = new RandomRange(0,
                    this.enemy.getCurrentMP());
            final int drainAmount = drained.generate();
            this.enemy.offsetCurrentMP(-drainAmount);
            playerCharacter.offsetCurrentMP(drainAmount);
            return true;
        } else {
            // Failed
            return false;
        }
    }

    final void updateMessageAreaStealFailed() {
        this.setStatusMessage(
                "You try to steal money from the enemy, but the attempt fails!");
    }

    final void updateMessageAreaDrainFailed() {
        this.setStatusMessage(
                "You try to drain the enemy's MP, but the attempt fails!");
    }

    @Override
    public void doResult() {
        if (PreferencesManager
                .getMusicEnabled(PreferencesManager.MUSIC_BATTLE)) {
            MusicManager.stopMusic();
        }
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        final AbstractMonster m = this.enemy;
        if (this.result == BattleResults.WON) {
            this.setStatusMessage("You gain " + m.getExperience()
                    + " experience and " + m.getGold() + " Gold.");
            playerCharacter.offsetExperience(m.getExperience());
            playerCharacter.offsetGold(m.getGold());
            SoundManager.playSound(SoundConstants.SOUND_VICTORY);
            FantastleX.getApplication().getGameManager()
                    .addToScore(m.getExperience() + m.getGold());
        } else if (this.result == BattleResults.PERFECT) {
            this.setStatusMessage("You gain " + m.getExperience()
                    + " experience and " + m.getGold() + " Gold,\nplus "
                    + m.getPerfectBonusGold()
                    + " extra gold for a perfect fight!");
            playerCharacter.offsetExperience(m.getExperience());
            playerCharacter.offsetGold(m.getGold() + m.getPerfectBonusGold());
            SoundManager.playSound(SoundConstants.SOUND_VICTORY);
            FantastleX.getApplication().getGameManager().addToScore(
                    m.getExperience() + m.getGold() + m.getPerfectBonusGold());
        } else if (this.result == BattleResults.LOST) {
            this.setStatusMessage("You lost...");
        } else if (this.result == BattleResults.ANNIHILATED) {
            this.setStatusMessage(
                    "You lost without hurting your foe... you were annihilated!");
        } else if (this.result == BattleResults.DRAW) {
            this.setStatusMessage(
                    "The battle was a draw. You are fully healed!");
            playerCharacter
                    .healPercentage(AbstractCreature.FULL_HEAL_PERCENTAGE);
            playerCharacter.regeneratePercentage(
                    AbstractCreature.FULL_HEAL_PERCENTAGE);
        } else if (this.result == BattleResults.FLED) {
            this.setStatusMessage("You ran away successfully!");
        } else if (this.result == BattleResults.ENEMY_FLED) {
            this.setStatusMessage("The enemy runs away!");
            this.setStatusMessage(
                    "Since the enemy ran away, you gain nothing for this battle.");
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
            this.setStatusMessage(
                    "You reached level " + playerCharacter.getLevel() + ".");
        }
        // Final Cleanup
        this.gui.doResultFinalCleanup();
    }

    @Override
    public final void setResult(final int newResult) {
        this.result = newResult;
    }

    @Override
    public final void battleDone() {
        this.gui.getOutputFrame().setVisible(false);
        FantastleX.getApplication().getGameManager().showOutput();
        FantastleX.getApplication().getGameManager().redrawMaze();
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
    public boolean updatePosition(final int x, final int y) {
        return true;
    }

    @Override
    public void fireArrow(final int x, final int y) {
        // Do nothing
    }

    @Override
    public void arrowDone(final BattleCharacter hit) {
        // Do nothing
    }

    @Override
    public boolean castSpell() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        return SpellCaster.selectAndCastSpell(playerCharacter);
    }

    @Override
    public boolean useItem() {
        final PartyMember playerCharacter = PartyManager.getParty().getLeader();
        return CombatItemChucker.selectAndUseItem(playerCharacter);
    }

    @Override
    public void endTurn() {
        // Do nothing
    }

    @Override
    public void redrawOneBattleSquare(final int x, final int y,
            final AbstractMazeObject obj3) {
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
