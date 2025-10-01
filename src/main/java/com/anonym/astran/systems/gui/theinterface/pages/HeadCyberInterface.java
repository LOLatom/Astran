package com.anonym.astran.systems.gui.theinterface.pages;

import com.anonym.astran.systems.gui.theinterface.CameraCyberInterfaceScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.glfw.GLFW;

public class HeadCyberInterface extends CameraCyberInterfaceScreen {

    private float headYawInc = 0f;
    private float headPitchInc = 0f;
    private float headYawIncOld = 0f;
    private float headPitchIncOld = 0f;
    private boolean keyW, keyS, keyA, keyD;


    public HeadCyberInterface() {
        super(8, 9, false);
        Player player = Minecraft.getInstance().player;
        Vec3 dir = player.getViewVector(Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true));
        Vec3 normalizeDir = dir.subtract(0,dir.y,0).normalize();

        this.setCameraPosOffset(new Vec3(-(normalizeDir.x * 0.6),0.8f,-(normalizeDir.z * 0.6)));
        this.setCameraRotOffset(new Vec3(17.5f,2,0));
    }

    @Override
    public void animatePlayerModel(Player player, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, ModelPart head, ModelPart body, ModelPart rightArm, ModelPart leftArm, ModelPart rightLeg, ModelPart leftLeg) {
        super.animatePlayerModel(player, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, head, body, rightArm, leftArm, rightLeg, leftLeg);
        float partialTicks = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);

        float addedTicks = (this.tickCount + partialTicks);


        float headP = Mth.lerp(partialTicks, this.headPitchIncOld, this.headPitchInc);
        float headY   = Mth.lerp(partialTicks, this.headYawIncOld, this.headYawInc);


        head.setRotation((float) Math.toRadians(-35f  + (Math.cos(addedTicks * 0.1)*5.5f) + (headP*35f)),(float) Math.toRadians((headY * 40f)-25f),(float) Math.toRadians(10f + (Math.sin(addedTicks * 0.1)*5.5f)));
        rightArm.setRotation((float) Math.toRadians(25f),0,(float) Math.toRadians(-9f));
        leftArm.setRotation((float) Math.toRadians(25f),0,(float) Math.toRadians(9f));

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_W) this.keyW = true;
        if (keyCode == GLFW.GLFW_KEY_S) this.keyS = true;
        if (keyCode == GLFW.GLFW_KEY_A) this.keyA = true;
        if (keyCode == GLFW.GLFW_KEY_D) this.keyD = true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_W) this.keyW = false;
        if (keyCode == GLFW.GLFW_KEY_S) this.keyS = false;
        if (keyCode == GLFW.GLFW_KEY_A) this.keyA = false;
        if (keyCode == GLFW.GLFW_KEY_D) this.keyD = false;
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public void tick() {
        super.tick();

        this.headYawIncOld = this.headYawInc;
        this.headPitchIncOld = this.headPitchInc;
        float speed = 0.2f;
        float returnSpeed = 0.1f;

        if (this.keyW) this.headPitchInc -= speed;
        if (this.keyS) this.headPitchInc += speed;
        if (this.keyA) this.headYawInc   += speed;
        if (this.keyD) this.headYawInc   -= speed;

        this.headPitchInc = Math.clamp(this.headPitchInc, -1f, 1f);
        this.headYawInc   = Math.clamp(this.headYawInc, -1f, 1f);
        if (!this.keyW && !this.keyS) this.headPitchInc = Mth.lerp(returnSpeed, this.headPitchInc, 0f);
        if (!this.keyA && !this.keyD) this.headYawInc   = Mth.lerp(returnSpeed, this.headYawInc, 0f);
    }

    @Override
    public boolean isCameraTransition() {
        return true;
    }
}
