/*
 * Copyright (c) 2021.
 *
 * This source code is subject to the terms of the GNU General Public
 * License, version 3. If a copy of the GPL was not distributed with this
 * file, You can obtain one at: https://www.gnu.org/licenses/gpl-3.0.txt
 */

package com.cullinan.cullinanclient.hacks;

import com.cullinan.cullinanclient.SearchTags;
import com.cullinan.cullinanclient.event.CullinanEvents;
import com.cullinan.cullinanclient.events.TickListener;
import com.cullinan.cullinanclient.hack.Hack;
import com.cullinan.cullinanclient.hack.HackCategory;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

@SearchTags({"no fall"})
public class NoFallHack extends Hack implements TickListener {

    public NoFallHack() {
        super("NoFall");
        setCategory(HackCategory.MOVEMENT);
        setEnabled(true); // uncomment for testing purposes while there is no gui
    }

    @Override
    public void onTick() {
        ClientPlayerEntity player = MC.player;
        if(player.fallDistance <= (player.isFallFlying() ? 1 : 2))
            return;

        if(player.isFallFlying() && player.isSneaking()
                && !(player.getVelocity().y < -0.5))
            return;

        player.networkHandler.sendPacket(new PlayerMoveC2SPacket.OnGroundOnly(true));
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
