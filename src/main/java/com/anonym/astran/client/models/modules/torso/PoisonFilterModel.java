package com.anonym.astran.client.models.modules.torso;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.anonym.astran.client.models.modules.ModuleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class PoisonFilterModel extends ModuleModel {
	private final ModelPart main;

	public PoisonFilterModel(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.5F, -1.0F, 6.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(16, 0).addBox(-3.0F, -4.5F, -1.0F, 6.0F, 9.0F, 2.0F, new CubeDeformation(-0.1F))
				.texOffs(0, 11).addBox(-3.0F, -4.5F, -1.0F, 6.0F, 9.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public ModelPart getMainPart() {
		return this.main;
	}
}