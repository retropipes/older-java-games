/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.scripts.internal;

import java.awt.Point;

public class InternalScriptArea extends InternalScript {
    // Fields
    private Point upperLeft;
    private Point lowerRight;

    // Constructor
    public InternalScriptArea() {
        super();
        this.upperLeft = new Point(0, 0);
        this.lowerRight = new Point(0, 0);
    }

    // Methods
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    public void setUpperLeft(final Point newUpper) {
        this.upperLeft = newUpper;
    }

    public Point getLowerRight() {
        return this.lowerRight;
    }

    public void setLowerRight(final Point newLower) {
        this.lowerRight = newLower;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result
                + (this.lowerRight == null ? 0 : this.lowerRight.hashCode());
        return prime * result
                + (this.upperLeft == null ? 0 : this.upperLeft.hashCode());
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof InternalScriptArea)) {
            return false;
        }
        final InternalScriptArea other = (InternalScriptArea) obj;
        if (this.lowerRight == null) {
            if (other.lowerRight != null) {
                return false;
            }
        } else if (!this.lowerRight.equals(other.lowerRight)) {
            return false;
        }
        if (this.upperLeft == null) {
            if (other.upperLeft != null) {
                return false;
            }
        } else if (!this.upperLeft.equals(other.upperLeft)) {
            return false;
        }
        return true;
    }
}
