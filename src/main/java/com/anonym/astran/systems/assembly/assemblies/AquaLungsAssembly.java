package com.anonym.astran.systems.assembly.assemblies;

import com.anonym.astran.registries.AstranItemRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.head.EyeModule;
import com.anonym.astran.systems.cybernetics.torso.AquaLungsModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class AquaLungsAssembly extends AssemblyAbstractRecipe {
    public AquaLungsAssembly(AssemblyType type) {
        super(type);
    }

    @Override
    public LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup() {
        Builder builder = new Builder();
        builder.addIngredientsUnder("plate", AstranItemRegistry.ELECTRUM_PLATE.get(),5);
        builder.addIngredientsUnder("plate", AstranItemRegistry.BRONZINE_PLATE.get(),5);
        builder.addIngredientsUnder("adaptor", AstranItemRegistry.ELECTRUM_ADAPTOR.get(),10);
        builder.addIngredientsUnder("adaptor", AstranItemRegistry.BRONZINE_ADAPTOR.get(),10);

        return builder.build();
    }

    @Override
    public boolean hasAssemblyBlueprint(Player player) {
        return true;
    }

    @Override
    public Optional<CyberModule> getResultModule() {
        return Optional.of(new AquaLungsModule());
    }

    @Override
    public Optional<ItemStack> getResultStack() {
        return Optional.empty();
    }

    @Override
    public String getAssemblyID() {
        return "aqua_lungs";
    }

    @Override
    public Component getRecipeName() {
        return Component.nullToEmpty("Aqua Lungs Module");
    }

    @Override
    public Component getDescription() {
        return Component.nullToEmpty("Allows a User to Breath under water");
    }
}
