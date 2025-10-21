package com.anonym.astran.client.models.modules.torso;// Made with Blockbench 5.0.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.anonym.astran.client.models.modules.ModuleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FrontBaseModel extends ModuleModel {
	private final ModelPart FrontBase;

	public FrontBaseModel(ModelPart root) {
		this.FrontBase = root.getChild("FrontBase");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition FrontBase = partdefinition.addOrReplaceChild("FrontBase", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -5.3333F, 0.6667F, 12.0F, 15.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(12, 24).addBox(-2.0F, -2.3333F, -0.3333F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(16, 16).addBox(-6.0F, -5.3333F, -1.3333F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(22, 24).addBox(-2.0F, -5.3333F, -1.3333F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 24).addBox(2.0F, -5.3333F, -1.3333F, 4.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 16).addBox(-3.0F, 3.6667F, -1.3333F, 6.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public ModelPart getMainPart() {
		return this.FrontBase;
	}
}