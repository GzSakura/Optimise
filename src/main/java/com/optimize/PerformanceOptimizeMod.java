package com.optimize;

import com.optimize.config.ModConfig;
import com.optimize.gui.PerformanceScreen;
import com.optimize.manager.PerformanceManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerformanceOptimizeMod implements ModInitializer, ClientModInitializer {
    public static final String MOD_ID = "performance-optimize";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    
    private static PerformanceManager performanceManager;
    private static KeyBinding openGuiKey;
    
    @Override
    public void onInitialize() {
        LOGGER.info("性能优化模组正在初始化...");
        
        // 初始化配置
        ModConfig.init();
        
        // 初始化性能管理器
        performanceManager = new PerformanceManager();
        performanceManager.initialize();
        
        LOGGER.info("性能优化模组初始化完成！");
    }
    
    @Override
    public void onInitializeClient() {
        LOGGER.info("性能优化模组客户端初始化...");
        
        // 注册按键绑定
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.performance-optimize.open_gui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_O,
            "category.performance-optimize.general"
        ));
        
        // 注册客户端事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openGuiKey.wasPressed() && client.player != null) {
                client.setScreen(new PerformanceScreen());
            }
        });
        
        if (performanceManager != null) {
            performanceManager.initializeClient();
        }
        
        LOGGER.info("性能优化模组客户端初始化完成！");
    }
    
    public static PerformanceManager getPerformanceManager() {
        return performanceManager;
    }
}