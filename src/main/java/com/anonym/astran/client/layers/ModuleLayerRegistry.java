package com.anonym.astran.client.layers;

import com.anonym.astran.Astran;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModuleLayerRegistry {

    public static final ModelLayerLocation EYE_MODULE = register("eye_module");



    private static ModelLayerLocation register(String loc) {
        return register(loc, "main");
    }

    private static ModelLayerLocation register(String id, String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Astran.MODID, id), name);
    }
}
