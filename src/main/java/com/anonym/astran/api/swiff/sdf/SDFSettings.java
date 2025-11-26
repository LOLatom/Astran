package com.anonym.astran.api.swiff.sdf;

import org.joml.Vector2f;
import org.joml.Vector4f;

import java.awt.*;

public class SDFSettings {

    public SDFType type;
    public float radius;
    public Vector2f pos;
    public Color color;
    public Vector4f whab;

    public SDFSettings() {
        this.type = SDFType.CIRCLE;
        this.radius = 5f;
        this.pos = new Vector2f(250,250);
        this.whab = new Vector4f(5f,5f,5f,5f);
        this.color = new Color(255,255,255,255);
    }
    public SDFSettings(SDFType type, float rad, Vector2f pos, Color color, Vector4f whab) {
        this.type = type;
        this.radius = rad;
        this.pos = pos;
        this.whab = whab;
        this.color = color;
    }

    public SDFSettings withShape(SDFType type) {
        this.type = type;
        return this;
    }

    public SDFSettings withRadius(float radius) {
        this.radius = radius;
        return this;
    }

    public SDFSettings withPos(Vector2f pos) {
        this.pos = pos;
        return this;
    }

    public SDFSettings withColor(Color color) {
        this.color = color;
        return this;
    }

    public SDFSettings copy() {
        return new SDFSettings(this.type,this.radius,this.pos,this.color,this.whab);
    }

    public SDFType getType() {
        return this.type;
    }

    public float getRadius() {
        return this.radius;
    }

    public Vector2f getPos() {
        return this.pos;
    }

    public Color getColor() {
        return this.color;
    }

    public void setType(SDFType type) {
        this.type = type;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void setPos(Vector2f pos) {
        this.pos = pos;
    }

    public void setWidth(float width) {
        this.whab.x = width;
    }
    public void setHeight(float height) {
        this.whab.y = height;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
