package com.anonym.astran.registries;

import com.anonym.astran.Astran;
import com.anonym.astran.server.TestEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstranEntityRegistry {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, Astran.MODID);

    public static final DeferredHolder<EntityType<?>,EntityType<TestEntity>> TEST = ENTITIES.register("test", () ->
            EntityType.Builder.<TestEntity>of(TestEntity::new, MobCategory.MISC).sized(3F,4F).build(
                    ResourceLocation.fromNamespaceAndPath(Astran.MODID, "test").toString()));

}
