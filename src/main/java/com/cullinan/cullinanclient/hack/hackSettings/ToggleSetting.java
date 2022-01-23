/*
 * Copyright (c) 2022.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.hack.hackSettings;

import com.cullinan.cullinanclient.hack.Hack;
import com.cullinan.cullinanclient.hack.HackSetting;

public class ToggleSetting extends HackSetting {

    private boolean toggled;

    public ToggleSetting(String name, String description, Hack hack, boolean defaultValue) {
        super(name, description, ToggleSetting.class, hack);
        this.toggled = defaultValue;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
}
