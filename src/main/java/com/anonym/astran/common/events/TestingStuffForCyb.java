package com.anonym.astran.common.events;

import com.anonym.astran.common.entities.AstraniumMeteor;
import com.anonym.astran.registries.AstranEntityRegistry;
import com.anonym.astran.registries.AstranItemRegistry;
import com.anonym.astran.systems.events.BrushBlockEvent;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BrushableBlock;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Random;

@EventBusSubscriber
public class TestingStuffForCyb {

    @SubscribeEvent
    public static void spawnTimer(PlayerTickEvent.Post event) {
        /*
        if (!event.getEntity().level().isClientSide) {
            if (event.getEntity().level().getGameTime() % 12000 == 0) {
                AstraniumMeteor meteor = new AstraniumMeteor(AstranEntityRegistry.ASTRANIUM_METEOR.get(), event.getEntity().level());
                Vec3 pos = event.getEntity().getPosition(1);
                //event.getEntity().surface
                pos = pos.add(event.getEntity().getViewVector(1).multiply(50,50,50));
                pos = pos.add(0,100,0);
                meteor.setPos(pos);
                event.getEntity().level().addFreshEntity(meteor);
                System.out.println("SPAWNED METEOR");
            }
        }

         */
    }

    @SubscribeEvent
    public static void spawnTimer(BrushBlockEvent event) {
        if (event.getBlockstate().getBlock() == Blocks.BEDROCK) {
            Random rdm = new Random();
            if (rdm.nextInt(100) == 2) {
                ItemEntity itementity = new ItemEntity(event.getPlayer().level(), event.getBlockpos().getX(), event.getBlockpos().getY() + 1, event.getBlockpos().getZ(),
                        new ItemStack(AstranItemRegistry.BEDROCK_POWDER.asItem()));
                itementity.setDefaultPickUpDelay();
                event.getPlayer().level().addFreshEntity(itementity);
            }
        }
    }


}
