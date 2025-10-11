package com.anonym.astran.systems.gui.theinterface;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.AstranAttachmentTypeRegistry;
import com.anonym.astran.registries.AstranDataComponentRegistry;
import com.anonym.astran.registries.client.AstranSoundRegistry;
import com.anonym.astran.systems.attachments.SteelHeartReservoirData;
import com.anonym.astran.systems.cybernetics.core.SteelHeartData;
import com.anonym.astran.systems.energy.CoreNodeItem;
import com.anonym.astran.systems.gui.MouseHandlerHelper;
import com.anonym.astran.systems.gui.buttons.DiamondDetectionButton;
import com.anonym.astran.systems.gui.buttons.IGlowModifier;
import com.anonym.astran.systems.gui.buttons.IHasInterfaceName;
import com.anonym.astran.systems.gui.theinterface.pages.HomePageCyberInterface;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.util.Easing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.List;
import java.util.Locale;

public class CyberInterfaceScreen extends Screen {

    private final ResourceLocation SLICED_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,
                    "textures/gui/sliced/9sliced_interface.png");
    private final ResourceLocation INTERFACE_MOUSE =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,
                    "textures/gui/interface/interface_mouse.png");
    private final ResourceLocation INTERFACE_PATTERN =
            ResourceLocation.fromNamespaceAndPath(Astran.MODID,
                    "textures/gui/interface/interface_pattern.png");

    private float interfaceWidth;
    private float interfaceHeight;
    private float initialInterfaceWidth;
    private float initialInterfaceHeight;
    public int tickCount = 0;
    private Vector2f offsetParallax = new Vector2f(0,0);
    private Vector2f mouseOld = new Vector2f(0,0);
    protected CyberInterfaceScreen nextInterface = null;
    private float maxTransitionTick = 0f;
    private int currentTransitionTick = 0;
    private Easing transitionEase = Easing.EASE_IN_BACK;
    private boolean startupAnim = false;
    private AbstractWidget currentFocused = null;

    private Color currentColor = new Color(255,255,255,255);

    public final List<AbstractWidget> glowingWidgets = Lists.newArrayList();

    private CyberInterfaceScreen(boolean hasStartupAnimation) {
        super(Component.empty());
        this.startupAnim = hasStartupAnimation;
    }
    public CyberInterfaceScreen(float interfaceWidth, float interfaceHeight,boolean hasStartupAnimation) {
        this(hasStartupAnimation);
        this.interfaceHeight = interfaceHeight;
        this.interfaceWidth = interfaceWidth;
        this.initialInterfaceHeight = interfaceHeight;
        this.initialInterfaceWidth = interfaceWidth;

        if (Minecraft.getInstance().player.getData(AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR).getSteelHeart().isPresent()) {
            ItemStack stack = Minecraft.getInstance().player.getData(AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR).getSteelHeart().get();
            SteelHeartData data = stack.get(AstranDataComponentRegistry.STEEL_HEART_DATA);
            if (data.firstNode().get().getItem() instanceof CoreNodeItem item) {
                this.currentColor = item.getNodeColor();
            }
        }

    }
    public CyberInterfaceScreen(float interfaceWidth, float interfaceHeight) {
        this(interfaceWidth,interfaceHeight,false);
    }
    public CyberInterfaceScreen() {
        this(0f,0f);
    }


    protected <T extends AbstractWidget & GuiEventListener & Renderable & NarratableEntry & IGlowModifier> T addGlowingRenderable(T widget) {
        this.renderables.add(widget);
        this.glowingWidgets.add(widget);
        return (T)this.addWidget(widget);
    }
    protected <T extends AbstractWidget & Renderable> T addGlowingRenderableOnly(T renderable) {
        this.renderables.add(renderable);
        this.glowingWidgets.add(renderable);
        return renderable;
    }

    public void addTransition(CyberInterfaceScreen transitionedScreen, float transitionTime , Easing transitionEase)  {
        this.transitionEase = transitionEase;
        this.maxTransitionTick = transitionTime;
        this.nextInterface = transitionedScreen;

        this.initialInterfaceWidth = this.interfaceWidth;
        this.initialInterfaceHeight = this.interfaceHeight;
        MouseHandlerHelper.isMouseReleaseDisabled = true;

    }

    @Override
    protected void init() {
        super.init();
        MouseHandlerHelper.isMouseReleaseDisabled = false;
    }

    public boolean hasStartUpAnimation() {
        return this.startupAnim;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        float offsetX = getOffsetX();
        float offsetY = getOffsetY();

        if (this.nextInterface != null) {
            float normalizedCount = Math.clamp(this.currentTransitionTick + partialTick, 0, this.maxTransitionTick) / this.maxTransitionTick;
            normalizedCount = this.transitionEase.ease(normalizedCount);

            offsetX = getOffsetX() + ((this.nextInterface.getOffsetX() - offsetX) * normalizedCount);
            offsetY = getOffsetY() + ((this.nextInterface.getOffsetY() - offsetY) * normalizedCount);

            float progressX = (this.nextInterface.getInterfaceWidth() - this.initialInterfaceWidth) * normalizedCount;
            float progressY = (this.initialInterfaceHeight - this.nextInterface.getInterfaceHeight()) * normalizedCount;

            this.nextInterface.mouseOld.set(this.mouseOld.x,this.mouseOld.y);
            this.nextInterface.offsetParallax = this.offsetParallax;

            setInterfaceWidth(this.initialInterfaceWidth + progressX);
            setInterfaceHeight(this.initialInterfaceHeight -  progressY);
        } else {
            if (this.hasStartUpAnimation()) {
                float normalizedCount = Math.clamp(this.tickCount + partialTick, 0, 20) / 20;
                normalizedCount = Easing.EASE_OUT_BACK.ease(normalizedCount);

                float progressX = this.initialInterfaceWidth * normalizedCount;
                float progressY = this.initialInterfaceHeight * normalizedCount;

                setInterfaceWidth(progressX);
                setInterfaceHeight(progressY);

            }

        }

        float x = (float) offsetX - (((144-16) + (16*interfaceWidth))/2);
        float y = (float) offsetY - (((112-16) + (16*interfaceHeight))/2);





        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(x,y,0);
        guiGraphics.blit(SLICED_TEXTURE,0,0,0,0,64,48,144,112);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(64,0,0);
        guiGraphics.pose().scale(interfaceWidth,1,1);
        guiGraphics.blit(SLICED_TEXTURE,0,0,64,0,16,48,144,112);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(64 + (16 * interfaceWidth),0,0);
        guiGraphics.blit(SLICED_TEXTURE,0,0,64+16,0,64,48,144,112);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0,48,0);
        guiGraphics.pose().scale(1,interfaceHeight,1);
        guiGraphics.blit(SLICED_TEXTURE,0,0,0,48,64,16,144,112);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(64,48,0);
        guiGraphics.pose().scale(interfaceWidth,interfaceHeight,1);
        guiGraphics.blit(SLICED_TEXTURE,0,0,64,48,16,16,144,112);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(64 + (16 * interfaceWidth),48,0);
        guiGraphics.pose().scale(1,interfaceHeight,1);
        guiGraphics.blit(SLICED_TEXTURE,0,0,64+16,48,64,16,144,112);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0,48+(16*interfaceHeight),0);
        guiGraphics.blit(SLICED_TEXTURE,0,0,0,48+16,64,48,144,112);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(64,48+(16*interfaceHeight),0);
        guiGraphics.pose().scale(interfaceWidth,1,1);
        guiGraphics.blit(SLICED_TEXTURE,0,0,64,48+16,16,48,144,112);
        guiGraphics.pose().popPose();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(64 + (16 * interfaceWidth),48+(16*interfaceHeight),0);
        guiGraphics.blit(SLICED_TEXTURE,0,0,64+16,48+16,64,48,144,112);
        guiGraphics.pose().popPose();
        guiGraphics.pose().popPose();
        double scale = Minecraft.getInstance().getWindow().getGuiScale();
        int scWidth = (int) ((64+64+(16*interfaceWidth)-36)*scale);
        int scHeight = (int) ((48+48+(16*interfaceHeight)-51)*scale);

        int scStartX = (int) ((x+18)*scale);
        int scStartY = (int) (Minecraft.getInstance().getWindow().getHeight() - (((y+33) + scHeight/scale) * scale));

        RenderSystem.enableScissor(scStartX,scStartY,scWidth,scHeight);
        renderCut(guiGraphics,mouseX,mouseY,partialTick);
        RenderSystem.disableScissor();
    }

    public void renderCut(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (Minecraft.getInstance().player.getData(AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR).getSteelHeart().isPresent()) {
            ItemStack stack = Minecraft.getInstance().player.getData(AstranAttachmentTypeRegistry.STEEL_HEART_RESSERVOIR).getSteelHeart().get();
            SteelHeartData data = stack.get(AstranDataComponentRegistry.STEEL_HEART_DATA);
            if (data.firstNode().get().getItem() instanceof CoreNodeItem item) {
                this.currentColor = item.getNodeColor();
            }
        }

        float r = this.currentColor.getRed() / 255f;
        float g = this.currentColor.getGreen() / 255f;
        float b = this.currentColor.getBlue() / 255f;

        float x = (float) (Minecraft.getInstance().mouseHandler.xpos() - this.mouseOld.x);
        float y = (float) (Minecraft.getInstance().mouseHandler.ypos() - this.mouseOld.y);
        this.offsetParallax.add(x, y);
        this.mouseOld.set(Minecraft.getInstance().mouseHandler.xpos(),Minecraft.getInstance().mouseHandler.ypos());

        float intensity1 = 20f;
        float intensity2 = 30f;
        float intensity3 = 40f;


        RenderSystem.enableBlend();
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(2f,2f,2f);
        guiGraphics.setColor(r,g,b,0.4f);
        guiGraphics.blit(INTERFACE_PATTERN,0,0,this.offsetParallax.x / intensity1,this.offsetParallax.y / intensity1,this.width,this.height,160,160);
        guiGraphics.pose().popPose();

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(1.4f,1.4f,1.4f);
        guiGraphics.setColor(r,g,b,0.15f);
        guiGraphics.blit(INTERFACE_PATTERN,0,0,this.offsetParallax.x / intensity2,this.offsetParallax.y / intensity2,this.width,this.height,160,160);
        guiGraphics.pose().popPose();


        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(0.5f,0.5f,0.5f);

        guiGraphics.setColor(r,g,b,0.1f);
        guiGraphics.blit(INTERFACE_PATTERN,0,0,this.offsetParallax.x / intensity3,this.offsetParallax.y / intensity3,this.width*2,this.height*2,160,160);
        guiGraphics.pose().popPose();



        float normalizedCount = 0;
        if (this.nextInterface != null) {
            normalizedCount = Math.clamp(this.currentTransitionTick + partialTick, 0, this.maxTransitionTick) / this.maxTransitionTick;
            normalizedCount = this.transitionEase.ease(normalizedCount);
            normalizedCount = 1f - normalizedCount;
        } else {
            normalizedCount = Math.clamp(this.tickCount + partialTick, 0, 20) / 20;
            normalizedCount = Easing.EASE_OUT_QUAD.ease(normalizedCount);
        }


        guiGraphics.setColor(1,1,1,normalizedCount);
        RenderSystem.disableBlend();
        for(AbstractWidget renderable : this.glowingWidgets) {
            RenderSystem.enableBlend();
            if (renderable instanceof IGlowModifier glowModifier) {
                Color modColor = glowModifier.getGlowColor();
                guiGraphics.setColor(modColor.getRed()/255f,modColor.getGreen()/255f,modColor.getBlue()/255f,normalizedCount);
                renderable.render(guiGraphics, mouseX, mouseY, partialTick);
                guiGraphics.setColor(1,1,1,1f);
            } else {
                renderable.render(guiGraphics, mouseX, mouseY, partialTick);
            }
            RenderSystem.disableBlend();

        }
        renderBlurredBackground(partialTick);


        for(Renderable renderable : this.renderables) {
            RenderSystem.enableBlend();
            guiGraphics.setColor(1,1,1,normalizedCount);
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
            guiGraphics.setColor(1,1,1,1f);
            RenderSystem.disableBlend();
        }





        if (this.showButtonName() && this.currentFocused() != null) {
            //System.out.println("AAA");
            if (this.currentFocused instanceof IHasInterfaceName interfaceName) {
                float y1 =  ((int) ((48+48+(16*interfaceHeight)-51)));


                Component text = Component.literal("<"+interfaceName.name().toUpperCase(Locale.ROOT)+">")
                        .withStyle(style -> style.withFont(ResourceLocation.fromNamespaceAndPath("astran", "cyber_font")));

                guiGraphics.pose().pushPose();
                guiGraphics.pose().translate(
                        this.getOffsetX()- (Minecraft.getInstance().font.width(text)/2f) - 0.5f,
                        this.getOffsetY() - (y1/2) + 12,
                        750);

                RenderSystem.enableBlend();
                guiGraphics.setColor(1,1,1,normalizedCount);

                guiGraphics.drawString(Minecraft.getInstance().font,
                        text,
                        0,
                        0,
                        new Color(interfaceName.nameColor().getRed() /255f,
                                interfaceName.nameColor().getGreen()/255f,
                                interfaceName.nameColor().getBlue()/255f,Math.clamp(RenderSystem.getShaderColor()[3],0,1f)).getRGB()
                );
                guiGraphics.setColor(1,1,1,1f);
                RenderSystem.disableBlend();


                guiGraphics.pose().popPose();
            }
        }

        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(mouseX,mouseY,210);
        guiGraphics.blit(INTERFACE_MOUSE,0,0,0,0,12,12,12,12);

        guiGraphics.pose().popPose();

    }

    public Vector2f getMouseOld() {
        return this.mouseOld;
    }

    public float getInterfaceHeight() {
        return this.interfaceHeight;
    }

    public float getInterfaceWidth() {
        return this.interfaceWidth;
    }

    public void setInterfaceHeight(float interfaceHeight) {
        this.interfaceHeight = interfaceHeight;
    }

    public void setInterfaceWidth(float interfaceWidth) {
        this.interfaceWidth = interfaceWidth;
    }

    public float getOffsetX() {
        return Minecraft.getInstance().getWindow().getGuiScaledWidth()/2f;
    }

    public float getOffsetY() {
        return Minecraft.getInstance().getWindow().getGuiScaledHeight()/2f;
    }

    public AbstractWidget currentFocused() {
        return this.currentFocused;
    }

    public void setCurrentFocused(AbstractWidget currentFocused) {
        this.currentFocused = currentFocused;
    }

    public void updateFocused(AbstractWidget currentFocused) {
        if (this.currentFocused.equals(currentFocused)) {
          this.currentFocused = null;
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        super.onClose();
        MouseHandlerHelper.isMouseReleaseDisabled = false;
    }

    public boolean showButtonName() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        this.tickCount++;
        if (this.nextInterface != null) {
            if (this.currentTransitionTick >= this.maxTransitionTick) {
                this.onClose();
                Minecraft.getInstance().setScreen(this.nextInterface);
            }
            this.currentTransitionTick++;
        }
    }
}
