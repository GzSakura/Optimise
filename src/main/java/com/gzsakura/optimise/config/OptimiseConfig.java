package com.gzsakura.optimise.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gzsakura.optimise.OptimiseModClient;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class OptimiseConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File("config/optimise.json");
    
    // Client-side configuration options
    public boolean enableMemoryOptimizations = true;
    public boolean enableRenderOptimizations = true;
    public int maxChunkRenderDistance = 16;
    public boolean enableEntityCulling = true;
    public boolean disableAllParticles = false;
    public boolean enableSmoothLighting = true;
    public int particleQuality = 2; // 0=Minimal, 1=Decreased, 2=All
    
    // Auto sprint configuration// 自动疾跑配置
    public boolean enableAutoSprint = false;
    
    public static OptimiseConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                return GSON.fromJson(reader, OptimiseConfig.class);
            } catch (IOException e) {
                OptimiseModClient.LOGGER.error("Failed to load config, using defaults", e);
            }
        }
        
        OptimiseConfig config = new OptimiseConfig();
        config.save();
        return config;
    }
    
    public void save() {
        CONFIG_FILE.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            OptimiseModClient.LOGGER.error("Failed to save config", e);
        }
    }
}