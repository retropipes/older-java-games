/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class NPort extends GenericPort {
    // Constructors
    public NPort() {
        super(new NPlug(), 'N');
    }
}