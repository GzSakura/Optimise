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
        }
    }
    
    @Inject(method = "tickParticle", at = @At("HEAD"), cancellable = true)
    private void optimizeParticleTicking(Particle particle, CallbackInfo ci) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeRendering) {
            return;
        }
        
        // 距离太远的粒子不更新
        if (particle.getWorld() != null && particle.getWorld().getClosestPlayer(particle.getX(), particle.getY(), particle.getZ(), 64, false) == null) {
            ci.cancel();
        }
    }
}