package com.anonym.astran.api.swiff;

import com.anonym.astran.api.swiff.effects.SwiffEffectPipeline;
import com.anonym.astran.api.swiff.scene.Scene3D;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.framebuffer.FramebufferManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.*;

/**
 * API BY : 546f6b72786565
 */

public abstract class SwiffUI extends Screen {

    private final SwiffEffectPipeline effectPipeline = new SwiffEffectPipeline(
            ResourceLocation.fromNamespaceAndPath("astran","swiff/swiff_swap"),
            ResourceLocation.fromNamespaceAndPath("astran","swiff/swiff_swap2")
    );


    private final HashMap<String,Scene3D> scenes = new HashMap<>();
    private final HashMap<String,ResourceLocation> cachedTextures = new HashMap<>();


    public SwiffUI(Component title) {
        super(title);

    }

    public SwiffUI() {
        this(Component.empty());
    }

    @Override
    protected void init() {
        super.init();
        this.createElements();
    }

    public SwiffEffectPipeline getEffectPipeline() {
        return this.effectPipeline;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        for(Renderable renderable : this.renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    public <T extends GuiEventListener & Renderable & NarratableEntry> T addChild(T widget) {
        return super.addRenderableWidget(widget);
    }

    public <T extends GuiEventListener & Renderable & NarratableEntry> T addDummyChild(T widget) {
        return super.addRenderableOnly(widget);
    }

    public abstract void createElements();

    public void addScene(String name, Scene3D scene) {
        this.scenes.put(name,scene);
    }
    public void addScene(String name) {
        this.addScene(name,new Scene3D());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
