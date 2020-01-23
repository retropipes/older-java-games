/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPass;

public class EnergySphere extends GenericPass {
    // Constructors
    public EnergySphere() {
	super();
    }

    @Override
    public String getName() {
	return "Energy Sphere";
    }

    @Override
    public String getPluralName() {
	return "Energy Spheres";
    }

    @Override
    public byte getObjectID() {
	return (byte) 2;
    }

    @Override
    public String getDescription() {
	return "Energy Spheres permit walking on Force Fields.";
    }
}
