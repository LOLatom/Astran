package com.anonym.astran.common.entities;

import com.anonym.astran.registries.AstranBlockRegistry;
import com.anonym.astran.registries.AstranItemRegistry;
import com.anonym.astran.systems.entity.breakableentity.BreakableEntity;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.*;

public class AstraniumMeteor extends BreakableEntity {

    private double floorDistance = 0;

    private boolean land = false;

    private List<ChunkPos> tickets = new ArrayList<>();


    public AstraniumMeteor(EntityType<? extends AstraniumMeteor> entityType, Level level) {
        super(entityType, level,5f);
        //this.setDeltaMovement(1.59,-4.51,0);
        if (this.onGround()) this.land = true;
    }



    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            Vec3 norm = new Vec3(Direction.DOWN.getNormal().getX(), Direction.DOWN.getNormal().getY(), Direction.DOWN.getNormal().getZ());
            Vec3 loc = this.position().add(norm.multiply(500, 500, 500));

            ClipContext context = new ClipContext(this.position(), loc, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, CollisionContext.empty());

            BlockHitResult hitResult = this.level().clip(context);

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                this.floorDistance = (Math.sqrt(distanceToSqr(hitResult.getBlockPos().getCenter())));
            }
        }
        updateRotation();

        if (this.onGround() &&!this.land) {
            this.setLand(true);
        }

        if (!this.landed()) {
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(6,-2.51,0.5);
            if (this.level() instanceof ServerLevel serverLevel) {

                if (!this.tickets.contains(this.chunkPosition())) {
                    serverLevel.getChunkSource().addRegionTicket(TicketType.FORCED, this.chunkPosition(), 31, this.chunkPosition(),true);
                    this.tickets.add(this.chunkPosition());
                }
            }

        } else {
            this.setDeltaMovement(0,-4.51,0);
            this.move(MoverType.SELF, Vec3.ZERO);
        }

        if (this.landed() && !this.onGround()) {
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(0,-4.51,0);
        }



    }

    @Override
    public SoundEvent getSoundEvent() {
        return SoundEvents.STONE_STEP;
    }

    @Override
    public SoundEvent getFinishedBreakingSound() {
        return SoundEvents.STONE_BREAK;
    }

    @Override
    public Block getParticleBlock() {
        return AstranBlockRegistry.ELECTRUM_CHUNK_BLOCK.get();
    }

    @Override
    public float getEntityWidth() {
        return 3;
    }

    @Override
    public float getEntityHeight() {
        return 4;
    }

    protected void updateRotation() {
        Vec3 vec3 = this.getDeltaMovement();
        double d0 = vec3.horizontalDistance();
        this.setXRot(lerpRotation(this.xRotO, (float)(Mth.atan2(vec3.y, d0) * (double)180.0F / (double)(float)Math.PI)));
        this.setYRot(lerpRotation(this.yRotO, (float)(Mth.atan2(vec3.x, vec3.z) * (double)180.0F / (double)(float)Math.PI)));
    }

    protected static float lerpRotation(float currentRotation, float targetRotation) {
        while(targetRotation - currentRotation < -180.0F) {
            currentRotation -= 360.0F;
        }

        while(targetRotation - currentRotation >= 180.0F) {
            currentRotation += 360.0F;
        }

        return Mth.lerp(0.2F, currentRotation, targetRotation);
    }

    @Override
    public void onBreak() {
        if (!level().isClientSide) {

            for (int i = 0; i < 6; i++) {
                spawnAtLocation(new ItemStack(AstranItemRegistry.ELECTRUM_CLUSTER.get(),random.nextInt(12)));
                System.out.println("spawn");
            }
            spawnAtLocation(new ItemStack(AstranItemRegistry.ASTRANIUM_CORE.get(),1));

        }
    }

    @Override
    public void setOnGround(boolean onGround) {
        super.setOnGround(onGround);
    }

    @OnlyIn(Dist.CLIENT)
    public double getFloorDistance() {
        return this.floorDistance;
    }

    public boolean landed() {
        return this.land;
    }

    public void setLand(boolean land) {
        this.land = land;
        System.out.println("BAM");
        this.setDeltaMovement(-this.getDeltaMovement().x,-4.51,-this.getDeltaMovement().z);
        if (!this.level().isClientSide) {

            if (this.tickCount > 20) {
                this.level().explode(this, this.position().x, this.position().y, this.position().z, 6, Level.ExplosionInteraction.BLOCK);
            }
            if (this.level() instanceof ServerLevel serverLevel) {
                for (ChunkPos pos : this.tickets) {
                    serverLevel.getChunkSource().removeRegionTicket(TicketType.FORCED, pos, 31, pos,true);
                }
                this.tickets.clear();
            }
        }
    }
}