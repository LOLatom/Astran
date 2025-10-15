package com.anonym.astran.registries.custom;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.assembly.assemblies.AquaLungsAssembly;
import com.anonym.astran.systems.assembly.assemblies.EyeAssembly;
import com.anonym.astran.systems.assembly.assemblies.PoisonFilterAssembly;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstranAssemblyRecipesRegistry {

    public static final DeferredRegister<AssemblyAbstractRecipe> ASSEMBLY_RECIPES = DeferredRegister.create(AstranRegistries.ASSEMBLY_RECIPES_REGISTRY, Astran.MODID);


    public static final DeferredHolder<AssemblyAbstractRecipe, ? extends AssemblyAbstractRecipe> EYES = ASSEMBLY_RECIPES.register("eyes",
            () -> new EyeAssembly(AssemblyAbstractRecipe.AssemblyType.MODULE));
    public static final DeferredHolder<AssemblyAbstractRecipe, ? extends AssemblyAbstractRecipe> AQUA_LUNGS = ASSEMBLY_RECIPES.register("aqua_lungs",
            () -> new AquaLungsAssembly(AssemblyAbstractRecipe.AssemblyType.MODULE));
    public static final DeferredHolder<AssemblyAbstractRecipe, ? extends AssemblyAbstractRecipe> POISON_FILTER = ASSEMBLY_RECIPES.register("poison_filter",
            () -> new PoisonFilterAssembly(AssemblyAbstractRecipe.AssemblyType.MODULE));
}
