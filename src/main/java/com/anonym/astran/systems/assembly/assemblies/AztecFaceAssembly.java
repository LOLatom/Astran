package com.anonym.astran.systems.assembly.assemblies;

import com.anonym.astran.registries.AstranItemRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.head.AztecFaceModule;
import com.anonym.astran.systems.cybernetics.head.FrontFaceModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class AztecFaceAssembly extends AssemblyAbstractRecipe {
    public AztecFaceAssembly(AssemblyType type) {
        super(type);
    }

    @Override
    public LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup() {
        Builder builder = new Builder();
        builder.addIngredientsUnder("plate", AstranItemRegistry.ELECTRUM_PLATE.get(),6);
        builder.addIngredientsUnder("plate", AstranItemRegistry.BRONZINE_PLATE.get(),6);
        builder.addIngredientsUnder("plate", AstranItemRegistry.INFERNIUM_PLATE.get(),6);

        builder.addIngredientsUnder("ingot", AstranItemRegistry.ELECTRUM_INGOT.get(),2);
        builder.addIngredientsUnder("ingot", AstranItemRegistry.BRONZINE_INGOT.get(),2);
        builder.addIngredientsUnder("ingot", AstranItemRegistry.INFERNIUM_INGOT.get(),2);

        return builder.build();
    }

    @Override
    public boolean hasAssemblyBlueprint(Player player) {
        return true;
    }

    @Override
    public Optional<CyberModule> getResultModule() {
        return Optional.of(new AztecFaceModule());
    }

    @Override
    public Optional<ItemStack> getResultStack() {
        return Optional.empty();
    }

    @Override
    public String getAssemblyID() {
        return "aztec_face";
    }

    @Override
    public Component getRecipeName() {
        return Component.nullToEmpty("Aztec Face Module");
    }

    @Override
    public Component getDescription() {
        return Component.nullToEmpty("This is piece of cosmetic for the face");
    }
}
