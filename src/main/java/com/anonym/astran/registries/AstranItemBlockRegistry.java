package com.anonym.astran.registries;

import com.anonym.astran.Astran;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstranItemBlockRegistry {
    public static final DeferredRegister.Items BLOCK_ITEMS = DeferredRegister.createItems(Astran.MODID);


    public static void registerItemsForBlocks() {
        AstranBlockRegistry.ORE_BLOCKS.getEntries().stream().forEach((deferredHolder)-> {
            BLOCK_ITEMS.registerSimpleBlockItem(deferredHolder.getId().getPath(),deferredHolder);
        });
        AstranBlockRegistry.BLOCKS.getEntries().stream().forEach((deferredHolder)-> {
            BLOCK_ITEMS.registerSimpleBlockItem(deferredHolder.getId().getPath(),deferredHolder);
        });
    }

}
