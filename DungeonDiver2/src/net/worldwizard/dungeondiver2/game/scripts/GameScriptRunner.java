package net.worldwizard.dungeondiver2.game.scripts;

import net.worldwizard.dungeondiver2.DungeonDiverII;
import net.worldwizard.dungeondiver2.game.GameManager;
import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.battle.Battle;
import net.worldwizard.support.items.Shop;
import net.worldwizard.support.map.generic.MapObject;
import net.worldwizard.support.resourcemanagers.SoundManager;
import net.worldwizard.support.scripts.game.GameActionCode;
import net.worldwizard.support.scripts.game.GameScript;
import net.worldwizard.support.scripts.game.GameScriptConstants;
import net.worldwizard.support.scripts.game.GameScriptEntry;

public final class GameScriptRunner {
    private GameScriptRunner() {
        // Do nothing
    }

    public static void runScript(final GameScript s) {
        int actionCounter = 0;
        try {
            final GameManager gm = DungeonDiverII.getApplication()
                    .getGameManager();
            if (s != null) {
                final int totAct = s.getActionCount();
                for (int x = 0; x < totAct; x++) {
                    actionCounter = x + 1;
                    final GameScriptEntry se = s.getAction(x);
                    GameScriptRunner.validateScriptEntry(se);
                    final GameActionCode code = se.getActionCode();
                    switch (code) {
                    case NONE:
                        // Do nothing
                        break;
                    case MESSAGE:
                        // Show the message
                        final String msg = se.getFirstActionArg().getString();
                        DungeonDiverII.getApplication().showMessage(msg);
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
                        final Shop shop = DungeonDiverII.getApplication()
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
                        final int moveX = se.getActionArg(2).getInteger();
                        final int moveY = se.getActionArg(3).getInteger();
                        final int moveZ = se.getActionArg(4).getInteger();
                        if (moveType == GameScriptConstants.MOVE_RELATIVE) {
                            if (eventFlag) {
                                if (moveZ != 0) {
                                    gm.goToFloor(moveZ);
                                }
                                gm.updatePositionRelative(moveX, moveY);
                            } else {
                                if (moveZ != 0) {
                                    gm.goToFloorRelative(moveZ);
                                }
                                gm.updatePositionRelativeNoEvents(moveX, moveY);
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
                    case END_GAME:
                        // End Game
                        gm.exitGame();
                        break;
                    case MODIFY:
                        // Modify
                        final boolean stickyScript = se.getActionArg(0)
                                .getBoolean();
                        final String modObjName = se.getActionArg(1)
                                .getString();
                        final int modX = se.getActionArg(2).getInteger();
                        final int modY = se.getActionArg(3).getInteger();
                        final int modZ = se.getActionArg(4).getInteger();
                        final int modL = se.getActionArg(5).getInteger();
                        final MapObject modObj = DungeonDiverII.getApplication()
                                .getObjects().getNewInstanceByName(modObjName);
                        if (stickyScript) {
                            final MapObject wasThere = DungeonDiverII
                                    .getApplication().getVariablesManager()
                                    .getMap().getCell(modX, modY, modZ, modL);
                            if (wasThere.hasCustomScript()) {
                                modObj.setCustomScript(
                                        wasThere.getCustomScript());
                            }
                        }
                        DungeonDiverII.getApplication().getVariablesManager()
                                .getMap()
                                .setCell(modObj, modX, modY, modZ, modL);
                        break;
                    case DELETE_SCRIPT:
                        // Delete Script
                        gm.getSavedMapObject().setCustomScript(null);
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
                        DungeonDiverII.getApplication().getGameManager()
                                .hideOutput();
                        // Battle
                        final Battle battle = new Battle();
                        new Thread("Battle") {
                            @Override
                            public void run() {
                                try {
                                    DungeonDiverII.getApplication().getBattle()
                                            .doFixedBattle(DungeonDiverII
                                                    .getApplication()
                                                    .getGameManager()
                                                    .getTemporaryBattleCopy(),
                                                    battle);
                                } catch (final Exception e) {
                                    // Something went wrong in the battle
                                    DungeonDiverII.getErrorLogger().logError(e);
                                }
                            }
                        }.start();
                        break;
                    case DECAY:
                        DungeonDiverII.getApplication().getGameManager()
                                .decay();
                        break;
                    case LEVEL_CHANGE:
                        final int destLevel = se.getActionArg(0).getInteger();
                        DungeonDiverII.getApplication().getGameManager()
                                .goToLevel(destLevel);
                        break;
                    case RELATIVE_LEVEL_CHANGE:
                        final int rDestLevel = se.getActionArg(0).getInteger();
                        DungeonDiverII.getApplication().getGameManager()
                                .goToLevelRelative(rDestLevel);
                        break;
                    case ADD_TO_SCORE:
                        final int points = se.getActionArg(0).getInteger();
                        DungeonDiverII.getApplication().getGameManager()
                                .addToScore(points);
                        break;
                    case SWAP_PAIRS:
                        final String swap1 = se.getActionArg(0).getString();
                        final String swap2 = se.getActionArg(1).getString();
                        final MapObject swapObj1 = DungeonDiverII
                                .getApplication().getObjects()
                                .getNewInstanceByName(swap1);
                        final MapObject swapObj2 = DungeonDiverII
                                .getApplication().getObjects()
                                .getNewInstanceByName(swap2);
                        DungeonDiverII.getApplication().getVariablesManager()
                                .getMap()
                                .findAllObjectPairsAndSwap(swapObj1, swapObj2);
                        break;
                    case REDRAW:
                        DungeonDiverII.getApplication().getGameManager()
                                .redrawMapNoRebuild();
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Illegal Action Code: " + code.toString());
                    }
                }
            }
        } catch (final Exception e) {
            final int px = DungeonDiverII.getApplication().getGameManager()
                    .getPlayerManager().getPlayerLocationX() + 1;
            final int py = DungeonDiverII.getApplication().getGameManager()
                    .getPlayerManager().getPlayerLocationY() + 1;
            final int pz = DungeonDiverII.getApplication().getGameManager()
                    .getPlayerManager().getPlayerLocationZ() + 1;
            final int pw = DungeonDiverII.getApplication().getGameManager()
                    .getPlayerManager().getPlayerLocationW() + 1;
            final String beginMsg = "Buggy Game Script at row " + px
                    + ", column " + py + ", floor " + pz + ", level " + pw
                    + ", action #" + actionCounter + ": ";
            final String endMsg = e.getMessage();
            final String scriptMsg = beginMsg + endMsg;
            DungeonDiverII.getNonFatalLogger()
                    .logNonFatalError(new GameScriptException(scriptMsg, e));
        }
    }

    private static void validateScriptEntry(final GameScriptEntry se) {
        final GameActionCode code = se.getActionCode();
        final int rargc = GameScriptConstants.ARGUMENT_COUNT_VALIDATION[code
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
        final Class<?>[] rargt = GameScriptConstants.ARGUMENT_TYPE_VALIDATION[code
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
