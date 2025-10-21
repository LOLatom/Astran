package com.anonym.astran.client.models.modules.head;// Made with Blockbench 5.0.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.anonym.astran.client.models.modules.ModuleModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class AztecFaceModel extends ModuleModel {
	private final ModelPart AztecFace;
	private final ModelPart tile;
	private final ModelPart right_tile;
	private final ModelPart left_tile;
	private final ModelPart mask;

	public AztecFaceModel(ModelPart root) {
		this.AztecFace = root.getChild("AztecFace");
		this.tile = this.AztecFace.getChild("tile");
		this.right_tile = this.AztecFace.getChild("right_tile");
		this.left_tile = this.AztecFace.getChild("left_tile");
		this.mask = this.AztecFace.getChild("mask");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition AztecFace = partdefinition.addOrReplaceChild("AztecFace", CubeListBuilder.create().texOffs(0, 18).addBox(-6.0F, -6.0F, -3.0F, 12.0F, 12.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition tile = AztecFace.addOrReplaceChild("tile", CubeListBuilder.create().texOffs(0, 36).addBox(-3.0F, -2.25F, 0.5F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.5F))
				.texOffs(24, 36).addBox(-3.0F, -3.0F, 0.5F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, -5.0F, -4.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition right_tile = AztecFace.addOrReplaceChild("right_tile", CubeListBuilder.create().texOffs(48, 23).addBox(-2.0F, -1.0F, -2.121F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F))
				.texOffs(48, 18).addBox(-2.0F, -1.7575F, -2.121F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(6.25F, -5.0F, -0.5F, 0.4363F, 0.0F, 0.9599F));

		PartDefinition left_tile = AztecFace.addOrReplaceChild("left_tile", CubeListBuilder.create().texOffs(48, 23).mirror().addBox(-2.0F, -1.0F, -2.121F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false)
				.texOffs(48, 18).mirror().addBox(-2.0F, -1.7575F, -2.121F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.5F)).mirror(false), PartPose.offsetAndRotation(-6.25F, -5.0F, -0.5F, 0.4363F, 0.0F, -0.9599F));

		PartDefinition mask = AztecFace.addOrReplaceChild("mask", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 0.0436F, 0.0F, 0.0F));

		PartDefinition cube_r1 = mask.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(28, 46).addBox(-6.0F, -6.0F, 0.5F, 12.0F, 12.0F, 6.0F, new CubeDeformation(0.3F))
				.texOffs(28, 0).addBox(-6.0F, -6.0F, 0.5F, 12.0F, 12.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.5F, 0.0F, -0.3927F, 0.0F));

		PartDefinition cube_r2 = mask.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 46).addBox(-6.0F, -6.0F, 0.5F, 12.0F, 12.0F, 6.0F, new CubeDeformation(0.3F))
				.texOffs(0, 0).addBox(-6.0F, -6.0F, 0.5F, 12.0F, 12.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.5F, 0.0F, 0.3927F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	@Override
	public ModelPart getMainPart() {
		return this.AztecFace;
	}
}