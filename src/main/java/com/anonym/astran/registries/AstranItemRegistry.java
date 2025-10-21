package com.anonym.astran.registries;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.custom.AstranMaterialTypeRegistry;
import com.anonym.astran.systems.cybernetics.core.SteelHeartData;
import com.anonym.astran.systems.cybernetics.core.SteelHeartItem;
import com.anonym.astran.systems.cybernetics.material.ComponentItem;
import com.anonym.astran.systems.energy.node.AstraniumNodeItem;
import com.anonym.astran.systems.energy.node.EmptyNodeItem;
import net.minecraft.world.item.*;
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

    public static final DeferredItem<Item> INFERNIUM_INGOT = ITEMS.register("infernium_ingot",
            () -> new ComponentItem(AstranMaterialTypeRegistry.INFERNIUM,new Item.Properties()));

    public static final DeferredItem<Item> INFERNIUM_PLATE = ITEMS.register("infernium_plate",
            () -> new ComponentItem(AstranMaterialTypeRegistry.INFERNIUM,new Item.Properties()));

    public static final DeferredItem<Item> INFERNIUM_ADAPTOR = ITEMS.register("infernium_adaptor",
            () -> new ComponentItem(AstranMaterialTypeRegistry.INFERNIUM,new Item.Properties()));


    public static final DeferredItem<Item> ELECTRUM_SWORD = ITEMS.register("electrum_sword",
            () -> new SwordItem(Tiers.IRON,new Item.Properties().stacksTo(1)
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 7f,2f))));
    public static final DeferredItem<Item> ELECTRUM_PICKAXE = ITEMS.register("electrum_pickaxe",
            () -> new PickaxeItem(Tiers.IRON,new Item.Properties().stacksTo(1)
                    .attributes(PickaxeItem.createAttributes(Tiers.IRON, 4f,2f))));
    public static final DeferredItem<Item> ELECTRUM_AXE = ITEMS.register("electrum_axe",
            () -> new AxeItem(Tiers.IRON,new Item.Properties().stacksTo(1)
                    .attributes(AxeItem.createAttributes(Tiers.IRON, 10f,1f))));
    public static final DeferredItem<Item> ELECTRUM_SHOVEL = ITEMS.register("electrum_shovel",
            () -> new ShovelItem(Tiers.IRON,new Item.Properties().stacksTo(1)
                    .attributes(ShovelItem.createAttributes(Tiers.IRON, 6f,2f))));
    public static final DeferredItem<Item> ELECTRUM_HOE = ITEMS.register("electrum_hoe",
            () -> new HoeItem(Tiers.IRON,new Item.Properties().stacksTo(1)
                    .attributes(HoeItem.createAttributes(Tiers.IRON, 3f,2f))));


    public static final DeferredItem<Item> BRONZINE_SWORD = ITEMS.register("bronzine_sword",
            () -> new SwordItem(Tiers.IRON,new Item.Properties().stacksTo(1)
                    .attributes(SwordItem.createAttributes(Tiers.IRON, 6.5f,2f))));
    public static final DeferredItem<Item> BRONZINE_PICKAXE = ITEMS.register("bronzine_pickaxe",
            () -> new PickaxeItem(Tiers.IRON,new Item.Properties().stacksTo(1)
                    .attributes(PickaxeItem.createAttributes(Tiers.IRON, 3.5f,2f))));
    public static final DeferredItem<Item> BRONZINE_AXE = ITEMS.register("bronzine_axe",
            () -> new AxeItem(Tiers.IRON,new Item.Properties().stacksTo(1)
                    .attributes(AxeItem.createAttributes(Tiers.IRON, 9.5f,1f))));
    public static final DeferredItem<Item> BRONZINE_SHOVEL = ITEMS.register("bronzine_shovel",
            () -> new ShovelItem(Tiers.IRON,new Item.Properties().stacksTo(1)
                    .attributes(ShovelItem.createAttributes(Tiers.IRON, 5.5f,2f))));
    public static final DeferredItem<Item> BRONZINE_HOE = ITEMS.register("bronzine_hoe",
            () -> new HoeItem(Tiers.IRON,new Item.Properties().stacksTo(1)
                    .attributes(HoeItem.createAttributes(Tiers.IRON, 2.5f,2f))));

    public static final DeferredItem<Item> INFERNIUM_SWORD = ITEMS.register("infernium_sword",
            () -> new SwordItem(Tiers.DIAMOND,new Item.Properties().stacksTo(1)
                    .attributes(SwordItem.createAttributes(Tiers.DIAMOND, 8.5f,1.2f))));
    public static final DeferredItem<Item> INFERNIUM_PICKAXE = ITEMS.register("infernium_pickaxe",
            () -> new PickaxeItem(Tiers.DIAMOND,new Item.Properties().stacksTo(1)
                    .attributes(PickaxeItem.createAttributes(Tiers.DIAMOND, 4.5f,1.2f))));
    public static final DeferredItem<Item> INFERNIUM_AXE = ITEMS.register("infernium_axe",
            () -> new AxeItem(Tiers.DIAMOND,new Item.Properties().stacksTo(1)
                    .attributes(AxeItem.createAttributes(Tiers.DIAMOND, 11.5f,0.5f))));
    public static final DeferredItem<Item> INFERNIUM_SHOVEL = ITEMS.register("infernium_shovel",
            () -> new ShovelItem(Tiers.DIAMOND,new Item.Properties().stacksTo(1)
                    .attributes(ShovelItem.createAttributes(Tiers.DIAMOND, 6.5f,1.2f))));
    public static final DeferredItem<Item> INFERNIUM_HOE = ITEMS.register("infernium_hoe",
            () -> new HoeItem(Tiers.DIAMOND,new Item.Properties().stacksTo(1)
                    .attributes(HoeItem.createAttributes(Tiers.DIAMOND, 4.5f,1.2f))));



}
