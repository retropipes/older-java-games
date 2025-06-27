/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class KPort extends GenericPort {
    // Constructors
    public KPort() {
        super(new KPlug(), 'K');
    }
}