package com.anonym.astran.client.models.misc;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MinigameHammer {
	private final ModelPart main;

	public MinigameHammer(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, -6.0F, 1.0F, 11.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 13).addBox(-1.5F, 1.0F, 3.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 22).addBox(-1.5F, 1.0F, 3.0F, 3.0F, 7.0F, 2.0F, new CubeDeformation(0.2F))
				.texOffs(10, 13).addBox(-2.5F, 8.0F, 3.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public ModelPart getModelPart() {
		return this.main;
	}
}