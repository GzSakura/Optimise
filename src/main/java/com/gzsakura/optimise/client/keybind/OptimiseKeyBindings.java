package com.gzsakura.optimise.client.keybind;

import com.gzsakura.optimise.OptimiseModClient;
import com.gzsakura.optimise.client.gui.OptimiseConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class OptimiseKeyBindings {
    private static final String CATEGORY = "key.categories.optimise";
    public static KeyBinding openConfigKey;
    
    public static void register() {
        // 注册K键绑定
        openConfigKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.optimise.open_config", // 翻译键
            InputUtil.Type.KEYSYM,      // 键盘输入类型
            GLFW.GLFW_KEY_K,            // K键
            CATEGORY                    // 分类
        ));
        
        // 注册按键事件监听
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (openConfigKey.wasPressed() && client.player != null) {
                // 打开配置界面
            MinecraftClient.getInstance().setScreen(new OptimiseConfigScreen(client.currentScreen));
            OptimiseModClient.LOGGER.info(Text.translatable("optimise.messages.gui_opened").getString());
            }
        });
        
        OptimiseModClient.LOGGER.info("Optimise mod按键绑定已注册");
    }
}