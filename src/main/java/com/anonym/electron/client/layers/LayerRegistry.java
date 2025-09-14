package com.anonym.electron.client.layers;

import com.anonym.electron.Electron;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class LayerRegistry {


    private static ModelLayerLocation register(String loc) {
        return register(loc, "main");
    }

    private static ModelLayerLocation register(String id, String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Electron.MODID, id), name);
    }
}
