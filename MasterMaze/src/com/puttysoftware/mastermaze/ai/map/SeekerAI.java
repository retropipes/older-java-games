/*  MasterMaze: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.ai.map;

import java.awt.Point;

import com.puttysoftware.randomrange.RandomRange;

class SeekerAI extends MapAIRoutine {
    // Fields
    private final RandomRange randMove;
    private int failedMoveAttempts;
    private int[] roundsRemaining;
    private static final int STUCK_THRESHOLD = 16;
    private static final int CAST_SPELL_CHANCE = 50;
    private static final int STEAL_CHANCE = 5;
    private static final int DRAIN_CHANCE = 20;
    private static final int SPELL_INDEX_HEAL = 1;
    private static final int HEAL_THRESHOLD = 50;

    // Constructor
    public SeekerAI() {
        super();
        this.randMove = new RandomRange(-1, 1);
        this.failedMoveAttempts = 0;
    }

    @Override
    public int getNextAction(final MapAIContext ac) {
        if (this.roundsRemaining == null) {
            this.roundsRemaining = new int[ac.getCharacter().getTemplate()
                    .getSpellBook().getSpellCount()];
        }
        if (this.spellCheck(ac)) {
            // Cast a spell
            return MapAIRoutine.ACTION_CAST_SPELL;
        } else {
            Point there = ac.isEnemyNearby();
            if (there != null) {
                if (SeekerAI.stealCheck(ac)) {
                    // Steal
                    return MapAIRoutine.ACTION_STEAL;
                } else if (SeekerAI.drainCheck(ac)) {
                    // Drain MP
                    return MapAIRoutine.ACTION_DRAIN;
                } else {
                    // Something hostile is nearby, so attack it
                    if (ac.getCharacter().getCurrentAT() > 0) {
                        this.moveX = there.x;
                        this.moveY = there.y;
                        return MapAIRoutine.ACTION_MOVE;
                    } else {
                        this.failedMoveAttempts = 0;
                        return MapAIRoutine.ACTION_END_TURN;
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
                                return MapAIRoutine.ACTION_END_TURN;
                            }
                            // Last move failed, try to move around object
                            final RandomRange randTurn = new RandomRange(0, 1);
                            final int rt = randTurn.generate();
                            if (rt == 0) {
                                there = SeekerAI.turnRight45(this.moveX,
                                        this.moveY);
                            } else {
                                there = SeekerAI.turnLeft45(this.moveX,
                                        this.moveY);
                            }
                            this.moveX = there.x;
                            this.moveY = there.y;
                        } else {
                            this.moveX = (int) Math.signum(there.x);
                            this.moveY = (int) Math.signum(there.y);
                        }
                        break;
                    }
                }
                if (ac.getCharacter().getCurrentAP() > 0) {
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
                    return MapAIRoutine.ACTION_MOVE;
                } else {
                    this.failedMoveAttempts = 0;
                    return MapAIRoutine.ACTION_END_TURN;
                }
            }
        }
    }

    private static Point turnRight45(final int x, final int y) {
        if (x == -1 && y == -1) {
            return new Point(-1, 0);
        } else if (x == -1 && y == 0) {
            return new Point(-1, -1);
        } else if (x == -1 && y == 1) {
            return new Point(-1, 0);
        } else if (x == 0 && y == -1) {
            return new Point(1, -1);
        } else if (x == 0 && y == 1) {
            return new Point(-1, 1);
        } else if (x == 1 && y == -1) {
            return new Point(1, 0);
        } else if (x == 1 && y == 0) {
            return new Point(1, 1);
        } else if (x == 1 && y == 1) {
            return new Point(0, 1);
        } else {
            return new Point(x, y);
        }
    }

    private static Point turnLeft45(final int x, final int y) {
        if (x == -1 && y == -1) {
            return new Point(-1, 0);
        } else if (x == -1 && y == 0) {
            return new Point(-1, 1);
        } else if (x == -1 && y == 1) {
            return new Point(0, 1);
        } else if (x == 0 && y == -1) {
            return new Point(-1, -1);
        } else if (x == 0 && y == 1) {
            return new Point(1, 1);
        } else if (x == 1 && y == -1) {
            return new Point(0, -1);
        } else if (x == 1 && y == 0) {
            return new Point(1, -1);
        } else if (x == 1 && y == 1) {
            return new Point(0, -1);
        } else {
            return new Point(x, y);
        }
    }

    private boolean spellCheck(final MapAIContext ac) {
        final RandomRange random = new RandomRange(1, 100);
        final int chance = random.generate();
        if (chance <= SeekerAI.CAST_SPELL_CHANCE) {
            final int maxIndex = SeekerAI.getMaxCastIndex(ac);
            if (maxIndex > -1) {
                if (ac.getCharacter().getCurrentSP() > 0) {
                    // Select a random spell to cast
                    final RandomRange randomSpell = new RandomRange(0,
                            maxIndex);
                    final int randomSpellID = randomSpell.generate();
                    if (randomSpellID == SeekerAI.SPELL_INDEX_HEAL) {
                        // Healing spell was selected - is healing needed?
                        if (ac.getCharacter().getTemplate()
                                .getCurrentHP() > ac.getCharacter()
                                        .getTemplate().getMaximumHP()
                                        * SeekerAI.HEAL_THRESHOLD / 100) {
                            // Do not need healing
                            return false;
                        }
                    }
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

    @Override
    public void newRoundHook() {
        // Decrement effect counters
        for (int z = 0; z < this.roundsRemaining.length; z++) {
            if (this.roundsRemaining[z] > 0) {
                this.roundsRemaining[z]--;
            }
        }
    }

    private static int getMaxCastIndex(final MapAIContext ac) {
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

    private static boolean stealCheck(final MapAIContext ac) {
        final RandomRange random = new RandomRange(1, 100);
        final int chance = random.generate();
        if (chance <= SeekerAI.STEAL_CHANCE) {
            if (ac.getCharacter().getCurrentAP() > 0) {
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

    private static boolean drainCheck(final MapAIContext ac) {
        final RandomRange random = new RandomRange(1, 100);
        final int chance = random.generate();
        if (chance <= SeekerAI.DRAIN_CHANCE) {
            if (ac.getCharacter().getCurrentAP() > 0) {
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
