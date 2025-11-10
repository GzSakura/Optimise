package com.optimize.mixin;

import com.optimize.config.ModConfig;
import com.optimize.manager.RenderingOptimizer;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    
    @Inject(method = "addParticle", at = @At("HEAD"), cancellable = true)
    private void optimizeParticleAddition(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> cir) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeRendering) {
            return;
        }
        
        // We can't check if we should render the particle here since it hasn't been created yet
        // Instead, we'll handle this in the RenderingOptimizer when the particle is actually created
    }
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void optimizeParticleTicking(CallbackInfo ci) {
        if (!ModConfig.get().enabled || !ModConfig.get().optimizeRendering) {
            return;
        }
        
        // Periodically clean up excessive particles
        RenderingOptimizer.cleanupParticles();
    }
}