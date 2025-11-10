package com.optimize.mixin;

import com.optimize.config.ModConfig;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class WorldMixin {
    
    @Inject(method = "tickEntity", at = @At("HEAD"), cancellable = true)
    private void optimizeWorldTicking(CallbackInfo ci) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeEntities) {
            return;
        }
        
        // 世界级别的实体优化
        World world = (World) (Object) this;
        
        // 如果世界中没有玩家，减少tick频率
        if (world.getPlayers().isEmpty()) {
            if (world.getTime() % 20 != 0) { // 每秒只tick一次
                ci.cancel();
            }
        }
    }
}