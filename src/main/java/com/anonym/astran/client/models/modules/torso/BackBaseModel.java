package com.anonym.astran.client.models.modules.torso;// Made with Blockbench 5.0.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.anonym.astran.client.models.modules.ModuleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class BackBaseModel extends ModuleModel {
	private final ModelPart BackBase;

	public BackBaseModel(ModelPart root) {
		this.BackBase = root.getChild("BackBase");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition BackBase = partdefinition.addOrReplaceChild("BackBase", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, -1.5F, 12.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public ModelPart getMainPart() {
		return this.BackBase;
	}
}