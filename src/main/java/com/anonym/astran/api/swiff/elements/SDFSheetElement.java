package com.anonym.astran.api.swiff.elements;

import com.anonym.astran.api.swiff.SwiffUI;
import com.anonym.astran.api.swiff.sdf.SDFShape;
import com.anonym.astran.api.swiff.tools.SwiffTexture;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.shader.program.ShaderProgram;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SDFSheetElement extends AbstractElement<SDFSheetElement> {

    public boolean isPixel = false;
    private List<SDFShape> sdfList = new ArrayList<>();
    private Map<String, SDFShape> sdfMap = new HashMap<>();

    private float blendingF = 0.3f;

    private ShaderProgram sdfSheetShader;

    private SwiffTexture.BlendType blendMode = SwiffTexture.BlendType.NORMAL;

    public SDFSheetElement(AbstractElement<?> parent) {
        super(parent);
    }

    public SDFSheetElement(SwiffUI root) {
        super(root);
    }

    public SDFSheetElement withBlendingFactor(float blendF) {
        this.blendingF = blendF;
        return this;
    }

    public SDFSheetElement withBlendMode(SwiffTexture.BlendType blend) {
        this.blendMode = blend;
        return this;
    }

    public void addSDF(String name, SDFShape shape) {
        this.sdfMap.put(name, shape);
        this.sdfList = new ArrayList<>(this.sdfMap.values());
    }
    public void addSDF(int index, SDFShape shape) {
        this.sdfMap.put(String.valueOf(index), shape);
        this.sdfList = new ArrayList<>(this.sdfMap.values());
    }

    public int sdfSize() {
        return this.sdfList.size();
    }

    public void removeSDF(String name) {
        this.sdfMap.remove(name);
        this.sdfList = new ArrayList<>(this.sdfMap.values());
    }

    public SDFShape getSDF(String name) {
        return this.sdfMap.get(name);
    }

    public SDFShape getSDF(int index) {
        return this.sdfList.get(index);
    }

    @Override
    public void setup() {
        this.sdfSheetShader = VeilRenderSystem.setShader(
                ResourceLocation.fromNamespaceAndPath("astran","swiffui/sdf_sheet"));
    }

    @Override
    public void renderAll(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.sdfSheetShader != null) {
            boolean isNormal = this.blendMode == SwiffTexture.BlendType.NORMAL;

            float x1 = this.getAccurateX();
            float x2 = x1 + this.getScaleX();
            float y1 = this.getAccurateY();
            float y2 = y1 + this.getScaleY();
            if (!isNormal) {
                SwiffTexture.defineBlendMode(this.blendMode);
            }
            RenderSystem.setShader(this.sdfSheetShader::toShaderInstance);
            float width = x2 - x1;
            float height = y2 - y1;

            for (int i = 0; i < this.sdfList.size(); i++) {
                SDFShape shape = this.sdfList.get(i);

                Vector2f pos = shape.getPos();
                float posX = (pos.x - x1) / width;
                float posY = (pos.y - y1) / height;

                float radius = shape.getRadius();
                float normalizedRadius = radius / Math.min(width, height);
                int id = shape.getShapeID();
                var uPos = this.sdfSheetShader.getUniform("Pos[" + i + "]");
                if (uPos != null) uPos.setVector(new Vector2f(posX,posY));

                var uR = this.sdfSheetShader.getUniform("Radius[" + i + "]");
                if (uR != null) uR.setFloat(normalizedRadius);

                var uID = this.sdfSheetShader.getUniform("IDs[" + i + "]");
                if (uID != null) uID.setInt(id);
                var colorR = this.sdfSheetShader.getUniform("Color[" + i + "]");
                if (colorR != null) colorR.setVector(new Vector4f(
                        shape.getColor().getRed()/255f,
                        shape.getColor().getGreen()/255f,
                        shape.getColor().getBlue()/255f,
                        shape.getColor().getAlpha()/255f
                ));
                var uWHAB = this.sdfSheetShader.getUniform("WHAB[" + i + "]");
                if (uWHAB != null) uWHAB.setVector(new Vector4f(
                        shape.getSettings().whab.x / width,
                        shape.getSettings().whab.y / height,
                        shape.getSettings().whab.z / Math.min(width, height),
                        shape.getSettings().whab.w / Math.min(width, height)
                        ));

            }

            float k = this.blendingF;
            float normalizedK = k / Math.min(width, height);

            var uK = this.sdfSheetShader.getUniform("BlendK");
            if (uK != null) uK.setFloat(normalizedK);

            var uSize = this.sdfSheetShader.getUniform("Size");
            if (uSize != null) uSize.setInt(this.sdfList.size());

            RenderSystem.enableBlend();

            Matrix4f matrix4f = guiGraphics.pose().last().pose();
            BufferBuilder bufferbuilder =
                    Tesselator.getInstance()
                            .begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);

            bufferbuilder.addVertex(matrix4f, 0f, 0f, 0).setUv(0, 0).setColor(1, 1, 1, 1);
            bufferbuilder.addVertex(matrix4f, 0f, this.getScaleY(), 0).setUv(0, 1).setColor(1, 1, 1, 1);
            bufferbuilder.addVertex(matrix4f, this.getScaleX(), this.getScaleY(), 0).setUv(1, 1).setColor(1, 1, 1, 1);
            bufferbuilder.addVertex(matrix4f, this.getScaleX(), 0f, 0).setUv(1, 0).setColor(1, 1, 1, 1);

            BufferUploader.drawWithShader(bufferbuilder.buildOrThrow());
            if (!isNormal) {
                RenderSystem.blendEquation(32774);
                RenderSystem.defaultBlendFunc();
            }
            RenderSystem.disableBlend();
        }
    }
}