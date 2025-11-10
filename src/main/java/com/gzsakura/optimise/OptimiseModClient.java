package com.gzsakura.optimise;

import com.gzsakura.optimise.client.RenderOptimizer;
import com.gzsakura.optimise.client.keybind.OptimiseKeyBindings;
import com.gzsakura.optimise.config.OptimiseConfig;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptimiseModClient implements ClientModInitializer {
    public static final String MOD_ID = "optimise";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static OptimiseConfig CONFIG;

    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        LOGGER.info("╔════════════════════════════════════╗");
        LOGGER.info("║    Optimise Client Mod v0.1        ║");
        LOGGER.info("║        Author: GZ_Sakura           ║");
        LOGGER.info("║   Client-side Performance Mod      ║");
        LOGGER.info("╚════════════════════════════════════╝");
        
        // Load configuration
        CONFIG = OptimiseConfig.load();
        LOGGER.info("Client configuration loaded successfully!");
        
        // Apply client-side optimizations based on config
        if (CONFIG.enableRenderOptimizations) {
            LOGGER.info("Client render optimizations enabled!");
            RenderOptimizer.init();
        }
        if (CONFIG.enableMemoryOptimizations) {
            LOGGER.info("Client memory optimizations enabled!");
        }
        
        // Register key bindings
        OptimiseKeyBindings.register();
        
        LOGGER.info("Optimise client mod initialization completed!");
    }
}