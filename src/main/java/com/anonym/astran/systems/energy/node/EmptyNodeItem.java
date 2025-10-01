package com.anonym.astran.systems.energy.node;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.energy.CoreNodeItem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import java.awt.*;

public class EmptyNodeItem extends CoreNodeItem {

    private static final Color nodeColor = new Color(255,255,255);
    private static final foundry.veil.api.client.color.Color first = new foundry.veil.api.client.color.Color(0,0,0);
    private static final foundry.veil.api.client.color.Color second = new foundry.veil.api.client.color.Color(255,255,255);

    public EmptyNodeItem(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation getNodeTexture() {
        return ResourceLocation.fromNamespaceAndPath(Astran.MODID,"textures/node/empty_node_icon.png");
    }


    @Override
    public Color getNodeColor() {
        foundry.veil.api.client.color.Color col = new foundry.veil.api.client.color.Color(0,0,0);
        Minecraft mc = Minecraft.getInstance();
        col.mix(second, (float) (0.5f + (Math.sin((mc.player.tickCount + mc.getTimer().getGameTimeDeltaPartialTick(true))*0.01f)*0.01f)));
        return new Color(col.rgb());
    }


    @Override
    public String energyType() {
        return "Null";
    }
}
