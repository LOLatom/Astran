package com.anonym.astran.helpers;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class CursorHelper {

    public static BlockHitResult getBlockHitResultAtMouse(float range,ClipContext.Block block,
                                                          ClipContext.Fluid fluid, CollisionContext collisionContext) {
        var cam = Minecraft.getInstance().gameRenderer.getMainCamera();
        float xRot = cam.getXRot();
        float yRot = cam.getYRot();
        GameRendererAccessor gameRendererAccessor = (GameRendererAccessor) Minecraft.getInstance().gameRenderer;

        float pct = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
        int screenHeight = Minecraft.getInstance().getWindow().getScreenHeight();
        int screenWidth = Minecraft.getInstance().getWindow().getScreenWidth();

        double mousePosX = Minecraft.getInstance().mouseHandler.xpos();
        double mousePosY = Minecraft.getInstance().mouseHandler.ypos();

        double b = (screenHeight/2)/Math.tan(Math.toRadians(gameRendererAccessor.accessGetFov(cam,pct,true))/2);
        Vector3f vector3f = new Vector3f(
                (float) -(mousePosX - screenWidth/2)
                , (float) -(mousePosY - screenHeight/2)
                , (float) b);

        vector3f.rotateX((float) Math.toRadians(xRot));
        vector3f.rotateY((float) Math.toRadians(-yRot));
        vector3f.normalize();


        Vec3 startVec = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();
        Vec3 endVec = startVec.add(new Vec3(vector3f.x, vector3f.y, vector3f.z).multiply(range,range,range));

        ClipContext context = new ClipContext(startVec,endVec, block, fluid, collisionContext);
        BlockHitResult hitResult = Minecraft.getInstance().level.clip(context);

        return hitResult;
    }

    public static Entity getEntityAtMouseNoFluid(float range) {
        BlockHitResult hitResult = getBlockHitResultAtMouse(range, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, CollisionContext.empty());
        if (hitResult.getType() == HitResult.Type.ENTITY) {
            //EntityHitResult result = (EntityHitResult) hitResult;

        }
        return null;
    }



    public static BlockPos getBlockAtMouseNoFluid(float range) {
        BlockHitResult hitResult = getBlockHitResultAtMouse(range, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, CollisionContext.empty());
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            return hitResult.getBlockPos();
        }
        return null;
    }
    public static BlockPos getBlockAtMouseAnyFluid(float range) {
        BlockHitResult hitResult = getBlockHitResultAtMouse(range, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, CollisionContext.empty());
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            return hitResult.getBlockPos();
        }
        return null;
    }

    public static BlockPos getEntityPosAtMouseAnyFluid(float range) {
        BlockHitResult hitResult = getBlockHitResultAtMouse(range, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, CollisionContext.empty());
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            return hitResult.getBlockPos();
        }
        return null;
    }



    private static Matrix4f getCameraViewMatrix() {
        Minecraft minecraft = Minecraft.getInstance();
        Vec3 cameraPos = minecraft.gameRenderer.getMainCamera().getPosition();
        float pitch = minecraft.gameRenderer.getMainCamera().getXRot();
        float yaw = minecraft.gameRenderer.getMainCamera().getYRot();

        // Create transformation manually (rotation + translation)
        return new Matrix4f()
                .rotateX((float) Math.toRadians(-pitch))
                .rotateY((float) Math.toRadians(-yaw))
                .translate((float) -cameraPos.x, (float) -cameraPos.y, (float) -cameraPos.z);
    }



}
