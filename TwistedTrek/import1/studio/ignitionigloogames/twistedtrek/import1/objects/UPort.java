/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class UPort extends GenericPort {
    // Constructors
    public UPort() {
	super(new UPlug(), 'U');
    }
}