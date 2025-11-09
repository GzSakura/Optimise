package com.example.optimise.mixin;

import com.example.optimise.OptimiseConfig;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    private static final int ENTITY_CULL_DISTANCE = 64; // blocks
    
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void shouldRenderEntity(Entity entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (!OptimiseConfig.getInstance().isEntityRenderingOptimization()) {
            return;
        }
        
        // Skip for player entities to avoid issues
        if (entity instanceof PlayerEntity) {
            return;
        }
        
        // Entity distance culling
        if (OptimiseConfig.getInstance().isEntityDistanceCulling()) {
            double distance = Math.sqrt(x * x + y * y + z * z);
            if (distance > ENTITY_CULL_DISTANCE) {
                cir.setReturnValue(false);
                return;
            }
        }
        
        // Entity LOD (Level of Detail) - skip rendering certain entities at distance
        if (OptimiseConfig.getInstance().isEntityLOD()) {
            double distance = Math.sqrt(x * x + y * y + z * z);
            // For entities far away, use simplified rendering
            if (distance > ENTITY_CULL_DISTANCE * 0.7) {
                // This could be expanded to use simplified models
                // For now, we'll just reduce the render frequency
                if (entity.age % 2 != 0) {
                    cir.setReturnValue(false);
                    return;
                }
            }
        }
    }
}