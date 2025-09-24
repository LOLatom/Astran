package com.anonym.astran.systems.cybernetics.core;

import com.anonym.astran.registries.AstranDataComponentRegistry;
import com.anonym.astran.registries.client.AstranSoundRegistry;
import com.anonym.astran.systems.energy.CoreNodeItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SteelHeartItem extends Item {
    public SteelHeartItem(Properties properties) {
        super(properties);
    }


    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return super.getTooltipImage(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public SteelHeartData getNodes(ItemStack stack) {
        return stack.get(AstranDataComponentRegistry.STEEL_HEART_DATA);
    }


    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction action, Player player) {
        SteelHeartData data = getNodes(stack);
        if (slot.getItem().isEmpty()) return false;
        if (data.secondNode().isPresent()&& data.thirdNode().isPresent()&& data.firstNode().isPresent()) return false;
        if (slot.index >= 36 && slot.index <= 45) return false;
        if (slot.getItem().getItem() instanceof CoreNodeItem ) {
            SteelHeartData newData = data;
            Optional<ItemStack> stackOptional = player.getInventory().getItem(slot.index).is(Items.AIR) ? Optional.empty() : Optional.of(player.getInventory().getItem(slot.index).copy());
            if (data.firstNode().isPresent()) {
                if (data.secondNode().isPresent()) {
                    newData = new SteelHeartData(data.firstNode(),data.secondNode(),stackOptional);
                } else {
                    newData = new SteelHeartData(data.firstNode(),stackOptional,data.thirdNode());

                }
            } else {
                newData = new SteelHeartData(stackOptional,data.secondNode(),data.thirdNode());

            }
            System.out.println(newData);
            Random random = new Random();
            stack.set(AstranDataComponentRegistry.STEEL_HEART_DATA,newData);
            player.playSound(AstranSoundRegistry.STEEL_HEART_INSERT.get(),0.7f + (random.nextFloat()*0.2f),1.5f + (random.nextFloat()*0.5f));
            player.getInventory().removeItem(slot.index,1);

            return true;
        }

        return false;
        //return super.overrideStackedOnOther(stack, slot, action, player);e
    }

}
