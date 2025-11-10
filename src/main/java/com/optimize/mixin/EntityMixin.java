package com.optimize.mixin;

import com.optimize.config.ModConfig;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityMixin {
    
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void optimizeEntityTicking(CallbackInfo ci) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeEntities) {
            return;
        }
        
        Entity entity = (Entity) (Object) this;
        
        // 距离玩家太远的实体减少tick频率
        if (entity.getWorld() != null && entity.getWorld().getClosestPlayer(entity, 128) == null) {
            if (entity.age % 4 != 0) { // 减少75%的tick频率
                ci.cancel();
            }
        }
    }
}