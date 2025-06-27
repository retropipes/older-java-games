/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.game.scripts;

import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.riskyrescue.RiskyRescue;
import com.puttysoftware.riskyrescue.assets.SoundManager;
import com.puttysoftware.riskyrescue.battle.Battle;
import com.puttysoftware.riskyrescue.items.Shop;
import com.puttysoftware.riskyrescue.map.Map;
import com.puttysoftware.riskyrescue.map.objects.MapObject;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScript;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptActionCode;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptConstants;
import com.puttysoftware.riskyrescue.scripts.internal.InternalScriptEntry;

public final class InternalScriptRunner {
    private InternalScriptRunner() {
        // Do nothing
    }

    public static void runScript(final InternalScript s) {
        int actionCounter = 0;
        try {
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
                            RiskyRescue.getApplication().showMessage(msg);
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
                            final Shop shop = RiskyRescue.getApplication()
                                    .getGenericShop(shopType);
                            if (shop != null) {
                                shop.showShop();
                            } else {
                                throw new IllegalArgumentException(
                                        "Illegal Shop Type: " + shopType);
                            }
                            break;
                        case DECAY:
                            RiskyRescue.getApplication().getGameManager().decay();
                            break;
                        case SWAP_PAIRS:
                            final String swap1 = se.getActionArg(0).getString();
                            final String swap2 = se.getActionArg(1).getString();
                            final MapObject swapObj1 = RiskyRescue.getApplication()
                                    .getObjects().getInstanceByName(swap1);
                            final MapObject swapObj2 = RiskyRescue.getApplication()
                                    .getObjects().getInstanceByName(swap2);
                            RiskyRescue.getApplication().getScenarioManager()
                                    .getMap()
                                    .findAllObjectPairsAndSwap(swapObj1, swapObj2);
                            break;
                        case REDRAW:
                            RiskyRescue.getApplication().getGameManager()
                                    .redrawMap();
                            break;
                        case ADD_TO_SCORE:
                            final int points = se.getActionArg(0).getInteger();
                            RiskyRescue.getApplication().getGameManager()
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
                            RiskyRescue.getApplication().getGameManager()
                                    .hideOutput();
                            // Battle
                            final Battle battle = new Battle();
                            new Thread("Battle") {
                                @Override
                                public void run() {
                                    try {
                                        RiskyRescue.getApplication()
                                                .getGameManager();
                                        RiskyRescue.getApplication().getBattle()
                                                .doFixedBattle(Map
                                                        .getTemporaryBattleCopy(),
                                                        battle);
                                    } catch (final Exception e) {
                                        // Something went wrong in the battle
                                        RiskyRescue.logError(e);
                                    }
                                }
                            }.start();
                            break;
                        case RELATIVE_LEVEL_CHANGE:
                            final int rDestLevel = se.getActionArg(0).getInteger();
                            RiskyRescue.getApplication().getGameManager()
                                    .goToLevelRelative(rDestLevel);
                            break;
                        case UPDATE_GSA:
                            final int gsaMod = se.getActionArg(0).getInteger();
                            RiskyRescue.getApplication().getScenarioManager()
                                    .getMap().rebuildGSA(gsaMod);
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
            RiskyRescue.logNonFatalError(
                    new InternalScriptException(scriptMsg, e));
        }
    }

    private static void validateScriptEntry(final InternalScriptEntry se) {
        final InternalScriptActionCode code = se.getActionCode();
        final int rargc = InternalScriptConstants.ARGUMENT_COUNT_VALIDATION[code
                .ordinal()];
        int aargc;
        if (se.getActionArgs() != null) {
            aargc = se.getActionArgs().length;
        } else {
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
