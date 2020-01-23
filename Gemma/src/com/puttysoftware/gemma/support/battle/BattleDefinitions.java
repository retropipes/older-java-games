/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.battle;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.gemma.support.ai.AIContext;
import com.puttysoftware.gemma.support.creatures.Creature;
import com.puttysoftware.gemma.support.creatures.PrestigeConstants;
import com.puttysoftware.gemma.support.map.Map;
import com.puttysoftware.gemma.support.map.objects.BattleCharacter;
import com.puttysoftware.randomrange.RandomRange;

public class BattleDefinitions {
    // Fields
    private BattleCharacter activeCharacter;
    private final BattleCharacter[] battlers;
    private final AIContext[] aiContexts;
    private Map battleMap;
    private int battlerCount;
    private static final int MAX_BATTLERS = 10;

    // Constructors
    public BattleDefinitions() {
        this.battlers = new BattleCharacter[BattleDefinitions.MAX_BATTLERS];
        this.aiContexts = new AIContext[BattleDefinitions.MAX_BATTLERS];
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
                    this.battlers[x].resetItems();
                    this.battlers[x].resetSteals();
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
                    this.battlers[x].resetItems();
                    this.battlers[x].resetSteals();
                }
            }
        }
    }

    public boolean addBattler(BattleCharacter battler) {
        if (this.battlerCount < BattleDefinitions.MAX_BATTLERS) {
            this.battlers[this.battlerCount] = battler;
            this.battlerCount++;
            return true;
        } else {
            return false;
        }
    }

    public AIContext[] getBattlerAIContexts() {
        return this.aiContexts;
    }

    public BattleCharacter getActiveCharacter() {
        return this.activeCharacter;
    }

    public void setActiveCharacter(BattleCharacter bc) {
        this.activeCharacter = bc;
    }

    public Map getBattleMap() {
        return this.battleMap;
    }

    public void setBattleMap(Map bMap) {
        this.battleMap = bMap;
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

    public String getNameOfPartyEnemy() {
        String[] tempNames = new String[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempNames.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() != Creature.TEAM_PARTY) {
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
        return names[0];
    }

    public Creature pickOneFriendOfTeam(int teamID) {
        String[] pickNames = this.buildNameListOfTeamFriends(teamID);
        return this.pickFriendOfTeamInternal(pickNames, 1, 1);
    }

    public Creature pickOneFriendOfTeamRandomly(int teamID) {
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

    private Creature pickFriendOfTeamInternal(String[] pickNames, int current,
            int number) {
        String text;
        if (number > 1) {
            text = "Pick " + number + " Friends";
        } else {
            text = "Pick 1 Friend";
        }
        String response = CommonDialogs.showInputDialog(
                text + " - " + current + " of " + number, "Battle", pickNames,
                pickNames[0]);
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

    public Creature pickOneEnemyOfTeam(int teamID) {
        String[] pickNames = this.buildNameListOfTeamEnemies(teamID);
        return this.pickEnemyOfTeamInternal(pickNames, 1, 1);
    }

    public Creature pickOneEnemyOfTeamRandomly(int teamID) {
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

    private Creature pickEnemyOfTeamInternal(String[] pickNames, int current,
            int number) {
        String text;
        if (number > 1) {
            text = "Pick " + number + " Enemies";
        } else {
            text = "Pick 1 Enemy";
        }
        String response = CommonDialogs.showInputDialog(
                text + " - " + current + " of " + number, "Battle", pickNames,
                pickNames[0]);
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

    public Creature getSelfTarget() {
        return this.activeCharacter.getTemplate();
    }

    // Prestige Stuff
    public static void dealtDamage(Creature c, int value) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_GIVEN, value);
    }

    public static void tookDamage(Creature c, int value) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_TAKEN, value);
    }

    public static void hitEnemy(Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_HITS_GIVEN, 1);
    }

    public static void hitByEnemy(Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_HITS_TAKEN, 1);
    }

    public static void missedEnemy(Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_MISSED_ATTACKS, 1);
    }

    public static void killedEnemy(Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_MONSTERS_KILLED, 1);
    }

    public static void dodgedAttack(Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_ATTACKS_DODGED, 1);
    }

    public static void castSpell(Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_SPELLS_CAST, 1);
    }

    public static void killedInBattle(Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_KILLED, 1);
    }

    public static void ranAway(Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_RAN_AWAY, 1);
    }
}
