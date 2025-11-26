package com.anonym.astran.api.swiff;

import com.anonym.astran.api.swiff.elements.AbstractElement;
import com.anonym.astran.api.swiff.scene.Scene3D;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import foundry.veil.api.client.render.framebuffer.FramebufferManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.*;

/**
 * API BY : 546f6b72786565
 */

public abstract class SwiffUI extends Screen {

    private final AdvancedFbo effectBuffer;


    private final HashMap<String,Scene3D> scenes = new HashMap<>();
    private final HashMap<String,ResourceLocation> cachedTextures = new HashMap<>();
    public @Nullable UUID currentInteraction = null;

    public LinkedHashMap<UUID, AbstractElement<?>> elements = new LinkedHashMap<>();
    private List<AbstractElement<?>> renderedElements = new ArrayList<>();
    public List<AbstractElement<?>> pendingRemovals = new ArrayList<>();


    public SwiffUI(Component title) {
        super(title);
        //FramebufferManager manager = VeilRenderSystem.renderer().getFramebufferManager();
        //this.effectBuffer = manager.getFramebuffer(ResourceLocation.fromNamespaceAndPath("astran","swiff/swiff_main"));
        this.effectBuffer = null;
    }

    public SwiffUI() {
        this(Component.empty());
    }

    @Override
    protected void init() {
        super.init();
        this.createElements();
    }

    public AdvancedFbo getEffectBuffer() {
        return this.effectBuffer;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        float packedTick = Minecraft.getInstance().player.tickCount + partialTick;
        this.renderPre(guiGraphics,mouseX,mouseY,partialTick, packedTick);

        this.processPendingRemovals();

        for (var entry : this.renderedElements) {
            if (entry != null) {
                entry.render(guiGraphics, mouseX, mouseY, partialTick);
            }
        }
        this.renderPost(guiGraphics,mouseX,mouseY,partialTick, packedTick);
    }

    public void addChild(AbstractElement<?> widget) {
        this.elements.put(widget.getUuid(),widget);
        List<AbstractElement<?>> reversed = new ArrayList<>(this.elements.values());
        Collections.reverse(reversed);
        this.renderedElements = reversed;
    }

    public <T extends GuiEventListener & Renderable & NarratableEntry> T addDummyChild(T widget) {
        return super.addRenderableOnly(widget);
    }

    public void setInteraction(AbstractElement<?> interactiveElement) {
        this.currentInteraction = interactiveElement.getUuid();
        this.reOrderElements(interactiveElement);
    }

    private void reOrderElements(UUID elementUUID) {
        LinkedHashMap<UUID, AbstractElement<?>> result = new LinkedHashMap<>();
        if (this.elements.containsKey(elementUUID)) {
            result.put(elementUUID, this.elements.get(elementUUID));
        }
        for (Map.Entry<UUID, AbstractElement<?>> entry : this.elements.entrySet()) {
            if (!entry.getKey().equals(elementUUID)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        List<AbstractElement<?>> reversed = new ArrayList<>(result.values());
        Collections.reverse(reversed);
        this.renderedElements = reversed;
        this.elements = result;
    }
    public void reOrderElements(AbstractElement<?> element) {
        this.reOrderElements(element.getUuid());
    }

    public Set<Map.Entry<UUID, AbstractElement<?>>> elements() {
        return this.elements.entrySet();
    }

    public void processPendingRemovals() {
        if (pendingRemovals.isEmpty()) return;
        System.out.println(this.pendingRemovals);


        for (var element : pendingRemovals) {
            renderedElements.remove(element);
            elements.remove(element.getUuid());
        }

        pendingRemovals.clear();
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        for (var element : this.elements()) {
            if (element != null) {
                if (this.currentInteraction == null) {
                    element.getValue().mouseDragged(mouseX, mouseY, button, dragX, dragY);
                } else {
                    if (element.getValue().getUuid().equals(this.currentInteraction)) {
                        element.getValue().mouseDragged(mouseX, mouseY, button, dragX, dragY);
                    }
                }
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    public void removeElement(UUID uuid) {
        this.renderedElements.remove(this.elements.get(uuid));
        this.elements.remove(uuid);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        for (var element : this.elements()) {
            if (element != null) {
                element.getValue().mouseScrolled(mouseX, mouseY, scrollX, scrollY);
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (var element : this.elements()) {
            if (element != null) {
                element.getValue().mouseReleased(mouseX, mouseY, button);
            }
            this.currentInteraction = null;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (var element : this.elements()) {
            if (element != null) {
                if (this.currentInteraction == null) {
                    element.getValue().mouseClicked(mouseX, mouseY, button);
                } else {
                    if (element.getValue().getUuid().equals(this.currentInteraction)) {
                        element.getValue().mouseClicked(mouseX, mouseY, button);
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        for (var element : this.elements()) {
            if (element != null) {
                element.getValue().mouseMoved(mouseX, mouseY);
            }
        }
        super.mouseMoved(mouseX, mouseY);
    }

    public abstract void renderPre(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, float packedTick);
    public abstract void renderPost(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick, float packedTick);

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
