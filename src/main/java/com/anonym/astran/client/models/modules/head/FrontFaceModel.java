package com.anonym.astran.client.models.modules.head;// Made with Blockbench 5.0.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.anonym.astran.client.models.modules.ModuleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FrontFaceModel extends ModuleModel {
	private final ModelPart FrontFace;
	private final ModelPart Jaw;

	public FrontFaceModel(ModelPart root) {
		this.FrontFace = root.getChild("FrontFace");
		this.Jaw = this.FrontFace.getChild("Jaw");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition FrontFace = partdefinition.addOrReplaceChild("FrontFace", CubeListBuilder.create().texOffs(0, 13).addBox(-6.0F, -6.0F, 1.0F, 12.0F, 12.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-6.0F, -6.0F, -3.0F, 12.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition Jaw = FrontFace.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(0, 27).addBox(-4.0F, -0.015F, -1.9962F, 8.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(24, 27).addBox(-4.0F, -0.0151F, -1.9962F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.25F, -1.15F, 0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public ModelPart getMainPart() {
		return this.FrontFace;
	}
}