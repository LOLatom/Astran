package com.anonym.astran.systems.assembly;

import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.MaterialType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
    public abstract Optional<ItemStack> getResultStack();

    public abstract Component getRecipeName();

    public Component getDescription() {
        return Component.empty();
    }

    public LinkedHashMap<String, List<ItemStack>> getNamedIngredients() {
        return this.namedIngredients;
    }

    public LinkedHashMap<String, List<ItemStack>> getInInventoryIngredients(Player player) {
        LinkedHashMap<String, List<ItemStack>> mapping = new LinkedHashMap<>();

        Map<Item, Integer> mergedInventory = new HashMap<>();
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.isEmpty()) {
                mergedInventory.merge(stack.getItem(), stack.getCount(), Integer::sum);
            }
        }

        int i = 0;
        for (Map.Entry<String, List<ItemStack>> entry : this.namedIngredients.entrySet()) {
            String name = entry.getKey();
            List<ItemStack> requiredVariants = entry.getValue();
            List<ItemStack> stackList = new ArrayList<>();

            for (ItemStack ingredient : requiredVariants) {
                int availableCount = mergedInventory.getOrDefault(ingredient.getItem(), 0);
                if (availableCount >= ingredient.getCount()) {
                    stackList.add(new ItemStack(ingredient.getItem(), availableCount));
                }
            }
            if (!stackList.isEmpty()) {
                mapping.put(name, stackList);
            }
            i++;
        }

        return mapping;
    }

    public boolean canBeCrafted(LinkedHashMap<String, List<ItemStack>> ingredientsInInventory) {
        boolean hasIngredients = true;
        for (String ing : this.getNamedIngredients().keySet()) {
            if (!ingredientsInInventory.containsKey(ing)) hasIngredients = false;
        }
        return hasIngredients;
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

    public class Builder {

        private LinkedHashMap<String,List<ItemStack>> hash = new LinkedHashMap<>();

        public Builder() {
        }

        private LinkedHashMap<String, List<ItemStack>> getHash() {
            return this.hash;
        }

        public Builder addIngredientsUnder(String name, Item item, int count) {
            List<ItemStack> stacks = new ArrayList<>();
            if (getHash().containsKey(name)) stacks = getHash().get(name);
            stacks.add(new ItemStack(item,count));
            if (getHash().containsKey(name)) {
                getHash().replace(name,stacks);
            } else {
                getHash().put(name,stacks);
            }
            return this;
        }

        public LinkedHashMap<String,List<ItemStack>> build() {
            return new LinkedHashMap<>(this.hash);
        }
    }
}
