/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class ZPort extends GenericPort {
    // Constructors
    public ZPort() {
	super(new ZPlug(), 'Z');
    }
}