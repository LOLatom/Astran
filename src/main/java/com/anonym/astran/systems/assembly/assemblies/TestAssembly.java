package com.anonym.astran.systems.assembly.assemblies;

import com.anonym.astran.registries.custom.AstranMaterialTypeRegistry;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.assembly.BasicAssemblyRecipe;
import com.anonym.astran.systems.assembly.IAssemblyComponent;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.MaterialType;
import com.anonym.astran.systems.cybernetics.head.EyeModule;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.awt.*;
import java.util.*;
import java.util.List;

public class TestAssembly extends AssemblyAbstractRecipe {
    public TestAssembly(AssemblyType type) {
        super(type);
    }

    @Override
    public LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup() {
        LinkedHashMap<String,List<ItemStack>> hash = new LinkedHashMap<>();
        return hash;
    }

    @Override
    public boolean hasAssemblyBlueprint(Player player) {
        return false;
    }

    @Override
    public Optional<CyberModule> getResultModule() {

        List<MaterialType> types = new ArrayList<>();
        types.add(AstranMaterialTypeRegistry.ELECTRUM.get());
        types.add(AstranMaterialTypeRegistry.BRONZINE.get());

        return Optional.of(new EyeModule(CyberModule.Quality.NORMAL,1,
                new Color(255,255,255).getRGB(),
                new Color(255,255,255).getRGB(),
                new Color(255,255,255).getRGB(),types));
    }

    @Override
    public Optional<ItemStack> getResultStack(CyberModule.Quality quality) {
        return Optional.empty();
    }
}
