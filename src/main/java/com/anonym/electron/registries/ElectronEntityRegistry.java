package com.anonym.electron.registries;

import com.anonym.electron.Electron;
import com.anonym.electron.server.TestEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ElectronEntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Electron.MODID);

    public static final DeferredHolder<EntityType<?>,EntityType<TestEntity>> TEST = ENTITIES.register("test", () ->
            EntityType.Builder.<TestEntity>of(TestEntity::new, MobCategory.MISC).sized(3F,4F).build(
                    ResourceLocation.fromNamespaceAndPath(Electron.MODID, "test").toString()));

}
