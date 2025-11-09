package com.example.optimise;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptimiseMod implements ModInitializer {
	public static final String MOD_ID = "optimise";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// Keybinding for opening the GUI
	public static KeyBinding openGuiKeybind;

	@Override
	public void onInitialize() {
		LOGGER.info("Optimise mod is initializing!");

		// Load config
		OptimiseConfig.loadConfig();

		// Register the keybinding
		openGuiKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.optimise.open_gui",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_K,
				"category.optimise.main"
		));

		// Register client tick event to check for key presses
		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openGuiKeybind.wasPressed()) {
				// Open the GUI when K is pressed
				client.setScreen(new OptimiseConfigScreen(client.currentScreen));
			}
		});

		LOGGER.info("Optimise mod has been initialized!");
	}
}