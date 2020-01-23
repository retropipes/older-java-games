/*
 * Copyright (C) 2000 ymnk<ymnk@jcraft.com>
 *               2015 Trilarion
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.trilarion.sound.vorbis.jcraft.jorbis;

/**
 *
 */
class PsyLook {
    int n;
    PsyInfo vi;
    float[][][] tonecurves;
    float[][] peakatt;
    float[][][] noisecurves;
    float[] ath;
    int[] octave;

    /**
     * 
     * @param vi1
     * @param n1
     * @param rate
     */
    void init(final PsyInfo vi1, final int n1, final int rate) {
    }
}
