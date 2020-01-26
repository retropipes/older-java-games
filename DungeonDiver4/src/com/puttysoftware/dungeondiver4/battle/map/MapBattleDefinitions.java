/*  DungeonDiver4: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.battle.map;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver4.ai.map.MapAIContext;
import com.puttysoftware.dungeondiver4.creatures.AbstractCreature;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.objects.BattleCharacter;
import com.puttysoftware.randomrange.RandomRange;

public class MapBattleDefinitions {
    // Fields
    private BattleCharacter activeCharacter;
    private final BattleCharacter[] battlers;
    private final MapAIContext[] aiContexts;
    private Dungeon battleDungeon;
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
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                if (battler.getTemplate().isAlive()) {
                    battler.activate();
                    battler.resetAP();
                    battler.resetAttacks();
                    battler.resetSpells();
                    battler.resetLocation();
                }
            }
        }
    }

    public void roundResetBattlers() {
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                if (battler.getTemplate().isAlive()) {
                    battler.resetAP();
                    battler.resetAttacks();
                    battler.resetSpells();
                }
            }
        }
    }

    public boolean addBattler(final BattleCharacter battler) {
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

    public void setActiveCharacter(final BattleCharacter bc) {
        this.activeCharacter = bc;
    }

    public Dungeon getBattleDungeon() {
        return this.battleDungeon;
    }

    public void setBattleDungeon(final Dungeon bDungeon) {
        this.battleDungeon = bDungeon;
    }

    public AbstractCreature[] getAllFriendsOfTeam(final int teamID) {
        final AbstractCreature[] tempFriends = new AbstractCreature[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempFriends.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() == teamID) {
                    tempFriends[x] = this.battlers[x].getTemplate();
                    nnc++;
                }
            }
        }
        final AbstractCreature[] friends = new AbstractCreature[nnc];
        nnc = 0;
        for (final AbstractCreature tempFriend : tempFriends) {
            if (tempFriend != null) {
                friends[nnc] = tempFriend;
                nnc++;
            }
        }
        return friends;
    }

    public AbstractCreature[] getAllEnemiesOfTeam(final int teamID) {
        final AbstractCreature[] tempEnemies = new AbstractCreature[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempEnemies.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() != teamID) {
                    tempEnemies[x] = this.battlers[x].getTemplate();
                    nnc++;
                }
            }
        }
        final AbstractCreature[] enemies = new AbstractCreature[nnc];
        nnc = 0;
        for (final AbstractCreature tempEnemie : tempEnemies) {
            if (tempEnemie != null) {
                enemies[nnc] = tempEnemie;
                nnc++;
            }
        }
        return enemies;
    }

    private String[] buildNameListOfTeamFriends(final int teamID) {
        final String[] tempNames = new String[this.battlers.length];
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
        final String[] names = new String[nnc];
        nnc = 0;
        for (final String tempName : tempNames) {
            if (tempName != null) {
                names[nnc] = tempName;
                nnc++;
            }
        }
        return names;
    }

    private String[] buildNameListOfTeamEnemies(final int teamID) {
        final String[] tempNames = new String[this.battlers.length];
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
        final String[] names = new String[nnc];
        nnc = 0;
        for (final String tempName : tempNames) {
            if (tempName != null) {
                names[nnc] = tempName;
                nnc++;
            }
        }
        return names;
    }

    public AbstractCreature pickOneFriendOfTeam(final int teamID) {
        final String[] pickNames = this.buildNameListOfTeamFriends(teamID);
        return this.pickFriendOfTeamInternal(pickNames, 1, 1);
    }

    public AbstractCreature pickOneFriendOfTeamRandomly(final int teamID) {
        final String[] pickNames = this.buildNameListOfTeamFriends(teamID);
        if (pickNames.length == 0) {
            return null;
        }
        final RandomRange r = new RandomRange(0, pickNames.length - 1);
        final int index = r.generate();
        if (index < 0) {
            return null;
        }
        final int res = this.findBattler(pickNames[index]);
        if (res != -1) {
            return this.battlers[res].getTemplate();
        } else {
            return null;
        }
    }

    private AbstractCreature pickFriendOfTeamInternal(final String[] pickNames,
            final int current, final int number) {
        String text;
        if (number > 1) {
            text = "Pick " + number + " Friends";
        } else {
            text = "Pick 1 Friend";
        }
        final String response = CommonDialogs.showInputDialog(
                text + " - " + current + " of " + number, "Battle", pickNames,
                pickNames[0]);
        if (response != null) {
            final int loc = this.findBattler(response);
            if (loc != -1) {
                return this.battlers[loc].getTemplate();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public AbstractCreature pickOneEnemyOfTeam(final int teamID) {
        final String[] pickNames = this.buildNameListOfTeamEnemies(teamID);
        return this.pickEnemyOfTeamInternal(pickNames, 1, 1);
    }

    public AbstractCreature pickOneEnemyOfTeamRandomly(final int teamID) {
        final String[] pickNames = this.buildNameListOfTeamEnemies(teamID);
        if (pickNames.length == 0) {
            return null;
        }
        final RandomRange r = new RandomRange(0, pickNames.length - 1);
        final int index = r.generate();
        if (index < 0) {
            return null;
        }
        final int res = this.findBattler(pickNames[index]);
        if (res != -1) {
            return this.battlers[res].getTemplate();
        } else {
            return null;
        }
    }

    private AbstractCreature pickEnemyOfTeamInternal(final String[] pickNames,
            final int current, final int number) {
        String text;
        if (number > 1) {
            text = "Pick " + number + " Enemies";
        } else {
            text = "Pick 1 Enemy";
        }
        final String response = CommonDialogs.showInputDialog(
                text + " - " + current + " of " + number, "Battle", pickNames,
                pickNames[0]);
        if (response != null) {
            final int loc = this.findBattler(response);
            if (loc != -1) {
                return this.battlers[loc].getTemplate();
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public int findBattler(final String name) {
        return this.findBattler(name, 0, this.battlers.length);
    }

    private int findBattler(final String name, final int start,
            final int limit) {
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
