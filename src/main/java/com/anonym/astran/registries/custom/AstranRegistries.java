package com.anonym.astran.registries.custom;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.MaterialType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class AstranRegistries {

    public static final Registry<CyberModule> CYBER_MODULE_REGISTRY = new RegistryBuilder<>(Keys.CYBER_MODULE_KEY)
            .defaultKey(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"empty"))
            .create();

    public static final Registry<MaterialType> MATERIAL_TYPE_REGISTRY = new RegistryBuilder<>(Keys.MATERIAL_TYPE_KEY)
            .defaultKey(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"empty"))
            .create();

    public static final Registry<AssemblyAbstractRecipe> ASSEMBLY_RECIPES_REGISTRY = new RegistryBuilder<>(Keys.ASSEMBLY_RECIPES_KEY)
            .defaultKey(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"empty"))
            .create();



    public static class Keys {

        public static final ResourceKey<Registry<CyberModule>> CYBER_MODULE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"cyber_module"));

        public static final ResourceKey<Registry<MaterialType>> MATERIAL_TYPE_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"material_type"));

        public static final ResourceKey<Registry<AssemblyAbstractRecipe>> ASSEMBLY_RECIPES_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Astran.MODID,"assembly_recipe"));


    }
}
