/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class LPort extends GenericPort {
    // Constructors
    public LPort() {
	super(new LPlug(), 'L');
    }
}