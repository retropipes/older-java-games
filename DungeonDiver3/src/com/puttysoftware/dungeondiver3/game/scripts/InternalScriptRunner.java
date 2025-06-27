/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.game.scripts;

import com.puttysoftware.dungeondiver3.DungeonDiver3;
import com.puttysoftware.dungeondiver3.game.GameLogic;
import com.puttysoftware.dungeondiver3.support.battle.Battle;
import com.puttysoftware.dungeondiver3.support.items.Shop;
import com.puttysoftware.dungeondiver3.support.map.Map;
import com.puttysoftware.dungeondiver3.support.map.generic.MapObject;
import com.puttysoftware.dungeondiver3.support.resourcemanagers.SoundManager;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScript;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptConstants;
import com.puttysoftware.dungeondiver3.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.randomrange.RandomRange;

public final class InternalScriptRunner {
    private InternalScriptRunner() {
        // Do nothing
    }

    public static void runScript(final InternalScript s) {
        int actionCounter = 0;
        final Map m = DungeonDiver3.getApplication().getScenarioManager()
                .getMap();
        try {
            final GameLogic gm = DungeonDiver3.getApplication()
                    .getGameManager();
            if (s != null) {
                final int totAct = s.getActionCount();
                for (int x = 0; x < totAct; x++) {
                    actionCounter = x + 1;
                    final InternalScriptEntry se = s.getAction(x);
                    InternalScriptRunner.validateScriptEntry(se);
                    final InternalScriptActionCode code = se.getActionCode();
                    switch (code) {
                        case MESSAGE:
                            // Show the message
                            final String msg = se.getFirstActionArg().getString();
                            DungeonDiver3.getApplication().showMessage(msg);
                            break;
                        case SOUND:
                            // Play the sound
                            final int snd = se.getFirstActionArg().getInteger();
                            SoundManager.playSound(snd);
                            break;
                        case SHOP:
                            // Show the shop
                            final int shopType = se.getFirstActionArg()
                                    .getInteger();
                            final Shop shop = DungeonDiver3.getApplication()
                                    .getGenericShop(shopType);
                            if (shop != null) {
                                shop.showShop();
                            } else {
                                throw new IllegalArgumentException(
                                        "Illegal Shop Type: " + shopType);
                            }
                            break;
                        case MOVE:
                            // Move
                            final boolean moveType = se.getActionArg(0)
                                    .getBoolean();
                            final boolean eventFlag = se.getActionArg(1)
                                    .getBoolean();
                            int moveX = se.getActionArg(2).getInteger();
                            int moveY = se.getActionArg(3).getInteger();
                            int moveZ = se.getActionArg(4).getInteger();
                            if (moveType == InternalScriptConstants.MOVE_ABSOLUTE) {
                                // Check
                                if (moveX < 0) {
                                    // Negative means keep as-is
                                    moveX = m.getPlayerLocationX();
                                }
                                if (moveY < 0) {
                                    // Negative means keep as-is
                                    moveY = m.getPlayerLocationY();
                                }
                                if (moveZ < 0) {
                                    // Negative means keep as-is
                                    moveZ = m.getPlayerLocationZ();
                                }
                            }
                            if (moveType == InternalScriptConstants.MOVE_RELATIVE) {
                                if (eventFlag) {
                                    gm.updatePositionRelative(moveX, moveY, moveZ);
                                } else {
                                    gm.updatePositionRelativeNoEvents(moveX, moveY,
                                            moveZ);
                                }
                            } else {
                                if (eventFlag) {
                                    gm.updatePositionAbsolute(moveX, moveY, moveZ);
                                } else {
                                    gm.updatePositionAbsoluteNoEvents(moveX, moveY,
                                            moveZ);
                                }
                            }
                            break;
                        case DECAY:
                            DungeonDiver3.getApplication().getGameManager().decay();
                            break;
                        case SWAP_PAIRS:
                            final String swap1 = se.getActionArg(0).getString();
                            final String swap2 = se.getActionArg(1).getString();
                            final MapObject swapObj1 = DungeonDiver3
                                    .getApplication().getObjects()
                                    .getNewInstanceByName(swap1);
                            final MapObject swapObj2 = DungeonDiver3
                                    .getApplication().getObjects()
                                    .getNewInstanceByName(swap2);
                            DungeonDiver3.getApplication().getScenarioManager()
                                    .getMap()
                                    .findAllObjectPairsAndSwap(swapObj1, swapObj2);
                            break;
                        case REDRAW:
                            DungeonDiver3.getApplication().getGameManager()
                                    .redrawMap();
                            break;
                        case ADD_TO_SCORE:
                            final int points = se.getActionArg(0).getInteger();
                            DungeonDiver3.getApplication().getGameManager()
                                    .addToScore(points);
                            break;
                        case RANDOM_CHANCE:
                            // Random Chance
                            final int threshold = se.getActionArg(0).getInteger();
                            final RandomRange random = new RandomRange(0, 9999);
                            final int chance = random.generate();
                            if (chance > threshold) {
                                return;
                            }
                            break;
                        case BATTLE:
                            // Hide the game
                            DungeonDiver3.getApplication().getGameManager()
                                    .hideOutput();
                            // Battle
                            final Battle battle = new Battle();
                            new Thread("Battle") {
                                @Override
                                public void run() {
                                    try {
                                        DungeonDiver3.getApplication()
                                                .getGameManager();
                                        DungeonDiver3.getApplication().getBattle()
                                                .doFixedBattle(Map
                                                        .getTemporaryBattleCopy(),
                                                        battle);
                                    } catch (final Exception e) {
                                        // Something went wrong in the battle
                                        DungeonDiver3.getErrorLogger().logError(e);
                                    }
                                }
                            }.start();
                            break;
                        case RELATIVE_LEVEL_CHANGE:
                            final int rDestLevel = se.getActionArg(0).getInteger();
                            DungeonDiver3.getApplication().getGameManager()
                                    .goToLevelRelative(rDestLevel);
                            break;
                        default:
                            throw new IllegalArgumentException(
                                    "Illegal Action Code: " + code.toString());
                    }
                }
            }
        } catch (final Exception e) {
            final String beginMsg = "Buggy Internal Script, action #"
                    + actionCounter + ": ";
            final String endMsg = e.getMessage();
            final String scriptMsg = beginMsg + endMsg;
            DungeonDiver3.getNonFatalLogger().logNonFatalError(
                    new InternalScriptException(scriptMsg, e));
        }
    }

    private static void validateScriptEntry(final InternalScriptEntry se) {
        final InternalScriptActionCode code = se.getActionCode();
        final int rargc = InternalScriptConstants.ARGUMENT_COUNT_VALIDATION[code
                .ordinal()];
        int aargc;
        try {
            aargc = se.getActionArgs().length;
        } catch (final NullPointerException npe) {
            aargc = 0;
        }
        if (rargc != aargc) {
            throw new IllegalArgumentException("Expected " + rargc
                    + " arguments, found " + aargc + " arguments!");
        }
        final Class<?>[] rargt = InternalScriptConstants.ARGUMENT_TYPE_VALIDATION[code
                .ordinal()];
        if (rargt != null) {
            final Class<?>[] aargt = new Class[aargc];
            for (int x = 0; x < aargc; x++) {
                aargt[x] = se.getActionArg(x).getArgumentClass();
                if (!aargt[x].getName().equals(rargt[x].getName())) {
                    throw new IllegalArgumentException(
                            "Expected argument of type " + rargt[x].getName()
                                    + " at position " + (x + 1) + ", found "
                                    + aargt[x].getName() + "!");
                }
            }
        }
    }
}
