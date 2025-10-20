package com.anonym.astran.systems.assembly.assemblies;

import com.anonym.astran.registries.AstranItemRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.torso.BackCoverModule;
import com.anonym.astran.systems.cybernetics.torso.KineticAccumulatorModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class KineticAccumulatorAssembly extends AssemblyAbstractRecipe {
    public KineticAccumulatorAssembly(AssemblyType type) {
        super(type);
    }

    @Override
    public LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup() {
        Builder builder = new Builder();
        builder.addIngredientsUnder("ingot", AstranItemRegistry.ELECTRUM_INGOT.get(),7);
        builder.addIngredientsUnder("ingot", AstranItemRegistry.BRONZINE_INGOT.get(),7);
        builder.addIngredientsUnder("adaptor", AstranItemRegistry.ELECTRUM_ADAPTOR.get(),3);
        builder.addIngredientsUnder("adaptor", AstranItemRegistry.BRONZINE_ADAPTOR.get(),3);
        builder.addIngredientsUnder("shield", Items.SHIELD,2);
        builder.addIngredientsUnder("slime", Items.SLIME_BLOCK,3);



        return builder.build();
    }

    @Override
    public boolean hasAssemblyBlueprint(Player player) {
        return true;
    }

    @Override
    public Optional<CyberModule> getResultModule() {
        return Optional.of(new KineticAccumulatorModule());
    }

    @Override
    public Optional<ItemStack> getResultStack() {
        return Optional.empty();
    }

    @Override
    public String getAssemblyID() {
        return "kinetic_accumulator";
    }

    @Override
    public Component getRecipeName() {
        return Component.nullToEmpty("Kinetic Accumulator Module");
    }

    @Override
    public Component getDescription() {
        return Component.nullToEmpty("Absorb hits taken by the wearer");
    }
}
