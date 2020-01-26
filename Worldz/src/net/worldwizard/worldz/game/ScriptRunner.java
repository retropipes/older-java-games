package net.worldwizard.worldz.game;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.worldz.Messager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.items.Shop;
import net.worldwizard.worldz.resourcemanagers.SoundManager;
import net.worldwizard.worldz.scripts.ActionCode;
import net.worldwizard.worldz.scripts.Script;
import net.worldwizard.worldz.scripts.ScriptEntry;

public final class ScriptRunner {
    private ScriptRunner() {
        // Do nothing
    }

    public static void runScript(final Script s) {
        int actionCounter = 0;
        try {
            final GameManager gm = Worldz.getApplication().getGameManager();
            if (s != null) {
                final int totAct = s.getActionCount();
                for (int x = 0; x < totAct; x++) {
                    actionCounter = x + 1;
                    final ScriptEntry se = s.getAction(x);
                    ScriptRunner.validateScriptEntry(se);
                    final ActionCode code = se.getActionCode();
                    switch (code) {
                    case NONE:
                        // Do nothing
                        break;
                    case MESSAGE:
                        // Show the message
                        final String msg = se.getFirstActionArg().getString();
                        Messager.showMessage(msg);
                        break;
                    case SOUND:
                        // Play the sound
                        final String snd = se.getFirstActionArg().getString();
                        SoundManager.playSound(snd);
                        break;
                    case SHOP:
                        // Show the shop
                        final int shopType = se.getFirstActionArg()
                                .getInteger();
                        final Shop shop = Worldz.getApplication()
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
                        final int moveW = se.getActionArg(5).getInteger();
                        if (moveType == ScriptConstants.MOVE_RELATIVE) {
                            if (eventFlag) {
                                gm.updatePositionRelative(moveX, moveY);
                            } else {
                                gm.updatePositionRelativeNoEvents(moveX, moveY);
                            }
                        } else {
                            if (eventFlag) {
                                gm.updatePositionAbsolute(moveX, moveY, moveZ);
                            } else {
                                gm.updatePositionAbsoluteNoEvents(moveX, moveY,
                                        moveZ, moveW);
                            }
                        }
                        break;
                    case END_GAME:
                        // End Game
                        gm.solvedWorld();
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
                        final WorldObject modObj = Worldz.getApplication()
                                .getObjects().getNewInstanceByName(modObjName);
                        if (stickyScript) {
                            final WorldObject wasThere = Worldz.getApplication()
                                    .getWorldManager().getWorld()
                                    .getCell(modX, modY, modZ, modL);
                            if (wasThere.hasCustomScript()) {
                                modObj.setCustomScript(
                                        wasThere.getCustomScript());
                            }
                        }
                        Worldz.getApplication().getWorldManager().getWorld()
                                .setCell(modObj, modX, modY, modZ, modL);
                        break;
                    case DELETE_SCRIPT:
                        // Delete Script
                        gm.getSavedWorldObject().setCustomScript(null);
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
                        // Battle
                        new Thread("Battle") {
                            @Override
                            public void run() {
                                try {
                                    Worldz.getApplication().getBattle()
                                            .doBattle();
                                } catch (final Exception e) {
                                    // Something went wrong in the battle
                                    Worldz.getDebug().debug(e);
                                }
                            }
                        }.start();
                        break;
                    default:
                        throw new IllegalArgumentException(
                                "Illegal Action Code: " + code.toString());
                    }
                }
            }
        } catch (final Exception e) {
            final int px = Worldz.getApplication().getGameManager()
                    .getPlayerManager().getPlayerLocationX() + 1;
            final int py = Worldz.getApplication().getGameManager()
                    .getPlayerManager().getPlayerLocationY() + 1;
            final int pz = Worldz.getApplication().getGameManager()
                    .getPlayerManager().getPlayerLocationZ() + 1;
            final int pw = Worldz.getApplication().getGameManager()
                    .getPlayerManager().getPlayerLocationW() + 1;
            final String beginMsg = "Buggy Script at row " + px + ", column "
                    + py + ", floor " + pz + ", level " + pw + ", action #"
                    + actionCounter + ": ";
            final String endMsg = e.getMessage();
            final String scriptMsg = beginMsg + endMsg;
            Worldz.getDebug().logError(new ScriptException(scriptMsg, e));
        }
    }

    private static void validateScriptEntry(final ScriptEntry se) {
        final ActionCode code = se.getActionCode();
        final int rargc = ScriptConstants.ARGUMENT_COUNT_VALIDATION[code
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
        final Class<?>[] rargt = ScriptConstants.ARGUMENT_TYPE_VALIDATION[code
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
