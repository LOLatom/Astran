package com.anonym.astran.systems.gui.theinterface;

import com.anonym.astran.systems.camera.CameraCynematicsHelper;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class CameraCyberInterfaceScreen extends CyberInterfaceScreen {

    private Vec3 cameraOldPos = Vec3.ZERO;
    private Vec3 cameraOldRot = Vec3.ZERO;
    private Vec3 cameraPrePos;
    private Vec3 cameraPreRot;
    private Vec3 cameraTargetPos = Vec3.ZERO;
    private Vec3 cameraTargetRot = Vec3.ZERO;
    private Vec3 cameraPosOffset = new Vec3(0,0,0);
    private Vec3 cameraRotOffset = new Vec3(0,0,0);

    private boolean cameraAnimating = false;
    private int cameraAnimTicks = 0;
    private final int animationDurationTicks = 20;
    private int localTicks = 0;

    public CameraCyberInterfaceScreen(int width, int height, boolean animated) {
        super(width,height,animated);
    }

    @Override
    protected void init() {
        super.init();

        Minecraft mc = Minecraft.getInstance();
        mc.options.setCameraType(CameraType.THIRD_PERSON_BACK);

        if (this.isCameraTransition()) {
            startCameraAnimation();
        }
    }

    private void startCameraAnimation() {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        Camera currentCamera = mc.gameRenderer.getMainCamera();
        this.cameraOldPos = currentCamera.getPosition();
        this.cameraOldRot = new Vec3(currentCamera.getXRot(), currentCamera.getYRot(), 0);
        if (this.cameraPrePos != null) {
            this.cameraOldPos = this.cameraPrePos;
        }
        if (this.cameraPreRot != null) {
            this.cameraOldRot = this.cameraPreRot;
        }

        Vec3 transformed = player.position();
        Vec3 dir = player.getViewVector(mc.getTimer().getGameTimeDeltaPartialTick(true));
        Vec3 normalizeDir = dir.normalize();

        Vec3 eyePos = player.position().add(0, player.getEyeHeight(), 0);

        Vec3 baseCam = transformed.add(0, 2, 0)
                .add(normalizeDir.x * 2, -0.6 + (Math.sin((this.localTicks) * 0.1) * 0.03f), normalizeDir.z * 2);

        Vec3 horizontalForward = new Vec3(normalizeDir.x, 0, normalizeDir.z);
        if (horizontalForward.length() < 1e-6) {
            double yawRad = Math.toRadians(player.getYRot());
            horizontalForward = new Vec3(-Math.sin(yawRad), 0.0, Math.cos(yawRad));
        } else {
            horizontalForward = horizontalForward.normalize();
        }
        Vec3 right = new Vec3(-horizontalForward.z, 0, horizontalForward.x).normalize();
        Vec3 camPosFinal = baseCam.add(right.scale(-1.0));

        float[] py = CameraCynematicsHelper.lookAt(camPosFinal, eyePos);
        float pitch = py[0];
        float yaw = py[1];

        this.cameraTargetPos = camPosFinal;
        this.cameraTargetRot = new Vec3(pitch, yaw + 180f, 0);
        this.cameraAnimating = true;
        this.cameraAnimTicks = 0;
    }

    @Override
    public void tick() {
        super.tick();
        localTicks++;
        if (cameraAnimating) {
            cameraAnimTicks++;
            if (cameraAnimTicks > animationDurationTicks) {
                cameraAnimating = false;
            }
        }
    }

    public void setCameraPosOffset(Vec3 cameraPosOffset) {
        this.cameraPosOffset = cameraPosOffset;
    }

    public void setCameraRotOffset(Vec3 cameraRotOffset) {
        this.cameraRotOffset = cameraRotOffset;
    }

    private Vec3 lerpVec3(Vec3 a, Vec3 b, float t) {
        return new Vec3(
                Mth.lerp(t, (float) a.x, (float) b.x),
                Mth.lerp(t, (float) a.y, (float) b.y),
                Mth.lerp(t, (float) a.z, (float) b.z)
        );
    }

    private float lerpAngle(float a, float b, float t) {
        float diff = Mth.wrapDegrees(b - a);
        return a + diff * t;
    }

    private void doCameraStuff(float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) return;

        Vec3 transformed = player.position();
        Vec3 dir = player.getViewVector(mc.getTimer().getGameTimeDeltaPartialTick(true));
        Vec3 normalizeDir = dir.subtract(0,dir.y,0).normalize();

        Vec3 eyePos = player.position().add(0, player.getEyeHeight(), 0);

        Vec3 baseCam = transformed.add(0, 2, 0)
                .add((normalizeDir.x * 2) + this.cameraPosOffset.x,
                        (-0.6 + (Math.sin((this.localTicks + partialTicks) * 0.1) * 0.03f)) + this.cameraPosOffset.y,
                        (normalizeDir.z * 2)+ + this.cameraPosOffset.z);

        Vec3 horizontalForward = new Vec3(normalizeDir.x, 0, normalizeDir.z);
        if (horizontalForward.length() < 1e-6) {
            double yawRad = Math.toRadians(player.getYRot());
            horizontalForward = new Vec3(-Math.sin(yawRad), 0.0, Math.cos(yawRad));
        } else {
            horizontalForward = horizontalForward.normalize();
        }
        Vec3 right = new Vec3(-horizontalForward.z, 0, horizontalForward.x).normalize();

        Vec3 camPosFinal = baseCam.add(right.scale(-1.0));


        float[] py = CameraCynematicsHelper.lookAt(camPosFinal, eyePos);
        float pitch = py[0];
        float yaw = py[1];
        if (cameraAnimating && this.isCameraTransition()) {
            float progress = (cameraAnimTicks + partialTicks) / (float) animationDurationTicks;
            progress = Mth.clamp(progress, 0f, 1f);
            float eased = Easing.EASE_IN_OUT_CIRC.ease(progress);
            Vec3 curPos = lerpVec3(cameraOldPos, camPosFinal, eased);

            float curPitch = lerpAngle((float) cameraOldRot.x,
                    (float) (0 + 9f + (Math.cos((this.localTicks + partialTicks) * 0.1) * 0.4)),
                    eased);
            float curYaw   = lerpAngle((float) cameraOldRot.y,
                    (float) (yaw + 30f - (Math.sin((this.localTicks + partialTicks) * 0.1) * 0.4)),
                    eased);

            CameraCynematicsHelper.currentCameraPosition = curPos;
            CameraCynematicsHelper.currentCameraRotation = new Vec3(curPitch + this.cameraRotOffset.x,
                    curYaw + this.cameraRotOffset.y,
                    0f + this.cameraRotOffset.z);
            CameraCynematicsHelper.isCameraOverriden = true;
            return;
        } else {
                CameraCynematicsHelper.currentCameraPosition = camPosFinal;
                CameraCynematicsHelper.currentCameraRotation = new Vec3(0 + 9f + (Math.cos((this.localTicks + partialTicks) * 0.1) * 0.4) + this.cameraRotOffset.x,
                        yaw + 30f - (Math.sin((this.localTicks + partialTicks) * 0.1) * 0.4) + this.cameraRotOffset.y,
                        0 + this.cameraRotOffset.z);
        }
        CameraCynematicsHelper.isCameraOverriden = true;
        if (this.nextInterface != null) {
            if (this.nextInterface instanceof CameraCyberInterfaceScreen camInterface) {
                camInterface.cameraPrePos = CameraCynematicsHelper.currentCameraPosition;
                camInterface.cameraPreRot = CameraCynematicsHelper.currentCameraRotation;
            }
        }

    }

    public void animatePlayerModel(Player player , float limbSwing, float limbSwingAmount,
                                   float ageInTicks, float netHeadYaw, float headPitch,
                                   ModelPart head, ModelPart body, ModelPart rightArm,
                                   ModelPart leftArm, ModelPart rightLeg, ModelPart leftLeg, ModelPart hat) {
            float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);

            float addedTicks = (this.tickCount + partialTicks);

            rightArm.setRotation((float) Math.toRadians(-Math.cos(addedTicks * 0.1)*10f - 5f),
                    (float) Math.toRadians(Math.sin(addedTicks * 0.1)*10f - 5f),
                    (float) Math.toRadians(-Math.cos(addedTicks * 0.1)*10f + 10f));

            rightArm.setPos(rightArm.getInitialPose().x,
                    (float) (rightArm.getInitialPose().y -(Math.sin(addedTicks * 0.1)*0.3f + 0.3f)),
                    rightArm.getInitialPose().z);

            leftArm.setRotation((float) -Math.toRadians(-Math.cos(addedTicks * 0.1)*10f - 5f),
                    (float) -Math.toRadians(Math.sin(addedTicks * 0.1)*10f - 5f),
                    (float) -Math.toRadians(-Math.cos(addedTicks * 0.1)*10f + 10f));

            leftArm.setPos(leftArm.getInitialPose().x,
                    (float) (leftArm.getInitialPose().y + (Math.sin(addedTicks * 0.1)*0.3f + 0.3f)),
                    leftArm.getInitialPose().z);

        double radians = Math.toRadians(Math.cos(addedTicks * 0.1) * 3f - 20);
        double radians2 = Math.toRadians(-Math.sin(addedTicks * 0.1)*5f  +10f);
        head.setRotation((float) radians2,(float) radians,0);
            hat.setRotation((float) radians2,(float) radians,0);

        body.setPos(body.getInitialPose().x,
                    (float) (body.getInitialPose().y + (Math.sin(addedTicks * 0.1)*0.1f +0.1)),
                    (float) (body.getInitialPose().z + (Math.cos(addedTicks * 0.1)*0.1f)));
            body.setRotation((float) Math.toRadians(-Math.sin(addedTicks * 0.1)*2f - 2f),0,(float) Math.toRadians(Math.cos(addedTicks * 0.1)*1f));

            rightLeg.setPos(rightLeg.getInitialPose().x,rightLeg.getInitialPose().y,
                    (float) (rightLeg.getInitialPose().z - (Math.sin(addedTicks * 0.1)*0.3f + 0.3f)));
            leftLeg.setPos(leftLeg.getInitialPose().x,leftLeg.getInitialPose().y,
                    (float) (leftLeg.getInitialPose().z - (Math.sin(addedTicks * 0.1)*0.3f + 0.3f)));

            rightLeg.setRotation(
                    (float) Math.toRadians(Math.sin(addedTicks * 0.1)*2 + 2),
                    (float) -Math.toRadians(-Math.sin(addedTicks * 0.1)*1f - 15f),
                    (float) -Math.toRadians(0));

        leftLeg.setRotation(
                (float) Math.toRadians(Math.sin(addedTicks * 0.1)*2 + 2),
                (float) Math.toRadians(-Math.sin(addedTicks * 0.1)*1f +5f),
                (float) Math.toRadians(-5));

    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        doCameraStuff(partialTick);
    }

    public boolean isCameraTransition() {
        return true;
    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft mc = Minecraft.getInstance();
        mc.options.setCameraType(CameraType.FIRST_PERSON);
        CameraCynematicsHelper.isCameraOverriden = false;
        cameraAnimating = false;
    }

    @Override
    public boolean showButtonName() {
        return false;
    }

    @Override
    public float getOffsetX() {
        return super.getOffsetX() + 110;
    }
}