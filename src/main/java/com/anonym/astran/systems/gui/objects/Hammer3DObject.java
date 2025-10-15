package com.anonym.astran.systems.gui.objects;

import com.anonym.astran.Astran;
import com.anonym.astran.client.layers.LayerRegistry;
import com.anonym.astran.client.models.misc.MinigameAnvil;
import com.anonym.astran.client.models.misc.MinigameHammer;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.gui.scene.Scene3D;
import com.anonym.astran.systems.gui.scene.SceneObject;
import com.anonym.astran.systems.gui.theinterface.AssemblyMiniGame;
import com.mojang.math.Axis;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;

import java.util.Random;

public class Hammer3DObject extends SceneObject {

    private MinigameHammer hammer;

    private ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/gui/object/minigame_hammer_texture.png");

    private AssemblyMiniGame screen;

    public Hammer3DObject(float x, float y, float z, float width, float height, float depth, AssemblyMiniGame screen) {
        super(x, y, z, width, height, depth);
        this.screen = screen;
        this.hammer = new MinigameHammer(Minecraft.getInstance().getEntityModels().bakeLayer(LayerRegistry.MINIGAME_HAMMER));
        this.rotX = 25f;
    }

    @Override
    public void onRender(GuiGraphics graphics, float partialTicks) {

        float eased = Easing.EASE_OUT_BACK.ease(this.screen.spaceBarProgress);
        if (this.screen.spaceBar) {
            System.out.println(this.screen.spaceBarProgress);
        }
        if (this.screen.spaceBarProgress >= 0.9D && this.screen.spaceBarProgress < 1D) {
            Random rdm = new Random();
            if (this.screen.assemblyProgress >=1) {
                Minecraft.getInstance().player.playSound(SoundEvents.ANVIL_USE, 1, 1);

            } else {
                Minecraft.getInstance().player.playSound(SoundEvents.ANVIL_LAND, 1, 1 + (rdm.nextFloat() * 0.2f));
                int r = rdm.nextInt(1000);
                this.screen.module = this.screen.module.withQuality(
                        r < 3 && r > 0 ? CyberModule.Quality.PERFECT :
                                r < 150 && r > 3 ? CyberModule.Quality.WELL_FORGED :
                                        r < 400 && r > 150 ? CyberModule.Quality.GOOD :
                                                r < 900 && r > 400 ? CyberModule.Quality.NORMAL : CyberModule.Quality.LESSER);

            }
            this.screen.assemblyProgress = Math.clamp(this.screen.assemblyProgress + 0.1f,0f,1f);
            this.screen.shake = 0.8f;
        }

        graphics.pose().pushPose();
        graphics.pose().translate(0.8,0.8,0.2);
        graphics.pose().mulPose(Axis.XP.rotationDegrees(180));
        graphics.pose().mulPose(Axis.YP.rotationDegrees(70));
        graphics.pose().mulPose(Axis.ZP.rotationDegrees(((float) (-Math.cos((Minecraft.getInstance().player.tickCount + partialTicks)*0.1)*5f))*(1f - eased)));
        graphics.pose().translate(0,
                ((float) (Math.sin((Minecraft.getInstance().player.tickCount + partialTicks)*0.1)*0.035f))*(1f - eased),
                0);

        graphics.pose().translate(-0.5 * eased,0.25 * eased,-0.6 * eased);
        graphics.pose().mulPose(Axis.ZP.rotationDegrees(-90 * eased));

        this.hammer.getModelPart()
                .render(graphics.pose(),
                        graphics.bufferSource().getBuffer(RenderType.entityCutoutNoCull(TEXTURE)),
                        15728880,
                        OverlayTexture.NO_OVERLAY);
        graphics.flush();

        graphics.pose().popPose();

    }
}
