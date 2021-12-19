/*
 * Copyright (c) 2021.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.hacks;

import com.cullinan.cullinanclient.CullinanClient;
import com.cullinan.cullinanclient.SearchTags;
import com.cullinan.cullinanclient.event.CullinanEvents;
import com.cullinan.cullinanclient.events.TickListener;
import com.cullinan.cullinanclient.hack.Hack;
import com.cullinan.cullinanclient.hack.HackCategory;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.Vec3d;

@SearchTags({"Spider", "Climb"})
public class SpiderHack extends Hack implements TickListener {

    public SpiderHack() {
        super("Spider");
        setCategory(HackCategory.MOVEMENT);
        setColor(0x0003045e);
        setEnabled(true); // uncomment for testing purposes while there is no gui
        // TODO: Add speed option
    }

    @Override
    public void onTick() {
        ClientPlayerEntity player = CullinanClient.MINECRAFT.player;
        if (!player.horizontalCollision)
            return;

        Vec3d vel = player.getVelocity();
        if (vel.y >= 0.2)
            return;

        player.setVelocity(vel.x, 0.2, vel.z);
    }

    @Override
    protected void onEnable() {
        CullinanEvents.getInstance().add(TickListener.class, this);
    }

    @Override
    protected void onDisable() {
        CullinanEvents.getInstance().remove(TickListener.class, this);
    }
}
