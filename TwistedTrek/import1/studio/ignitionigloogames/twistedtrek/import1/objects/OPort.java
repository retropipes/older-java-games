/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class OPort extends GenericPort {
    // Constructors
    public OPort() {
        super(new OPlug(), 'O');
    }
}