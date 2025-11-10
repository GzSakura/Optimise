package com.gzsakura.optimise.client;

import com.gzsakura.optimise.OptimiseModClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.network.ClientPlayerEntity;

@Environment(EnvType.CLIENT)
public class AutoSprintManager {
    private static boolean autoSprintActive = false;
    
    public static void init() {
        // 注册客户端tick事件
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!OptimiseModClient.CONFIG.enableAutoSprint) {
                return;
            }
            
            MinecraftClient minecraft = MinecraftClient.getInstance();
            if (minecraft.player == null) {
                return;
            }
            
            ClientPlayerEntity player = minecraft.player;
            KeyBinding forwardKey = minecraft.options.forwardKey;
            
            // 如果按住W键且满足疾跑条件
            if (forwardKey.isPressed() && player.isOnGround() && !player.isSneaking() && 
                !player.isUsingItem() && !player.isTouchingWater() && !player.isInLava() &&
                player.getHungerManager().getFoodLevel() > 6) {
                
                player.setSprinting(true);
            }
        });
        
        OptimiseModClient.LOGGER.info("自动疾跑管理器已初始化 - 按住W键自动疾跑");
    }
    
    public static boolean isAutoSprintActive() {
        return autoSprintActive;
    }
    
    public static void setAutoSprintActive(boolean active) {
        autoSprintActive = active;
        if (!active && MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.setSprinting(false);
        }
    }
}