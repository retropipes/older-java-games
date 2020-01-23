/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.scenario;

public class ScenarioManager {
    // Fields
    private final NamesFileManager nfMgr;

    // Constructors
    public ScenarioManager() {
        this.nfMgr = new NamesFileManager();
    }

    // Methods
    public final NamesFileManager getNamesFileManager() {
        return this.nfMgr;
    }
}