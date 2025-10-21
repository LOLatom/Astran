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
import org.joml.Vector3f;

public class KineticAccumulatorModel extends ModuleModel {
	private final ModelPart KineticAccumulator;
	public final ModelPart rotor;
	private final ModelPart kinetic_storage;
	private final ModelPart kinetic_storage2;
	private final ModelPart kinetic_storage3;

	public KineticAccumulatorModel(ModelPart root) {
		this.KineticAccumulator = root.getChild("KineticAccumulator");
		this.rotor = this.KineticAccumulator.getChild("rotor");
		this.kinetic_storage = this.rotor.getChild("kinetic_storage");
		this.kinetic_storage2 = this.rotor.getChild("kinetic_storage2");
		this.kinetic_storage3 = this.rotor.getChild("kinetic_storage3");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition KineticAccumulator = partdefinition.addOrReplaceChild("KineticAccumulator", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = KineticAccumulator.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.1F, 0.0F, 0.0F, 0.7854F));

		PartDefinition rotor = KineticAccumulator.addOrReplaceChild("rotor", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.5F, 1.75F, 0.0F, 0.0F, 0.5236F));

		PartDefinition kinetic_storage = rotor.addOrReplaceChild("kinetic_storage", CubeListBuilder.create().texOffs(0, 8).addBox(-5.25F, -3.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 0.0F));

		PartDefinition kinetic_storage2 = rotor.addOrReplaceChild("kinetic_storage2", CubeListBuilder.create().texOffs(0, 8).addBox(-5.25F, -3.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, 2.138F));

		PartDefinition kinetic_storage3 = rotor.addOrReplaceChild("kinetic_storage3", CubeListBuilder.create().texOffs(0, 8).addBox(-5.25F, -3.0F, 0.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3927F, -2.0944F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public ModelPart getMainPart() {
		return this.KineticAccumulator;
	}

	@Override
	public void animate(PoseStack poseStack, AbstractClientPlayer player, CyberModule module, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		float rotation = 0f;
		if (module.getAdditionalData().isPresent()) {
			if (module.getAdditionalData().get().contains("storedKineticCharge")) {
				rotation = Math.clamp(module.getAdditionalData().get().getFloat("storedKineticCharge") / 100f, 0f, 1f);
			}
		}
		this.rotor.zRot = (player.tickCount + partialTicks) * rotation;
		this.rotor.setInitialPose(this.rotor.storePose());
	}

}