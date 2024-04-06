package com.takaranoao.donothitme;

import com.takaranoao.donothitme.data.DNHKeyType;
import net.minecraft.client.KeyMapping;
import org.apache.commons.compress.utils.Lists;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class DNHKeyManager {
    private final DoNotHitMe mod;
    private final List<DNHKey> keys = Lists.newArrayList();

    DNHKeyManager(DoNotHitMe mod) {
        this.mod = mod;
        register("swing_main_hand", GLFW.GLFW_KEY_UNKNOWN, DNHKeyType.SWING_MAIN_HAND);
        register("swing_off_hand", GLFW.GLFW_KEY_UNKNOWN, DNHKeyType.SWING_OFF_HAND);
        register("enable", GLFW.GLFW_KEY_UNKNOWN, DNHKeyType.ENABLE);
    }

    public void register(String name, int defaultKey, DNHKeyType keyType) {
        keys.add(new DNHKey(new KeyMapping("key.donothitme." + name, defaultKey, "key.category.donothitme"), keyType));
    }

    public List<KeyMapping> getKeyBindings() {
        return keys.stream().map(DNHKey::mapping).toList();
    }

    void onTick() {
        keys.forEach(key -> {
            while (key.mapping.consumeClick()) {
                mod.handleKeyPress(key.keyType);
            }
        });
    }

    record DNHKey(KeyMapping mapping, DNHKeyType keyType) {
    }
}
