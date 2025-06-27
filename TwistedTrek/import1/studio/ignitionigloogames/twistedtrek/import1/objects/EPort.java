/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class EPort extends GenericPort {
    // Constructors
    public EPort() {
        super(new EPlug(), 'E');
    }
}