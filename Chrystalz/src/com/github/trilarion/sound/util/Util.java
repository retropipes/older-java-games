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
package com.github.trilarion.sound.util;

/**
 *
 * @author
 */
public class Util {
    /**
     *
     * @param v
     * @return
     */
    public static int ilog(final int v1) {
        int v = v1;
        int ret = 0;
        while (v != 0) {
            ret++;
            v >>>= 1;
        }
        return ret;
    }

    /**
     *
     * @param v
     * @return
     */
    public static int ilog2(final int v1) {
        int v = v1;
        int ret = 0;
        while (v > 1) {
            ret++;
            v >>>= 1;
        }
        return ret;
    }

    /**
     *
     * @param v
     * @return
     */
    public static int icount(final int v1) {
        int v = v1;
        int ret = 0;
        while (v != 0) {
            ret += v & 1;
            v >>>= 1;
        }
        return ret;
    }
}
