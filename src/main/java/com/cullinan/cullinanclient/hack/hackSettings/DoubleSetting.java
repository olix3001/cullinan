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

public class DoubleSetting extends HackSetting {

    private double value;

    public DoubleSetting(String name, String description, Hack hack, double defaultValue) {
        super(name, description, DoubleSetting.class, hack);
        this.value = defaultValue;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
