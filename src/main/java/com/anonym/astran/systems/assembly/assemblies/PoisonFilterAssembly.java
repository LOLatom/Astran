package com.anonym.astran.systems.assembly.assemblies;

import com.anonym.astran.registries.AstranItemRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.torso.AquaLungsModule;
import com.anonym.astran.systems.cybernetics.torso.PoisonFilterModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class PoisonFilterAssembly extends AssemblyAbstractRecipe {
    public PoisonFilterAssembly(AssemblyType type) {
        super(type);
    }

    @Override
    public LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup() {
        Builder builder = new Builder();
        builder.addIngredientsUnder("plate", AstranItemRegistry.ELECTRUM_PLATE.get(),5);
        builder.addIngredientsUnder("plate", AstranItemRegistry.BRONZINE_PLATE.get(),5);
        builder.addIngredientsUnder("adaptor", AstranItemRegistry.ELECTRUM_ADAPTOR.get(),10);
        builder.addIngredientsUnder("adaptor", AstranItemRegistry.BRONZINE_ADAPTOR.get(),10);
        builder.addIngredientsUnder("filtering", Items.PAPER,5);
        builder.addIngredientsUnder("filtering", Items.MAP,5);
        builder.addIngredientsUnder("filteringSecond", Items.COAL,10);
        builder.addIngredientsUnder("filteringThird", Items.SAND,20);


        return builder.build();
    }

    @Override
    public boolean hasAssemblyBlueprint(Player player) {
        return true;
    }

    @Override
    public Optional<CyberModule> getResultModule() {
        return Optional.of(new PoisonFilterModule());
    }

    @Override
    public Optional<ItemStack> getResultStack() {
        return Optional.empty();
    }

    @Override
    public String getAssemblyID() {
        return "poison_filter";
    }

    @Override
    public Component getRecipeName() {
        return Component.nullToEmpty("Poison Filter Module");
    }

    @Override
    public Component getDescription() {
        return Component.nullToEmpty("Allows a User to Filter PoisonEffects");
    }
}
