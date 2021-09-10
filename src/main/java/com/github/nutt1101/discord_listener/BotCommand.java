package com.github.nutt1101.discord_listener;

import java.awt.Color;

import com.github.nutt1101.Config;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotCommand extends ListenerAdapter{
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!event.isFromGuild() || event.getAuthor().isBot()) return;

        if (event.getMessage().getContentRaw().equalsIgnoreCase("hi")) {
            event.getChannel().sendMessage("Hi! 你好! " + event.getMessage().getAuthor().getAsMention()).queue();;
        }
        
        if (event.getMessage().getContentRaw().equalsIgnoreCase("dayeh")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("點我進入大葉大學官網", Config.bootEmAuthorTextLink, Config.bootEmAuthorImageLink);
            embedBuilder.setColor(Color.decode(Config.bootEmHexColor));
            event.getChannel().sendMessage(embedBuilder.build()).queue();
        }

        if (event.getMessage().getContentRaw().equalsIgnoreCase("fresh")) {
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setAuthor("點我進入大葉大學新生專區", "http://fresh.dyu.edu.tw/", Config.bootEmAuthorImageLink);
            embedBuilder.setColor(Color.decode(Config.bootEmHexColor));
            event.getChannel().sendMessage(embedBuilder.build()).queue();;
        }
    }
    
    
}
