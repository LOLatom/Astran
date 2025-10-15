package com.anonym.astran.systems.assembly.network;

import com.anonym.astran.registries.custom.AstranRegistries;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.HashMap;
import java.util.Map;


public class AssembleModuleHandler {
    public class Server {
        public static void handleDataOnNetwork(AssembleModulePayload data, final IPayloadContext context) {
            for (ResourceLocation loc : AstranRegistries.ASSEMBLY_RECIPES_REGISTRY.keySet()) {
                if (data.assemblyID().equals(loc.getPath())) {
                    consumeChosenIngredients(context.player(),data.selection());
                    break;
                }

            }
        }

        public static void consumeChosenIngredients(Player player, Map<String, ItemStack> chosen) {
            if (chosen == null || chosen.isEmpty()) return;

            Map<Item, Integer> required = new HashMap<>();
            for (ItemStack stack : chosen.values()) {
                required.merge(stack.getItem(), stack.getCount(), Integer::sum);
            }

            NonNullList<ItemStack> inv = player.getInventory().items;
            for (int slot = 0; slot < inv.size(); slot++) {
                ItemStack slotStack = inv.get(slot);
                if (slotStack == null || slotStack.isEmpty()) continue;

                Item item = slotStack.getItem();
                Integer need = required.get(item);
                if (need == null || need <= 0) continue;

                int take = Math.min(slotStack.getCount(), need);
                slotStack.shrink(take);
                int remaining = need - take;
                if (remaining <= 0) required.remove(item);
                else required.put(item, remaining);
                if (slotStack.isEmpty()) inv.set(slot, ItemStack.EMPTY);
                else inv.set(slot, slotStack);
            }
        }
    }
}
