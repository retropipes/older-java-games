/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class MPort extends GenericPort {
    // Constructors
    public MPort() {
        super(new MPlug(), 'M');
    }
}