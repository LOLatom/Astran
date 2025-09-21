package com.anonym.astran.systems.entity.breakableentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;

public abstract class BreakableEntity extends Entity {

    public final float HARDNESS_VALUE;

    public float breakProgress = 0f;


    private static EntityDataAccessor<Integer> BREAKING_STAGE = SynchedEntityData.defineId(BreakableEntity.class, EntityDataSerializers.INT);

    public BreakableEntity(EntityType<?> entityType, Level level, float hardness) {
        super(entityType, level);
        this.HARDNESS_VALUE = hardness;
        if (this.breakProgress < 10 && this.getBreakingStage() == 1) this.breakProgress = 11f;
        if (this.breakProgress < 30 && this.getBreakingStage() == 2) this.breakProgress = 31f;
        if (this.breakProgress < 50 && this.getBreakingStage() == 3) this.breakProgress = 51f;
        if (this.breakProgress < 70 && this.getBreakingStage() == 4) this.breakProgress = 71f;
    }

    public BreakableEntity(EntityType<?> entityType, Level level) {
        this(entityType, level, 1.0f);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }


    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }


    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(BREAKING_STAGE,0);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (BREAKING_STAGE.equals(key)) {
            this.refreshDimensions();
        }

        super.onSyncedDataUpdated(key);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {
        this.setBreakingStage(compoundTag.getInt("BreakingStage"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {
        compoundTag.putInt("BreakingStage",this.getBreakingStage());
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) {
            int i = (int) this.breakProgress;
            if (i >= 10) {
                if (i >= 30) {
                    if (i >= 50) {
                        if (i >= 70) {
                            if (i >= 100) {
                                this.setBreakingStage(5);
                            } else {
                                this.setBreakingStage(4);
                            }
                        } else {
                            this.setBreakingStage(3);

                        }
                    } else {
                        this.setBreakingStage(2);

                    }
                } else {
                    this.setBreakingStage(1);

                }
            }

        }
    }

    public void setBreakingStage(int stage) {
        this.entityData.set(BREAKING_STAGE,stage);
    }

    public int getBreakingStage() {
        return this.entityData.get(BREAKING_STAGE);
    }

    public abstract SoundEvent getSoundEvent();

    public abstract SoundEvent getFinishedBreakingSound();

    public abstract Block getParticleBlock();

    public abstract float getEntityWidth();

    public abstract float getEntityHeight();

    public void onBreak() {

    }


}
