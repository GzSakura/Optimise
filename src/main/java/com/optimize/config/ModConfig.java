package com.optimize.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.AutoConfig;

@Config(name = "performance-optimize")
public class ModConfig implements ConfigData {
    
    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.Tooltip
    public boolean enabled = true;
    
    @ConfigEntry.Category("rendering")
    @ConfigEntry.Gui.Tooltip
    public boolean optimizeRendering = true;
    
    @ConfigEntry.Category("rendering")
    @ConfigEntry.Gui.Tooltip
    public int maxParticles = 4000;
    
    @ConfigEntry.Category("rendering")
    @ConfigEntry.Gui.Tooltip
    public boolean reduceEntityRendering = true;
    
    @ConfigEntry.Category("rendering")
    @ConfigEntry.Gui.Tooltip
    public int entityRenderDistance = 64;
    
    @ConfigEntry.Category("chunk")
    @ConfigEntry.Gui.Tooltip
    public boolean optimizeChunkLoading = true;
    
    @ConfigEntry.Category("chunk")
    @ConfigEntry.Gui.Tooltip
    public int chunkLoadRadius = 8;
    
    @ConfigEntry.Category("chunk")
    @ConfigEntry.Gui.Tooltip
    public boolean lazyChunkRendering = true;
    
    @ConfigEntry.Category("entity")
    @ConfigEntry.Gui.Tooltip
    public boolean optimizeEntities = true;
    
    @ConfigEntry.Category("entity")
    @ConfigEntry.Gui.Tooltip
    public int maxEntitiesPerChunk = 32;
    
    @ConfigEntry.Category("memory")
    @ConfigEntry.Gui.Tooltip
    public boolean optimizeMemory = true;
    
    @ConfigEntry.Category("memory")
    @ConfigEntry.Gui.Tooltip
    public int gcThreshold = 80;
    
    @ConfigEntry.Category("debug")
    @ConfigEntry.Gui.Tooltip
    public boolean showDebugInfo = false;
    
    @ConfigEntry.Category("debug")
    @ConfigEntry.Gui.Tooltip
    public boolean logPerformance = false;
    
    @ConfigEntry.Category("advanced")
    @ConfigEntry.Gui.Tooltip
    public boolean aggressiveOptimizations = false;
    
    @ConfigEntry.Category("advanced")
    @ConfigEntry.Gui.Tooltip
    public boolean threadedRendering = false;
    
    public static ModConfig get() {
        return AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }
    
    public static void init() {
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
    }
    
    @Override
    public void validatePostLoad() {
        maxParticles = Math.max(100, Math.min(10000, maxParticles));
        entityRenderDistance = Math.max(16, Math.min(128, entityRenderDistance));
        chunkLoadRadius = Math.max(4, Math.min(16, chunkLoadRadius));
        maxEntitiesPerChunk = Math.max(8, Math.min(64, maxEntitiesPerChunk));
        gcThreshold = Math.max(50, Math.min(95, gcThreshold));
    }
}