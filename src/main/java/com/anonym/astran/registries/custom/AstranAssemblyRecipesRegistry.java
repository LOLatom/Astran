package com.anonym.astran.registries.custom;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.assembly.BasicAssemblyRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.MaterialType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstranAssemblyRecipesRegistry {

    public static final DeferredRegister<AssemblyAbstractRecipe> ASSEMBLY_RECIPES = DeferredRegister.create(AstranRegistries.ASSEMBLY_RECIPES_REGISTRY, Astran.MODID);


    public static final DeferredHolder<AssemblyAbstractRecipe, ? extends AssemblyAbstractRecipe> TEST1 = ASSEMBLY_RECIPES.register("test",
            () -> new BasicAssemblyRecipe(AssemblyAbstractRecipe.AssemblyType.MODULE));
    public static final DeferredHolder<AssemblyAbstractRecipe, ? extends AssemblyAbstractRecipe> TEST2 = ASSEMBLY_RECIPES.register("test2",
            () -> new BasicAssemblyRecipe(AssemblyAbstractRecipe.AssemblyType.MODULE));
}
