/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.game;

import com.puttysoftware.commondialogs.CommonDialogs;

class ScoreTracker {
    // Fields
    private long score;

    // Constructors
    ScoreTracker() {
        this.score = 0L;
    }

    // Methods
    void commitScore() {
        CommonDialogs.showDialog("Your final score: " + this.score + " points");
    }

    void addToScore(final long value) {
        this.score += value;
    }

    void showCurrentScore() {
        CommonDialogs
                .showDialog("Your current score: " + this.score + " points");
    }
}
