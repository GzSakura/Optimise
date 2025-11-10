package com.optimize.mixin;

import com.optimize.config.ModConfig;
import com.optimize.manager.RenderingOptimizer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void optimizeEntityRendering(double distance, CallbackInfoReturnable<Boolean> cir) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeEntities) {
            return;
        }
        
        Entity entity = (Entity) (Object) this;
        
        // 检查实体距离
        if (distance > ModConfig.get().entityRenderDistance) {
            cir.setReturnValue(false);
            return;
        }
        
        // 检查区块内的实体数量
        if (entity.getWorld() != null) {
            int entityCount = entity.getWorld().getOtherEntities(entity, entity.getBoundingBox().expand(16), 
                e -> e.squaredDistanceTo(entity) < 256).size();
            
            if (entityCount > ModConfig.get().maxEntitiesPerChunk) {
                cir.setReturnValue(false);
            }
        }
    }
    
    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void optimizeEntityTicking(CallbackInfoReturnable<Void> cir) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeEntities) {
            return;
        }
        
        Entity entity = (Entity) (Object) this;
        
        // 距离玩家太远的实体减少tick频率
        if (entity.getWorld() != null && entity.getWorld().getClosestPlayer(entity, 128) == null) {
            if (entity.age % 4 != 0) { // 减少75%的tick频率
                cir.setReturnValue(null);
            }
        }
    }
}