/*
 * Copyright (c) 2021.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.hack;

import net.minecraft.client.MinecraftClient;

public class Hack {
    private final String name, description;
    private HackCategory category;
    private boolean enabled = false;

    protected static final MinecraftClient MC = MinecraftClient.getInstance();

    public Hack(String name) {
        this.name = name;
        this.description = "description.cullinan.hack." + name.toLowerCase();
    }

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getRenderDescription() {
        // TODO: Create some translation files to allow multiple languages
        return description;
    }

    public HackCategory getCategory() {
        return category;
    }

    protected final void setCategory(HackCategory category) {
        this.category = category;
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public final void setEnabled(boolean enabled) {
        if (this.enabled == enabled) return;

        this.enabled = enabled;

        if (enabled)
                onEnable();
        else
                onDisable();

        // TODO: Save state
    }

    public final String getAction() {
        return enabled ? "Disable" : "Enable";
    }

    public final void doAction() {
        setEnabled(!enabled);
    }

    protected void onEnable() {}
    protected void onDisable() {}
}
