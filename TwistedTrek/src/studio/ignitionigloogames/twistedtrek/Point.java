/* Twisted Trek: A Dual-World Action RPG */
package studio.ignitionigloogames.twistedtrek;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {
    public int x;
    public int y;
    public int z;

    public Point(final int nx, final int ny, final int nz) {
	this.x = nx;
	this.y = ny;
	this.z = nz;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + this.x;
	result = prime * result + this.y;
	result = prime * result + this.z;
	return result;
    }

    @Override
    public boolean equals(final Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof Point)) {
	    return false;
	}
	final Point other = (Point) obj;
	if (this.x != other.x) {
	    return false;
	}
	if (this.y != other.y) {
	    return false;
	}
	if (this.z != other.z) {
	    return false;
	}
	return true;
    }

    public List<Point> neighbors8() {
	final List<Point> points = new ArrayList<>();
	for (int ox = -1; ox < 2; ox++) {
	    for (int oy = -1; oy < 2; oy++) {
		if (ox == 0 && oy == 0) {
		    continue;
		}
		points.add(new Point(this.x + ox, this.y + oy, this.z));
	    }
	}
	Collections.shuffle(points);
	return points;
    }
}
