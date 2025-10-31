package com.anonym.astran.api.swiff.scene;

import com.anonym.astran.helpers.PickingHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public abstract class SceneObject {
    public float posX, posY, posZ;
    public float rotX, rotY, rotZ;
    public float width, height, depth;
    public boolean isStatic = false;
    public boolean affectedByGravity = false;
    public Vector3f velocity = new Vector3f();

    private boolean hovered = false;
    public float scale = 1.0f;

    public SceneObject(float x, float y, float z, float width, float height, float depth) {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.width = width * 0.0663F;
        this.height = height * 0.0663F;
        this.depth = depth * 0.0663F;
    }

    public void onUpdate(float deltaSeconds) {
        if (!this.isStatic) {
            if (this.affectedByGravity) this.velocity.y -= 0.098f * deltaSeconds;
            this.posX += this.velocity.x * deltaSeconds;
            this.posY += this.velocity.y * deltaSeconds;
            this.posZ += this.velocity.z * deltaSeconds;
        }
    }

    public AABB getAABB() {
        return new AABB(
                this.posX - this.width / 2, this.posY, this.posZ - this.depth / 2,
                this.posX + this.width / 2, this.posY + this.height, this.posZ + this.depth / 2
        );
    }

    public void render(GuiGraphics graphics, float partialTicks) {
        PoseStack pose = graphics.pose();
        pose.pushPose();

        pose.translate(this.posX, this.posY, this.posZ);
        pose.mulPose(Axis.XP.rotationDegrees(rotX));
        pose.mulPose(Axis.YP.rotationDegrees(rotY));
        pose.mulPose(Axis.ZP.rotationDegrees(rotZ));
        pose.scale(this.scale, this.scale, this.scale);

        onRender(graphics, partialTicks);

        pose.popPose();
    }

    public abstract void onRender(GuiGraphics graphics, float partialTicks);

    public void setRotation(float rotX, float rotY, float rotZ) {
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public boolean isHovered() {
        return this.hovered;
    }

    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    public Matrix4f getModelMatrix() {
        Matrix4f m = new Matrix4f();
        m.translate(this.posX, this.posY, this.posZ);
        m.rotateX((float) Math.toRadians(this.rotX));
        m.rotateY((float) Math.toRadians(this.rotY));
        m.rotateZ((float) Math.toRadians(this.rotZ));
        m.scale(this.scale);
        return m;
    }


    public float getRayIntersectionDistance(Vector3f rayOriginWorld, Vector3f rayDirWorld, Matrix4f parentTransform) {
        Matrix4f combined = new Matrix4f(parentTransform).mul(getModelMatrix());

        Matrix4f inv = new Matrix4f(combined).invert();

        Vector3f originLocal = new Vector3f(rayOriginWorld);
        inv.transformPosition(originLocal);

        Vector3f dirLocal = new Vector3f(rayDirWorld);
        inv.transformDirection(dirLocal);
        dirLocal.normalize();

        Vector3f halfSize = new Vector3f(this.width / 2f, this.height / 2f, this.depth / 2f);

        return PickingHelper.rayAABBIntersectionDistance(originLocal, dirLocal, new Vector3f(0, 0, 0), halfSize);
    }

    public float getRayIntersectionDistance(Vector3f rayOriginWorld, Vector3f rayDirWorld) {
        return getRayIntersectionDistance(rayOriginWorld, rayDirWorld, new Matrix4f());
    }
}
