/*
 * Copyright (c) 2022.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.hack;

public abstract class HackSetting {
    private String name;
    private String description;
    private Class<? extends HackSetting> settingType;

    public HackSetting(String name, String description, Class<? extends HackSetting> type, Hack hack) {
        this.name = name;
        this.description = description;
        this.settingType = type;
        hack.addSetting(this);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Class<? extends HackSetting> getSettingType() {
        return settingType;
    }
}
