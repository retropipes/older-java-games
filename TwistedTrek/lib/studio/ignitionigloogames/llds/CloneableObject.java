/*  LLDS: Arbitrary dimension arrays for Java programs
Licensed under Apache 2.0. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/wrldwzrd89/lib-java-low-level-data-storage
 */
package studio.ignitionigloogames.llds;

public class CloneableObject implements Cloneable {
    // Constructor
    public CloneableObject() {
        super();
    }

    // Method
    @Override
    public CloneableObject clone() {
        try {
            return (CloneableObject) super.clone();
        } catch (final CloneNotSupportedException e) {
            // Should not ever get here
            return null;
        }
    }
}
