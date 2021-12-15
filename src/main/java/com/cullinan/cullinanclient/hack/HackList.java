/*
 * Copyright (c) 2021.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.hack;

import com.cullinan.cullinanclient.hacks.NoFallHack;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;

public class HackList {

    public final NoFallHack noFallHack = new NoFallHack();

    private final TreeMap<String, Hack> hacks =
            new TreeMap<>(String::compareToIgnoreCase);
    private static HackList instance;

    public static HackList getInstance() {
        if(instance == null) instance = new HackList();
        return instance;
    }

    private HackList() {
        try
        {
            for(Field field : HackList.class.getDeclaredFields())
            {
                if(!field.getName().endsWith("Hack"))
                    continue;

                Hack hack = (Hack)field.get(this);
                hacks.put(hack.getName(), hack);
            }

        }catch(Exception e)
        {
            e.printStackTrace();

            String message = "Initializing Cullinan hacks";
            CrashReport report = CrashReport.create(e, message);
            throw new CrashException(report);
        }
    }

    public Hack getHackByName(String name) {
        return hacks.get(name);
    }

    public Collection<Hack> getAllHacks()
    {
        return Collections.unmodifiableCollection(hacks.values());
    }

}
