package com.anonym.astran.systems.assembly.assemblies;

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
        builder.addIngredientsUnder("plates",Items.COAL,5)
                .addIngredientsUnder("plates",Items.RAW_GOLD,10)
                .addIngredientsUnder("raw",Items.COPPER_INGOT,8)
                .addIngredientsUnder("flat",Items.BIRCH_WOOD,115)
                .addIngredientsUnder("flat",Items.ACACIA_WOOD,115)
                .addIngredientsUnder("flat",Items.DARK_OAK_WOOD,115)
                .addIngredientsUnder("blist",Items.IRON_NUGGET,25)
                .addIngredientsUnder("blist",Items.GOLD_NUGGET,25);
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
