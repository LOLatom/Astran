package com.anonym.astran.systems.camera;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.joml.Vector3f;

@EventBusSubscriber(value = Dist.CLIENT)
public class CameraCynematicsHelper {
    public static boolean isCameraOverriden = false;
    public static Vec3 currentCameraPosition = new Vec3(0,0,0);
    public static Vec3 oldCameraPos = new Vec3(0,0,0);
    public static Vec3 currentCameraRotation = new Vec3(0,0,0);
    public static Vec3 oldCameraRot = new Vec3(0,0,0);

    public static float[] vecToYawPitch(Vec3 vec) {
        float yaw = (float)(Math.atan2(vec.z, vec.x) * (180F / Math.PI)) - 90F;
        float pitch = (float)(-(Math.atan2(vec.y, Math.sqrt(vec.x * vec.x + vec.z * vec.z)) * (180F / Math.PI)));
        return new float[]{yaw, pitch};
    }

    public static float[] lookAt(Vec3 from, Vec3 to) {
        Vec3 dir = to.subtract(from);
        double dx = dir.x;
        double dy = dir.y;
        double dz = dir.z;
        double horizontal = Math.sqrt(dx*dx + dz*dz);

        float rawYaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float rawPitch = (float) (-Math.toDegrees(Math.atan2(dy, horizontal)));

        // normalize to -180 .. 180
        float yaw = Mth.wrapDegrees(rawYaw);
        float pitch = Mth.wrapDegrees(rawPitch);
        // clamp pitch to -90..90 to avoid weird flips
        pitch = Mth.clamp(pitch, -90.0F, 90.0F);

        return new float[]{pitch, yaw}; // pitch first -> matches Camera.getXRot -> .x
    }

    @SubscribeEvent
    public static void reDoCameraComputation(ViewportEvent.ComputeCameraAngles event) {
        if (isCameraOverriden) {
            event.setPitch((float) currentCameraRotation.x);
            event.setYaw((float) currentCameraRotation.y);
            event.setRoll((float) currentCameraRotation.z);
        }
    }

}
