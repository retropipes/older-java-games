package net.worldwizard.support.ai;

import net.worldwizard.randomnumbers.RandomRange;

public class SeekerAI extends AIRoutine {
    // Fields
    private final RandomRange randMove;
    private int failedMoveAttempts;
    private final int[] roundsRemaining;
    private static final int STUCK_THRESHOLD = 8;
    private static final int CAST_SPELL_CHANCE = 30;
    private static final int STEAL_CHANCE = 20;
    private static final int DRAIN_CHANCE = 10;

    // Constructor
    public SeekerAI() {
        super();
        this.randMove = new RandomRange(-1, 1);
        this.failedMoveAttempts = 0;
        this.roundsRemaining = new int[7];
    }

    @Override
    public int getNextAction(final AIContext ac) {
        if (this.spellCheck(ac)) {
            // Cast a spell
            return AIRoutine.ACTION_CAST_SPELL;
        } else {
            int[] there = ac.isEnemyNearby();
            if (there != null) {
                if (SeekerAI.stealCheck(ac)) {
                    // Steal
                    return AIRoutine.ACTION_STEAL;
                } else if (SeekerAI.drainCheck(ac)) {
                    // Drain MP
                    return AIRoutine.ACTION_DRAIN;
                } else {
                    // Something hostile is nearby, so attack it
                    if (ac.getCharacter().getAttacks() > 0) {
                        this.moveX = there[0];
                        this.moveY = there[1];
                        return AIRoutine.ACTION_MOVE;
                    } else {
                        this.failedMoveAttempts = 0;
                        return AIRoutine.ACTION_END_TURN;
                    }
                }
            } else {
                // Look further
                for (int x = 2; x <= 9; x++) {
                    there = ac.isEnemyNearby(x, x);
                    if (there != null) {
                        // Found something hostile, move towards it
                        if (this.lastResult == false) {
                            this.failedMoveAttempts++;
                            if (this.failedMoveAttempts >= SeekerAI.STUCK_THRESHOLD) {
                                // We're stuck!
                                this.failedMoveAttempts = 0;
                                return AIRoutine.ACTION_END_TURN;
                            }
                            // Last move failed, try to move around object
                            there = SeekerAI.turnRight45(this.moveX,
                                    this.moveY);
                            this.moveX = there[0];
                            this.moveY = there[1];
                        } else {
                            this.moveX = (int) Math.signum(there[0]);
                            this.moveY = (int) Math.signum(there[1]);
                        }
                        break;
                    }
                }
                if (ac.getCharacter().getAP() > 0) {
                    if (there == null) {
                        // Wander randomly
                        this.moveX = this.randMove.generate();
                        this.moveY = this.randMove.generate();
                        // Don't attack self
                        while (this.moveX == 0 && this.moveY == 0) {
                            this.moveX = this.randMove.generate();
                            this.moveY = this.randMove.generate();
                        }
                    }
                    return AIRoutine.ACTION_MOVE;
                } else {
                    this.failedMoveAttempts = 0;
                    return AIRoutine.ACTION_END_TURN;
                }
            }
        }
    }

    @Override
    public void newRoundHook() {
        // Decrement round counters
        for (int x = 0; x < this.roundsRemaining.length; x++) {
            if (this.roundsRemaining[x] > 0) {
                this.roundsRemaining[x]--;
            }
        }
    }

    private static int[] turnRight45(final int x, final int y) {
        if (x == -1 && y == -1) {
            return new int[] { -1, 0 };
        } else if (x == -1 && y == 0) {
            return new int[] { -1, -1 };
        } else if (x == -1 && y == 1) {
            return new int[] { -1, 0 };
        } else if (x == 0 && y == -1) {
            return new int[] { 1, -1 };
        } else if (x == 0 && y == 1) {
            return new int[] { -1, 1 };
        } else if (x == 1 && y == -1) {
            return new int[] { 1, 0 };
        } else if (x == 1 && y == 0) {
            return new int[] { 1, -1 };
        } else if (x == 1 && y == 1) {
            return new int[] { 0, -1 };
        } else {
            return new int[] { x, y };
        }
    }

    private boolean spellCheck(final AIContext ac) {
        final RandomRange random = new RandomRange(1, 100);
        final int chance = random.generate();
        if (chance <= SeekerAI.CAST_SPELL_CHANCE) {
            final int maxIndex = SeekerAI.getMaxCastIndex(ac);
            if (maxIndex > -1) {
                if (ac.getCharacter().getSpells() > 0) {
                    // Select a random spell to cast
                    final RandomRange randomSpell = new RandomRange(0,
                            maxIndex);
                    final int randomSpellID = randomSpell.generate();
                    if (this.roundsRemaining[randomSpellID] == 0) {
                        this.spell = ac.getCharacter().getTemplate()
                                .getSpellBook().getSpellByID(randomSpellID);
                        this.roundsRemaining[randomSpellID] = this.spell
                                .getEffect().getInitialRounds();
                        return true;
                    } else {
                        // Spell selected already active
                        return false;
                    }
                } else {
                    // Can't cast any more spells
                    return false;
                }
            } else {
                // Not enough MP to cast anything
                return false;
            }
        } else {
            // Not casting a spell
            return false;
        }
    }

    private static int getMaxCastIndex(final AIContext ac) {
        final int currMP = ac.getCharacter().getTemplate().getCurrentMP();
        final int[] allCosts = ac.getCharacter().getTemplate().getSpellBook()
                .getAllSpellCosts();
        int result = -1;
        if (currMP > 0) {
            for (int x = 0; x < allCosts.length; x++) {
                if (currMP >= allCosts[x]) {
                    result = x;
                }
            }
        }
        return result;
    }

    private static boolean stealCheck(final AIContext ac) {
        final RandomRange random = new RandomRange(1, 100);
        final int chance = random.generate();
        if (chance <= SeekerAI.STEAL_CHANCE) {
            if (ac.getCharacter().getAP() > 0) {
                return true;
            } else {
                // Can't steal any more times
                return false;
            }
        } else {
            // Not stealing
            return false;
        }
    }

    private static boolean drainCheck(final AIContext ac) {
        final RandomRange random = new RandomRange(1, 100);
        final int chance = random.generate();
        if (chance <= SeekerAI.DRAIN_CHANCE) {
            if (ac.getCharacter().getAP() > 0) {
                return true;
            } else {
                // Can't drain any more times
                return false;
            }
        } else {
            // Not draining
            return false;
        }
    }
}
