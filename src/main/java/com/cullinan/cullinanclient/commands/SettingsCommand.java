/*
 * Copyright (c) 2022.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.commands;

import com.cullinan.cullinanclient.command.Command;
import com.cullinan.cullinanclient.command.CommandResult;
import com.cullinan.cullinanclient.hack.Hack;
import com.cullinan.cullinanclient.hack.HackList;
import com.cullinan.cullinanclient.hack.HackSetting;
import com.cullinan.cullinanclient.hack.hackSettings.DoubleSetting;
import com.cullinan.cullinanclient.hack.hackSettings.ToggleSetting;

import java.util.Collection;
import java.util.Objects;

public class SettingsCommand extends Command {

    public SettingsCommand() {
        super("settings", "changes command settings");

    }

    @Override
    public CommandResult execute(String[] args) {
        if (args.length != 1 && args.length != 3)
            return new CommandResult(false, "This command takes one or three arguments");

        Hack hack = HackList.getInstance().getHackByName(args[0]);
        if (hack == null)
            return new CommandResult(false, "Specified hack do not exist");

        if (args.length == 1) {
            StringBuilder result = new StringBuilder();
            Collection<HackSetting> settings = hack.getAllSettings();
            for (HackSetting setting : settings) {
                result.append(setting.getName() + " (" + setting.getClass().getName() + ") " + setting.getDescription() + "\n");
            }
            return new CommandResult(true, result.toString());
        } else {
            HackSetting setting = hack.getSettingByName(args[1]);
            if (setting == null)
                return new CommandResult(false, "This setting do not exist in this command");

            if (setting.getSettingType() == ToggleSetting.class) {
                // Toggle setting
                if (!Objects.equals(args[2], "true") && !Objects.equals(args[2], "false"))
                    return new CommandResult(false, "This is a toggle settings so it needs to be \"true\" or \"false\"");
                ((ToggleSetting)setting).setToggled(args[2].equals("true"));
            } else if (setting.getSettingType() == DoubleSetting.class){
                // Double setting
                ((DoubleSetting)setting).setValue(Double.parseDouble(args[2]));
            }

            return new CommandResult(true, "Value of " + setting.getName() + " is now set to " + args[2]);
        }

    }
}
