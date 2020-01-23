/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell


Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.generic;

import net.worldwizard.images.BufferedImageIcon;
import net.worldwizard.worldz.creatures.Creature;
import net.worldwizard.worldz.objects.Empty;
import net.worldwizard.worldz.world.WorldConstants;

public abstract class GenericBattleCharacter extends WorldObject {
    // Fields
    private final Creature template;
    private WorldObject saved;
    private int x, y;
    private int saveX, saveY;
    private int actionCounter;
    private int attackCounter;
    private int spellCounter;
    private boolean isActive;
    private int teamID;

    // Constructors
    protected GenericBattleCharacter(final Creature newTemplate) {
        super(true);
        this.template = newTemplate;
        this.saved = new Empty();
        this.x = 0;
        this.y = 0;
        this.saveX = 0;
        this.saveY = 0;
        this.actionCounter = newTemplate.getActionsPerRound();
        this.attackCounter = newTemplate.getEffectedAttacksPerRound();
        this.spellCounter = newTemplate.getEffectedSpellsPerRound();
        this.isActive = true;
        this.teamID = 0;
    }

    public WorldObject getSavedObject() {
        return this.saved;
    }

    public void setSavedObject(final WorldObject newSaved) {
        this.saved = newSaved;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(final int newX) {
        this.x = newX;
    }

    public void setY(final int newY) {
        this.y = newY;
    }

    public void offsetX(final int newX) {
        this.x += newX;
    }

    public void offsetY(final int newY) {
        this.y += newY;
    }

    public void saveLocation() {
        this.saveX = this.x;
        this.saveY = this.y;
    }

    public void restoreLocation() {
        this.x = this.saveX;
        this.y = this.saveY;
    }

    public Creature getTemplate() {
        return this.template;
    }

    public int getTeamID() {
        return this.teamID;
    }

    public void setTeamID(final int newTeam) {
        this.teamID = newTeam;
    }

    public boolean isActive() {
        return this.isActive;
    }

    public void deactivate() {
        this.isActive = false;
    }

    public void activate() {
        this.isActive = true;
    }

    public void resetAP() {
        this.actionCounter = this.template.getActionsPerRound();
    }

    public void modifyAP(final int mod) {
        this.actionCounter -= mod;
    }

    public int getAP() {
        return this.actionCounter;
    }

    public void resetAttacks() {
        this.attackCounter = this.template.getEffectedAttacksPerRound();
    }

    public void modifyAttacks(final int mod) {
        this.attackCounter -= mod;
    }

    public int getAttacks() {
        return this.attackCounter;
    }

    public void resetSpells() {
        this.spellCounter = this.template.getEffectedSpellsPerRound();
    }

    public void modifySpells(final int mod) {
        this.spellCounter -= mod;
    }

    public int getSpells() {
        return this.spellCounter;
    }

    public String getAPString() {
        return "Moves: " + (this.actionCounter >= 0 ? this.actionCounter : 0);
    }

    public String getAttackString() {
        return "Attacks: " + (this.attackCounter >= 0 ? this.attackCounter : 0);
    }

    public String getSpellString() {
        return "Spells: " + (this.spellCounter >= 0 ? this.spellCounter : 0);
    }

    @Override
    public BufferedImageIcon getImage() {
        return this.template.getImage();
    }

    @Override
    public String getName() {
        return this.template.getName();
    }

    @Override
    public int getLayer() {
        return WorldConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return WorldObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public String getDescription() {
        // Description isn't used for battle characters
        return null;
    }

    @Override
    public String getPluralName() {
        // Plural name isn't used for battle characters
        return null;
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BATTLE_CHARACTER);
    }
}
