package com.anonym.astran.api.swiff.elements;

import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.effects.SwiffEffect;
import com.anonym.astran.api.swiff.tools.AreaOfEffect;
import com.anonym.astran.api.swiff.tools.UIAnchor;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.annotation.Nullable;
import java.util.*;

public abstract class AbstractElement<T extends AbstractElement<T>> extends AbstractWidget {

    public float posX = 0, posY = 0;
    public float scaleX = 0, scaleY = 0;
    public float rotX = 0, rotY = 0, rotZ = 0;

    public @Nullable UIAnchor anchorPoint = null;
    private boolean boundObstruction = false;

    private final UUID uuid;
    public boolean isInteractive = false;

    public @Nullable UUID currentInteraction = null;


    private LinkedHashMap<UUID,AbstractElement<?>> childrenElements = new LinkedHashMap<>();
    private List<AbstractElement<?>> renderedElements = new ArrayList<>();
    public List<AbstractElement<?>> pendingRemovals = new ArrayList<>();


    private @Nullable AdvancedFbo effectBuffer;
    private SwiffEffect effect;
    private AreaOfEffect aoe;

    private @Nullable AbstractElement<?> parent;
    private SwiffUI root;

    private final Minecraft mc =  Minecraft.getInstance();

    private AbstractElement() {
        super(0, 0, 0, 0, Component.empty());
        this.uuid = UUID.randomUUID();
        this.parent = null;
        this.aoe = new AreaOfEffect(5,5,5,5);
        this.setup();
    }

    public AbstractElement(AbstractElement<?> parent) {
        this(parent.getRoot());
        this.parent = parent;
    }
    public AbstractElement(SwiffUI root) {
        this();
        this.root = root;
    }

    @SuppressWarnings("unchecked")
    public T withPos(float x, float y) {
        this.posX = x;
        this.posY = y;
        return (T) this;
    }
    @SuppressWarnings("unchecked")
    public T withInteractions() {
        this.isInteractive = true;
        return (T) this;
    }
    @SuppressWarnings("unchecked")
    public T withScale(float x, float y) {
        this.scaleX = x;
        this.scaleY = y;
        return (T) this;
    }
    @SuppressWarnings("unchecked")
    public T anchor(UIAnchor uiAnchor) {
        this.anchorPoint = uiAnchor;
        return (T) this;
    }
    @SuppressWarnings("unchecked")
    public T withEffects(SwiffEffect effect) {
        this.effect = effect;
        return (T) this;
    }
    @SuppressWarnings("unchecked")
    public T withEffectBuffer(AdvancedFbo effectBuffer) {
        this.effectBuffer = effectBuffer;
        return (T) this;
    }
    @SuppressWarnings("unchecked")
    public T withAreaOfEffect(AreaOfEffect aoe) {
        this.aoe = aoe;
        return (T) this;
    }
    @SuppressWarnings("unchecked")
    public T withBoundObstruction() {
        this.boundObstruction = true;
        return (T) this;
    }
    @SuppressWarnings("unchecked")
    public T withRotations(float x,float y,float z) {
        this.setRotXYZ(x,y,z);
        return (T) this;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public void setRotX(float rotX) {
        this.rotX = rotX;
    }
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }
    public void setRotXYZ(float x,float y,float z) {
        this.rotX = x;
        this.rotY = y;
        this.rotZ = z;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public boolean hasParent() {
        return this.getParent() != null;
    }

    public boolean isInteractingWithElement() {
        return this.currentInteraction != null;
    }

    public boolean isInteractedWith() {
        if (this.hasParent()) {
            return this.getUuid().equals(this.getParent().currentInteraction);
        } else {
            return this.getUuid().equals(this.getRoot().currentInteraction);
        }
    }

    public void setInteraction() {
        if (this.hasParent()) {
            this.setInteraction(this);
        } else {
            this.getRoot().setInteraction(this);
        }
    }
    public void setInteraction(AbstractElement<?> element) {
        this.getParent().currentInteraction = element.getUuid();
        this.getParent().reOrderElements(element);
        this.getParent().setInteraction();
    }

    private void reOrderElements(UUID elementUUID) {
        LinkedHashMap<UUID, AbstractElement<?>> result = new LinkedHashMap<>();
        if (this.childrenElements.containsKey(elementUUID)) {
            result.put(elementUUID, this.childrenElements.get(elementUUID));
        }
        for (Map.Entry<UUID, AbstractElement<?>> entry : this.childrenElements.entrySet()) {
            if (!entry.getKey().equals(elementUUID)) {
                result.put(entry.getKey(), entry.getValue());
            }
        }
        List<AbstractElement<?>> reversed = new ArrayList<>(result.values());
        Collections.reverse(reversed);
        this.renderedElements = reversed;
        this.childrenElements = result;
    }
    public void reOrderElements(AbstractElement<?> element) {
        this.reOrderElements(element.getUuid());
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
    public AbstractElement<?> getParent() {
        return this.parent;
    }

    public SwiffUI getRoot() {
        return this.root;
    }

    public float getAccurateX() {
        float Ax = this.getAnchoring(false);
        return this.posX + Ax;
    }

    public float getAccurateY() {
        float Ay = this.getAnchoring(true);
        return this.posY + Ay;
    }

    public float[] getOffsetFromAnchor() {
        float[] anchorOffset = {0,0};
        if (this.anchorPoint != null) {
            if (this.getParent() != null) {
                switch (this.anchorPoint) {
                    case BOTTOM -> {
                        anchorOffset[0] = (this.getParent().getScaleX()/2f);
                        anchorOffset[1] = (this.getParent().getScaleY());
                    }
                    case LEFT -> {
                        anchorOffset[0] = (0);
                        anchorOffset[1] = (this.getParent().getScaleY()/2f);
                    }
                    case RIGHT -> {
                        anchorOffset[0] = (this.getParent().getScaleX());
                        anchorOffset[1] = (this.getParent().getScaleY()/2f);
                    }
                    case TOP -> {
                        anchorOffset[0] = (this.getParent().getScaleX()/2f);
                        anchorOffset[1] = (0);
                    }
                    case CENTER -> {
                        anchorOffset[0] = (this.getParent().getScaleX()/2f);
                        anchorOffset[1] = (this.getParent().getScaleY()/2f);
                    }
                    case TOP_LEFT -> {
                        anchorOffset[0] = (0);
                        anchorOffset[1] = (0);
                    }
                    case BOTTOM_LEFT -> {
                        anchorOffset[0] = (0);
                        anchorOffset[1] = (this.getParent().getScaleY());
                    }
                    case TOP_RIGHT -> {
                        anchorOffset[0] = (this.getParent().getScaleX());
                        anchorOffset[1] = (0);
                    }
                    case BOTTOM_RIGHT -> {
                        anchorOffset[0] = (this.getParent().getScaleX());
                        anchorOffset[1] = (this.getParent().getScaleY());

                    }
                    case NONE -> {

                    }
                }
            }
        }
        return anchorOffset;
    }


    public float getAnchoring(boolean isY) {
        float anchorX = 0f;
        float anchorY = 0f;
        float w = Minecraft.getInstance().getWindow().getGuiScaledWidth();
        float h = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        if (this.anchorPoint != null) {
            if (this.getParent() == null) {
                switch (this.anchorPoint) {
                    case BOTTOM -> {
                        anchorX = (((float)w) / 2f) - (this.getScaleX() / 2f);
                        anchorY = h - (this.scaleY);
                    }
                    case LEFT -> {
                        anchorX = 0;
                        anchorY = (h / 2f) - (this.scaleY / 2f);
                    }
                    case RIGHT -> {
                        anchorX = ((float)w) - (this.getScaleX());
                        anchorY = (h / 2f) - (this.scaleY / 2f);
                    }
                    case TOP -> {
                        anchorX = (((float)w) / 2f) - (this.getScaleX() / 2f);
                        anchorY = 0;
                    }
                    case CENTER -> {
                        anchorX = (((float)w) / 2f) - (this.getScaleX() / 2f);
                        anchorY = (h / 2f) - (this.scaleY / 2f);
                    }
                    case TOP_LEFT -> {
                        anchorX = 0;
                        anchorY = 0;
                    }
                    case BOTTOM_LEFT -> {
                        anchorX = 0;
                        anchorY = h - (this.scaleY);
                    }
                    case TOP_RIGHT -> {
                        anchorX = ((float)w) - (this.getScaleX());
                        anchorY = 0;
                    }
                    case BOTTOM_RIGHT -> {
                        anchorX = ((float)w) - (this.getScaleX());
                        anchorY = h - (this.scaleY);
                    }
                    case NONE -> {

                    }
                }
            } else {
                switch (this.anchorPoint) {
                    case BOTTOM -> {
                        anchorX = this.getParent().getAccurateX() + (this.getParent().getScaleX()/2f) - (this.scaleX/2f);
                        anchorY = this.getParent().getAccurateY() + this.getParent().getScaleY() - this.scaleY;
                    }
                    case LEFT -> {
                        anchorX = this.getParent().getAccurateX();
                        anchorY = this.getParent().getAccurateY() + (this.getParent().getScaleY()/2f)- (this.scaleY/2f);
                    }
                    case RIGHT -> {
                        anchorX = this.getParent().getAccurateX() + this.getParent().getScaleX() - this.scaleX;
                        anchorY = this.getParent().getAccurateY() + (this.getParent().getScaleY()/2f)- (this.scaleY/2f);
                    }
                    case TOP -> {
                        anchorX = this.getParent().getAccurateX() + (this.getParent().getScaleX()/2f) - (this.scaleX/2f);
                        anchorY = this.getParent().getAccurateY();
                    }
                    case CENTER -> {
                        anchorX = this.getParent().getAccurateX() + (this.getParent().getScaleX()/2f) - (this.scaleX/2f);
                        anchorY = this.getParent().getAccurateY() + (this.getParent().getScaleY()/2f)- (this.scaleY/2f);
                    }
                    case TOP_LEFT -> {
                        anchorX = this.getParent().getAccurateX();
                        anchorY = this.getParent().getAccurateY();
                    }
                    case BOTTOM_LEFT -> {
                        anchorX = this.getParent().getAccurateX();
                        anchorY = this.getParent().getAccurateY() + this.getParent().getScaleY() - this.scaleY;
                    }
                    case TOP_RIGHT -> {
                        anchorX = this.getParent().getAccurateX() + this.getParent().getScaleX() - this.scaleX;
                        anchorY = this.getParent().getAccurateY();
                    }
                    case BOTTOM_RIGHT -> {
                        anchorX = this.getParent().getAccurateX() + this.getParent().getScaleX() - this.scaleX;
                        anchorY = this.getParent().getAccurateY() + this.getParent().getScaleY() - this.scaleY;
                    }
                    case NONE -> {

                    }
                }
            }
        }
        return isY ? anchorY : anchorX;
    }


    @Override
    public int getWidth() {
        return (int) this.scaleX;
    }

    @Override
    public int getHeight() {
        return (int) this.scaleY;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void setScaleX(float scaleX) {
        this.scaleX = scaleX;
    }

    public void setScaleY(float scaleY) {
        this.scaleY = scaleY;
    }

    public void addChild(AbstractElement<?> child) {
        this.childrenElements.put(child.getUuid(),child);
        List<AbstractElement<?>> reversed = new ArrayList<>(this.childrenElements.values());
        Collections.reverse(reversed);
        this.renderedElements = reversed;
    }


    public Set<Map.Entry<UUID, AbstractElement<?>>> children() {
        return this.childrenElements.entrySet();
    }

    public abstract void setup();

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        if (this.effect != null && this.effectBuffer != null) {
            /*
            Window window = mc.getWindow();
            double guiScale = window.getGuiScale();
            int screenWidth = window.getWidth();
            int screenHeight = window.getHeight();

            int x = (int) (this.getAccurateX() * guiScale);
            int y = (int) (this.getAccurateY() * guiScale);
            int width = (int) (this.scaleX * guiScale);
            int height = (int) (this.scaleY * guiScale);

            int scissorX = (int) (x - (width/2) - this.aoe.left());
            int scissorY = screenHeight - (y + (height/2) + this.aoe.bottom());
            int scissorWidth = width + this.aoe.right() + this.aoe.left();
            int scissorHeight = height + this.aoe.top() + this.aoe.bottom();

            scissorX = Math.max(0, scissorX);
            scissorY = Math.max(0, scissorY);
            scissorWidth = Math.min(screenWidth - scissorX, scissorWidth);
            scissorHeight = Math.min(screenHeight - scissorY, scissorHeight);

            RenderSystem.enableScissor(scissorX, scissorY, scissorWidth, scissorHeight);

            this.effectBuffer.bind(true);
            this.effectBuffer.clear();

            RenderSystem.enableBlend();
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(this.getAccurateX(),this.getAccurateY(), 0);
            this.renderAll(guiGraphics, mouseX, mouseY, partialTick);
            guiGraphics.pose().popPose();
            guiGraphics.flush();

            Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
            RenderSystem.disableScissor();
            this.effect.shader.setSampler("EffectSampler0",this.effectBuffer.getColorTextureAttachment(0).getId());
            this.effect.shader.setSampler("DiffuseSampler0",AdvancedFbo.getMainFramebuffer().getColorTextureAttachment(0).getId());
            this.effect.prepare(this.effect.shader);
            RenderSystem.setShader(this.effect.shader::toShaderInstance);
            RenderSystem.setShaderTexture(0, this.effectBuffer.getColorTextureAttachment(0).getId());

            Matrix4f matrix4f = guiGraphics.pose().last().pose();
            RenderSystem.enableScissor(scissorX, scissorY, scissorWidth, scissorHeight);

            BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
            bufferbuilder.addVertex(matrix4f, 0, 0, 0).setUv(0, 1);
            bufferbuilder.addVertex(matrix4f, 0, guiGraphics.guiHeight(), 0).setUv(0, 0);
            bufferbuilder.addVertex(matrix4f, guiGraphics.guiWidth(), guiGraphics.guiHeight(), 0).setUv(1, 0);
            bufferbuilder.addVertex(matrix4f, guiGraphics.guiWidth(), 0, 0).setUv(1, 1);

            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
            RenderSystem.disableScissor();
            RenderSystem.disableBlend();


             */
        } else {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(getAccurateX(),getAccurateY(), 0);
            //guiGraphics.pose().mulPose((new Quaternionf()).rotationZYX(this.rotZ, this.rotY, this.rotZ));
            this.renderAll(guiGraphics, mouseX, mouseY, partialTick);
            guiGraphics.pose().popPose();

            this.processPendingRemovals();
            guiGraphics.enableScissor((int)this.getAccurateX(),(int)this.getAccurateY(),
                    (int)(this.getAccurateX()+this.getScaleX()),
                    (int)(this.getAccurateY()+this.getScaleY()));
            for (var child : this.renderedElements) {
                if (child != null) {
                    child.renderWidget(guiGraphics, mouseX, mouseY, partialTick);
                }
            }
            guiGraphics.disableScissor();

        }
    }

    public void remove() {
        if (this.getParent() == null) {
            this.getRoot().pendingRemovals.add(this);
        } else {
            this.getParent().pendingRemovals.add(this);
        }
    }

    public void processPendingRemovals() {
        if (pendingRemovals.isEmpty()) return;
        System.out.println(this.pendingRemovals);

        for (var element : pendingRemovals) {
            renderedElements.remove(element);
            childrenElements.remove(element.getUuid());
        }

        pendingRemovals.clear();
    }

    public abstract void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);

    @Override
    protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {

    }

    @Override
    public void playDownSound(@NotNull SoundManager handler) {
    }

    public float clampWithBoundX (float value,float offset,float size) {

        return Math.clamp(value,
                this.getAccurateX() - this.getAnchoring(false) - (offset) + (size) ,
                this.getAccurateX() - this.getAnchoring(false) + this.getScaleX() - (offset)
        );
    }
    public float clampWithBoundY (float value,float offset,float size) {
        return Math.clamp(
                value,
                this.getAccurateY() - this.getAnchoring(true)- (offset) + (size/2f) ,
                this.getAccurateY() - this.getAnchoring(true) + this.getScaleY() - (offset) - (size/2f)
        );
    }
    public boolean insideBounds(double mouseX, double mouseY) {
        if (!this.children().isEmpty()) {
            for (var element : this.children()) {
                if (element != null) {
                    if (element.getValue().boundObstruction) {
                        if (element.getValue().insideBounds(mouseX, mouseY)) {
                            return false;
                        }
                    }
                }
            }
        }
        if (
                ((mouseX > this.getAccurateX()) &&
                (mouseY > this.getAccurateY())) &&
                (mouseX < (this.getAccurateX() + this.getScaleX())) &&
                (mouseY < (this.getAccurateY() + this.getScaleY()))) {
            return true;
        }
        return false;
    }
    public boolean insideBoundsIgnoreChildren(double mouseX, double mouseY) {
        if (((mouseX > this.getAccurateX()) &&
                        (mouseY > this.getAccurateY())) &&
                        (mouseX < (this.getAccurateX() + this.getScaleX())) &&
                        (mouseY < (this.getAccurateY() + this.getScaleY()))) {
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        for (var element : this.children()) {
            if (element != null) {
                if (element.getValue().insideBoundsIgnoreChildren(mouseX, mouseY)) {
                    element.getValue().mouseScrolled(mouseX, mouseY, scrollX, scrollY);
                }
            }
        }
        return super.mouseScrolled(mouseX, mouseY, scrollX, scrollY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (var element : this.children()) {
            if (element != null) {
                element.getValue().mouseReleased(mouseX, mouseY, button);
            }
        }
        this.currentInteraction = null;
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        for (var element : this.children()) {
            if (element != null) {
                if (this.insideBoundsIgnoreChildren(mouseX, mouseY)) {
                    if (this.currentInteraction == null) {
                        element.getValue().mouseDragged(mouseX, mouseY, button, dragX, dragY);
                    } else {
                        if (element.getValue().getUuid().equals(this.currentInteraction)) {
                            element.getValue().mouseDragged(mouseX, mouseY, button, dragX, dragY);
                        }
                    }
                }
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (var element : this.children()) {
            if (element != null) {
                if (element.getValue().insideBoundsIgnoreChildren(mouseX, mouseY)) {
                    if (this.currentInteraction == null) {
                        element.getValue().mouseClicked(mouseX, mouseY, button);
                    } else {
                        if (element.getValue().getUuid().equals(this.currentInteraction)) {
                            element.getValue().mouseClicked(mouseX, mouseY, button);
                        }
                    }
                }
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        for (var element : this.children()) {
            if (element != null) {
                element.getValue().mouseMoved(mouseX, mouseY);
            }
        }
        super.mouseMoved(mouseX, mouseY);
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return super.keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClick(double mouseX, double mouseY, int button) {
        super.onClick(mouseX, mouseY, button);
    }
}
