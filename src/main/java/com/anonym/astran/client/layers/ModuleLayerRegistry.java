package com.anonym.astran.client.layers;

import com.anonym.astran.Astran;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class ModuleLayerRegistry {

    public static final ModelLayerLocation EYE_MODULE = register("eye_module");
    public static final ModelLayerLocation AQUA_LUNGS_MODULE = register("aqua_lungs_module");
    public static final ModelLayerLocation POISON_FILTER_MODULE = register("poison_filter_module");
    public static final ModelLayerLocation BACK_BASE = register("back_base");
    public static final ModelLayerLocation BACK_COVER = register("back_cover");
    public static final ModelLayerLocation KINETIC_ACCUMULATOR = register("kinetic_accumulator");
    public static final ModelLayerLocation KINETIC_DISTRIBUTOR = register("kinetic_distributor");
    public static final ModelLayerLocation BACK_WINGS = register("back_wings");



    private static ModelLayerLocation register(String loc) {
        return register(loc, "main");
    }

    private static ModelLayerLocation register(String id, String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Astran.MODID, id), name);
    }
}
