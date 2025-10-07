package com.anonym.astran.registries.custom;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.assembly.assemblies.EyeAssembly;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstranAssemblyRecipesRegistry {

    public static final DeferredRegister<AssemblyAbstractRecipe> ASSEMBLY_RECIPES = DeferredRegister.create(AstranRegistries.ASSEMBLY_RECIPES_REGISTRY, Astran.MODID);


    public static final DeferredHolder<AssemblyAbstractRecipe, ? extends AssemblyAbstractRecipe> TEST1 = ASSEMBLY_RECIPES.register("test",
            () -> new EyeAssembly(AssemblyAbstractRecipe.AssemblyType.MODULE));
    public static final DeferredHolder<AssemblyAbstractRecipe, ? extends AssemblyAbstractRecipe> TEST2 = ASSEMBLY_RECIPES.register("test2",
            () -> new EyeAssembly(AssemblyAbstractRecipe.AssemblyType.MODULE));
    public static final DeferredHolder<AssemblyAbstractRecipe, ? extends AssemblyAbstractRecipe> TEST3 = ASSEMBLY_RECIPES.register("test3",
            () -> new EyeAssembly(AssemblyAbstractRecipe.AssemblyType.MODULE));
    public static final DeferredHolder<AssemblyAbstractRecipe, ? extends AssemblyAbstractRecipe> TEST4 = ASSEMBLY_RECIPES.register("test4",
            () -> new EyeAssembly(AssemblyAbstractRecipe.AssemblyType.MODULE));
    public static final DeferredHolder<AssemblyAbstractRecipe, ? extends AssemblyAbstractRecipe> TEST5 = ASSEMBLY_RECIPES.register("test5",
            () -> new EyeAssembly(AssemblyAbstractRecipe.AssemblyType.MODULE));
}
