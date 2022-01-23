package com.cullinan.cullinanclient;

import com.cullinan.cullinanclient.command.CommandProcessor;
import com.cullinan.cullinanclient.event.CullinanEvents;
import com.cullinan.cullinanclient.events.ChatMessageListener;
import com.cullinan.cullinanclient.events.GUIRenderListener;
import com.cullinan.cullinanclient.gui.InGameHud;
import com.cullinan.cullinanclient.hack.HackList;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class CullinanClient implements ModInitializer {

    private static CullinanClient instance;
    public static final MinecraftClient MINECRAFT = MinecraftClient.getInstance();
    private InGameHud hud;
    private CommandProcessor commandProcessor;
    private boolean enabled = true;

    @Override
    public void onInitialize() {
        instance = this;
        HackList.getInstance(); //initialize hacklist

        hud = new InGameHud();
        CullinanEvents.getInstance().add(GUIRenderListener.class, hud);
        hud.hackListHud.updateState();

        commandProcessor = new CommandProcessor();
        CullinanEvents.getInstance().add(ChatMessageListener.class, commandProcessor);
    }

    public static CullinanClient getInstance() {
        return instance;
    }

    public InGameHud getHud() {
        return hud;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
