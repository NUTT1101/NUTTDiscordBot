package com.github.nutt1101.discord_listener;

import java.awt.Color;

import com.github.nutt1101.Config;
import com.github.nutt1101.NUTTDiscordBot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class BotDisconnect implements EventListener{

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof DisconnectEvent) {
            JDA jda = NUTTDiscordBot.jda;
            
        }
        
    }
    
    
}
