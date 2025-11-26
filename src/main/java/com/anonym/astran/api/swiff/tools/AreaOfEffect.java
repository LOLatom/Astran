package com.anonym.astran.api.swiff.tools;

public class AreaOfEffect {
    private int right,  left,  top,  bottom;
    public AreaOfEffect(int right, int left, int top, int bottom) {
        this.right = right;
        this.left = left;
        this.top = top;
        this.bottom = bottom;

    }

    public int left() {
        return this.left;
    }
    public int right() {
        return this.right;
    }
    public int top() {
        return this.top;
    }
    public int bottom() {
        return this.bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
