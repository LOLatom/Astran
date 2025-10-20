package com.anonym.astran.client.models.modules.torso;// Made with Blockbench 5.0.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.anonym.astran.client.models.modules.ModuleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class KineticDistributorModel extends ModuleModel {
	private final ModelPart KineticDistributor;
	private final ModelPart piston;
	private final ModelPart fist;

	public KineticDistributorModel(ModelPart root) {
		this.KineticDistributor = root.getChild("KineticDistributor");
		this.piston = this.KineticDistributor.getChild("piston");
		this.fist = this.piston.getChild("fist");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition KineticDistributor = partdefinition.addOrReplaceChild("KineticDistributor", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-3.0F, -5.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(24, 0).mirror().addBox(-3.0F, -5.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(0, 31).mirror().addBox(-3.0F, -5.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition piston = KineticDistributor.addOrReplaceChild("piston", CubeListBuilder.create().texOffs(0, 23).mirror().addBox(-1.0F, -8.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(18, 21).mirror().addBox(-1.0F, -8.5F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 4.5F, 0.0F));

		PartDefinition cube_r1 = piston.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 13).mirror().addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition fist = piston.addOrReplaceChild("fist", CubeListBuilder.create().texOffs(24, 30).mirror().addBox(0.25F, -1.25F, -3.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 13).mirror().addBox(-2.75F, -2.25F, -3.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.25F, 0.75F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public ModelPart getMainPart() {
		return this.KineticDistributor;
	}
}