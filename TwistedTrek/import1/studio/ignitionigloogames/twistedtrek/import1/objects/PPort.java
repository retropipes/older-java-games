/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class PPort extends GenericPort {
    // Constructors
    public PPort() {
	super(new PPlug(), 'P');
    }
}