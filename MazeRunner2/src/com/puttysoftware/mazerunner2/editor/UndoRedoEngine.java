/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.editor;

import com.puttysoftware.mazerunner2.maze.abc.AbstractMazeObject;

public class UndoRedoEngine {
    // Fields
    private LinkStack undoHistory;
    private LinkStack redoHistory;
    private AbstractMazeObject object;
    private int destX, destY, destZ, destW, destE;

    // Constructors
    public UndoRedoEngine() {
        this.undoHistory = new LinkStack();
        this.redoHistory = new LinkStack();
        this.object = null;
        this.destX = -1;
        this.destY = -1;
        this.destZ = -1;
        this.destW = -1;
        this.destE = -1;
    }

    // Public methods
    public void undo() {
        if (!(this.undoHistory.isEmpty())) {
            Link entry = this.undoHistory.pop();
            this.object = entry.mo;
            this.destX = entry.coordX;
            this.destY = entry.coordY;
            this.destZ = entry.coordZ;
            this.destW = entry.coordW;
            this.destE = entry.coordE;
        } else {
            this.object = null;
            this.destX = -1;
            this.destY = -1;
            this.destZ = -1;
            this.destW = -1;
            this.destE = -1;
        }
    }

    public void redo() {
        if (!(this.redoHistory.isEmpty())) {
            Link entry = this.redoHistory.pop();
            this.object = entry.mo;
            this.destX = entry.coordX;
            this.destY = entry.coordY;
            this.destZ = entry.coordZ;
            this.destW = entry.coordW;
            this.destE = entry.coordE;
        } else {
            this.object = null;
            this.destX = -1;
            this.destY = -1;
            this.destZ = -1;
            this.destW = -1;
            this.destE = -1;
        }
    }

    public boolean tryUndo() {
        return !(this.undoHistory.isEmpty());
    }

    public boolean tryRedo() {
        return !(this.redoHistory.isEmpty());
    }

    public boolean tryBoth() {
        return this.undoHistory.isEmpty() && this.redoHistory.isEmpty();
    }

    public void updateUndoHistory(AbstractMazeObject obj, int x, int y, int z,
            int w, int e) {
        this.undoHistory.push(obj, x, y, z, w, e);
    }

    public void updateRedoHistory(AbstractMazeObject obj, int x, int y, int z,
            int w, int e) {
        this.redoHistory.push(obj, x, y, z, w, e);
    }

    public AbstractMazeObject getObject() {
        return this.object;
    }

    public int getX() {
        return this.destX;
    }

    public int getY() {
        return this.destY;
    }

    public int getZ() {
        return this.destZ;
    }

    public int getW() {
        return this.destW;
    }

    public int getE() {
        return this.destE;
    }

    // Inner classes
    private static class Link {
        // Fields
        public AbstractMazeObject mo;
        public int coordX, coordY, coordZ, coordW, coordE;
        public Link next;

        public Link(AbstractMazeObject obj, int x, int y, int z, int w, int e) {
            this.mo = obj;
            this.coordX = x;
            this.coordY = y;
            this.coordZ = z;
            this.coordW = w;
            this.coordE = e;
            this.next = null;
        }
    }

    private static class LinkList {
        // Fields
        private Link first;

        public LinkList() {
            this.first = null;
        }

        public boolean isEmpty() {
            return this.first == null;
        }

        public void insertFirst(AbstractMazeObject obj, int x, int y, int z,
                int w, int e) {
            Link newLink = new Link(obj, x, y, z, w, e);
            newLink.next = this.first;
            this.first = newLink;
        }

        public Link deleteFirst() {
            Link temp = this.first;
            this.first = this.first.next;
            return temp;
        }
    }

    private static class LinkStack {
        // Fields
        private LinkList theList;

        public LinkStack() {
            this.theList = new LinkList();
        }

        public void push(AbstractMazeObject obj, int x, int y, int z, int w,
                int e) {
            this.theList.insertFirst(obj, x, y, z, w, e);
        }

        public Link pop() {
            return this.theList.deleteFirst();
        }

        public boolean isEmpty() {
            return this.theList.isEmpty();
        }
    }
}
