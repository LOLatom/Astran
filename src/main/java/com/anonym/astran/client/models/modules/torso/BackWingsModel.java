package com.anonym.astran.client.models.modules.torso;// Made with Blockbench 5.0.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.anonym.astran.client.models.modules.ModuleModel;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.AbstractClientPlayer;

public class BackWingsModel extends ModuleModel {
	private final ModelPart BackWings;
	private final ModelPart right_wings;
	private final ModelPart right_wing;
	private final ModelPart right_wing2;
	private final ModelPart right_wing3;
	private final ModelPart left_wings;
	private final ModelPart left_wing;
	private final ModelPart left_wing2;
	private final ModelPart left_wing3;

	public BackWingsModel(ModelPart root) {
		this.BackWings = root.getChild("BackWings");
		this.right_wings = this.BackWings.getChild("right_wings");
		this.right_wing = this.right_wings.getChild("right_wing");
		this.right_wing2 = this.right_wings.getChild("right_wing2");
		this.right_wing3 = this.right_wings.getChild("right_wing3");
		this.left_wings = this.BackWings.getChild("left_wings");
		this.left_wing = this.left_wings.getChild("left_wing");
		this.left_wing2 = this.left_wings.getChild("left_wing2");
		this.left_wing3 = this.left_wings.getChild("left_wing3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition BackWings = partdefinition.addOrReplaceChild("BackWings", CubeListBuilder.create().texOffs(0, 6).addBox(-2.0F, -4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 16).addBox(-2.0F, -6.0F, -6.0F, 4.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_wings = BackWings.addOrReplaceChild("right_wings", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition right_wing = right_wings.addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(0, 0).addBox(9.0F, -4.5F, 0.0F, 15.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(12, 16).addBox(0.0F, -1.5F, 0.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 6).addBox(5.0F, -4.5F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -4.5F, -2.0F, -0.3008F, 0.4353F, -0.5906F));

		PartDefinition right_wing2 = right_wings.addOrReplaceChild("right_wing2", CubeListBuilder.create().texOffs(0, 0).addBox(9.0F, -4.5F, 0.0F, 15.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(12, 16).addBox(0.0F, -1.5F, 0.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 6).addBox(5.0F, -4.5F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 1.5F, -2.0F, 0.281F, 0.4478F, 0.588F));

		PartDefinition right_wing3 = right_wings.addOrReplaceChild("right_wing3", CubeListBuilder.create().texOffs(0, 0).addBox(9.0F, -4.5F, 0.0F, 15.0F, 6.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(12, 16).addBox(0.0F, -1.5F, 0.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 6).addBox(5.0F, -4.5F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -1.5F, -2.0F, 0.0F, 0.48F, 0.0F));

		PartDefinition left_wings = BackWings.addOrReplaceChild("left_wings", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -3.0F));

		PartDefinition left_wing = left_wings.addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-24.0F, -4.5F, 0.0F, 15.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 16).mirror().addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(16, 6).mirror().addBox(-9.0F, -4.5F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, -4.5F, -2.0F, -0.3008F, -0.4353F, 0.5906F));

		PartDefinition left_wing2 = left_wings.addOrReplaceChild("left_wing2", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-24.0F, -4.5F, 0.0F, 15.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 16).mirror().addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(16, 6).mirror().addBox(-9.0F, -4.5F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 1.5F, -2.0F, 0.281F, -0.4478F, -0.588F));

		PartDefinition left_wing3 = left_wings.addOrReplaceChild("left_wing3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-24.0F, -4.5F, 0.0F, 15.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(12, 16).mirror().addBox(-5.0F, -1.5F, 0.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(16, 6).mirror().addBox(-9.0F, -4.5F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.0F, -1.5F, -2.0F, 0.0F, -0.48F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void animate(PoseStack poseStack, AbstractClientPlayer player, CyberModule module, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		this.left_wings.yRot = (float) Math.toRadians(-22.5 * limbSwingAmount);
		this.right_wings.yRot = (float) Math.toRadians(22.5 * limbSwingAmount);
		float v1 = (float) player.getDeltaMovement().y; //Is this a reference... hhehehehe
		this.left_wing.zRot = (float) Math.toRadians(-22.5 * v1) + this.left_wing.getInitialPose().zRot;
		this.left_wing2.zRot = (float) Math.toRadians(-22.5 * v1) + this.left_wing2.getInitialPose().zRot;
		this.left_wing3.zRot = (float) Math.toRadians(-22.5 * v1) + this.left_wing3.getInitialPose().zRot;
		this.right_wing.zRot = (float) Math.toRadians(22.5 * v1) + this.right_wing.getInitialPose().zRot;
		this.right_wing2.zRot = (float) Math.toRadians(22.5 * v1) + this.right_wing2.getInitialPose().zRot;
		this.right_wing3.zRot = (float) Math.toRadians(22.5 * v1) + this.right_wing3.getInitialPose().zRot;
	}

	@Override
	public ModelPart getMainPart() {
		return this.BackWings;
	}
}