package com.anonym.astran.helpers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Quaternionf;
import org.joml.Vector2f;

public class ScreenHelper {
    public static void setup3DPerspective(float fov, float zNear, float zFar, float zCloseness) {
        RenderSystem.enableDepthTest();
        int width = Minecraft.getInstance().getWindow().getWidth();
        int height = Minecraft.getInstance().getWindow().getHeight();
        float aspect = (float) width / height;

        Matrix4f perspective = new Matrix4f();
        perspective.setPerspective((float) Math.toRadians(fov), aspect, zNear, zFar);
        RenderSystem.setProjectionMatrix(perspective, VertexSorting.DISTANCE_TO_ORIGIN);

        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.identity();

        modelView.translate(0.0F, 0.0F, zCloseness);

        Quaternionf rot = new Quaternionf()
                .rotateX((float) Math.toRadians(0))
                .rotateY((float) Math.toRadians(0));
        Matrix4f rotationMatrix = new Matrix4f().rotation(rot);
        modelView.mul(rotationMatrix);
        RenderSystem.applyModelViewMatrix();
    }

    public static void setup3DPerspectiveForParallax(float fov, float zNear, float zFar, float zCloseness) {
        RenderSystem.enableDepthTest();
        int width = Minecraft.getInstance().getWindow().getWidth();
        int height = Minecraft.getInstance().getWindow().getHeight();
        float aspect = (float) width / height;

        Matrix4f perspective = new Matrix4f();
        perspective.setPerspective((float) Math.toRadians(fov), aspect, zNear, zFar);
        RenderSystem.setProjectionMatrix(perspective, VertexSorting.DISTANCE_TO_ORIGIN);

        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.identity();

        modelView.translate(0.0F, 0.0F, zCloseness);
        RenderSystem.applyModelViewMatrix();
    }



    public static void setup3DPerspective(float fov) {
    setup3DPerspective(fov,0.05F, 1000.0F,-5.0F);
    }
    public static void setup3DPerspective(float fov, float zCloseness) {
        setup3DPerspective(fov,0.05F, 1000.0F,zCloseness);
    }

    public static void setup3DPerspectiveForParallax(float fov) {
        setup3DPerspectiveForParallax(fov,0.05F, 1000.0F,-5.0F);
    }
    public static void setup3DPerspectiveForParallax(float fov, float zCloseness) {
        setup3DPerspectiveForParallax(fov,0.05F, 1000.0F,zCloseness);
    }

    public static void restoreOrthographic() {
        Matrix4f ortho = new Matrix4f().setOrtho(
                0.0f,
                Minecraft.getInstance().getWindow().getGuiScaledWidth(),
                Minecraft.getInstance().getWindow().getGuiScaledHeight(),
                0.0f,
                1000.0f,
                3000.0f
        );
        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.identity();
        RenderSystem.applyModelViewMatrix();
        RenderSystem.disableDepthTest();
    }

    public static class MouseRotationEaser {
        private float startRotX = 0, startRotY = 0;
        private float targetRotX = 0, targetRotY = 0;

        private float animTimeX = 0f, animTimeY = 0f;
        private float animDuration = 0.25f; // seconds to reach target

        private final Easing easingX;
        private final Easing easingY;

        private final float maxAngle;
        private final float originX;
        private final float originY;

        public MouseRotationEaser(Easing easingX, Easing easingY, float maxAngle, float originX, float originY, float animDuration) {
            this.easingX = easingX;
            this.easingY = easingY;
            this.maxAngle = maxAngle;
            this.originX = originX;
            this.originY = originY;
            this.animDuration = animDuration;
        }

        public MouseRotationEaser(Easing easingX, Easing easingY, float maxAngle, float originX, float originY) {
            this(easingX,easingY,maxAngle,originX,originY,0.25F);
        }

        public void apply(PoseStack poseStack, float partialTick) {
            float tX = Mth.clamp((animTimeX + partialTick / 60f) / animDuration, 0f, 1f);
            float tY = Mth.clamp((animTimeY + partialTick / 60f) / animDuration, 0f, 1f);

            tX = this.easingX.ease(tX);
            tY = this.easingY.ease(tY);

            float rotX = Mth.lerp(tX, this.startRotX, this.targetRotX);
            float rotY = Mth.lerp(tY, this.startRotY, this.targetRotY);

            poseStack.mulPose(Axis.XP.rotationDegrees(rotX));
            poseStack.mulPose(Axis.YP.rotationDegrees(-rotY));
        }

        public void calculate(Vector2f vector2f, float partialTick) {
            float tX = Mth.clamp((this.animTimeX + partialTick / 60f) / this.animDuration, 0f, 1f);
            float tY = Mth.clamp((this.animTimeY + partialTick / 60f) / this.animDuration, 0f, 1f);

            tX = this.easingX.ease(tX);
            tY = this.easingY.ease(tY);

            float rotX = Mth.lerp(tX, this.startRotX, this.targetRotX);
            float rotY = Mth.lerp(tY, this.startRotY, this.targetRotY);

            vector2f.x = rotX;
            vector2f.y = rotY;
        }

        public void tick() {
            Minecraft mc = Minecraft.getInstance();

            double mouseX = mc.mouseHandler.xpos() * mc.getWindow().getGuiScaledWidth() / mc.getWindow().getScreenWidth();
            double mouseY = mc.mouseHandler.ypos() * mc.getWindow().getGuiScaledHeight() / mc.getWindow().getScreenHeight();

            float screenWidth = mc.getWindow().getGuiScaledWidth();
            float screenHeight = mc.getWindow().getGuiScaledHeight();

            float x = (float) (mouseX - screenWidth * this.originX) / (screenWidth * this.originX);
            float y = (float) (mouseY - screenHeight * this.originY) / (screenHeight * this.originY);

            float newTargetRotY = x * this.maxAngle;
            float newTargetRotX = y * this.maxAngle;

            if (newTargetRotX != this.targetRotX) {
                this.startRotX = getCurrentRotX();
                this.targetRotX = newTargetRotX;
                this.animTimeX = 0f;
            }
            if (newTargetRotY != this.targetRotY) {
                startRotY = getCurrentRotY();
                targetRotY = newTargetRotY;
                animTimeY = 0f;
            }

            this.animTimeX = Math.min(this.animTimeX + 1f / 60f, this.animDuration);
            this.animTimeY = Math.min(this.animTimeY + 1f / 60f, this.animDuration);
        }

        private float getCurrentRotX() {
            float t = Mth.clamp(this.animTimeX / this.animDuration, 0f, 1f);
            t = this.easingX.ease(t);
            return Mth.lerp(t, this.startRotX, this.targetRotX);
        }

        private float getCurrentRotY() {
            float t = Mth.clamp(this.animTimeY / this.animDuration, 0f, 1f);
            t = this.easingY.ease(t);
            return Mth.lerp(t, this.startRotY, this.targetRotY);
        }
    }
}
