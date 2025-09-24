package com.anonym.astran.registries;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.cybernetics.core.SteelHeartData;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstranDataComponentRegistry {
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Astran.MODID);


    public static final DeferredHolder<DataComponentType<?>, DataComponentType<SteelHeartData>> STEEL_HEART_DATA = DATA_COMPONENTS.registerComponentType(
            "steel_heart_data",
            builder -> builder
                    .persistent(SteelHeartData.CODEC)
                    .networkSynchronized(SteelHeartData.STREAM_CODEC).cacheEncoding()
    );



}
