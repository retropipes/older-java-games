/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class APort extends GenericPort {
    // Constructors
    public APort() {
        super(new APlug(), 'A');
    }
}