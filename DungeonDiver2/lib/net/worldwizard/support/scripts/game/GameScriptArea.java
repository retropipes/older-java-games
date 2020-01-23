package net.worldwizard.support.scripts.game;

import java.awt.Point;
import java.io.IOException;

import net.worldwizard.xio.XDataReader;
import net.worldwizard.xio.XDataWriter;

public class GameScriptArea extends GameScript {
    // Fields
    private Point upperLeft;
    private Point lowerRight;
    private String name;

    // Constructor
    private GameScriptArea(final GameScript gs) {
        super(gs);
        this.upperLeft = new Point(0, 0);
        this.lowerRight = new Point(0, 0);
        this.name = "Un-named Script Area";
    }

    public GameScriptArea() {
        super();
        this.upperLeft = new Point(0, 0);
        this.lowerRight = new Point(0, 0);
        this.name = "Un-named Script Area";
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

    public String getName() {
        return this.name;
    }

    public void setName(final String newName) {
        this.name = newName;
    }

    public static GameScriptArea read(final XDataReader reader)
            throws IOException {
        final GameScript gs = GameScript.read(reader);
        final GameScriptArea gsa = new GameScriptArea(gs);
        gsa.upperLeft.x = reader.readInt();
        gsa.upperLeft.y = reader.readInt();
        gsa.lowerRight.x = reader.readInt();
        gsa.lowerRight.y = reader.readInt();
        gsa.name = reader.readString();
        return gsa;
    }

    @Override
    public void write(final XDataWriter writer) throws IOException {
        super.write(writer);
        writer.writeInt(this.upperLeft.x);
        writer.writeInt(this.upperLeft.y);
        writer.writeInt(this.lowerRight.x);
        writer.writeInt(this.lowerRight.y);
        writer.writeString(this.name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + (this.lowerRight == null ? 0 : this.lowerRight.hashCode());
        result = prime * result
                + (this.name == null ? 0 : this.name.hashCode());
        result = prime * result
                + (this.upperLeft == null ? 0 : this.upperLeft.hashCode());
        return result;
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
        if (!(obj instanceof GameScriptArea)) {
            return false;
        }
        final GameScriptArea other = (GameScriptArea) obj;
        if (this.lowerRight == null) {
            if (other.lowerRight != null) {
                return false;
            }
        } else if (!this.lowerRight.equals(other.lowerRight)) {
            return false;
        }
        if (this.name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!this.name.equals(other.name)) {
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
