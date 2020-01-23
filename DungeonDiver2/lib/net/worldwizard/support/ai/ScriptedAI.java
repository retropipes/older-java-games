package net.worldwizard.support.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.support.Support;
import net.worldwizard.support.creatures.StatConstants;

public class ScriptedAI extends AIRoutine {
    // Fields
    private final ArrayList<String> rawScript;
    private int counter;

    // Constructors
    public ScriptedAI(final File scriptFile) throws IOException {
        try (final BufferedReader reader = new BufferedReader(new FileReader(
                scriptFile))) {
            this.rawScript = new ArrayList<>();
            String line = "";
            while (line != null) {
                line = reader.readLine();
                if (line != null) {
                    this.rawScript.add(line);
                }
            }
        }
    }

    @Override
    public int getNextAction(final AIContext ac) {
        this.counter = 0;
        // Jump to main section
        this.counter = this.jumpToSection("main");
        if (this.counter == -1) {
            Support.getNonFatalLogger().logNonFatalError(
                    new RuntimeException(
                            "AI script section \"main\" not found!"));
            return AIRoutine.ACTION_END_TURN;
        }
        // Loop until done
        while (true) {
            try {
                final String command = this.rawScript.get(this.counter)
                        .toLowerCase();
                // Full command parsing
                if (command.equals(AIScriptConstants.COMMAND_MOVE)) {
                    return AIRoutine.ACTION_MOVE;
                } else if (command.equals(AIScriptConstants.COMMAND_CAST_SPELL)) {
                    return AIRoutine.ACTION_CAST_SPELL;
                } else if (command.equals(AIScriptConstants.COMMAND_STEAL)) {
                    return AIRoutine.ACTION_STEAL;
                } else if (command.equals(AIScriptConstants.COMMAND_DRAIN)) {
                    return AIRoutine.ACTION_DRAIN;
                } else if (command.equals(AIScriptConstants.COMMAND_USE_ITEM)) {
                    return AIRoutine.ACTION_USE_ITEM;
                } else if (command.equals(AIScriptConstants.COMMAND_END_TURN)) {
                    return AIRoutine.ACTION_END_TURN;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_TURN_45_RIGHT)) {
                    this.turn45Right();
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_TURN_45_LEFT)) {
                    this.turn45Left();
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_TURN_90_RIGHT)) {
                    this.turn90Right();
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_TURN_90_LEFT)) {
                    this.turn90Left();
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_TURN_180)) {
                    this.turn180();
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_TURN_RANDOMLY)) {
                    this.turnRandomly();
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_SELECT_SPELL_POISON)) {
                    this.selectSpellPoison(ac);
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_SELECT_SPELL_HEAL)) {
                    this.selectSpellHeal(ac);
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_SELECT_SPELL_WEAPON_DRAIN)) {
                    this.selectSpellWeaponDrain(ac);
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_SELECT_SPELL_ARMOR_DRAIN)) {
                    this.selectSpellArmorDrain(ac);
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_SELECT_SPELL_WEAPON_CHARGE)) {
                    this.selectSpellWeaponCharge(ac);
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_SELECT_SPELL_ARMOR_CHARGE)) {
                    this.selectSpellArmorCharge(ac);
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_SELECT_SPELL_ATTACK_LOCK)) {
                    this.selectSpellAttackLock(ac);
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_RESET_LAST_RESULT)) {
                    this.setLastResult(true);
                    this.counter++;
                } else if (command
                        .equals(AIScriptConstants.META_COMMAND_ATTACK)) {
                    return AIRoutine.ACTION_MOVE;
                } else if (command
                        .equals(AIScriptConstants.TEST_POSITIVE_LAST_RESULT)) {
                    if (this.lastResult) {
                        this.counter += 1;
                    } else {
                        this.counter += 2;
                    }
                } else if (command
                        .equals(AIScriptConstants.TEST_NEGATIVE_LAST_RESULT)) {
                    if (!this.lastResult) {
                        this.counter += 1;
                    } else {
                        this.counter += 2;
                    }
                } else {
                    // Partial command parsing
                    String testJump = "";
                    try {
                        testJump = command.substring(0,
                                AIScriptConstants.JUMP_TO.length());
                    } catch (final StringIndexOutOfBoundsException sioob) {
                        // Ignore
                    }
                    String testNegative = "";
                    try {
                        testNegative = command.substring(0,
                                AIScriptConstants.TEST_NEGATIVE_COMMAND_PREFIX
                                        .length());
                    } catch (final StringIndexOutOfBoundsException sioob) {
                        // Ignore
                    }
                    String testPositive = "";
                    try {
                        testPositive = command.substring(0,
                                AIScriptConstants.TEST_POSITIVE_COMMAND_PREFIX
                                        .length());
                    } catch (final StringIndexOutOfBoundsException sioob) {
                        // Ignore
                    }
                    String testScan = "";
                    try {
                        testScan = command.substring(0,
                                AIScriptConstants.META_COMMAND_SCAN_RADIUS
                                        .length());
                    } catch (final StringIndexOutOfBoundsException sioob) {
                        // Ignore
                    }
                    if (testJump.equals(AIScriptConstants.JUMP_TO)) {
                        final String sectionName = command
                                .substring(AIScriptConstants.JUMP_TO.length());
                        this.counter = this.jumpToSection(sectionName);
                        if (this.counter == -1) {
                            Support.getNonFatalLogger().logNonFatalError(
                                    new RuntimeException("AI script section \""
                                            + sectionName + "\" not found!"));
                            return AIRoutine.ACTION_END_TURN;
                        }
                    } else if (testNegative
                            .equals(AIScriptConstants.TEST_NEGATIVE_COMMAND_PREFIX)) {
                        final String testingWhat = command
                                .substring(AIScriptConstants.TEST_NEGATIVE_COMMAND_PREFIX
                                        .length());
                        boolean testResult = true;
                        if (testingWhat.equals(AIScriptConstants.COMMAND_MOVE)) {
                            testResult = !ScriptedAI.testMove(ac);
                        } else if (testingWhat
                                .equals(AIScriptConstants.COMMAND_CAST_SPELL)) {
                            testResult = !this.testSpell(ac);
                        } else if (testingWhat
                                .equals(AIScriptConstants.COMMAND_STEAL)) {
                            testResult = !ScriptedAI.testSteal(ac);
                        } else if (testingWhat
                                .equals(AIScriptConstants.COMMAND_DRAIN)) {
                            testResult = !ScriptedAI.testDrain(ac);
                        } else if (testingWhat
                                .equals(AIScriptConstants.COMMAND_USE_ITEM)) {
                            testResult = !ScriptedAI.testItem(ac);
                        } else if (testingWhat
                                .equals(AIScriptConstants.COMMAND_END_TURN)) {
                            testResult = !ScriptedAI.testEndTurn();
                        } else if (testingWhat
                                .equals(AIScriptConstants.META_COMMAND_ATTACK)) {
                            testResult = !ScriptedAI.testAttack(ac);
                        } else {
                            Support.getNonFatalLogger().logNonFatalError(
                                    new RuntimeException(
                                            "Unknown AI script command found: "
                                                    + command));
                            return AIRoutine.ACTION_END_TURN;
                        }
                        if (testResult) {
                            this.counter += 1;
                        } else {
                            this.counter += 2;
                        }
                    } else if (testPositive
                            .equals(AIScriptConstants.TEST_POSITIVE_COMMAND_PREFIX)) {
                        final String testingWhat = command
                                .substring(AIScriptConstants.TEST_POSITIVE_COMMAND_PREFIX
                                        .length());
                        boolean testResult = true;
                        if (testingWhat.equals(AIScriptConstants.COMMAND_MOVE)) {
                            testResult = ScriptedAI.testMove(ac);
                        } else if (testingWhat
                                .equals(AIScriptConstants.COMMAND_CAST_SPELL)) {
                            testResult = this.testSpell(ac);
                        } else if (testingWhat
                                .equals(AIScriptConstants.COMMAND_STEAL)) {
                            testResult = ScriptedAI.testSteal(ac);
                        } else if (testingWhat
                                .equals(AIScriptConstants.COMMAND_DRAIN)) {
                            testResult = ScriptedAI.testDrain(ac);
                        } else if (testingWhat
                                .equals(AIScriptConstants.COMMAND_USE_ITEM)) {
                            testResult = ScriptedAI.testItem(ac);
                        } else if (testingWhat
                                .equals(AIScriptConstants.COMMAND_END_TURN)) {
                            testResult = ScriptedAI.testEndTurn();
                        } else if (testingWhat
                                .equals(AIScriptConstants.META_COMMAND_ATTACK)) {
                            testResult = ScriptedAI.testAttack(ac);
                        } else {
                            Support.getNonFatalLogger().logNonFatalError(
                                    new RuntimeException(
                                            "Unknown AI script command found: "
                                                    + command));
                            return AIRoutine.ACTION_END_TURN;
                        }
                        if (testResult) {
                            this.counter += 1;
                        } else {
                            this.counter += 2;
                        }
                    } else if (testScan
                            .equals(AIScriptConstants.META_COMMAND_SCAN_RADIUS)) {
                        final String scanRadiusRaw = command
                                .substring(AIScriptConstants.META_COMMAND_SCAN_RADIUS
                                        .length());
                        int scanRadius = 1;
                        try {
                            scanRadius = Integer.parseInt(scanRadiusRaw);
                            if (scanRadius < 1 | scanRadius > 6) {
                                throw new NumberFormatException();
                            }
                        } catch (final NumberFormatException nfe) {
                            Support.getNonFatalLogger().logNonFatalError(
                                    new RuntimeException(
                                            "Malformed scan command found: "
                                                    + command));
                            return AIRoutine.ACTION_END_TURN;
                        }
                        final int[] scanResult = ac.isEnemyNearby(scanRadius,
                                scanRadius);
                        if (scanResult == null) {
                            this.setLastResult(false);
                        } else {
                            this.moveX = (int) Math.signum(scanResult[0]);
                            this.moveY = (int) Math.signum(scanResult[1]);
                            this.setLastResult(true);
                        }
                        this.counter++;
                    } else {
                        Support.getNonFatalLogger().logNonFatalError(
                                new RuntimeException(
                                        "Unknown AI script command found: "
                                                + command));
                        return AIRoutine.ACTION_END_TURN;
                    }
                }
            } catch (final RuntimeException re) {
                Support.getNonFatalLogger().logNonFatalError(re);
                return AIRoutine.ACTION_END_TURN;
            }
        }
    }

    private void turn45Right() {
        if (this.moveX == -1 && this.moveY == -1) {
            this.moveX = 0;
            this.moveY = -1;
        } else if (this.moveX == -1 && this.moveY == 0) {
            this.moveX = -1;
            this.moveY = -1;
        } else if (this.moveX == -1 && this.moveY == 1) {
            this.moveX = -1;
            this.moveY = 0;
        } else if (this.moveX == 0 && this.moveY == -1) {
            this.moveX = 1;
            this.moveY = -1;
        } else if (this.moveX == 0 && this.moveY == 1) {
            this.moveX = -1;
            this.moveY = 1;
        } else if (this.moveX == 1 && this.moveY == -1) {
            this.moveX = 1;
            this.moveY = 0;
        } else if (this.moveX == 1 && this.moveY == 0) {
            this.moveX = 1;
            this.moveY = 1;
        } else if (this.moveX == 1 && this.moveY == 1) {
            this.moveX = 0;
            this.moveY = 1;
        }
    }

    private void turn45Left() {
        if (this.moveX == -1 && this.moveY == -1) {
            this.moveX = -1;
            this.moveY = 0;
        } else if (this.moveX == -1 && this.moveY == 0) {
            this.moveX = -1;
            this.moveY = 1;
        } else if (this.moveX == -1 && this.moveY == 1) {
            this.moveX = 0;
            this.moveY = -1;
        } else if (this.moveX == 0 && this.moveY == -1) {
            this.moveX = -1;
            this.moveY = -1;
        } else if (this.moveX == 0 && this.moveY == 1) {
            this.moveX = 1;
            this.moveY = 1;
        } else if (this.moveX == 1 && this.moveY == -1) {
            this.moveX = 0;
            this.moveY = -1;
        } else if (this.moveX == 1 && this.moveY == 0) {
            this.moveX = 1;
            this.moveY = -1;
        } else if (this.moveX == 1 && this.moveY == 1) {
            this.moveX = 1;
            this.moveY = 0;
        }
    }

    private void turn90Right() {
        if (this.moveX == -1 && this.moveY == -1) {
            this.moveX = 1;
            this.moveY = -1;
        } else if (this.moveX == -1 && this.moveY == 0) {
            this.moveX = 0;
            this.moveY = -1;
        } else if (this.moveX == -1 && this.moveY == 1) {
            this.moveX = -1;
            this.moveY = -1;
        } else if (this.moveX == 0 && this.moveY == -1) {
            this.moveX = 1;
            this.moveY = 0;
        } else if (this.moveX == 0 && this.moveY == 1) {
            this.moveX = -1;
            this.moveY = 0;
        } else if (this.moveX == 1 && this.moveY == -1) {
            this.moveX = 1;
            this.moveY = -1;
        } else if (this.moveX == 1 && this.moveY == 0) {
            this.moveX = 0;
            this.moveY = 1;
        } else if (this.moveX == 1 && this.moveY == 1) {
            this.moveX = -1;
            this.moveY = 1;
        }
    }

    private void turn90Left() {
        if (this.moveX == -1 && this.moveY == -1) {
            this.moveX = -1;
            this.moveY = 1;
        } else if (this.moveX == -1 && this.moveY == 0) {
            this.moveX = 0;
            this.moveY = 1;
        } else if (this.moveX == -1 && this.moveY == 1) {
            this.moveX = 1;
            this.moveY = 1;
        } else if (this.moveX == 0 && this.moveY == -1) {
            this.moveX = -1;
            this.moveY = 0;
        } else if (this.moveX == 0 && this.moveY == 1) {
            this.moveX = 1;
            this.moveY = 0;
        } else if (this.moveX == 1 && this.moveY == -1) {
            this.moveX = -1;
            this.moveY = -1;
        } else if (this.moveX == 1 && this.moveY == 0) {
            this.moveX = 0;
            this.moveY = -1;
        } else if (this.moveX == 1 && this.moveY == 1) {
            this.moveX = 1;
            this.moveY = -1;
        }
    }

    private void turn180() {
        if (this.moveX == -1 && this.moveY == -1) {
            this.moveX = 1;
            this.moveY = 1;
        } else if (this.moveX == -1 && this.moveY == 0) {
            this.moveX = 1;
            this.moveY = 0;
        } else if (this.moveX == -1 && this.moveY == 1) {
            this.moveX = 1;
            this.moveY = -1;
        } else if (this.moveX == 0 && this.moveY == -1) {
            this.moveX = 0;
            this.moveY = 1;
        } else if (this.moveX == 0 && this.moveY == 1) {
            this.moveX = 0;
            this.moveY = -1;
        } else if (this.moveX == 1 && this.moveY == -1) {
            this.moveX = -1;
            this.moveY = 1;
        } else if (this.moveX == 1 && this.moveY == 0) {
            this.moveX = -1;
            this.moveY = 0;
        } else if (this.moveX == 1 && this.moveY == 1) {
            this.moveX = -1;
            this.moveY = -1;
        }
    }

    private void turnRandomly() {
        final RandomRange randX = new RandomRange(-1, 1);
        final RandomRange randY = new RandomRange(-1, 1);
        int x = randX.generate();
        int y = randY.generate();
        while (x == 0 && y == 0) {
            x = randX.generate();
            y = randY.generate();
        }
        this.moveX = x;
        this.moveY = y;
    }

    private void selectSpellPoison(final AIContext ac) {
        this.spell = ac.getCharacter().getTemplate().getSpellBook()
                .getSpellByID(0);
    }

    private void selectSpellHeal(final AIContext ac) {
        this.spell = ac.getCharacter().getTemplate().getSpellBook()
                .getSpellByID(1);
    }

    private void selectSpellWeaponDrain(final AIContext ac) {
        this.spell = ac.getCharacter().getTemplate().getSpellBook()
                .getSpellByID(2);
    }

    private void selectSpellArmorDrain(final AIContext ac) {
        this.spell = ac.getCharacter().getTemplate().getSpellBook()
                .getSpellByID(3);
    }

    private void selectSpellWeaponCharge(final AIContext ac) {
        this.spell = ac.getCharacter().getTemplate().getSpellBook()
                .getSpellByID(4);
    }

    private void selectSpellArmorCharge(final AIContext ac) {
        this.spell = ac.getCharacter().getTemplate().getSpellBook()
                .getSpellByID(5);
    }

    private void selectSpellAttackLock(final AIContext ac) {
        this.spell = ac.getCharacter().getTemplate().getSpellBook()
                .getSpellByID(6);
    }

    private static boolean testMove(final AIContext ac) {
        return ac.getCharacter().getAP() > 0;
    }

    private static boolean testAttack(final AIContext ac) {
        return ac.getCharacter().getAttacks() > 0;
    }

    private boolean testSpell(final AIContext ac) {
        return ac.getCharacter().getTemplate()
                .getStat(StatConstants.STAT_CURRENT_MP) >= this.spell.getCost()
                && ac.getCharacter().getSpells() > 0;
    }

    private static boolean testSteal(final AIContext ac) {
        return ac.getCharacter().getAP() > 0;
    }

    private static boolean testDrain(final AIContext ac) {
        return ac.getCharacter().getAP() > 0;
    }

    private static boolean testItem(final AIContext ac) {
        return ac.getCharacter().getAP() > 0;
    }

    private static boolean testEndTurn() {
        return true;
    }

    private int jumpToSection(final String sectionName) {
        for (int z = 0; z < this.rawScript.size(); z++) {
            final String data = this.rawScript.get(z);
            if (data.length() > 0) {
                final String data1 = data.substring(0, 1);
                if (data1.equals(AIScriptConstants.SECTION_START)) {
                    final String data2 = data.substring(1, data.length() - 1);
                    if (data2.equals(sectionName)) {
                        return z + 1;
                    }
                }
            }
        }
        return -1;
    }
}
