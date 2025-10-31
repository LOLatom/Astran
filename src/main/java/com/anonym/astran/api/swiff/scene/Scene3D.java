package com.anonym.astran.api.swiff.scene;

import com.anonym.astran.helpers.PickingHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Scene3D {
    private List<SceneObject> objects = new ArrayList<>();

    public float sceneX = 0f, sceneY = 0f, sceneZ = 0f;
    public float sceneRotX = 0f, sceneRotY = 0f, sceneRotZ = 0f;
    public float sceneScale = 1f;

    public void addObject(SceneObject obj) {
        objects.add(obj);
    }

    public void setSceneTransform(float x, float y, float z,
                                  float rotXdeg, float rotYdeg, float rotZdeg,
                                  float scale) {
        this.sceneX = x; this.sceneY = y; this.sceneZ = z;
        this.sceneRotX = rotXdeg; this.sceneRotY = rotYdeg; this.sceneRotZ = rotZdeg;
        this.sceneScale = scale;
    }

    public Matrix4f getSceneMatrix() {
        Matrix4f m = new Matrix4f();
        m.translate(sceneX, sceneY, sceneZ);
        m.rotateX((float) Math.toRadians(sceneRotX));
        m.rotateY((float) Math.toRadians(sceneRotY));
        m.rotateZ((float) Math.toRadians(sceneRotZ));
        m.scale(sceneScale);
        return m;
    }



    public void updateHover(double mouseX, double mouseY, Matrix4f projection, Matrix4f view) {
        Vector3f rayDir = PickingHelper.getRayFromMouse(mouseX, mouseY, projection, view);
        Vector3f rayOrigin = PickingHelper.getCameraPosition(view);
        Matrix4f sceneMatrix = getSceneMatrix();

        Button3D closest = null;
        float minDist = Float.POSITIVE_INFINITY;

        for (SceneObject obj : objects) {
            if (obj instanceof Button3D b) {
                float dist = b.getRayIntersectionDistance(rayOrigin, rayDir, sceneMatrix);
                if (dist < minDist) {
                    minDist = dist;
                    closest = b;
                }
            }
        }

        for (SceneObject obj : objects) {
            if (obj instanceof Button3D b) {
                b.setHovered(b == closest);
            }
        }


    }


    public void render(GuiGraphics graphics, float partialTicks) {

        for (SceneObject obj : objects) {
            obj.render(graphics,partialTicks);
        }

        update(partialTicks);

    }
    public void update(float deltaSeconds) {
        for (SceneObject obj : objects) obj.onUpdate(deltaSeconds);

        for (int i = 0; i < objects.size(); i++) {
            SceneObject a = objects.get(i);
            if (a.isStatic) continue;
            for (int j = 0; j < objects.size(); j++) {
                if (i == j) continue;
                SceneObject b = objects.get(j);

                if (a.getAABB().intersects(b.getAABB())) {
                    AABB aabbA = a.getAABB();
                    AABB aabbB = b.getAABB();

                    float overlapX = (float) (Math.min(aabbA.maxX, aabbB.maxX) - Math.max(aabbA.minX, aabbB.minX));
                    float overlapY = (float) (Math.min(aabbA.maxY, aabbB.maxY) - Math.max(aabbA.minY, aabbB.minY));
                    float overlapZ = (float) (Math.min(aabbA.maxZ, aabbB.maxZ) - Math.max(aabbA.minZ, aabbB.minZ));

                    if (overlapX < overlapY && overlapX < overlapZ) {
                        if (a.posX > b.posX) a.posX += overlapX; else a.posX -= overlapX;
                        a.velocity.x = 0;
                    } else if (overlapY < overlapZ) {
                        if (a.posY > b.posY) a.posY += overlapY; else a.posY -= overlapY;
                        a.velocity.y = 0;
                    } else {
                        if (a.posZ > b.posZ) a.posZ += overlapZ; else a.posZ -= overlapZ;
                        a.velocity.z = 0;
                    }
                }
            }
        }
    }


    public void renderWithPose(GuiGraphics graphics, float partialTicks) {
        PoseStack pose = graphics.pose();
        pose.pushPose();
        pose.translate(sceneX, sceneY, sceneZ);
        pose.mulPose(Axis.XP.rotationDegrees(sceneRotX));
        pose.mulPose(Axis.YP.rotationDegrees(sceneRotY));
        pose.mulPose(Axis.ZP.rotationDegrees(sceneRotZ));
        pose.scale(sceneScale, sceneScale, sceneScale);

        render(graphics, partialTicks);

        pose.popPose();
    }

    public void tick() {
        for (SceneObject object : this.objects) {
            if (object instanceof Tickable tickable) {
                tickable.tick();
            }
        }
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (SceneObject b : objects) {
            if (b instanceof Button3D button3D) {
                if (button3D.mouseClicked(mouseX, mouseY, button)) return true;
            }
        }
        return false;
    }
}
