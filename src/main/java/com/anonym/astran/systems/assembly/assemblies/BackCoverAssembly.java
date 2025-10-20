package com.anonym.astran.systems.assembly.assemblies;

import com.anonym.astran.registries.AstranItemRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.torso.BackBaseModule;
import com.anonym.astran.systems.cybernetics.torso.BackCoverModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class BackCoverAssembly extends AssemblyAbstractRecipe {
    public BackCoverAssembly(AssemblyType type) {
        super(type);
    }

    @Override
    public LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup() {
        Builder builder = new Builder();
        builder.addIngredientsUnder("ingot", AstranItemRegistry.ELECTRUM_INGOT.get(),8);
        builder.addIngredientsUnder("ingot", AstranItemRegistry.BRONZINE_INGOT.get(),8);
        builder.addIngredientsUnder("plate", AstranItemRegistry.ELECTRUM_PLATE.get(),8);
        builder.addIngredientsUnder("plate", AstranItemRegistry.BRONZINE_PLATE.get(),8);



        return builder.build();
    }

    @Override
    public boolean hasAssemblyBlueprint(Player player) {
        return true;
    }

    @Override
    public Optional<CyberModule> getResultModule() {
        return Optional.of(new BackCoverModule());
    }

    @Override
    public Optional<ItemStack> getResultStack() {
        return Optional.empty();
    }

    @Override
    public String getAssemblyID() {
        return "back_cover";
    }

    @Override
    public Component getRecipeName() {
        return Component.nullToEmpty("Back Cover Module");
    }

    @Override
    public Component getDescription() {
        return Component.nullToEmpty("Add Armor to the wearer");
    }
}
