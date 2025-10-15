package com.anonym.astran.registries.custom;

import com.anonym.astran.Astran;
import com.anonym.astran.systems.assembly.AssemblyAbstractRecipe;
import com.anonym.astran.systems.assembly.assemblies.EyeAssembly;
import com.anonym.astran.systems.cybernetics.CyberModule;
import com.anonym.astran.systems.cybernetics.head.EyeModule;
import com.anonym.astran.systems.cybernetics.torso.AquaLungsModule;
import com.anonym.astran.systems.cybernetics.torso.PoisonFilterModule;
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
}
