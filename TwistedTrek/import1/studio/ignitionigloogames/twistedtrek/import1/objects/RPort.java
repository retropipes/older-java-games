/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class RPort extends GenericPort {
    // Constructors
    public RPort() {
        super(new RPlug(), 'R');
    }
}