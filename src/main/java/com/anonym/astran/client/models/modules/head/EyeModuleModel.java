package com.anonym.astran.client.models.modules.head;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.anonym.astran.client.models.modules.ModuleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class EyeModuleModel extends ModuleModel {
	private final ModelPart eye;

	public EyeModuleModel(ModelPart root) {
		this.eye = root.getChild("eye");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition eye = partdefinition.addOrReplaceChild("eye", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(6, 4).addBox(-1.0F, -1.0F, -1.2F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
				.texOffs(6, 7).addBox(-0.5F, -0.2F, -1.42F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(8, 0).addBox(-0.5F, -0.2F, -1.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.1F))
				.texOffs(0, 4).addBox(-1.0F, -1.0F, 0.3F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r1 = eye.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 7).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.17F)), PartPose.offsetAndRotation(0.0F, -0.6F, -0.8F, -0.0873F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 16, 16);
	}



	@Override
	public ModelPart getMainPart() {
		return this.eye;
	}
}