package com.example.optimise;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OptimiseConfig {
    private static final File configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), "optimise.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    // Rendering optimizations
    public boolean entityRenderingOptimization = true;
    public boolean particleOptimization = true;
    public boolean blockRenderingOptimization = true;
    public boolean weatherRenderingOptimization = true;
    
    // Advanced rendering options
    public boolean entityDistanceCulling = true;
    public boolean entityLOD = true;
    public boolean particleDistanceCulling = true;
    public boolean particleCountLimit = true;
    public int maxParticleCount = 100;
    public boolean fastSkyRendering = true;
    public boolean fastCloudRendering = true;
    public boolean optimizedBlockTicking = true;
    public boolean reducedFogRendering = true;
    
    // Memory optimizations
    public boolean memoryOptimization = true;
    public boolean textureOptimization = true;
    public boolean resourceUnloading = true;
    public int resourceUnloadDelay = 300; // seconds
    
    // GUI optimizations
    public boolean guiOptimization = true;
    public boolean reducedGuiAnimations = true;
    public boolean fastInventoryRendering = true;
    
    private static OptimiseConfig INSTANCE = new OptimiseConfig();
    
    public static OptimiseConfig getInstance() {
        return INSTANCE;
    }
    
    public static void loadConfig() {
        if (!configFile.exists()) {
            saveConfig();
            return;
        }
        
        try (FileReader reader = new FileReader(configFile)) {
            INSTANCE = gson.fromJson(reader, OptimiseConfig.class);
        } catch (IOException e) {
            OptimiseMod.LOGGER.error("Could not load config file", e);
        }
    }
    
    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(INSTANCE, writer);
        } catch (IOException e) {
            OptimiseMod.LOGGER.error("Could not save config file", e);
        }
    }
    
    // Getters and setters for rendering optimizations
    public boolean isEntityRenderingOptimization() {
        return entityRenderingOptimization;
    }
    
    public void setEntityRenderingOptimization(boolean entityRenderingOptimization) {
        this.entityRenderingOptimization = entityRenderingOptimization;
        saveConfig();
    }
    
    public boolean isParticleOptimization() {
        return particleOptimization;
    }
    
    public void setParticleOptimization(boolean particleOptimization) {
        this.particleOptimization = particleOptimization;
        saveConfig();
    }
    
    public boolean isBlockRenderingOptimization() {
        return blockRenderingOptimization;
    }
    
    public void setBlockRenderingOptimization(boolean blockRenderingOptimization) {
        this.blockRenderingOptimization = blockRenderingOptimization;
        saveConfig();
    }
    
    public boolean isWeatherRenderingOptimization() {
        return weatherRenderingOptimization;
    }
    
    public void setWeatherRenderingOptimization(boolean weatherRenderingOptimization) {
        this.weatherRenderingOptimization = weatherRenderingOptimization;
        saveConfig();
    }
    
    // Getters and setters for advanced rendering options
    public boolean isEntityDistanceCulling() {
        return entityDistanceCulling;
    }
    
    public void setEntityDistanceCulling(boolean entityDistanceCulling) {
        this.entityDistanceCulling = entityDistanceCulling;
        saveConfig();
    }
    
    public boolean isEntityLOD() {
        return entityLOD;
    }
    
    public void setEntityLOD(boolean entityLOD) {
        this.entityLOD = entityLOD;
        saveConfig();
    }
    
    public boolean isParticleDistanceCulling() {
        return particleDistanceCulling;
    }
    
    public void setParticleDistanceCulling(boolean particleDistanceCulling) {
        this.particleDistanceCulling = particleDistanceCulling;
        saveConfig();
    }
    
    public boolean isParticleCountLimit() {
        return particleCountLimit;
    }
    
    public void setParticleCountLimit(boolean particleCountLimit) {
        this.particleCountLimit = particleCountLimit;
        saveConfig();
    }
    
    public int getMaxParticleCount() {
        return maxParticleCount;
    }
    
    public void setMaxParticleCount(int maxParticleCount) {
        this.maxParticleCount = maxParticleCount;
        saveConfig();
    }
    
    public boolean isFastSkyRendering() {
        return fastSkyRendering;
    }
    
    public void setFastSkyRendering(boolean fastSkyRendering) {
        this.fastSkyRendering = fastSkyRendering;
        saveConfig();
    }
    
    public boolean isFastCloudRendering() {
        return fastCloudRendering;
    }
    
    public void setFastCloudRendering(boolean fastCloudRendering) {
        this.fastCloudRendering = fastCloudRendering;
        saveConfig();
    }
    
    public boolean isOptimizedBlockTicking() {
        return optimizedBlockTicking;
    }
    
    public void setOptimizedBlockTicking(boolean optimizedBlockTicking) {
        this.optimizedBlockTicking = optimizedBlockTicking;
        saveConfig();
    }
    
    public boolean isReducedFogRendering() {
        return reducedFogRendering;
    }
    
    public void setReducedFogRendering(boolean reducedFogRendering) {
        this.reducedFogRendering = reducedFogRendering;
        saveConfig();
    }
    
    // Getters and setters for memory optimizations
    public boolean isMemoryOptimization() {
        return memoryOptimization;
    }
    
    public void setMemoryOptimization(boolean memoryOptimization) {
        this.memoryOptimization = memoryOptimization;
        saveConfig();
    }
    
    public boolean isTextureOptimization() {
        return textureOptimization;
    }
    
    public void setTextureOptimization(boolean textureOptimization) {
        this.textureOptimization = textureOptimization;
        saveConfig();
    }
    
    public boolean isResourceUnloading() {
        return resourceUnloading;
    }
    
    public void setResourceUnloading(boolean resourceUnloading) {
        this.resourceUnloading = resourceUnloading;
        saveConfig();
    }
    
    public int getResourceUnloadDelay() {
        return resourceUnloadDelay;
    }
    
    public void setResourceUnloadDelay(int resourceUnloadDelay) {
        this.resourceUnloadDelay = resourceUnloadDelay;
        saveConfig();
    }
    
    // Getters and setters for GUI optimizations
    public boolean isGuiOptimization() {
        return guiOptimization;
    }
    
    public void setGuiOptimization(boolean guiOptimization) {
        this.guiOptimization = guiOptimization;
        saveConfig();
    }
    
    public boolean isReducedGuiAnimations() {
        return reducedGuiAnimations;
    }
    
    public void setReducedGuiAnimations(boolean reducedGuiAnimations) {
        this.reducedGuiAnimations = reducedGuiAnimations;
        saveConfig();
    }
    
    public boolean isFastInventoryRendering() {
        return fastInventoryRendering;
    }
    
    public void setFastInventoryRendering(boolean fastInventoryRendering) {
        this.fastInventoryRendering = fastInventoryRendering;
        saveConfig();
    }
}