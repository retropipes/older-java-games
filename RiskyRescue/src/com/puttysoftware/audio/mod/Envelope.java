package com.puttysoftware.audio.mod;

public class Envelope {
    public boolean enabled = false, sustain = false, looped = false;
    public int sustainTick = 0, loopStartTick = 0, loopEndTick = 0;
    public int numPoints = 1;
    public int[] pointsTick = new int[1];
    public int[] pointsAmpl = new int[1];

    public int nextTick(final int tick, final boolean keyOn) {
        int fixedTick = tick;
        fixedTick++;
        if (this.looped && fixedTick >= this.loopEndTick) {
            fixedTick = this.loopStartTick;
        }
        if (this.sustain && keyOn && fixedTick >= this.sustainTick) {
            fixedTick = this.sustainTick;
        }
        return fixedTick;
    }

    public int calculateAmpl(final int tick) {
        int ampl = this.pointsAmpl[this.numPoints - 1];
        if (tick < this.pointsTick[this.numPoints - 1]) {
            int point = 0;
            for (int idx = 1; idx < this.numPoints; idx++) {
                if (this.pointsTick[idx] <= tick) {
                    point = idx;
                }
            }
            final int dt = this.pointsTick[point + 1] - this.pointsTick[point];
            final int da = this.pointsAmpl[point + 1] - this.pointsAmpl[point];
            ampl = this.pointsAmpl[point];
            ampl += (da << 24) / dt * (tick - this.pointsTick[point]) >> 24;
        }
        return ampl;
    }
}
