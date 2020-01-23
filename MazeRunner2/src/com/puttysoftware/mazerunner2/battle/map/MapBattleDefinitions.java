/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.battle.map;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.mazerunner2.ai.map.MapAIContext;
import com.puttysoftware.mazerunner2.creatures.AbstractCreature;
import com.puttysoftware.mazerunner2.maze.Maze;
import com.puttysoftware.mazerunner2.maze.objects.BattleCharacter;
import com.puttysoftware.randomrange.RandomRange;

public class MapBattleDefinitions {
    // Fields
    private BattleCharacter activeCharacter;
    private final BattleCharacter[] battlers;
    private final MapAIContext[] aiContexts;
    private Maze battleMaze;
    private int battlerCount;
    private static final int MAX_BATTLERS = 100;

    // Constructors
    public MapBattleDefinitions() {
        this.battlers = new BattleCharacter[MapBattleDefinitions.MAX_BATTLERS];
        this.aiContexts = new MapAIContext[MapBattleDefinitions.MAX_BATTLERS];
        this.battlerCount = 0;
    }

    // Methods
    public BattleCharacter[] getBattlers() {
        return this.battlers;
    }

    public void resetBattlers() {
        for (int x = 0; x < this.battlers.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTemplate().isAlive()) {
                    this.battlers[x].activate();
                    this.battlers[x].resetAP();
                    this.battlers[x].resetAttacks();
                    this.battlers[x].resetSpells();
                    this.battlers[x].resetLocation();
                }
            }
        }
    }

    public void roundResetBattlers() {
        for (int x = 0; x < this.battlers.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTemplate().isAlive()) {
                    this.battlers[x].resetAP();
                    this.battlers[x].resetAttacks();
                    this.battlers[x].resetSpells();
                }
            }
        }
    }

    public boolean addBattler(BattleCharacter battler) {
        if (this.battlerCount < MapBattleDefinitions.MAX_BATTLERS) {
            this.battlers[this.battlerCount] = battler;
            this.battlerCount++;
            return true;
        } else {
            return false;
        }
    }

    public MapAIContext[] getBattlerAIContexts() {
        return this.aiContexts;
    }

    public BattleCharacter getActiveCharacter() {
        return this.activeCharacter;
    }

    public void setActiveCharacter(BattleCharacter bc) {
        this.activeCharacter = bc;
    }

    public Maze getBattleMaze() {
        return this.battleMaze;
    }

    public void setBattleMaze(Maze bMaze) {
        this.battleMaze = bMaze;
    }

    public AbstractCreature[] getAllFriendsOfTeam(int teamID) {
        AbstractCreature[] tempFriends = new AbstractCreature[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempFriends.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() == teamID) {
                    tempFriends[x] = this.battlers[x].getTemplate();
                    nnc++;
                }
            }
        }
        AbstractCreature[] friends = new AbstractCreature[nnc];
        nnc = 0;
        for (int x = 0; x < tempFriends.length; x++) {
            if (tempFriends[x] != null) {
                friends[nnc] = tempFriends[x];
                nnc++;
            }
        }
        return friends;
    }

    public AbstractCreature[] getAllEnemiesOfTeam(int teamID) {
        AbstractCreature[] tempEnemies = new AbstractCreature[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempEnemies.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() != teamID) {
                    tempEnemies[x] = this.battlers[x].getTemplate();
                    nnc++;
                }
            }
        }
        AbstractCreature[] enemies = new AbstractCreature[nnc];
        nnc = 0;
        for (int x = 0; x < tempEnemies.length; x++) {
            if (tempEnemies[x] != null) {
                enemies[nnc] = tempEnemies[x];
                nnc++;
            }
        }
        return enemies;
    }

    private String[] buildNameListOfTeamFriends(int teamID) {
        String[] tempNames = new String[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempNames.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() == teamID
                        && this.battlers[x].isActive()) {
                    tempNames[x] = this.battlers[x].getName();
                    nnc++;
                }
            }
        }
        String[] names = new String[nnc];
        nnc = 0;
        for (int x = 0; x < tempNames.length; x++) {
            if (tempNames[x] != null) {
                names[nnc] = tempNames[x];
                nnc++;
            }
        }
        return names;
    }

    private String[] buildNameListOfTeamEnemies(int teamID) {
        String[] tempNames = new String[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempNames.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() != teamID
                        && this.battlers[x].isActive()) {
                    tempNames[x] = this.battlers[x].getName();
                    nnc++;
                }
            }
        }
        String[] names = new String[nnc];
        nnc = 0;
        for (int x = 0; x < tempNames.length; x++) {
            if (tempNames[x] != null) {
                names[nnc] = tempNames[x];
                nnc++;
            }
        }
        return names;
    }

    public AbstractCreature pickOneFriendOfTeam(int teamID) {
        String[] pickNames = this.buildNameListOfTeamFriends(teamID);
        return this.pickFriendOfTeamInternal(pickNames, 1, 1);
    }

    public AbstractCreature pickOneFriendOfTeamRandomly(int teamID) {
        String[] pickNames = this.buildNameListOfTeamFriends(teamID);
        if (pickNames.length == 0) {
            return null;
        }
        RandomRange r = new RandomRange(0, pickNames.length - 1);
        int index = r.generate();
        if (index < 0) {
            return null;
        }
        int res = this.findBattler(pickNames[index]);
        if (res != -1) {
            return this.battlers[res].getTemplate();
        } else {
            return null;
        }
    }

    private AbstractCreature pickFriendOfTeamInternal(String[] pickNames,
            int current, int number) {
        String text;
        if (number > 1) {
            text = "Pick " + number + " Friends";
        } else {
            text = "Pick 1 Friend";
        }
        String response = CommonDialogs.showInputDialog(text + " - " + current
                + " of " + number, "Battle", pickNames, pickNames[0]);
        if (response != null) {
            int loc = this.findBattler(response);
            if (loc != -1) {
                return this.battlers[loc].getTemplate();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public AbstractCreature pickOneEnemyOfTeam(int teamID) {
        String[] pickNames = this.buildNameListOfTeamEnemies(teamID);
        return this.pickEnemyOfTeamInternal(pickNames, 1, 1);
    }

    public AbstractCreature pickOneEnemyOfTeamRandomly(int teamID) {
        String[] pickNames = this.buildNameListOfTeamEnemies(teamID);
        if (pickNames.length == 0) {
            return null;
        }
        RandomRange r = new RandomRange(0, pickNames.length - 1);
        int index = r.generate();
        if (index < 0) {
            return null;
        }
        int res = this.findBattler(pickNames[index]);
        if (res != -1) {
            return this.battlers[res].getTemplate();
        } else {
            return null;
        }
    }

    private AbstractCreature pickEnemyOfTeamInternal(String[] pickNames,
            int current, int number) {
        String text;
        if (number > 1) {
            text = "Pick " + number + " Enemies";
        } else {
            text = "Pick 1 Enemy";
        }
        String response = CommonDialogs.showInputDialog(text + " - " + current
                + " of " + number, "Battle", pickNames, pickNames[0]);
        if (response != null) {
            int loc = this.findBattler(response);
            if (loc != -1) {
                return this.battlers[loc].getTemplate();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public int findBattler(String name) {
        return this.findBattler(name, 0, this.battlers.length);
    }

    private int findBattler(String name, int start, int limit) {
        for (int x = start; x < limit; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getName().equals(name)) {
                    return x;
                }
            }
        }
        return -1;
    }

    public AbstractCreature getSelfTarget() {
        return this.activeCharacter.getTemplate();
    }
}
