package com.anonym.astran.systems.assembly;

import com.anonym.astran.systems.cybernetics.CyberModule;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class BasicAssemblyRecipe extends AssemblyAbstractRecipe{

    private final String id;

    public BasicAssemblyRecipe(AssemblyType type, String id) {
        super(type);
        this.id = id;
    }


    @Override
    public LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup() {
        LinkedHashMap<String,List<ItemStack>> map = new LinkedHashMap<>();
        return map;
    }

    @Override
    public boolean hasAssemblyBlueprint(Player player) {
        return true;
    }

    @Override
    public Optional<CyberModule> getResultModule() {
        return Optional.empty();
    }

    @Override
    public Optional<ItemStack> getResultStack() {
        return Optional.empty();
    }

    @Override
    public String getAssemblyID() {
        return this.id;
    }

    @Override
    public Component getRecipeName() {
        return null;
    }
}
