package com.anonym.astran.helpers;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.VertexSorting;
import net.minecraft.client.Minecraft;
import org.joml.*;

import java.lang.Math;

public class PickingHelper {

    public static Matrix4f LAST_PROJECTION;
    public static Matrix4f LAST_VIEW;

    public static void setup3DPerspective(float fov, float zNear, float zFar, float zCloseness) {
        RenderSystem.enableDepthTest();
        int width = Minecraft.getInstance().getWindow().getWidth();
        int height = Minecraft.getInstance().getWindow().getHeight();
        float aspect = (float) width / height;

        Matrix4f perspective = new Matrix4f();
        perspective.setPerspective((float) Math.toRadians(fov), aspect, zNear, zFar);
        RenderSystem.setProjectionMatrix(perspective, VertexSorting.DISTANCE_TO_ORIGIN);
        LAST_PROJECTION = new Matrix4f(perspective);

        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.identity();
        modelView.translate(0.0F, 0.0F, zCloseness);

        Quaternionf rot = new Quaternionf()
                .rotateX((float) Math.toRadians(0))
                .rotateY((float) Math.toRadians(0));
        Matrix4f rotationMatrix = new Matrix4f().rotation(rot);
        modelView.mul(rotationMatrix);

        RenderSystem.applyModelViewMatrix();
        LAST_VIEW = new Matrix4f(modelView);
    }

    public static void setup3DPerspective(float fov) {
        setup3DPerspective(fov, 0.05F, 1000.0F, -5.0F);
    }

    public static void setup3DPerspective(float fov, float zCloseness) {
        setup3DPerspective(fov, 0.05F, 1000.0F, zCloseness);
    }

    public static Vector3f getRayFromMouse(double mouseX, double mouseY, Matrix4f projection, Matrix4f view) {
        Minecraft mc = Minecraft.getInstance();

        int windowW = mc.getWindow().getScreenWidth();
        int windowH = mc.getWindow().getScreenHeight();
        int guiW = mc.getWindow().getGuiScaledWidth();
        int guiH = mc.getWindow().getGuiScaledHeight();

        double winMouseX = mouseX * ((double) windowW / (double) guiW);
        double winMouseY = mouseY * ((double) windowH / (double) guiH);

        float x = (float) (2.0 * winMouseX / windowW - 1.0);
        float y = (float) (1.0 - 2.0 * winMouseY / windowH);
        Vector4f rayClip = new Vector4f(x, y, -1.0f, 1.0f);

        Matrix4f invProj = new Matrix4f(projection).invert();
        Vector4f rayEye = rayClip.mul(invProj);
        rayEye.z = -1.0f;
        rayEye.w = 0.0f;

        Matrix4f invView = new Matrix4f(view).invert();
        Vector4f rayWorld4 = rayEye.mul(invView);

        Vector3f rayWorld = new Vector3f(rayWorld4.x, rayWorld4.y, rayWorld4.z);
        rayWorld.normalize();
        return rayWorld;
    }

    public static Vector3f getCameraPosition(Matrix4f view) {
        Matrix4f invView = new Matrix4f(view).invert();
        Vector3f camPos = new Vector3f();
        invView.getTranslation(camPos);
        return camPos;
    }

    public static float rayAABBIntersectionDistance(Vector3f origin, Vector3f dir,
                                                    Vector3f boxCenter, Vector3f halfSize) {
        Vector3f min = new Vector3f(boxCenter).sub(halfSize);
        Vector3f max = new Vector3f(boxCenter).add(halfSize);

        float tMin = Float.NEGATIVE_INFINITY;
        float tMax = Float.POSITIVE_INFINITY;

        if (Math.abs(dir.x) < 1e-6f) {
            if (origin.x < min.x || origin.x > max.x) return Float.POSITIVE_INFINITY;
        } else {
            float tx1 = (min.x - origin.x) / dir.x;
            float tx2 = (max.x - origin.x) / dir.x;
            float txMin = Math.min(tx1, tx2);
            float txMax = Math.max(tx1, tx2);
            tMin = Math.max(tMin, txMin);
            tMax = Math.min(tMax, txMax);
        }

        if (Math.abs(dir.y) < 1e-6f) {
            if (origin.y < min.y || origin.y > max.y) return Float.POSITIVE_INFINITY;
        } else {
            float ty1 = (min.y - origin.y) / dir.y;
            float ty2 = (max.y - origin.y) / dir.y;
            float tyMin = Math.min(ty1, ty2);
            float tyMax = Math.max(ty1, ty2);
            tMin = Math.max(tMin, tyMin);
            tMax = Math.min(tMax, tyMax);
        }

        if (Math.abs(dir.z) < 1e-6f) {
            if (origin.z < min.z || origin.z > max.z) return Float.POSITIVE_INFINITY;
        } else {
            float tz1 = (min.z - origin.z) / dir.z;
            float tz2 = (max.z - origin.z) / dir.z;
            float tzMin = Math.min(tz1, tz2);
            float tzMax = Math.max(tz1, tz2);
            tMin = Math.max(tMin, tzMin);
            tMax = Math.min(tMax, tzMax);
        }

        if (tMax < Math.max(tMin, 0f)) return Float.POSITIVE_INFINITY;

        return tMin >= 0f ? tMin : 0f;
    }
}
