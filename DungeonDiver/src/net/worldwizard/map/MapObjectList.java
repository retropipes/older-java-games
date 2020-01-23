package net.worldwizard.map;

import javax.swing.ImageIcon;

public class MapObjectList {
    // Fields
    private final MapObject[] allObjects;

    // Constructors
    public MapObjectList(final MapObject[] objects) {
        this.allObjects = objects;
    }

    // Methods
    public final MapObject[] getAllObjects() {
        return this.allObjects;
    }

    public final String[] getAllNames() {
        final String[] allNames = new String[this.allObjects.length];
        int x;
        for (x = 0; x < this.allObjects.length; x++) {
            allNames[x] = this.allObjects[x].getName();
        }
        return allNames;
    }

    public boolean[] getAllSolidStates() {
        final boolean[] allSolidStates = new boolean[this.allObjects.length];
        int x;
        for (x = 0; x < this.allObjects.length; x++) {
            allSolidStates[x] = this.allObjects[x].isSolid();
        }
        return allSolidStates;
    }

    public final ImageIcon[] getAllGameAppearances() {
        final ImageIcon[] allGameAppearances = new ImageIcon[this.allObjects.length];
        int x;
        for (x = 0; x < this.allObjects.length; x++) {
            allGameAppearances[x] = this.allObjects[x].getGameAppearance();
        }
        return allGameAppearances;
    }

    public final ImageIcon[] getAllEditorAppearances() {
        final ImageIcon[] allEditorAppearances = new ImageIcon[this.allObjects.length];
        int x;
        for (x = 0; x < this.allObjects.length; x++) {
            allEditorAppearances[x] = this.allObjects[x].getEditorAppearance();
        }
        return allEditorAppearances;
    }

    public final ImageIcon[][] getAllOtherAppearances() {
        final ImageIcon[][] allOtherAppearances = new ImageIcon[this.allObjects.length][];
        int x, y;
        for (x = 0; x < this.allObjects.length; x++) {
            final int otherAppearanceCount = this.allObjects[x]
                    .getOtherAppearanceCount();
            allOtherAppearances[x] = new ImageIcon[otherAppearanceCount];
            for (y = 0; y < otherAppearanceCount; y++) {
                allOtherAppearances[x][y] = this.allObjects[x]
                        .getOtherAppearance(y);
            }
        }
        return allOtherAppearances;
    }

    public final MapObject[] getAllRequired() {
        final MapObject[] tempAllRequired = new MapObject[this.allObjects.length];
        int x;
        int count = 0;
        for (x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isRequired()) {
                tempAllRequired[count] = this.allObjects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final MapObject[] allRequired = new MapObject[count];
            for (x = 0; x < count; x++) {
                allRequired[x] = tempAllRequired[x];
            }
            return allRequired;
        }
    }

    public final MapObject[] getAllNotRequired() {
        final MapObject[] tempAllNotRequired = new MapObject[this.allObjects.length];
        int x;
        int count = 0;
        for (x = 0; x < this.allObjects.length; x++) {
            if (!this.allObjects[x].isRequired()) {
                tempAllNotRequired[count] = this.allObjects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final MapObject[] allNotRequired = new MapObject[count];
            for (x = 0; x < count; x++) {
                allNotRequired[x] = tempAllNotRequired[x];
            }
            return allNotRequired;
        }
    }

    public final MapObject[] getAllWithoutPrerequisite() {
        final MapObject[] tempAllWithoutPrereq = new MapObject[this.allObjects.length];
        int x;
        int count = 0;
        for (x = 0; x < this.allObjects.length; x++) {
            if (!this.allObjects[x].hasPrerequisite()) {
                tempAllWithoutPrereq[count] = this.allObjects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final MapObject[] allWithoutPrereq = new MapObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final MapObject[] getAllWithoutPrerequisiteAndNotRequired() {
        final MapObject[] tempAllWithoutPrereq = new MapObject[this.allObjects.length];
        int x;
        int count = 0;
        for (x = 0; x < this.allObjects.length; x++) {
            if (!this.allObjects[x].hasPrerequisite()
                    && !this.allObjects[x].isRequired()) {
                tempAllWithoutPrereq[count] = this.allObjects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final MapObject[] allWithoutPrereq = new MapObject[count];
            for (x = 0; x < count; x++) {
                allWithoutPrereq[x] = tempAllWithoutPrereq[x];
            }
            return allWithoutPrereq;
        }
    }

    public final MapObject[] getAllWithNthPrerequisite(final int N) {
        final MapObject[] tempAllWithNthPrereq = new MapObject[this.allObjects.length];
        int x;
        int count = 0;
        for (x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].hasNthPrerequisite(N)) {
                tempAllWithNthPrereq[count] = this.allObjects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final MapObject[] allWithNthPrereq = new MapObject[count];
            for (x = 0; x < count; x++) {
                allWithNthPrereq[x] = tempAllWithNthPrereq[x];
            }
            return allWithNthPrereq;
        }
    }

    public final MapObject[] getAllCached() {
        final MapObject[] tempAllCached = new MapObject[this.allObjects.length];
        int x;
        int count = 0;
        for (x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].shouldCache()) {
                tempAllCached[count] = this.allObjects[x];
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final MapObject[] allCached = new MapObject[count];
            for (x = 0; x < count; x++) {
                allCached[x] = tempAllCached[x];
            }
            return allCached;
        }
    }

    public final String[] getAllUncached() {
        final String[] tempAllUncached = new String[this.allObjects.length];
        int x;
        int count = 0;
        for (x = 0; x < this.allObjects.length; x++) {
            if (!this.allObjects[x].shouldCache()) {
                tempAllUncached[count] = this.allObjects[x].getName();
                count++;
            }
        }
        if (count == 0) {
            return null;
        } else {
            final String[] allUncached = new String[count];
            for (x = 0; x < count; x++) {
                allUncached[x] = tempAllUncached[x];
            }
            return allUncached;
        }
    }

    public final MapObject getNewInstanceByName(final String name) {
        MapObject instance = null;
        int x;
        for (x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].getName().equals(name)) {
                instance = this.allObjects[x];
                break;
            }
        }
        if (instance == null) {
            return null;
        } else {
            try {
                return instance.getClass().newInstance();
            } catch (final IllegalAccessException iae) {
                return null;
            } catch (final InstantiationException ie) {
                return null;
            }
        }
    }

    protected final static MapObject getNewInstance(final MapObject instance) {
        if (instance == null) {
            return null;
        } else {
            try {
                return instance.getClass().newInstance();
            } catch (final IllegalAccessException iae) {
                return null;
            } catch (final InstantiationException ie) {
                return null;
            }
        }
    }
}
