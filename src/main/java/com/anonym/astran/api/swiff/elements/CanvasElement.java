package com.anonym.astran.api.swiff.elements;

import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.stroke.NDollarRecognizer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class CanvasElement extends AbstractElement<CanvasElement>{
    private NDollarRecognizer recognizer;

    private int currentLine = 0;

    private final List<NDollarRecognizer.Point> points = new ArrayList<>();
    private final List<List<NDollarRecognizer.Point>> gestures = new ArrayList<>();

    private float strokeWidth = 5f;


    private boolean drawing = false;
    private int strokeId = 0;
    private Vector2f a = new Vector2f();
    private Vector2f b = new Vector2f();
    private Vector2f c = new Vector2f();
    private Vector2f d = new Vector2f();
    private Vector2f directionAB = new Vector2f();
    private Vector2f directionBC = new Vector2f();
    private Vector2f midPointAB = new Vector2f();
    private Vector2f midPointBC = new Vector2f();

    public CanvasElement(AbstractElement<?> parent) {
        super(parent);
    }

    public CanvasElement(SwiffUI root) {
        super(root);
    }

    public CanvasElement withWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
        return this;
    }


    @Override
    public void setup() {
        this.recognizer = new NDollarRecognizer(true);
        this.recognizer.loadDefaultTemplates();
    }

    @Override
    public void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(-this.getAccurateX(),-this.getAccurateY(),0);
        float width = this.strokeWidth;
        for (List<NDollarRecognizer.Point> gesturePoints : gestures) {
            if (gesturePoints.size() > 3) {

                RenderSystem.setShader(GameRenderer::getPositionColorShader);
                Matrix4f matrix4f = guiGraphics.pose().last().pose();
                BufferBuilder bufferbuilder = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                for (int i = 1; i < gesturePoints.size(); i++) {
                    if (gesturePoints.size() >= i + 3) {

                        NDollarRecognizer.Point pointA = gesturePoints.get(i - 1);
                        NDollarRecognizer.Point pointB = gesturePoints.get(i);
                        NDollarRecognizer.Point pointC = gesturePoints.get(i + 1);

                        directionAB.set(pointA.x, pointA.y).sub((float) pointB.x, (float) pointB.y).normalize();
                        directionBC.set(pointB.x, pointB.y).sub((float) pointC.x, (float) pointC.y).normalize();

                        midPointAB.set((pointA.x + pointB.x) / 2, (pointA.y + pointB.y) / 2);
                        midPointBC.set((pointB.x + pointC.x) / 2, (pointB.y + pointC.y) / 2);

                        a.set(midPointAB.x, midPointAB.y);
                        b.set(midPointAB.x, midPointAB.y);

                        c.set(midPointBC.x, midPointBC.y);
                        d.set(midPointBC.x, midPointBC.y);

                        a.add((-directionAB.y) * width, (directionAB.x) * width);
                        b.add((directionAB.y) * width, (-directionAB.x) * width);

                        c.add((-directionBC.y) * width, (directionBC.x) * width);
                        d.add((directionBC.y) * width, (-directionBC.x) * width);

                        bufferbuilder.addVertex(matrix4f, a.x, a.y, (float) 0).setUv(0, 0).setColor(0f, 0f, 0f, 1f);
                        bufferbuilder.addVertex(matrix4f, b.x, b.y, (float) 0).setUv(0, 1).setColor(0f, 0f, 0f, 1f);
                        bufferbuilder.addVertex(matrix4f, d.x, d.y, (float) 0).setUv(1, 1).setColor(0f, 0f, 0f, 1f);
                        bufferbuilder.addVertex(matrix4f, c.x, c.y, (float) 0).setUv(1, 0).setColor(0f, 0f, 0f, 1f);
                    }

                }
                BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
            }
        }

        guiGraphics.pose().popPose();

    }


    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (!this.points.isEmpty() && button == 0) {
            NDollarRecognizer.Result r = this.recognizer.recognize(this.gestures, false, false, true);
            Minecraft.getInstance().player.sendSystemMessage(Component.nullToEmpty(
                    r.getName() + "  In : " + r.getTimeMs() + "ms  With score of : " + r.getScore()
            ));
            this.gestures.set(this.currentLine, new ArrayList<>(this.points));
            this.points.clear();
            this.gestures.add(new ArrayList<>());
            this.currentLine++;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.insideBounds(mouseX,mouseY)) {
            this.setInteraction();
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (this.insideBounds(mouseX,mouseY)) {
            if (button == 0) {
                float dist = 40f;
                if (this.gestures.isEmpty()) {
                    this.gestures.add(new ArrayList<>());
                }
                if (!this.points.isEmpty()) {
                    Vector2f f = new Vector2f((float) this.points.getLast().x, (float) this.points.getLast().y);
                    dist = f.distance((float) mouseX, (float) mouseY);
                }
                if (dist > 0.5f) {
                    this.points.add(new NDollarRecognizer.Point(
                            mouseX,
                            mouseY));
                    System.out.println("X  :  " + (mouseX - this.getAccurateX()));
                    System.out.println("Y  :  " + (mouseY - this.getAccurateY()));

                }
                this.gestures.set(this.currentLine, this.points);
            }
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);

    }

    public boolean isDrawing() {
        return this.drawing;
    }

    public void setDrawing(boolean drawing) {
        this.drawing = drawing;
    }

    public int getStrokeId() {
        return this.strokeId;
    }

    public void setStrokeId(int strokeId) {
        this.strokeId = strokeId;
    }
}
