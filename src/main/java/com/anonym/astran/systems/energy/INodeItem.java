package com.anonym.astran.systems.energy;

import com.anonym.astran.Astran;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public interface INodeItem {

    default ResourceLocation getNodeTexture() {
        return ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/node/astranium_node_icon.png");
    }

    default Color getNodeColor() {
        return new Color(255,255,255);
    }

    default String energyType() {return "Null";}


}
