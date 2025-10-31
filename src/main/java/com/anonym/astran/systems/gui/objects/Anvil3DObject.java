package com.anonym.astran.systems.gui.objects;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.LayerRegistry;
import com.anonym.astran.client.models.misc.MinigameAnvil;
import com.anonym.astran.api.swiff.scene.SceneObject;
import com.anonym.astran.systems.gui.theinterface.AssemblyMiniGame;
import com.mojang.math.Axis;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;

public class Anvil3DObject extends SceneObject {

    private MinigameAnvil anvil;

    private ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/gui/object/minigame_anvil_texture.png");

    private AssemblyMiniGame screen;

    public Anvil3DObject(float x, float y, float z, float width, float height, float depth, AssemblyMiniGame screen) {
        super(x, y, z, width, height, depth);
        this.screen = screen;
        this.anvil = new MinigameAnvil(Minecraft.getInstance().getEntityModels().bakeLayer(LayerRegistry.MINIGAME_ANVIL));
        this.rotX = 25f;
    }

    @Override
    public void onRender(GuiGraphics graphics, float partialTicks) {
        graphics.pose().pushPose();

        graphics.pose().mulPose(Axis.XP.rotationDegrees(180));
        graphics.pose().mulPose(Axis.ZP.rotationDegrees((float) (Math.sin((Minecraft.getInstance().player.tickCount + partialTicks)*0.1)*5f)));
        graphics.pose().mulPose(Axis.XP.rotationDegrees((float) (-Math.cos((Minecraft.getInstance().player.tickCount + partialTicks)*0.1)*5f)));

        graphics.pose().translate((float) (Math.sin((Minecraft.getInstance().player.tickCount + partialTicks)*(1 +((1-this.screen.assemblyProgress)*0.9)))*((this.screen.shake *0.1)*this.screen.assemblyProgress)),0,
                (float) (-Math.cos((Minecraft.getInstance().player.tickCount + partialTicks)*(1 +((1-this.screen.assemblyProgress)*0.9)))*((this.screen.shake *0.1)*this.screen.assemblyProgress)));

        this.anvil.getModelPart()
                .render(graphics.pose(),
                        graphics.bufferSource().getBuffer(RenderType.entityCutoutNoCull(TEXTURE)),
                        15728880,
                        OverlayTexture.NO_OVERLAY);

        graphics.flush();


        if (this.screen.renderedStack != null) {
            float eased = Easing.EASE_IN_OUT_QUAD.ease(this.screen.assemblyProgress);

            graphics.setColor(1+(4.8f * eased),1+ (0.8f * eased),1f,1f);

            graphics.pose().translate(-0.1,-0.37,0.15);

            graphics.pose().mulPose(Axis.ZP.rotationDegrees(90));
            graphics.pose().mulPose(Axis.XP.rotationDegrees(115));

            Minecraft.getInstance().getItemRenderer().render(
                    this.screen.renderedStack,
                    ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
                    false, graphics.pose(), graphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY,
                    Minecraft.getInstance().getItemRenderer().getModel(this.screen.renderedStack,
                            null, null, 0));

            graphics.flush();
            graphics.setColor(1f,1f,1f,1f);


        }
        graphics.pose().popPose();


    }
}
