package com.example.myapplication;

public class Obstacle {

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public int getRight() {
        return right;
    }

    public int getDown() {
        return down;
    }

    int left;
    int top;
    int right;
    int down;

    /**
     * an obstacle is a place that cannot be moved into or shot through.
     * @param left left wall of object
     * @param top upper wall of object
     * @param right right wall of object
     * @param down lower wall of object
     */
    public Obstacle(int left, int top, int right, int down){
        this.left=left;
        this.top=top;
        this.right=right;
        this.down=down;
    }

}

