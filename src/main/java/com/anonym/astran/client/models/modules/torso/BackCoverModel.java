package com.anonym.astran.client.models.modules.torso;// Made with Blockbench 5.0.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.anonym.astran.client.models.modules.ModuleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BackCoverModel extends ModuleModel {
	private final ModelPart BackCover;

	public BackCoverModel(ModelPart root) {
		this.BackCover = root.getChild("BackCover");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition BackCover = partdefinition.addOrReplaceChild("BackCover", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = BackCover.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -2.0F, -2.0F, 10.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 0.5F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r2 = BackCover.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 7).addBox(-3.0F, -2.0F, -2.0F, 6.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.25F, 0.35F, 0.1745F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public ModelPart getMainPart() {
		return this.BackCover;
	}
}