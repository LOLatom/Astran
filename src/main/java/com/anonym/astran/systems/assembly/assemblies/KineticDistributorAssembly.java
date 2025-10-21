package com.anonym.astran.systems.assembly.assemblies;

import com.anonym.astran.registries.AstranItemRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.hands.KineticDistributorModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class KineticDistributorAssembly extends AssemblyAbstractRecipe {
    public KineticDistributorAssembly(AssemblyType type) {
        super(type);
    }

    @Override
    public LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup() {
        Builder builder = new Builder();
        builder.addIngredientsUnder("ingot", AstranItemRegistry.ELECTRUM_INGOT.get(),7);
        builder.addIngredientsUnder("ingot", AstranItemRegistry.BRONZINE_INGOT.get(),7);
        builder.addIngredientsUnder("ingot", AstranItemRegistry.INFERNIUM_INGOT.get(),7);

        builder.addIngredientsUnder("adaptor", AstranItemRegistry.ELECTRUM_PLATE.get(),3);
        builder.addIngredientsUnder("adaptor", AstranItemRegistry.BRONZINE_PLATE.get(),3);
        builder.addIngredientsUnder("adaptor", AstranItemRegistry.INFERNIUM_PLATE.get(),3);



        return builder.build();
    }

    @Override
    public boolean hasAssemblyBlueprint(Player player) {
        return true;
    }

    @Override
    public Optional<CyberModule> getResultModule() {
        return Optional.of(new KineticDistributorModule());
    }

    @Override
    public Optional<ItemStack> getResultStack() {
        return Optional.empty();
    }

    @Override
    public String getAssemblyID() {
        return "kinetic_distributor";
    }

    @Override
    public Component getRecipeName() {
        return Component.nullToEmpty("Kinetic Distributor");
    }

    @Override
    public Component getDescription() {
        return Component.nullToEmpty("Transmit Absorbed Damage To the enemy");
    }
}
