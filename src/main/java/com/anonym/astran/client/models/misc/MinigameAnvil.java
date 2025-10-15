package com.anonym.astran.client.models.misc;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class MinigameAnvil {
	private final ModelPart main;

	public MinigameAnvil(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-10.5F, -4.0F, -1.0F, 21.0F, 8.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(0, 42).addBox(-10.5F, -4.0F, -1.0F, 21.0F, 8.0F, 11.0F, new CubeDeformation(0.2F))
		.texOffs(0, 19).addBox(-8.5F, 7.0F, 0.0F, 17.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-4.5F, 4.0F, 1.0F, 9.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	public ModelPart getModelPart() {
		return this.main;
	}

}