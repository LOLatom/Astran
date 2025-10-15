package com.anonym.astran.systems.cybernetics;

import com.anonym.astran.Astran;
import com.anonym.astran.registries.AstranAttachmentTypeRegistry;
import com.anonym.astran.registries.AstranBoneDataRegistry;
import com.anonym.astran.registries.custom.AstranRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.UUID;

public class CyberneticsManager {

    private Player player;

    public CyberneticsManager(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void addModule(CyberModule module) {
        StorageForLimbData storage = getStorageForLimb(module).copy();
        storage.addCyberModule(module);
        replaceLimbStorageData(storage, module.getAttachment());
    }
    public void removeModule(CyberModule module) {
        removeModule(module.getInstanceId(),module.getAttachment());
    }
    public void removeModule(UUID uuid, LimbType type) {
        CachedModuleData cache = moduleCache();
        if (cache.getEquippedModules().containsKey(uuid)) {
            BoneData bone = getBoneDataFromModule(type).copy();
            removeFromCache(uuid);
            for (SocketData socket : bone.getSockets()) {
                if (socket.hasModule()) {
                    if (socket.getModuleInstanceId().equals(uuid)) socket.setModuleInstanceId(null);
                }
            }
            replaceBoneData(bone);
        }
        StorageForLimbData storage = getStorageForLimb(type).copy();
        storage.removeCyberModule(uuid);
        replaceLimbStorageData(storage, type);
    }

    public void removeFromCache(CyberModule module) {
        removeFromCache(module.getInstanceId());
    }
    public void removeFromCache(UUID uuid) {
        CachedModuleData cache = moduleCache().copy();
        cache.getEquippedModules().remove(uuid);
        cache.getEquippedModuleTypes().remove(uuid);
        cache.getEquippedModuleInstances().remove(uuid);
        setCache(cache);
    }
    public void addToCache(CyberModule module) {
        CachedModuleData cache = moduleCache().copy();
        cache.getEquippedModules().put(module.getInstanceId(),module.getModuleID());
        cache.getEquippedModuleTypes().put(module.getInstanceId(),module.getSubType());
        cache.getEquippedModuleInstances().put(module.getInstanceId(),module);
        setCache(cache);
    }

    public void equipModule(int socketIndex, CyberModule module) {
        if (socketIndex < 10 && socketIndex > -1) {
            BoneData bone = getBoneDataFromModule(module).copy();
            bone.getSockets().get(socketIndex).setModuleInstanceId(module.getInstanceId());
            addToCache(module);
            replaceBoneData(bone);
        }
    }
    public void unEquipModule(int socketIndex , CyberModule module) {
        if (socketIndex < 10 && socketIndex > -1) {
            BoneData bone = getBoneDataFromModule(module).copy();
            bone.getSockets().get(socketIndex).setModuleInstanceId(null);
            removeFromCache(module);
            replaceBoneData(bone);
        }
    }
    public @Nullable CyberModule getModule(UUID uuid) {
        if (this.player.getData(AstranAttachmentTypeRegistry.HEAD_STORAGE).getCyberModuleMap().containsKey(uuid)) {
            return this.player.getData(AstranAttachmentTypeRegistry.HEAD_STORAGE).getCyberModuleMap().get(uuid);

        } else if (this.player.getData(AstranAttachmentTypeRegistry.TORSO_STORAGE).getCyberModuleMap().containsKey(uuid)) {
            return this.player.getData(AstranAttachmentTypeRegistry.TORSO_STORAGE).getCyberModuleMap().get(uuid);

        } else if (this.player.getData(AstranAttachmentTypeRegistry.HIP_STORAGE).getCyberModuleMap().containsKey(uuid)) {
            return this.player.getData(AstranAttachmentTypeRegistry.HIP_STORAGE).getCyberModuleMap().get(uuid);

        } else if (this.player.getData(AstranAttachmentTypeRegistry.LEFT_HAND_STORAGE).getCyberModuleMap().containsKey(uuid)) {
            return this.player.getData(AstranAttachmentTypeRegistry.LEFT_HAND_STORAGE).getCyberModuleMap().get(uuid);

        } else if (this.player.getData(AstranAttachmentTypeRegistry.LEFT_SHOULDER_STORAGE).getCyberModuleMap().containsKey(uuid)) {
            return this.player.getData(AstranAttachmentTypeRegistry.LEFT_SHOULDER_STORAGE).getCyberModuleMap().get(uuid);

        } else if (this.player.getData(AstranAttachmentTypeRegistry.LEFT_LEG_STORAGE).getCyberModuleMap().containsKey(uuid)) {
            return this.player.getData(AstranAttachmentTypeRegistry.LEFT_LEG_STORAGE).getCyberModuleMap().get(uuid);

        } else if (this.player.getData(AstranAttachmentTypeRegistry.RIGHT_HAND_STORAGE).getCyberModuleMap().containsKey(uuid)) {
            return this.player.getData(AstranAttachmentTypeRegistry.RIGHT_HAND_STORAGE).getCyberModuleMap().get(uuid);

        } else if (this.player.getData(AstranAttachmentTypeRegistry.RIGHT_SHOULDER_STORAGE).getCyberModuleMap().containsKey(uuid)) {
            return this.player.getData(AstranAttachmentTypeRegistry.RIGHT_SHOULDER_STORAGE).getCyberModuleMap().get(uuid);

        } else if (this.player.getData(AstranAttachmentTypeRegistry.RIGHT_LEG_STORAGE).getCyberModuleMap().containsKey(uuid)) {
            return this.player.getData(AstranAttachmentTypeRegistry.RIGHT_LEG_STORAGE).getCyberModuleMap().get(uuid);
        }
        return null;
    }

    public CyberModule getEquippedModuleClass(UUID uuid) {
        String type = moduleCache().getEquippedModules().get(uuid);
        return AstranRegistries.CYBER_MODULE_REGISTRY.get(ResourceLocation.fromNamespaceAndPath(Astran.MODID,type));
    }
    public CyberModule getEquippedModuleInstance(UUID uuid) {
        return moduleCache().getEquippedModuleInstances().get(uuid);
    }

    public CyberModule getEquippedModuleClass(CyberModule module) {
        return getEquippedModuleClass(module.getInstanceId());
    }

    public CyberModule getEquippedModuleClass(String type) {
        return AstranRegistries.CYBER_MODULE_REGISTRY.get(ResourceLocation.fromNamespaceAndPath(Astran.MODID,type));
    }

    public boolean hasEquippedModule(CyberModule module) {
        return hasEquippedModule(module.getInstanceId());
    }
    public boolean hasEquippedModule(UUID uuid) {
        return moduleCache().isModuleEquipped(uuid);
    }
    public boolean hasEquippedModule(String id) {
        return moduleCache().isModuleEquipped(id);
    }
    public boolean hasModuleTypeEquipped(CyberModule module) {
        return moduleCache().isModuleTypeEquipped(module.getSubType());
    }
    public boolean hasModuleTypeEquipped(UUID uuid) {
        return moduleCache().isModuleTypeEquipped(uuid);
    }
    public boolean hasModuleTypeEquipped(String type) {
        return moduleCache().isModuleTypeEquipped(type);
    }

    public CachedModuleData moduleCache() {
        return this.player.getData(AstranAttachmentTypeRegistry.MODULE_CACHE_DATA);
    }
    public CachedModuleData setCache(CachedModuleData cache) {
        return this.player.setData(AstranAttachmentTypeRegistry.MODULE_CACHE_DATA,cache);
    }

    public StorageForLimbData getStorageForLimb(CyberModule module) {
        return getStorageForLimb(module.getAttachment());
    }
    public StorageForLimbData getStorageForLimb(LimbType type) {
        return switch (type) {
            case HEAD -> this.player.getData(AstranAttachmentTypeRegistry.HEAD_STORAGE);
            case HIPS -> this.player.getData(AstranAttachmentTypeRegistry.HIP_STORAGE);
            case TORSO -> this.player.getData(AstranAttachmentTypeRegistry.TORSO_STORAGE);
            case LEFT_LEG -> this.player.getData(AstranAttachmentTypeRegistry.LEFT_LEG_STORAGE);
            case LEFT_HAND -> this.player.getData(AstranAttachmentTypeRegistry.LEFT_HAND_STORAGE);
            case LEFT_SHOULDER -> this.player.getData(AstranAttachmentTypeRegistry.LEFT_SHOULDER_STORAGE);
            case RIGHT_LEG -> this.player.getData(AstranAttachmentTypeRegistry.RIGHT_LEG_STORAGE);
            case RIGHT_HAND -> this.player.getData(AstranAttachmentTypeRegistry.RIGHT_HAND_STORAGE);
            case RIGHT_SHOULDER -> this.player.getData(AstranAttachmentTypeRegistry.RIGHT_SHOULDER_STORAGE);
            default -> this.player.getData(AstranAttachmentTypeRegistry.HEAD_STORAGE);
        };
    }

    public boolean replaceLimbStorageData(StorageForLimbData storage,LimbType type) {
        switch (type) {
            case HEAD -> this.player.setData(AstranAttachmentTypeRegistry.HEAD_STORAGE,storage);
            case HIPS -> this.player.setData(AstranAttachmentTypeRegistry.HIP_STORAGE,storage);
            case TORSO -> this.player.setData(AstranAttachmentTypeRegistry.TORSO_STORAGE,storage);
            case LEFT_LEG -> this.player.setData(AstranAttachmentTypeRegistry.LEFT_LEG_STORAGE,storage);
            case LEFT_HAND -> this.player.setData(AstranAttachmentTypeRegistry.LEFT_HAND_STORAGE,storage);
            case LEFT_SHOULDER -> this.player.setData(AstranAttachmentTypeRegistry.LEFT_SHOULDER_STORAGE,storage);
            case RIGHT_LEG -> this.player.setData(AstranAttachmentTypeRegistry.RIGHT_LEG_STORAGE,storage);
            case RIGHT_HAND -> this.player.setData(AstranAttachmentTypeRegistry.RIGHT_HAND_STORAGE,storage);
            case RIGHT_SHOULDER -> this.player.setData(AstranAttachmentTypeRegistry.RIGHT_SHOULDER_STORAGE,storage);
            case null, default -> {
                return false;
            }
        }
        return true;
    }


    public BoneData getBoneDataFromModule(CyberModule module) {
        return getBoneDataFromModule(module.getAttachment());
    }
    public BoneData getBoneDataFromModule(LimbType type) {
        return switch (type) {
            case HEAD -> this.player.getData(AstranBoneDataRegistry.HEAD);
            case HIPS -> this.player.getData(AstranBoneDataRegistry.HIP);
            case TORSO -> this.player.getData(AstranBoneDataRegistry.TORSO);
            case LEFT_LEG -> this.player.getData(AstranBoneDataRegistry.LEFT_LEG);
            case LEFT_HAND -> this.player.getData(AstranBoneDataRegistry.LEFT_HAND);
            case LEFT_SHOULDER -> this.player.getData(AstranBoneDataRegistry.LEFT_SHOULDER);
            case RIGHT_LEG -> this.player.getData(AstranBoneDataRegistry.RIGHT_LEG);
            case RIGHT_HAND -> this.player.getData(AstranBoneDataRegistry.RIGHT_HAND);
            case RIGHT_SHOULDER -> this.player.getData(AstranBoneDataRegistry.RIGHT_SHOULDER);
            default -> this.player.getData(AstranBoneDataRegistry.HEAD);
        };
    }

    public boolean replaceBoneData(BoneData boneData) {
        switch (boneData.getType()) {
            case HEAD -> this.player.setData(AstranBoneDataRegistry.HEAD,boneData);
            case HIPS -> this.player.setData(AstranBoneDataRegistry.HIP,boneData);
            case TORSO -> this.player.setData(AstranBoneDataRegistry.TORSO,boneData);
            case LEFT_LEG -> this.player.setData(AstranBoneDataRegistry.LEFT_LEG,boneData);
            case LEFT_HAND -> this.player.setData(AstranBoneDataRegistry.LEFT_HAND,boneData);
            case LEFT_SHOULDER -> this.player.setData(AstranBoneDataRegistry.LEFT_SHOULDER,boneData);
            case RIGHT_LEG -> this.player.setData(AstranBoneDataRegistry.RIGHT_LEG,boneData);
            case RIGHT_HAND -> this.player.setData(AstranBoneDataRegistry.RIGHT_HAND,boneData);
            case RIGHT_SHOULDER -> this.player.setData(AstranBoneDataRegistry.RIGHT_SHOULDER,boneData);
            case null, default -> {
                return false;
            }
        }
        return true;
    }


}
