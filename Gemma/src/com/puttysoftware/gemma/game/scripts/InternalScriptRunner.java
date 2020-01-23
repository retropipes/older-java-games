/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.game.scripts;

import com.puttysoftware.gemma.Gemma;
import com.puttysoftware.gemma.game.GameLogic;
import com.puttysoftware.gemma.support.battle.Battle;
import com.puttysoftware.gemma.support.items.Shop;
import com.puttysoftware.gemma.support.map.Map;
import com.puttysoftware.gemma.support.map.generic.MapObject;
import com.puttysoftware.gemma.support.resourcemanagers.SoundManager;
import com.puttysoftware.gemma.support.scripts.internal.InternalScript;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptConstants;
import com.puttysoftware.gemma.support.scripts.internal.InternalScriptEntry;
import com.puttysoftware.randomrange.RandomRange;

public final class InternalScriptRunner {
    private InternalScriptRunner() {
        // Do nothing
    }

    public static void runScript(InternalScript s) {
        int actionCounter = 0;
        Map m = Gemma.getApplication().getScenarioManager().getMap();
        try {
            GameLogic gm = Gemma.getApplication().getGameManager();
            if (s != null) {
                int totAct = s.getActionCount();
                for (int x = 0; x < totAct; x++) {
                    actionCounter = x + 1;
                    InternalScriptEntry se = s.getAction(x);
                    InternalScriptRunner.validateScriptEntry(se);
                    InternalScriptActionCode code = se.getActionCode();
                    switch (code) {
                    case MESSAGE:
                        // Show the message
                        String msg = se.getFirstActionArg().getString();
                        Gemma.getApplication().showMessage(msg);
                        break;
                    case SOUND:
                        // Play the sound
                        int snd = se.getFirstActionArg().getInteger();
                        SoundManager.playSound(snd);
                        break;
                    case SHOP:
                        // Show the shop
                        int shopType = se.getFirstActionArg().getInteger();
                        Shop shop = Gemma.getApplication()
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
                        boolean moveType = se.getActionArg(0).getBoolean();
                        boolean eventFlag = se.getActionArg(1).getBoolean();
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
                        Gemma.getApplication().getGameManager().decay();
                        break;
                    case SWAP_PAIRS:
                        String swap1 = se.getActionArg(0).getString();
                        String swap2 = se.getActionArg(1).getString();
                        MapObject swapObj1 = Gemma.getApplication().getObjects()
                                .getNewInstanceByName(swap1);
                        MapObject swapObj2 = Gemma.getApplication().getObjects()
                                .getNewInstanceByName(swap2);
                        Gemma.getApplication().getScenarioManager().getMap()
                                .findAllObjectPairsAndSwap(swapObj1, swapObj2);
                        break;
                    case REDRAW:
                        Gemma.getApplication().getGameManager().redrawMap();
                        break;
                    case ADD_TO_SCORE:
                        int points = se.getActionArg(0).getInteger();
                        Gemma.getApplication().getGameManager()
                                .addToScore(points);
                        break;
                    case RANDOM_CHANCE:
                        // Random Chance
                        int threshold = se.getActionArg(0).getInteger();
                        RandomRange random = new RandomRange(0, 9999);
                        int chance = random.generate();
                        if (chance > threshold) {
                            return;
                        }
                        break;
                    case BATTLE:
                        // Hide the game
                        Gemma.getApplication().getGameManager().hideOutput();
                        // Battle
                        final Battle battle = new Battle();
                        new Thread("Battle") {
                            @Override
                            public void run() {
                                try {
                                    Gemma.getApplication().getGameManager();
                                    Gemma.getApplication().getBattle()
                                            .doFixedBattle(Map
                                                    .getTemporaryBattleCopy(),
                                                    battle);
                                } catch (Exception e) {
                                    // Something went wrong in the battle
                                    Gemma.getErrorLogger().logError(e);
                                }
                            }
                        }.start();
                        break;
                    case RELATIVE_LEVEL_CHANGE:
                        int rDestLevel = se.getActionArg(0).getInteger();
                        Gemma.getApplication().getGameManager()
                                .goToLevelRelative(rDestLevel);
                        break;
                    case UPDATE_GSA:
                        int gsaMod = se.getActionArg(0).getInteger();
                        Gemma.getApplication().getScenarioManager().getMap()
                                .rebuildGSA(gsaMod);
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Illegal Action Code: " + code.toString());
                    }
                }
            }
        } catch (Exception e) {
            String beginMsg = "Buggy Internal Script, action #" + actionCounter
                    + ": ";
            String endMsg = e.getMessage();
            String scriptMsg = beginMsg + endMsg;
            Gemma.getNonFatalLogger().logNonFatalError(
                    new InternalScriptException(scriptMsg, e));
        }
    }

    private static void validateScriptEntry(InternalScriptEntry se) {
        InternalScriptActionCode code = se.getActionCode();
        int rargc = InternalScriptConstants.ARGUMENT_COUNT_VALIDATION[code
                .ordinal()];
        int aargc;
        try {
            aargc = se.getActionArgs().length;
        } catch (NullPointerException npe) {
            aargc = 0;
        }
        if (rargc != aargc) {
            throw new IllegalArgumentException("Expected " + rargc
                    + " arguments, found " + aargc + " arguments!");
        }
        Class<?>[] rargt = InternalScriptConstants.ARGUMENT_TYPE_VALIDATION[code
                .ordinal()];
        if (rargt != null) {
            Class<?>[] aargt = new Class[aargc];
            for (int x = 0; x < aargc; x++) {
                aargt[x] = se.getActionArg(x).getArgumentClass();
                if (!(aargt[x].getName().equals(rargt[x].getName()))) {
                    throw new IllegalArgumentException(
                            "Expected argument of type " + rargt[x].getName()
                                    + " at position " + (x + 1) + ", found "
                                    + aargt[x].getName() + "!");
                }
            }
        }
    }
}
