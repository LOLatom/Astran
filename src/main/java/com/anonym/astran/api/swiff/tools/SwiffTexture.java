package com.anonym.astran.api.swiff.tools;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.metadata.gui.GuiSpriteScaling;
import net.minecraft.resources.ResourceLocation;

public abstract class SwiffTexture {

    public final int width;
    public final int height;
    public final ResourceLocation texture;

    public SwiffTexture(ResourceLocation texture, int width, int height) {
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    public abstract void draw(GuiGraphics guiGraphics,int x, int y, int xScale, int yScale);



    public static class Sliced extends SwiffTexture {

        private final int slice;
        public Sliced(ResourceLocation texture,int width, int height, int slice) {
            super(texture,width, height);
            this.slice = slice;
        }

        @Override
        public void draw(GuiGraphics guiGraphics,int x, int y, int xScale, int yScale) {
            guiGraphics.pose().pushPose();
            guiGraphics.pose().translate(-this.slice,-this.slice,0);

            guiGraphics.blit(this.texture,x,y,this.slice,this.slice,0,0,
                    this.slice,this.slice,this.width,this.height);
            guiGraphics.blit(this.texture,x + this.slice,y,xScale,this.slice,this.slice,0,
                    this.width-this.slice-this.slice,this.slice,this.width,this.height);
            guiGraphics.blit(this.texture,x + this.slice + xScale,y,this.slice,this.slice,this.width-this.slice,0,
                    this.slice,this.slice,this.width,this.height);

            guiGraphics.blit(this.texture,x,y + this.slice,this.slice,yScale,0,this.slice,
                    this.slice,this.height-this.slice-this.slice,this.width,this.height);
            guiGraphics.blit(this.texture,x + this.slice,y+ this.slice,xScale,yScale,this.slice,this.slice,
                    this.width-this.slice-this.slice,this.height-this.slice-this.slice,this.width,this.height);
            guiGraphics.blit(this.texture,x + this.slice + xScale,y+ this.slice,this.slice,yScale,this.width-this.slice,this.slice,
                    this.slice,this.height-this.slice-this.slice,this.width,this.height);

            guiGraphics.blit(this.texture,x,y+ this.slice + yScale,this.slice,this.slice,0,this.height-this.slice,
                    this.slice,this.slice,this.width,this.height);
            guiGraphics.blit(this.texture,x + this.slice,y+ this.slice + yScale,xScale,this.slice,this.slice,this.height-this.slice,
                    this.width-this.slice-this.slice,this.slice,this.width,this.height);
            guiGraphics.blit(this.texture,x + this.slice + xScale,y+ this.slice + yScale,this.slice,this.slice,this.width-this.slice,this.height-this.slice,
                    this.slice,this.slice,this.width,this.height);


            guiGraphics.pose().popPose();

        }


    }
}
