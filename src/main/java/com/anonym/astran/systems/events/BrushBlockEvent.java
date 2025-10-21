package com.anonym.astran.systems.events;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.ICancellableEvent;

public class BrushBlockEvent extends Event implements ICancellableEvent {

    private final BlockPos blockpos;
    private final BlockState blockstate;
    private final HumanoidArm humanoidarm;
    private final Player player;
    private final ItemStack stack;

    public BrushBlockEvent(BlockPos blockpos, BlockState blockstate, HumanoidArm humanoidarm, Player player, ItemStack stack) {
        this.blockpos = blockpos;
        this.blockstate = blockstate;
        this.humanoidarm = humanoidarm;
        this.player = player;
        this.stack = stack;
    }


    public BlockPos getBlockpos() {
        return blockpos;
    }

    public Player getPlayer() {
        return player;
    }

    public BlockState getBlockstate() {
        return blockstate;
    }

    public HumanoidArm getHumanoidarm() {
        return humanoidarm;
    }

    public ItemStack getStack() {
        return stack;
    }
}
