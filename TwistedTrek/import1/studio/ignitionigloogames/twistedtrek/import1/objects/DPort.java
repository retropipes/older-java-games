/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import studio.ignitionigloogames.twistedtrek.import1.generic.GenericPort;

public class DPort extends GenericPort {
    // Constructors
    public DPort() {
        super(new DPlug(), 'D');
    }
}