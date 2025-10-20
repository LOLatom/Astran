package com.anonym.astran.registries.custom;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.hands.KineticDistributorModule;
import com.anonym.astran.systems.cybernetics.head.EyeModule;
import com.anonym.astran.systems.cybernetics.torso.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class AstranCyberModuleRegistry {

    public static final DeferredRegister<CyberModule> CYBER_MODULE_TYPE = DeferredRegister.create(AstranRegistries.CYBER_MODULE_REGISTRY, Astran.MODID);

    public static final DeferredHolder<CyberModule, EyeModule> EYES = CYBER_MODULE_TYPE.register("eyes",
            EyeModule::new);
    public static final DeferredHolder<CyberModule, AquaLungsModule> AQUA_LUNGS = CYBER_MODULE_TYPE.register("aqua_lungs",
            AquaLungsModule::new);
    public static final DeferredHolder<CyberModule, PoisonFilterModule> POISON_FILTER = CYBER_MODULE_TYPE.register("poison_filter",
            PoisonFilterModule::new);
    public static final DeferredHolder<CyberModule, BackBaseModule> BACK_BASE = CYBER_MODULE_TYPE.register("back_base",
            BackBaseModule::new);
    public static final DeferredHolder<CyberModule, BackCoverModule> BACK_COVER = CYBER_MODULE_TYPE.register("back_cover",
            BackCoverModule::new);
    public static final DeferredHolder<CyberModule, KineticAccumulatorModule> KINETIC_ACCUMULATOR = CYBER_MODULE_TYPE.register("kinetic_accumulator",
            KineticAccumulatorModule::new);
    public static final DeferredHolder<CyberModule, KineticDistributorModule> KINETIC_DISTRIBUTOR = CYBER_MODULE_TYPE.register("kinetic_distributor",
            KineticDistributorModule::new);
    public static final DeferredHolder<CyberModule, BackWingsModule> BACK_WINGS = CYBER_MODULE_TYPE.register("back_wings",
            BackWingsModule::new);
}
