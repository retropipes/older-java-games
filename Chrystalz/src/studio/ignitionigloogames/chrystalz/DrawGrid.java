/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz;

import studio.ignitionigloogames.common.images.BufferedImageIcon;
import studio.ignitionigloogames.common.llds.BasicLowLevelDataStore;

public class DrawGrid extends BasicLowLevelDataStore {
    public DrawGrid(final int numSquares) {
        super(numSquares, numSquares);
    }

    public BufferedImageIcon getImageCell(final int row, final int col) {
        return (BufferedImageIcon) this.getCell(row, col);
    }

    public void setImageCell(final BufferedImageIcon bii, final int row,
            final int col) {
        this.setCell(bii, row, col);
    }
}
