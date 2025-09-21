package com.anonym.astran.registries;

import com.anonym.astran.Astran;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstranBlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Astran.MODID);

    public static final DeferredRegister.Blocks ORE_BLOCKS = DeferredRegister.createBlocks(Astran.MODID);

    public static final DeferredHolder<Block, Block> ELECTRUM_CHUNK_BLOCK = BLOCKS.register("electrum_chunk_block", () -> new Block(BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(3.1f,10f)));


}
