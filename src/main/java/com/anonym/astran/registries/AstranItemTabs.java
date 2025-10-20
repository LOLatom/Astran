package com.anonym.astran.registries;

import com.anonym.astran.Astran;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstranItemTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Astran.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ASTRAN_MAIN_TAB = CREATIVE_MODE_TABS.register("radium_main_tab", () -> CreativeModeTab.builder()
            .title(Component.nullToEmpty("Astran"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> AstranItemRegistry.ASTRANIUM_NODE.asItem().getDefaultInstance())
            .displayItems((parameters, output) -> {
                AstranItemRegistry.ITEMS.getEntries().forEach(itemDeferredHolder -> {
                    output.accept(itemDeferredHolder.get().getDefaultInstance());

                });
                AstranItemBlockRegistry.BLOCK_ITEMS.getEntries().forEach(itemDeferredHolder -> {
                    output.accept(itemDeferredHolder.get().getDefaultInstance());

                });
            }).build());

}
