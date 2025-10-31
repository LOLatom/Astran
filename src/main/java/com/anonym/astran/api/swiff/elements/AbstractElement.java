package com.anonym.astran.api.swiff.elements;

import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.effects.SwiffEffect;
import com.anonym.astran.api.swiff.effects.SwiffEffectPipeline;
import com.anonym.astran.api.swiff.effects.SwiffShaderEffect;
import com.mojang.blaze3d.systems.RenderSystem;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.framebuffer.FramebufferAttachmentDefinition;
import foundry.veil.api.client.render.framebuffer.FramebufferDefinition;
import foundry.veil.api.client.render.framebuffer.FramebufferManager;
import foundry.veil.api.client.render.texture.TextureFilter;
import foundry.veil.impl.client.render.framebuffer.AdvancedFboImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public abstract class AbstractElement extends AbstractWidget {

    private float posX = 0, posY = 0;

    private float scaleX = 0, scaleY = 0;

    private final UUID uuid;

    private final List<AbstractElement> childrenElements = new ArrayList<>();

    private @Nullable SwiffEffectPipeline pipeline;
    private List<SwiffShaderEffect> effects = new ArrayList<>();

    private @Nullable AbstractElement parent;

    private SwiffUI root;

    private AbstractElement() {
        super(0, 0, 0, 0, Component.empty());
        this.uuid = UUID.randomUUID();
        this.parent = null;
    }

    public AbstractElement(AbstractElement parent) {
        this(parent.getRoot());
        this.parent = parent;
    }
    public AbstractElement(SwiffUI root) {
        this();
        this.root = root;
    }

    public AbstractElement withPos(float x, float y) {
        this.posX = x;
        this.posY = y;
        return this;
    }
    public AbstractElement withScale(float x, float y) {
        this.scaleX = x;
        this.scaleY = y;
        return this;
    }
    public AbstractElement withEffectPipeline(SwiffEffectPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public AbstractElement withEffects(SwiffShaderEffect... effects) {
        this.effects = Arrays.stream(effects).toList();
        return this;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    @Override
    public int getY() {
        return (int) this.posY;
    }

    @Override
    public int getX() {
        return (int) this.posX;
    }

    @Nullable
    public AbstractElement getParent() {
        return this.parent;
    }

    public SwiffUI getRoot() {
        return this.root;
    }

    public float getAccurateX() {
        return this.posX;
    }

    public float getAccurateY() {
        return this.posY;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.pipeline != null) {
            pipeline.begin();
            RenderSystem.enableBlend();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(this.posX, this.posY, 0);
            this.renderAll(guiGraphics, mouseX, mouseY, partialTick);
            guiGraphics.pose().popPose();



            for (AbstractElement child : this.childrenElements) {
                child.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
            }

            guiGraphics.flush();

            for (SwiffShaderEffect effect : this.effects) {
                pipeline.apply(effect, guiGraphics, partialTick);
            }

            pipeline.renderToScreen(guiGraphics);
            RenderSystem.disableBlend();

        } else {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(this.posX, this.posY, 0);
            this.renderAll(guiGraphics, mouseX, mouseY, partialTick);
            guiGraphics.pose().popPose();

            for (AbstractElement child : this.childrenElements) {
                child.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
            }
        }
    }

    public void addEffect(SwiffShaderEffect effect) {
        this.effects.add(effect);
    }

    public abstract void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }





}
