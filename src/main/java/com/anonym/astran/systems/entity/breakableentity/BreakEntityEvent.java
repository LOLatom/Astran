package com.anonym.astran.systems.entity.breakableentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import java.util.Random;

@EventBusSubscriber(value = Dist.CLIENT)
public class BreakEntityEvent {
    @SubscribeEvent
    public static void onAttackEntity(ClientTickEvent.Post event) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (Minecraft.getInstance().options.keyAttack.isDown()) {
            if (Minecraft.getInstance().crosshairPickEntity instanceof BreakableEntity breakableEntity) {
                player.swing(InteractionHand.MAIN_HAND);
                if (breakableEntity.tickCount % 4 == 0) {
                    player.playSound(breakableEntity.getSoundEvent(), 0.4f, 2f);
                    float added = 0;
                    if (player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof PickaxeItem item) added = item.getTier().getAttackDamageBonus() * 3.5f;
                    breakableEntity.breakProgress += (float) (0.1f * (added));


                    player.connection.send((new BreakingStagePayload(breakableEntity.getBreakingStage(),breakableEntity.getId())));
                    Vec3i v = player.getNearestViewDirection().getOpposite().getNormal();
                    Random random = new Random();
                    float xz = (random.nextFloat()* breakableEntity.getEntityWidth())-(breakableEntity.getEntityWidth()/2);
                    float y = (random.nextFloat()*breakableEntity.getEntityHeight());
                    Vec3 randFromV = new Vec3((v.getX() == 0 ? xz : v.getX()), y,(v.getZ() == 0 ? xz : v.getZ()));
                    player.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, breakableEntity.getParticleBlock().defaultBlockState()),
                            breakableEntity.getX() + (randFromV.x()*1.5),breakableEntity.getY()+randFromV.y(),breakableEntity.getZ()+ (randFromV.z()*1.5),0,-0.1,0);
                    if (breakableEntity.getBreakingStage() == 5) {
                        player.playSound(breakableEntity.getFinishedBreakingSound(), 1f, 1.5f);
                        for (int i = 0; i < 20; i++) {
                            player.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK,
                                            breakableEntity.getParticleBlock().defaultBlockState()),
                                    breakableEntity.getX() + (random.nextFloat()* breakableEntity.getEntityWidth())-(breakableEntity.getEntityWidth()/2),
                                    breakableEntity.getY()+(random.nextFloat()*breakableEntity.getEntityHeight()),
                                    breakableEntity.getZ() + (random.nextFloat()* breakableEntity.getEntityWidth())-(breakableEntity.getEntityWidth()/2),
                                    -0.1,-0.1,0);

                        }

                    }
                }
            }
        }
    }
}
