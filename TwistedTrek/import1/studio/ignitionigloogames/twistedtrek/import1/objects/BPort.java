/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class BPort extends GenericPort {
    // Constructors
    public BPort() {
        super(new BPlug(), 'B');
    }
}