/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class HPort extends GenericPort {
    // Constructors
    public HPort() {
	super(new HPlug(), 'H');
    }
}