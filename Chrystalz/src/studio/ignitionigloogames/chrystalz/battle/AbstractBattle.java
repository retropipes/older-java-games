/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.battle;

import javax.swing.JFrame;

import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;

public abstract class AbstractBattle {
    // Constructors
    protected AbstractBattle() {
        // Do nothing
    }

    // Generic Methods
    public abstract JFrame getOutputFrame();

    public abstract void resetGUI();

    public abstract void doBattle();

    public abstract void doBossBattle();

    public abstract void doFinalBossBattle();

    public abstract void doBattleByProxy();

    public abstract void setStatusMessage(final String msg);

    public abstract void executeNextAIAction();

    public abstract boolean getLastAIActionResult();

    public abstract boolean castSpell();

    public abstract boolean steal();

    public abstract boolean drain();

    public abstract void endTurn();

    public abstract AbstractCreature getEnemy();

    public abstract void battleDone();

    public abstract void displayActiveEffects();

    public abstract void displayBattleStats();

    public abstract boolean doPlayerActions(final int actionType);

    public abstract BattleResult getResult();

    public abstract void doResult();

    public abstract void setResult(final int resultCode);

    public abstract void maintainEffects(final boolean player);

    public abstract boolean updatePosition(int x, int y);

    public abstract boolean isWaitingForAI();
}
