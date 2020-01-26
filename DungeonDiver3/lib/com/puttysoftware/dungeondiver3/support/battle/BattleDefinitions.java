/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.battle;

import com.puttysoftware.commondialogs.CommonDialogs;
import com.puttysoftware.dungeondiver3.support.ai.AIContext;
import com.puttysoftware.dungeondiver3.support.creatures.Creature;
import com.puttysoftware.dungeondiver3.support.creatures.PrestigeConstants;
import com.puttysoftware.dungeondiver3.support.map.Map;
import com.puttysoftware.dungeondiver3.support.map.objects.BattleCharacter;
import com.puttysoftware.randomrange.RandomRange;

public class BattleDefinitions {
    // Fields
    private BattleCharacter activeCharacter;
    private final BattleCharacter[] battlers;
    private final AIContext[] aiContexts;
    private Map battleMap;
    private int battlerCount;
    private static final int MAX_BATTLERS = 100;

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

    public void setActiveCharacter(final BattleCharacter bc) {
        this.activeCharacter = bc;
    }

    public Map getBattleMap() {
        return this.battleMap;
    }

    public void setBattleMap(final Map bMap) {
        this.battleMap = bMap;
    }

    public Creature[] getAllFriendsOfTeam(final int teamID) {
        final Creature[] tempFriends = new Creature[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempFriends.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() == teamID) {
                    tempFriends[x] = this.battlers[x].getTemplate();
                    nnc++;
                }
            }
        }
        final Creature[] friends = new Creature[nnc];
        nnc = 0;
        for (final Creature tempFriend : tempFriends) {
            if (tempFriend != null) {
                friends[nnc] = tempFriend;
                nnc++;
            }
        }
        return friends;
    }

    public Creature[] getAllEnemiesOfTeam(final int teamID) {
        final Creature[] tempEnemies = new Creature[this.battlers.length];
        int nnc = 0;
        for (int x = 0; x < tempEnemies.length; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() != teamID) {
                    tempEnemies[x] = this.battlers[x].getTemplate();
                    nnc++;
                }
            }
        }
        final Creature[] enemies = new Creature[nnc];
        nnc = 0;
        for (final Creature tempEnemie : tempEnemies) {
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

    public Creature pickOneFriendOfTeam(final int teamID) {
        final String[] pickNames = this.buildNameListOfTeamFriends(teamID);
        return this.pickFriendOfTeamInternal(pickNames, 1, 1);
    }

    public Creature pickOneFriendOfTeamRandomly(final int teamID) {
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

    private Creature pickFriendOfTeamInternal(final String[] pickNames,
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

    public Creature pickOneEnemyOfTeam(final int teamID) {
        final String[] pickNames = this.buildNameListOfTeamEnemies(teamID);
        return this.pickEnemyOfTeamInternal(pickNames, 1, 1);
    }

    public Creature pickOneEnemyOfTeamRandomly(final int teamID) {
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

    private Creature pickEnemyOfTeamInternal(final String[] pickNames,
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

    public Creature getSelfTarget() {
        return this.activeCharacter.getTemplate();
    }

    // Prestige Stuff
    public static void dealtDamage(final Creature c, final int value) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_GIVEN, value);
    }

    public static void tookDamage(final Creature c, final int value) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_DAMAGE_TAKEN, value);
    }

    public static void hitEnemy(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_HITS_GIVEN, 1);
    }

    public static void hitByEnemy(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_HITS_TAKEN, 1);
    }

    public static void missedEnemy(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_MISSED_ATTACKS, 1);
    }

    public static void killedEnemy(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_MONSTERS_KILLED, 1);
    }

    public static void dodgedAttack(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_ATTACKS_DODGED, 1);
    }

    public static void castSpell(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_SPELLS_CAST, 1);
    }

    public static void killedInBattle(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_KILLED, 1);
    }

    public static void ranAway(final Creature c) {
        c.offsetPrestigeValue(PrestigeConstants.PRESTIGE_TIMES_RAN_AWAY, 1);
    }
}
