package com.takaranoao.donothitme;

import com.takaranoao.donothitme.data.DNHKeyType;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.List;

public class DNHKeyManager {
    private final DoNotHitMe mod;
    private final KeyMapping swingMainKey;
    private final KeyMapping swingOffKey;
    private final KeyMapping enableKey;

    DNHKeyManager(DoNotHitMe mod) {
        this.mod = mod;
        this.swingMainKey = new KeyMapping("key.donothitme.swing_main_hand", GLFW.GLFW_KEY_UNKNOWN, "key.category.donothitme");
        this.swingOffKey = new KeyMapping("key.donothitme.swing_off_hand", GLFW.GLFW_KEY_UNKNOWN, "key.category.donothitme");
        this.enableKey = new KeyMapping("key.donothitme.enable", GLFW.GLFW_KEY_UNKNOWN, "key.category.donothitme");
    }

    public List<KeyMapping> getKeyBindings() {
        return Arrays.asList(swingMainKey, swingOffKey, enableKey);
    }

    void onTick() {
        if (swingMainKey.consumeClick()) {
            mod.handleKeyPress(DNHKeyType.SWING_MAIN_HAND);
        }
        if (swingOffKey.consumeClick()) {
            mod.handleKeyPress(DNHKeyType.SWING_OFF_HAND);
        }
        if (enableKey.consumeClick()) {
            mod.handleKeyPress(DNHKeyType.ENABLE);
        }
    }
}
