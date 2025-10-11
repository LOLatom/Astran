package com.anonym.astran.systems.assembly.assemblies;

import com.anonym.astran.registries.AstranItemRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.head.EyeModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;
import java.util.List;

public class EyeAssembly extends AssemblyAbstractRecipe {
    public EyeAssembly(AssemblyType type) {
        super(type);
    }

    @Override
    public LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup() {
        Builder builder = new Builder();
        builder.addIngredientsUnder("plate", AstranItemRegistry.ELECTRUM_PLATE.get(),3);
        builder.addIngredientsUnder("plate", AstranItemRegistry.BRONZINE_PLATE.get(),3);
        builder.addIngredientsUnder("adaptor", AstranItemRegistry.ELECTRUM_ADAPTOR.get(),4);
        builder.addIngredientsUnder("adaptor", AstranItemRegistry.BRONZINE_ADAPTOR.get(),4);
        builder.addIngredientsUnder("ingot", AstranItemRegistry.ELECTRUM_INGOT.get(),2);
        builder.addIngredientsUnder("ingot", AstranItemRegistry.BRONZINE_INGOT.get(),2);


        return builder.build();
    }

    @Override
    public boolean hasAssemblyBlueprint(Player player) {
        return true;
    }

    @Override
    public Optional<CyberModule> getResultModule() {
        return Optional.of(new EyeModule());
    }

    @Override
    public Optional<ItemStack> getResultStack() {
        return Optional.empty();
    }

    @Override
    public Component getRecipeName() {
        return Component.nullToEmpty("Eye Module");
    }

    @Override
    public Component getDescription() {
        return Component.nullToEmpty("This module allow a user to modify their vision");
    }
}
