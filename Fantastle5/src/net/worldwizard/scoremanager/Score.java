/*  Fantastle: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

Any questions should be directed to the author via email at: fantastle@worldwizard.net
 */
package net.worldwizard.scoremanager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public final class Score {
    // Fields
    private long score;
    private String name;

    // Constructors
    public Score() {
        this.score = 0L;
        this.name = "Nobody";
    }

    public Score(final long newScore, final String newName) {
        this.score = newScore;
        this.name = newName;
    }

    // Methods
    public long getScore() {
        return this.score;
    }

    public String getName() {
        return this.name;
    }

    public void setScore(final long newScore) {
        this.score = newScore;
    }

    public void setName(final String newName) {
        this.name = newName;
    }

    public static Score readScore(final BufferedReader stream)
            throws IOException {
        final Score s = new Score();
        s.name = stream.readLine();
        s.score = Long.parseLong(stream.readLine());
        return s;
    }

    public void writeScore(final BufferedWriter stream) throws IOException {
        stream.write(this.name + "\n");
        stream.write(Long.toString(this.score) + "\n");
    }
}
