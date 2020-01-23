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
class Mdct {
    int n;
    int log2n;
    float[] trig;
    int[] bitrev;
    float scale;

    void init(final int n1) {
        this.bitrev = new int[n1 / 4];
        this.trig = new float[n1 + n1 / 4];
        this.log2n = (int) Math.rint(Math.log(n1) / Math.log(2));
        this.n = n1;
        final int AE = 0;
        final int AO = 1;
        final int BE = AE + n1 / 2;
        final int BO = BE + 1;
        final int CE = BE + n1 / 2;
        final int CO = CE + 1;
        // trig lookups...
        for (int i = 0; i < n1 / 4; i++) {
            this.trig[AE + i * 2] = (float) Math.cos(Math.PI / n1 * (4 * i));
            this.trig[AO + i * 2] = (float) -Math.sin(Math.PI / n1 * (4 * i));
            this.trig[BE + i * 2] = (float) Math
                    .cos(Math.PI / (2 * n1) * (2 * i + 1));
            this.trig[BO + i * 2] = (float) Math
                    .sin(Math.PI / (2 * n1) * (2 * i + 1));
        }
        for (int i = 0; i < n1 / 8; i++) {
            this.trig[CE + i * 2] = (float) Math.cos(Math.PI / n1 * (4 * i + 2));
            this.trig[CO
                    + i * 2] = (float) -Math.sin(Math.PI / n1 * (4 * i + 2));
        }
        {
            final int mask = (1 << this.log2n - 1) - 1;
            final int msb = 1 << this.log2n - 2;
            for (int i = 0; i < n1 / 8; i++) {
                int acc = 0;
                for (int j = 0; msb >>> j != 0; j++) {
                    if ((msb >>> j & i) != 0) {
                        acc |= 1 << j;
                    }
                }
                this.bitrev[i * 2] = ~acc & mask;
                // bitrev[i*2]=((~acc)&mask)-1;
                this.bitrev[i * 2 + 1] = acc;
            }
        }
        this.scale = 4.f / n1;
    }

    void clear() {
    }

    /**
     * 
     * @param in
     * @param out
     */
    void forward(final float[] in, final float[] out) {
    }

    float[] _x = new float[1024];
    float[] _w = new float[1024];

    synchronized void backward(final float[] in, final float[] out) {
        if (this._x.length < this.n / 2) {
            this._x = new float[this.n / 2];
        }
        if (this._w.length < this.n / 2) {
            this._w = new float[this.n / 2];
        }
        final float[] x = this._x;
        final float[] w = this._w;
        final int n2 = this.n >>> 1;
        final int n4 = this.n >>> 2;
        final int n8 = this.n >>> 3;
        // rotate + step 1
        {
            int inO = 1;
            int xO = 0;
            int A = n2;
            int i;
            for (i = 0; i < n8; i++) {
                A -= 2;
                x[xO++] = -in[inO + 2] * this.trig[A + 1]
                        - in[inO] * this.trig[A];
                x[xO++] = in[inO] * this.trig[A + 1]
                        - in[inO + 2] * this.trig[A];
                inO += 4;
            }
            inO = n2 - 4;
            for (i = 0; i < n8; i++) {
                A -= 2;
                x[xO++] = in[inO] * this.trig[A + 1]
                        + in[inO + 2] * this.trig[A];
                x[xO++] = in[inO] * this.trig[A]
                        - in[inO + 2] * this.trig[A + 1];
                inO -= 4;
            }
        }
        final float[] xxx = this.mdct_kernel(x, w, this.n, n2, n4, n8);
        int xx = 0;
        // step 8
        {
            int B = n2;
            int o1 = n4, o2 = o1 - 1;
            int o3 = n4 + n2, o4 = o3 - 1;
            for (int i = 0; i < n4; i++) {
                final float temp1 = xxx[xx] * this.trig[B + 1]
                        - xxx[xx + 1] * this.trig[B];
                final float temp2 = -(xxx[xx] * this.trig[B]
                        + xxx[xx + 1] * this.trig[B + 1]);
                out[o1] = -temp1;
                out[o2] = temp1;
                out[o3] = temp2;
                out[o4] = temp2;
                o1++;
                o2--;
                o3++;
                o4--;
                xx += 2;
                B += 2;
            }
        }
    }

    private float[] mdct_kernel(float[] nx, float[] nw, final int n1, final int n2,
            final int n4, final int n8) {
        float[] x = nx;
        float[] w = nw;
        // step 2
        int xA = n4;
        int xB = 0;
        int w2 = n4;
        int A = n2;
        for (int i = 0; i < n4;) {
            final float x0 = x[xA] - x[xB];
            float x1;
            w[w2 + i] = x[xA++] + x[xB++];
            x1 = x[xA] - x[xB];
            A -= 4;
            w[i++] = x0 * this.trig[A] + x1 * this.trig[A + 1];
            w[i] = x1 * this.trig[A] - x0 * this.trig[A + 1];
            w[w2 + i] = x[xA++] + x[xB++];
            i++;
        }
        // step 3
        {
            for (int i = 0; i < this.log2n - 3; i++) {
                int k0 = n1 >>> i + 2;
                final int k1 = 1 << i + 3;
                int wbase = n2 - 2;
                A = 0;
                float[] temp;
                for (int r = 0; r < k0 >>> 2; r++) {
                    int w1 = wbase;
                    w2 = w1 - (k0 >> 1);
                    final float AEv = this.trig[A];
                    float wA;
                    final float AOv = this.trig[A + 1];
                    float wB;
                    wbase -= 2;
                    k0++;
                    for (int s = 0; s < 2 << i; s++) {
                        wB = w[w1] - w[w2];
                        x[w1] = w[w1] + w[w2];
                        wA = w[++w1] - w[++w2];
                        x[w1] = w[w1] + w[w2];
                        x[w2] = wA * AEv - wB * AOv;
                        x[w2 - 1] = wB * AEv + wA * AOv;
                        w1 -= k0;
                        w2 -= k0;
                    }
                    k0--;
                    A += k1;
                }
                temp = w;
                w = x;
                x = temp;
            }
        }
        // step 4, 5, 6, 7
        {
            int C = n1;
            int bit = 0;
            int x1 = 0;
            int x2 = n2 - 1;
            for (int i = 0; i < n8; i++) {
                final int t1 = this.bitrev[bit++];
                final int t2 = this.bitrev[bit++];
                final float wA = w[t1] - w[t2 + 1];
                final float wB = w[t1 - 1] + w[t2];
                final float wC = w[t1] + w[t2 + 1];
                final float wD = w[t1 - 1] - w[t2];
                final float wACE = wA * this.trig[C];
                final float wBCE = wB * this.trig[C++];
                final float wACO = wA * this.trig[C];
                final float wBCO = wB * this.trig[C++];
                x[x1++] = (wC + wACO + wBCE) * .5f;
                x[x2--] = (-wD + wBCO - wACE) * .5f;
                x[x1++] = (wD + wBCO - wACE) * .5f;
                x[x2--] = (wC - wACO - wBCE) * .5f;
            }
        }
        return x;
    }
}
