package com.anonym.astran.api.swiff.sdf;

import org.joml.Vector2f;
import org.joml.Vector4f;

import java.awt.*;

public class SDFShape {

    private final SDFSettings sdfSettings;
    public SDFShape(SDFSettings sdfSettings) {
        this.sdfSettings = sdfSettings;
    }

    public int getShapeID() {
        return this.sdfSettings.type.id;
    }

    public float getRadius() {
        return this.sdfSettings.radius;
    }

    public SDFShape withRadius(float radius) {
        this.sdfSettings.radius = radius;
        return this;
    }
    public SDFShape withColor(Color color) {
        this.sdfSettings.color = color;
        return this;
    }

    public void setRadius(float radius) {
        this.sdfSettings.radius = radius;
    }

    public void setPos(Vector2f pos) {
        this.sdfSettings.pos = pos;
    }

    public Color getColor() {
        return this.sdfSettings.color;
    }

    public void setColor(Color color) {
        this.sdfSettings.color = color;
    }

    public Vector2f getPos() {
        return this.sdfSettings.pos;
    }

    public SDFSettings getSettings() {
        return this.sdfSettings;
    }
}
