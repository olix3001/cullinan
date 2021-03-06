/*
 * Copyright (c) 2021.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.hack;

import com.cullinan.cullinanclient.CullinanClient;
import com.cullinan.cullinanclient.command.Command;
import com.cullinan.cullinanclient.command.CommandList;
import com.cullinan.cullinanclient.gui.HackListHud;
import com.cullinan.cullinanclient.gui.InGameHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;

public class Hack {
    private final String name, description;
    private HackCategory category;
    private boolean enabled = false;
    private int color = 0x00ff0000;

    private final TreeMap<String, HackSetting> settings =
            new TreeMap<>(String::compareToIgnoreCase);

    protected static final MinecraftClient MC = MinecraftClient.getInstance();

    public Hack(String name) {
        this.name = name;
        this.description = "description.cullinan.hack." + name.toLowerCase();
    }

    public int getColor() { return color; }
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

    public void setColor(int color) {
        this.color = color;
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

        InGameHud hud = CullinanClient.getInstance().getHud();
        if (hud != null) hud.hackListHud.updateState(this);
        // TODO: Save state
    }

    public final String getAction() {
        return enabled ? "Disable" : "Enable";
    }

    public final void doAction() {
        setEnabled(!enabled);
    }

    public HackSetting getSettingByName(String name) {
        return settings.get(name);
    }
    public void addSetting(HackSetting setting) {
        settings.put(setting.getName(), setting);
    }
    public Collection<HackSetting> getAllSettings()
    {
        return Collections.unmodifiableCollection(settings.values());
    }

    protected void onEnable() {}
    protected void onDisable() {}
}
