/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericRandomInvisibleTeleport;

public class RandomInvisibleTeleport extends GenericRandomInvisibleTeleport {
    // Constructors
    public RandomInvisibleTeleport() {
        super(0, 0);
    }

    public RandomInvisibleTeleport(final int newRandomRangeY, final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX);
    }

    // Scriptability
    @Override
    public String getName() {
        return "Random Invisible Teleport";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Random Invisible Teleports";
    }

    @Override
    public byte getObjectID() {
        return (byte) 1;
    }

    @Override
    public String getDescription() {
        return "Random Invisible Teleports are both random and invisible.";
    }
}