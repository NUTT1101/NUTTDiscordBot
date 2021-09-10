package com.github.nutt1101.discord_listener;

import java.awt.Color;

import com.github.nutt1101.Config;
import com.github.nutt1101.NUTTDiscordBot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.EventListener;

public class BotReady implements EventListener{

    @Override
    public void onEvent(GenericEvent event) {
        if (event instanceof ReadyEvent) {
            JDA jda = NUTTDiscordBot.jda;
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor(Config.bootEmAuthor, Config.bootEmAuthorTextLink, Config.bootEmAuthorImageLink);
            embedBuilder.setColor(Color.decode(Config.bootEmHexColor));
            embedBuilder.setDescription(Config.bootEmDescription);
            jda.getTextChannelById(Config.channelID).sendMessage(embedBuilder.build()).queue();
            
        }
        
    }

    
}
