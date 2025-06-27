/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.battle.map.turn;

import studio.ignitionigloogames.twistedtrek.import2.Import2;
import studio.ignitionigloogames.twistedtrek.import2.battle.AbstractBattle;
import studio.ignitionigloogames.twistedtrek.import2.prefs.PreferencesManager;

public class MapTurnBattleAITask extends Thread {
	// Fields
	private final AbstractBattle b;

	// Constructors
	public MapTurnBattleAITask(final AbstractBattle battle) {
		this.setName("Map AI Runner");
		this.b = battle;
	}

	@Override
	public void run() {
		try {
			this.aiWait();
			while (true) {
				this.b.executeNextAIAction();
				if (this.b.getLastAIActionResult()) {
					// Delay, for animation purposes
					try {
						final int battleSpeed = PreferencesManager.getBattleSpeed();
						Thread.sleep(battleSpeed);
					} catch (final InterruptedException i) {
						// Ignore
					}
				}
			}
		} catch (final Throwable t) {
			Import2.getErrorLogger().logError(t);
		}
	}

	public synchronized void aiWait() {
		try {
			this.wait();
		} catch (final InterruptedException e) {
			// Ignore
		}
	}

	public synchronized void aiRun() {
		this.notify();
	}
}
