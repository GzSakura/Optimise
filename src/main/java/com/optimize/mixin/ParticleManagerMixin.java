package com.optimize.mixin;

import com.optimize.config.ModConfig;
import com.optimize.manager.RenderingOptimizer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    
    @Inject(method = "addParticle", at = @At("HEAD"), cancellable = true)
    private void optimizeParticleAddition(Particle particle, CallbackInfo ci) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeRendering) {
            return;
        }
        
        if (!RenderingOptimizer.shouldRenderParticle(particle)) {
            ci.cancel();
        } else {
            RenderingOptimizer.onParticleAdded(particle);
        }
    }
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void optimizeParticleTicking(CallbackInfo ci) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeRendering) {
            return;
        }
        
        // 定期清理过多的粒子
        RenderingOptimizer.cleanupParticles();
    }
}