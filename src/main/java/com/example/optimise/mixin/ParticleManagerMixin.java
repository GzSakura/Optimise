package com.example.optimise.mixin;

import com.example.optimise.OptimiseConfig;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Queue;
import java.util.Iterator;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    private static final int PARTICLE_CULL_DISTANCE = 32; // blocks
    
    @Inject(method = "renderParticles", at = @At("HEAD"), cancellable = true)
    private void onRenderParticles(LightmapTextureManager lightmapTextureManager, Camera camera, float tickDelta, CallbackInfo ci) {
        if (!OptimiseConfig.getInstance().isParticleOptimization()) {
            return;
        }
        
        // If particle optimization is enabled, we don't cancel rendering entirely
        // Instead, we optimize it in the tickParticles method below
    }
    
    @Inject(method = "tickParticles", at = @At("HEAD"))
    private void onTickParticles(CallbackInfo ci) {
        if (!OptimiseConfig.getInstance().isParticleOptimization()) {
            return;
        }
        
        ParticleManager manager = (ParticleManager)(Object)this;
        
        // Particle distance culling
        if (OptimiseConfig.getInstance().isParticleDistanceCulling()) {
            Iterator<Particle> iterator = manager.particles.iterator();
            while (iterator.hasNext()) {
                Particle particle = iterator.next();
                if (particle.isAlive()) {
                    double distance = Math.sqrt(
                        particle.getX() * particle.getX() + 
                        particle.getY() * particle.getY() + 
                        particle.getZ() * particle.getZ()
                    );
                    if (distance > PARTICLE_CULL_DISTANCE) {
                        particle.markDead();
                    }
                }
            }
        }
        
        // Particle count limit
        if (OptimiseConfig.getInstance().isParticleCountLimit()) {
            int maxParticles = OptimiseConfig.getInstance().getMaxParticleCount();
            int particleCount = 0;
            
            Iterator<Particle> iterator = manager.particles.iterator();
            while (iterator.hasNext()) {
                Particle particle = iterator.next();
                if (particle.isAlive()) {
                    particleCount++;
                    if (particleCount > maxParticles) {
                        particle.markDead();
                    }
                }
            }
        }
    }
}