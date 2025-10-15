package com.anonym.astran.client.models.modules.torso;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.anonym.astran.client.models.modules.ModuleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class AquaLungsModel extends ModuleModel {
	private final ModelPart main;

	public AquaLungsModel(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-3.2667F, -2.0833F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(12, 7).addBox(-3.2667F, -2.0833F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 7).addBox(0.2333F, -2.0833F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(12, 0).addBox(0.2333F, -2.0833F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 14).addBox(-2.9667F, -2.5833F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
				.texOffs(14, 14).addBox(-2.9667F, -2.5833F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}


	@Override
	public ModelPart getMainPart() {
		return this.main;
	}
}