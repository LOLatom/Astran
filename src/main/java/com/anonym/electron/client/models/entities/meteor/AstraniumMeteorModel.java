package com.anonym.electron.client.models.entities.meteor;

import com.anonym.electron.common.entities.AstraniumMeteor;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class AstraniumMeteorModel<T extends AstraniumMeteor> extends EntityModel<AstraniumMeteor> {
	private final ModelPart main;

	public AstraniumMeteorModel(ModelPart root) {
		this.main = root.getChild("main");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition main = partdefinition.addOrReplaceChild("main", CubeListBuilder.create().texOffs(0, 0).addBox(-24.0F, -64.0F, -24.0F, 48.0F, 64.0F, 48.0F, new CubeDeformation(0.0F))
		.texOffs(0, 112).addBox(-24.0F, -64.0F, -24.0F, 48.0F, 64.0F, 48.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 24.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void setupAnim(AstraniumMeteor entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, int i2) {

	}

}