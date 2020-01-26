package net.worldwizard.mazerunner;

public class MazeObjectList {
    // fields
    private final MazeObject[] allObjects = { new MazeGround(), new MazeWall(),
            new MazePlayer(), new MazeFinish(), new MazeInvisibleWall(),
            new MazeFakeWall(), new MazeKey(), new MazeLock(), new MazeTablet(),
            new MazeTabletSlot(), new MazeFakeFinish(), new MazeCrackedWall(),
            new MazeBomb(), new MazeTeleporter(), new MazeInvisibleTeleporter(),
            new MazeAKey(), new MazeALock(), new MazeCKey(), new MazeCLock(),
            new MazeDKey(), new MazeDLock(), new MazeFKey(), new MazeFLock(),
            new MazeGKey(), new MazeGLock(), new MazeKKey(), new MazeKLock(),
            new MazeLKey(), new MazeLLock(), new MazeMKey(), new MazeMLock(),
            new MazeNKey(), new MazeNLock(), new MazePKey(), new MazePLock(),
            new MazeRKey(), new MazeRLock(), new MazeUKey(), new MazeULock(),
            new MazeZKey(), new MazeZLock(), new MazeRandomTeleporter(),
            new MazeRandomInvisibleTeleporter(),
            new MazeRandomOneShotTeleporter(),
            new MazeRandomInvisibleOneShotTeleporter(),
            new MazeOneShotTeleporter(), new MazeInvisibleOneShotTeleporter(),
            new MazeOneWayEastWall(), new MazeOneWayNorthWall(),
            new MazeOneWaySouthWall(), new MazeOneWayWestWall(),
            new MazeStairsUp(), new MazeStairsDown(),
            new MazeTwoWayTeleporter(), new MazePit(), new MazeTile(),
            new MazePushableBlock(), new MazePullableBlock(),
            new MazePushablePullableBlock(), new MazeIce(), new MazeFinishTo(),
            new MazeBoots(), new MazeWater(), new MazeSunkenBlock(),
            new MazeEnergySphere(), new MazeForceField(),
            new MazeAnnihilationWand(), new MazeFinishMakingWand(),
            new MazeWallMakingWand(), new MazeTeleportWand(), new MazeVoid(),
            new MazeExplodingWall() };

    public MazeObject[] getAllObjects() {
        return this.allObjects;
    }

    public String[] getAllNames() {
        final String[] allNames = new String[this.allObjects.length];
        for (int x = 0; x < this.allObjects.length; x++) {
            allNames[x] = this.allObjects[x].getName();
        }
        return allNames;
    }

    public MazeObject[] getAllInventoryableObjects() {
        final MazeObject[] tempAllInventoryableObjects = new MazeObject[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isInventoryable()) {
                tempAllInventoryableObjects[x] = this.allObjects[x];
            }
        }
        for (final MazeObject tempAllInventoryableObject : tempAllInventoryableObjects) {
            if (tempAllInventoryableObject != null) {
                objectCount++;
            }
        }
        final MazeObject[] allInventoryableObjects = new MazeObject[objectCount];
        objectCount = 0;
        for (final MazeObject tempAllInventoryableObject : tempAllInventoryableObjects) {
            if (tempAllInventoryableObject != null) {
                allInventoryableObjects[objectCount] = tempAllInventoryableObject;
                objectCount++;
            }
        }
        return allInventoryableObjects;
    }

    public String[] getAllInventoryableNames() {
        final String[] tempAllInventoryableNames = new String[this.allObjects.length];
        int objectCount = 0;
        for (int x = 0; x < this.allObjects.length; x++) {
            if (this.allObjects[x].isInventoryable()) {
                tempAllInventoryableNames[x] = this.allObjects[x].getName();
            }
        }
        for (final String tempAllInventoryableName : tempAllInventoryableNames) {
            if (tempAllInventoryableName != null) {
                objectCount++;
            }
        }
        final String[] allInventoryableNames = new String[objectCount];
        objectCount = 0;
        for (final String tempAllInventoryableName : tempAllInventoryableNames) {
            if (tempAllInventoryableName != null) {
                allInventoryableNames[objectCount] = tempAllInventoryableName;
                objectCount++;
            }
        }
        return allInventoryableNames;
    }
}
