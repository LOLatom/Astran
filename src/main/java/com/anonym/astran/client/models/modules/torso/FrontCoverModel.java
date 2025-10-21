package com.anonym.astran.client.models.modules.torso;// Made with Blockbench 5.0.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.anonym.astran.client.models.modules.ModuleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FrontCoverModel extends ModuleModel {

	private final ModelPart FrontCover;

	public FrontCoverModel(ModelPart root) {
		this.FrontCover = root.getChild("FrontCover");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition FrontCover = partdefinition.addOrReplaceChild("FrontCover", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0722F, 0.5193F));

		PartDefinition cube_r1 = FrontCover.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 10).addBox(-1.5F, -2.5F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(3.5F, 5.4278F, -0.2693F, 0.0873F, -0.3491F, 0.0F));

		PartDefinition cube_r2 = FrontCover.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 10).addBox(-2.5F, -2.5F, -1.0F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-3.5F, 5.4278F, -0.2693F, 0.0873F, 0.3491F, 0.0F));

		PartDefinition cube_r3 = FrontCover.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 22).addBox(5.0F, -3.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.5F))
		.texOffs(0, 22).mirror().addBox(11.4F, -3.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.5F)).mirror(false)
		.texOffs(0, 18).addBox(2.7F, -2.075F, -1.5F, 12.0F, 1.0F, 3.0F, new CubeDeformation(0.5F))
		.texOffs(0, 0).addBox(2.7F, -3.5F, -1.5F, 12.0F, 7.0F, 3.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-8.7F, -2.5722F, -0.0193F, -0.1222F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public ModelPart getMainPart() {
		return this.FrontCover;
	}
}