/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class FPort extends GenericPort {
    // Constructors
    public FPort() {
        super(new FPlug(), 'F');
    }
}