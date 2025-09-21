package com.anonym.astran.helpers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class VertexHelper {
    public static void uvCube(VertexConsumer consumer,
                               PoseStack.Pose pose, float width, float height, float length, int packedLight,int r,int g, int b, int alpha) {

        float finalW = width / 2;
        float finalH = height / 2;
        float finalL = length / 2;


        vertex(consumer,pose,-finalW,finalH,finalL,r,g,b,alpha,0f,0f,packedLight);
        vertex(consumer,pose,-finalW,-finalH,finalL,r,g,b,alpha,0f,1f,packedLight);
        vertex(consumer,pose,finalW,-finalH,finalL,r,g,b,alpha,1f,1f,packedLight);
        vertex(consumer,pose,finalW,finalH,finalL,r,g,b,alpha,1f,0f,packedLight);


        vertex(consumer,pose,-finalW,-finalH,finalL,r,g,b,alpha,1f,1f,packedLight);
        vertex(consumer,pose,-finalW,finalH,finalL,r,g,b,alpha,1f,0f,packedLight);
        vertex(consumer,pose,-finalW,finalH,-finalL,r,g,b,alpha,0f,0f,packedLight);
        vertex(consumer,pose,-finalW,-finalH,-finalL,r,g,b,alpha,0f,1f,packedLight);

        vertex(consumer,pose,finalW,finalH,-finalL,r,g,b,alpha,0f,0f,packedLight);
        vertex(consumer,pose,finalW,-finalH,-finalL,r,g,b,alpha,0f,1f,packedLight);
        vertex(consumer,pose,-finalW,-finalH,-finalL,r,g,b,alpha,1f,1f,packedLight);
        vertex(consumer,pose,-finalW,finalH,-finalL,r,g,b,alpha,1f,0f,packedLight);

        vertex(consumer,pose,-finalW,finalH,-finalL,r,g,b,alpha,1f,0f,packedLight);
        vertex(consumer,pose,-finalW,finalH,finalL,r,g,b,alpha,0f,0f,packedLight);
        vertex(consumer,pose,finalW,finalH,finalL,r,g,b,alpha,1f,0f,packedLight);
        vertex(consumer,pose,finalW,finalH,-finalL,r,g,b,alpha,0f,0f,packedLight);

        vertex(consumer,pose,finalW,-finalH,-finalL,r,g,b,alpha,1f,1f,packedLight);
        vertex(consumer,pose,finalW,finalH,-finalL,r,g,b,alpha,1f,0f,packedLight);
        vertex(consumer,pose,finalW,finalH,finalL,r,g,b,alpha,0f,0f,packedLight);
        vertex(consumer,pose,finalW,-finalH,finalL,r,g,b,alpha,0f,1f,packedLight);

        vertex(consumer,pose,-finalW,-finalH,finalL,r,g,b,alpha,0f,1f,packedLight);
        vertex(consumer,pose,-finalW,-finalH,-finalL,r,g,b,alpha,1f,1f,packedLight);
        vertex(consumer,pose,finalW,-finalH,-finalL,r,g,b,alpha,0f,1f,packedLight);
        vertex(consumer,pose,finalW,-finalH,finalL,r,g,b,alpha,1f,1f,packedLight);

    }

    public static void uvCube(VertexConsumer consumer,
                              PoseStack.Pose pose, float width, float height, float length, int packedLight, int alpha) {
        uvCube(consumer,pose,width,height,length,packedLight,255,255,255,alpha);
    }

    public static void uvCube(VertexConsumer consumer,
                              PoseStack.Pose pose, float width, float height, float length, int packedLight) {
        uvCube(consumer,pose,width,height,length,packedLight,255);
    }

    public static void vertex(
            VertexConsumer consumer,
            PoseStack.Pose pose,
            float x,
            float y,
            float z,
            int red,
            int green,
            int blue,
            int alpha,
            float u,
            float v,
            int packedLight
    ) {
        consumer.addVertex(pose, x, y, z)
                .setColor(red, green, blue, alpha)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight)
                .setNormal(pose, 0.0F, 1.0F, 0.0F);
    }
}
