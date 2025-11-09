package com.example.optimise.mixin;

import com.example.optimise.OptimiseConfig;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Inject(method = "render", at = @At("RETURN"))
    private void onRender(CallbackInfo ci) {
        if (!OptimiseConfig.getInstance().isShowFps()) {
            return;
        }
        
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;
        
        // Display FPS in the corner of the screen
        int fps = client.getCurrentFps();
        String fpsText = "FPS: " + fps;
        
        // Position the text in the top right corner
        int x = client.getWindow().getScaledWidth() - textRenderer.getWidth(fpsText) - 2;
        int y = 2;
        
        // Render the FPS text
        textRenderer.draw(fpsText, x, y, 0xFFFFFF);
    }
}