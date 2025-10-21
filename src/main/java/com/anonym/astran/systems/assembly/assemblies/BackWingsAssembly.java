package com.anonym.astran.systems.assembly.assemblies;

import com.anonym.astran.registries.AstranItemRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.torso.BackCoverModule;
import com.anonym.astran.systems.cybernetics.torso.BackWingsModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class BackWingsAssembly extends AssemblyAbstractRecipe {
    public BackWingsAssembly(AssemblyType type) {
        super(type);
    }

    @Override
    public LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup() {
        Builder builder = new Builder();
        builder.addIngredientsUnder("ingot", AstranItemRegistry.ELECTRUM_INGOT.get(),8);
        builder.addIngredientsUnder("ingot", AstranItemRegistry.BRONZINE_INGOT.get(),8);
        builder.addIngredientsUnder("ingot", AstranItemRegistry.INFERNIUM_INGOT.get(),8);

        builder.addIngredientsUnder("plate", AstranItemRegistry.ELECTRUM_PLATE.get(),8);
        builder.addIngredientsUnder("plate", AstranItemRegistry.BRONZINE_PLATE.get(),8);
        builder.addIngredientsUnder("plate", AstranItemRegistry.INFERNIUM_PLATE.get(),8);



        return builder.build();
    }

    @Override
    public boolean hasAssemblyBlueprint(Player player) {
        return true;
    }

    @Override
    public Optional<CyberModule> getResultModule() {
        return Optional.of(new BackWingsModule());
    }

    @Override
    public Optional<ItemStack> getResultStack() {
        return Optional.empty();
    }

    @Override
    public String getAssemblyID() {
        return "back_wings";
    }

    @Override
    public Component getRecipeName() {
        return Component.nullToEmpty("Back Wings Module");
    }

    @Override
    public Component getDescription() {
        return Component.nullToEmpty("Uh... is this a reference?");
    }
}
