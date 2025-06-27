/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class CPort extends GenericPort {
    // Constructors
    public CPort() {
        super(new CPlug(), 'C');
    }
}