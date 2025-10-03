package com.anonym.astran.systems.assembly;

import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.MaterialType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.*;

public abstract class AssemblyAbstractRecipe {

    private final LinkedHashMap<String, List<ItemStack>> namedIngredients;
    private final AssemblyType assemblyType;

    public AssemblyAbstractRecipe(AssemblyType type) {
        this.namedIngredients = new LinkedHashMap<>(this.namedIngredientsSetup());
        this.assemblyType = type;
    }

    public abstract LinkedHashMap<String, List<ItemStack>> namedIngredientsSetup();

    public abstract boolean hasAssemblyBlueprint(Player player);
    public abstract Optional<CyberModule> getResultModule();
    public abstract Optional<ItemStack> getResultStack(CyberModule.Quality quality);

    public LinkedHashMap<String, List<ItemStack>> getNamedIngredients() {
        return this.namedIngredients;
    }

    public LinkedHashMap<String, List<ItemStack>> getInInventoryIngredients(Player player) {
        LinkedHashMap<String, List<ItemStack>> mapping = new LinkedHashMap<>();

        for (int i = 0; i < this.namedIngredients.size(); i++) {
            List<ItemStack> stackList = new ArrayList<>();
            String name = this.namedIngredients.keySet().stream().toList().get(i);
            for (ItemStack stack : player.getInventory().items) {
                if (this.namedIngredients.values().stream().toList().get(i).contains(stack.getItem().getDefaultInstance())) {
                    stackList.add(stack);
                }
            }
            if (!stackList.isEmpty()) {
                mapping.put(name, stackList);
            }
        }

        return mapping;
    }

    public LinkedHashMap<String, ItemStack> getSelectedStacks(LinkedHashMap<String, List<ItemStack>> ingredientChoices, int[] indexes) {
        LinkedHashMap<String, ItemStack> mapping = new LinkedHashMap<>();


        for(int i = 0; i < ingredientChoices.size(); i++) {
            ItemStack stack = ingredientChoices.values().stream().toList().get(i).get(indexes[i]);
            String name = ingredientChoices.keySet().stream().toList().get(i);
            mapping.put(name,stack);
        }

        return mapping;
    }

    public Optional<CyberModule> buildCyberModule(LinkedHashMap<String, ItemStack> map) {
        Optional<CyberModule> module = this.getResultModule();

        if (module.isPresent()) {
            CyberModule mod = module.get();
            List<MaterialType> materials = new ArrayList<>();
            for (ItemStack stack : map.values()) {
                if (stack.getItem() instanceof IAssemblyComponent component) {
                    if (!materials.contains(component.getMaterial())) {
                        materials.add(component.getMaterial());
                    }
                }
            }
            mod.withMaterials(materials);
            return Optional.of(mod);
        } else {
            return Optional.empty();
        }

    }


    public AssemblyType getAssemblyType() {
        return this.assemblyType;
    }

    public enum AssemblyType {
        MODULE,
        ITEM
    }
}
