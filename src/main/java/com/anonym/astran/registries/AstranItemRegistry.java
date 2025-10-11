package com.anonym.astran.registries;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.custom.AstranMaterialTypeRegistry;
import com.anonym.astran.systems.cybernetics.core.SteelHeartData;
import com.anonym.astran.systems.cybernetics.core.SteelHeartItem;
import com.anonym.astran.systems.cybernetics.material.ComponentItem;
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

    public static final DeferredItem<Item> ELECTRUM_INGOT = ITEMS.register("electrum_ingot",
            () -> new ComponentItem(AstranMaterialTypeRegistry.ELECTRUM,new Item.Properties()));

    public static final DeferredItem<Item> ELECTRUM_PLATE = ITEMS.register("electrum_plate",
            () -> new ComponentItem(AstranMaterialTypeRegistry.ELECTRUM,new Item.Properties()));

    public static final DeferredItem<Item> ELECTRUM_CLUSTER = ITEMS.register("electrum_cluster",
            () -> new ComponentItem(AstranMaterialTypeRegistry.ELECTRUM,new Item.Properties()));

    public static final DeferredItem<Item> ELECTRUM_ADAPTOR = ITEMS.register("electrum_adaptor",
            () -> new ComponentItem(AstranMaterialTypeRegistry.ELECTRUM,new Item.Properties()));

    public static final DeferredItem<Item> ELECTRUM_NODE_BRACKET = ITEMS.register("electrum_node_bracket",
            () -> new ComponentItem(AstranMaterialTypeRegistry.ELECTRUM,new Item.Properties()));

    public static final DeferredItem<Item> BRONZINE_INGOT = ITEMS.register("bronzine_ingot",
            () -> new ComponentItem(AstranMaterialTypeRegistry.BRONZINE,new Item.Properties()));

    public static final DeferredItem<Item> BRONZINE_PLATE = ITEMS.register("bronzine_plate",
            () -> new ComponentItem(AstranMaterialTypeRegistry.BRONZINE,new Item.Properties()));

    public static final DeferredItem<Item> BRONZINE_ADAPTOR = ITEMS.register("bronzine_adaptor",
            () -> new ComponentItem(AstranMaterialTypeRegistry.BRONZINE,new Item.Properties()));

}
