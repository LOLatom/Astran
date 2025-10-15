package com.anonym.astran.systems.gui.theinterface;

import com.anonym.astran.Astran;
import com.anonym.astran.helpers.PickingHelper;
import com.anonym.astran.helpers.ScreenHelper;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.assembly.IAssemblyComponent;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.assembly.network.AssembleModulePayload;
import com.anonym.astran.systems.gui.objects.Anvil3DObject;
import com.anonym.astran.systems.gui.objects.Hammer3DObject;
import com.anonym.astran.systems.gui.scene.Scene3D;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Random;

public class AssemblyMiniGame extends Screen {

    private Scene3D scene;
    public int passedTick = 0;

    public float shake = 0f;

    public float spaceBarProgress = 0f;
    public boolean spaceBar = false;

    public float assemblyProgress = 0f;
    public int tickCountSinceEnded = 0;

    public boolean tutorialDone = false;

    public AssemblyAbstractRecipe recipe;
    public CyberModule module;
    public ItemStack renderedStack;
    public Random rdm = new Random();
    public LinkedHashMap<String, ItemStack> selectedIngredients;

    private static final ResourceLocation BOARD_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID, "textures/gui/object/quality_board.png");


    public AssemblyMiniGame(AssemblyAbstractRecipe assembly, CyberModule module, LinkedHashMap<String, ItemStack> selectedIngredients) {
        super(Component.nullToEmpty("ANVIL"));
        this.selectedIngredients = selectedIngredients;
        this.recipe = assembly;
        this.module = module;
        for (ItemStack component : assembly.getNamedIngredients().values().stream().toList().get(0)) {
            if (component.getItem() instanceof IAssemblyComponent assemblyComponent) {
                //System.out.println(module.getMaterials().values().stream().toList().get(0));
                if (module.getMaterials().values().stream().toList().get(0) != null) {
                    if (assemblyComponent.getMaterial().getMaterialID().equals(module.getMaterials().values().stream().toList().get(0).getMaterialID())) {
                        this.renderedStack = component;
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void init() {
        super.init();
        this.scene = new Scene3D();

        this.scene.addObject(new Anvil3DObject(0f,-0.5f,-0.2f,2f,2f,2f,this));

        this.scene.addObject(new Hammer3DObject(0f,-0.5f,-0.2f,2f,2f,2f,this));
        Minecraft mc = Minecraft.getInstance();
        mc.options.hideGui = true;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

            if (this.spaceBar && (this.assemblyProgress < 1 || this.tickCountSinceEnded > 20)) {
                    this.spaceBarProgress = (float) Math.clamp(this.spaceBarProgress + 0.1f, 0f, 1f);
                    if (this.spaceBarProgress < 1) {

                    }
            } else if (this.spaceBarProgress > 0) {
                this.spaceBarProgress = (float) Math.clamp(this.spaceBarProgress - 0.1f, 0f, 1f);
                if (this.assemblyProgress == 1 && this.tickCountSinceEnded > 20)  {
                    Minecraft.getInstance().getConnection().send(new AssembleModulePayload(this.recipe.getAssemblyID(),this.selectedIngredients));
                    this.onClose();
                }
            }
        if (this.shake > 0) {
            this.shake = Math.clamp(this.shake - 0.1f,0f,1f);
        }
        float addedTicks = Minecraft.getInstance().player.tickCount + passedTick;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate((this.width/2) - 110,(this.height/2) - 20,0);
        guiGraphics.pose().translate(Math.sin(addedTicks*0.02)*5,Math.cos(addedTicks*0.02)*5,0);
        guiGraphics.pose().translate(
                Math.sin(addedTicks*0.5)*((this.shake*4)*(this.assemblyProgress)),
                -Math.cos(addedTicks*0.5)*((this.shake*4)*(this.assemblyProgress)),
                0);

        guiGraphics.pose().scale(2f,2f,1);
        guiGraphics.pose().mulPose(Axis.ZP.rotationDegrees(5));
        guiGraphics.blit(BOARD_TEXTURE,-41,-49,0,0,82,99,82,99);
        guiGraphics.drawString(Minecraft.getInstance().font, "QUALITY",-20,-35, Color.WHITE.getRGB());
        float size = Minecraft.getInstance().font.width(this.module.getQuality().name());
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(- (size/2),0,0);
        Color color = Color.WHITE;
        String percentage = "100%";
        switch (this.module.getQuality()) {
            case GOOD -> {
                color = Color.GREEN;
                percentage = "25%";
            }
            case LESSER -> {
                color = Color.GRAY;
                percentage = "10%";

            }
            case NORMAL -> {
                color = Color.WHITE;
                percentage = "50%";

            }
            case WELL_FORGED -> {
                color = Color.cyan;
                percentage = "14.3%";

            }
            case PERFECT -> {
                float r = (float) (0.5+ Math.sin(addedTicks*0.1)*0.5);
                float g = (float) (0.5+ Math.cos(addedTicks*0.1)*0.5);

                color = new Color(0.2f + (r*0.4f),(g*0.4f),0.4f + (r*0.4f));
                percentage = "0.2%";
            }
            case null, default -> color = Color.WHITE;
        }
        guiGraphics.drawString(Minecraft.getInstance().font, this.module.getQuality().name(),0,-23, color.getRGB());
        guiGraphics.pose().popPose();
        size = Minecraft.getInstance().font.width(percentage);
        guiGraphics.pose().translate(- (size/2),0,0);

        guiGraphics.drawString(Minecraft.getInstance().font, percentage,0,-10, color.getRGB());

        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        if (this.tickCountSinceEnded > 20) {
            guiGraphics.pose().translate(this.width/2,this.height/2,0);
            guiGraphics.pose().scale(2,2,1);
            Color bouncedColor = new Color(1f,1f,1f,(float)(0.8*( 0.5f + (Math.sin((this.tickCountSinceEnded-20 + passedTick)*0.05)*0.5))));
            guiGraphics.drawString(Minecraft.getInstance().font, "Press SPACE to confirm",-55,0, bouncedColor.getRGB());

        } else if (!this.tutorialDone) {
            guiGraphics.pose().translate(this.width/2,this.height/2,0);
            guiGraphics.pose().translate(Math.sin(addedTicks)*0.2f,Math.cos(addedTicks)*0.2f,0);
            guiGraphics.pose().scale(2,2,1);

            guiGraphics.drawString(Minecraft.getInstance().font, "Press SPACE to hit the metal",-60,0, Color.WHITE.getRGB());
        }


        guiGraphics.pose().popPose();

        PickingHelper.setup3DPerspective(70.0F,0.001f,1000f ,-1.7F);

        Matrix4f projection = PickingHelper.LAST_PROJECTION;
        Matrix4f view = PickingHelper.LAST_VIEW;


        Vector3f topLight = new Vector3f(0.2F, 1.0F, -0.7F);
        topLight.normalize();

        Vector3f topLight2 = new Vector3f(-0.2F, 1.0F, 8.7F);
        topLight2.normalize();

        RenderSystem.setShaderLights(topLight, topLight2);



        scene.setSceneTransform(0f, 0f, 1.62f,
                0f, 0f, 0f,
                0.0663f);

        scene.updateHover(mouseX, mouseY, projection, view);

        scene.renderWithPose(guiGraphics, partialTick);

        guiGraphics.flush();
        ScreenHelper.restoreOrthographic();


    }

    @Override
    public void onClose() {
        super.onClose();
        Minecraft mc = Minecraft.getInstance();
        mc.options.hideGui = false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {

        if (keyCode == GLFW.GLFW_KEY_SPACE) {
            this.spaceBar = true;
            this.tutorialDone = true;
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {

        if (keyCode == GLFW.GLFW_KEY_SPACE) {
            this.spaceBar = false;
            return true;
        }

        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public void tick() {
        super.tick();
        this.passedTick++;
        this.scene.tick();
        if (this.assemblyProgress == 1) {
            this.tickCountSinceEnded++;
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.scene.mouseClicked(mouseX, mouseY, button)) return true;
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
