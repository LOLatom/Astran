package com.anonym.astran.systems.assembly.assemblies;

import com.anonym.astran.registries.AstranItemRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.torso.BackBaseModule;
import com.anonym.astran.systems.cybernetics.torso.PoisonFilterModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class BackBaseAssembly extends AssemblyAbstractRecipe {
    public BackBaseAssembly(AssemblyType type) {
        super(type);
    }

    @Override
    public LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup() {
        Builder builder = new Builder();
        builder.addIngredientsUnder("plate", AstranItemRegistry.ELECTRUM_PLATE.get(),7);
        builder.addIngredientsUnder("plate", AstranItemRegistry.BRONZINE_PLATE.get(),7);
        builder.addIngredientsUnder("secondPlating", AstranItemRegistry.ELECTRUM_PLATE.get(),10);



        return builder.build();
    }

    @Override
    public boolean hasAssemblyBlueprint(Player player) {
        return true;
    }

    @Override
    public Optional<CyberModule> getResultModule() {
        return Optional.of(new BackBaseModule());
    }

    @Override
    public Optional<ItemStack> getResultStack() {
        return Optional.empty();
    }

    @Override
    public String getAssemblyID() {
        return "back_base";
    }

    @Override
    public Component getRecipeName() {
        return Component.nullToEmpty("Back Base Module");
    }

    @Override
    public Component getDescription() {
        return Component.nullToEmpty("Allows Back Module Addition");
    }
}
