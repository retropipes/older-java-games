/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class IPort extends GenericPort {
    // Constructors
    public IPort() {
        super(new IPlug(), 'I');
    }
}