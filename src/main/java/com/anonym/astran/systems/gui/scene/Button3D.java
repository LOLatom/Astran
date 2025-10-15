package com.anonym.astran.systems.gui.scene;

public abstract class Button3D extends SceneObject {

    public Button3D(float x, float y, float z, float width, float height, float depth) {
        super(x, y, z, width, height, depth);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isHovered()) {
            if (onClick(mouseX, mouseY, button)) return true;
        }
        return false;
    }

    public abstract boolean onClick(double mouseX, double mouseY, int button);
}
