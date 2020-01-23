/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.creatures.genders;

public class Gender {
    private final int genderID;

    Gender(final int gid) {
        this.genderID = gid;
    }

    public int getGenderID() {
        return this.genderID;
    }
}
