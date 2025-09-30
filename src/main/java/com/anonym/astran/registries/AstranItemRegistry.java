package com.anonym.astran.registries;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.cybernetics.core.SteelHeartData;
import com.anonym.astran.systems.cybernetics.core.SteelHeartItem;
import com.anonym.astran.systems.energy.node.AstraniumNodeItem;
import com.anonym.astran.systems.energy.node.EmptyNodeItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public class AstranItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Astran.MODID);

    public static final DeferredItem<Item> STEEL_HEART = ITEMS.register("steel_heart",
            () -> new SteelHeartItem(new Item.Properties().stacksTo(1)
                    .component(AstranDataComponentRegistry.STEEL_HEART_DATA,
                            new SteelHeartData(Optional.empty(),Optional.empty(),Optional.empty()))));

    public static final DeferredItem<Item> ASTRANIUM_NODE = ITEMS.register("astranium_node",
            () -> new AstraniumNodeItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> EMPTY_NODE = ITEMS.register("empty_node",
            () -> new EmptyNodeItem(new Item.Properties().stacksTo(1)));

}
